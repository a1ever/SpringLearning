package ru.verbitskiy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.verbitskiy.Operations.OwnerDataService;
import ru.verbitskiy.models.OwnerModel;

import java.util.UUID;

@Component
@EnableKafka
public class KafkaConsumer {
    ObjectMapper mapper;
    OwnerDataService ownerDataService;

    @Autowired
    public KafkaConsumer(OwnerDataService ownerDataService) {
        mapper = new ObjectMapper();
        this.ownerDataService = ownerDataService;
    }

    @SneakyThrows
    @KafkaListener(groupId="owner", topics = "owner.send")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) {
        String key = consumerRecord.key();
        String req = mapper.readTree(consumerRecord.value().toString()).toString();
        String response = "";
        switch (key) {
            case "add":
                ownerDataService.add(mapper.readValue(req, OwnerModel.class));
                break;
            case "update":
                ownerDataService.update(mapper.readValue(req, OwnerModel.class));
                break;
            case "getByID":
                response = mapper.writeValueAsString(ownerDataService.getByID(mapper.readValue(req, UUID.class)).orElseThrow());
                break;
            case "delete":
                ownerDataService.delete(mapper.readValue(req, OwnerModel.class));
                break;
            case "getAll":
                response = mapper.writeValueAsString(ownerDataService.getAll());
                break;
        }
        return MessageBuilder.withPayload(response).setHeader(KafkaHeaders.KEY, key).build();
    }
}
