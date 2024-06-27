package ru.verbitskiy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.verbitskiy.Operations.KittyDataService;
import ru.verbitskiy.models.KittyModel;

import java.util.UUID;

@Component
public class KafkaConsumer {
    ObjectMapper mapper;
    KittyDataService ownerDataService;

    @Autowired
    public KafkaConsumer(KittyDataService kittyDataService) {
        mapper = new ObjectMapper();
        this.ownerDataService = kittyDataService;
    }

    @SneakyThrows
    @KafkaListener(groupId="kitty", topics = "kitty.send")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) {
        String key = consumerRecord.key();
        String req = mapper.readTree(consumerRecord.value().toString()).toString();

        String response = "";
        switch (key) {
            case "add":
                ownerDataService.add(mapper.readValue(req, KittyModel.class));
                break;
            case "update":
                ownerDataService.update(mapper.readValue(req, KittyModel.class));
                break;
            case "getByID":
                response = mapper.writeValueAsString(ownerDataService.getByID(mapper.readValue(req, UUID.class)).orElseThrow());
                break;
            case "delete":
                ownerDataService.delete(mapper.readValue(req, KittyModel.class));
                break;
            case "getAll":
                response = mapper.writeValueAsString(ownerDataService.getAll());
                break;
        }
        return MessageBuilder.withPayload(response).setHeader(KafkaHeaders.KEY, key).build();
    }
}
