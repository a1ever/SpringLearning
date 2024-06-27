package ru.verbitskiy.Operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.models.KittyModel;
import ru.verbitskiy.services.KittyKafkaProducer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KittyDataServiceImpl implements KittyDataService {
    @Autowired
    KittyKafkaProducer kittyKafkaProducer;

    ObjectMapper mapper;

    public KittyDataServiceImpl() {
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public void add(KittyModel kittyModel) {
        kittyKafkaProducer.kafkaRequestReply("add", mapper.writeValueAsString(kittyModel)).toString();
    }

    @SneakyThrows
    @Override
    public void update(KittyModel kittyModel) {
        kittyKafkaProducer.kafkaRequestReply("update", mapper.writeValueAsString(kittyModel)).toString();
    }

    @SneakyThrows
    @Override
    public Optional<KittyModel> getByID(UUID id)
    {
        var send = kittyKafkaProducer.kafkaRequestReply("getByID", mapper.writeValueAsString(id)).toString();
        return Optional.of(mapper.readValue(send, KittyModel.class));
    }

    @SneakyThrows
    @Override
    public void delete(KittyModel kittyModel) {
        kittyKafkaProducer.kafkaRequestReply("delete", mapper.writeValueAsString(kittyModel)).toString();
    }

    @SneakyThrows
    @Override
    public List<KittyModel> getAll() {
        var send = kittyKafkaProducer.kafkaRequestReply("getAll", "").toString();
        return mapper.readValue(send, new TypeReference<List<KittyModel>>(){});
    }
}
