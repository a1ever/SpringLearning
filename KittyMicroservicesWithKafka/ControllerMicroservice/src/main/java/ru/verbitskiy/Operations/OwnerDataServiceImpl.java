package ru.verbitskiy.Operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.models.OwnerModel;
import ru.verbitskiy.services.OwnerKafkaProducer;

import java.util.List;
import java.util.UUID;

@Service
public class OwnerDataServiceImpl implements OwnerDataService {
    @Autowired
    OwnerKafkaProducer ownerKafkaProducer;

    ObjectMapper mapper;

    public OwnerDataServiceImpl() {
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public void add(OwnerModel model) {
        ownerKafkaProducer.kafkaRequestReply("add", mapper.writeValueAsString(model)).toString();
    }

    @SneakyThrows
    @Override
    public void update(OwnerModel model) {
        ownerKafkaProducer.kafkaRequestReply("update", mapper.writeValueAsString(model)).toString();
    }

    @SneakyThrows
    @Override
    public OwnerModel getByID(UUID id) {
        String send = ownerKafkaProducer.kafkaRequestReply("getByID", mapper.writeValueAsString(id)).toString();

        OwnerModel model = mapper.readValue(send, OwnerModel.class);

        return model;
    }

    @SneakyThrows
    @Override
    public void delete(OwnerModel model) {
        ownerKafkaProducer.kafkaRequestReply("delete", mapper.writeValueAsString(model)).toString();
    }

    @SneakyThrows
    @Override
    public List<OwnerModel> getAll() {
        var send = ownerKafkaProducer.kafkaRequestReply("getAll", "").toString();
        return mapper.readValue(send, new TypeReference<List<OwnerModel>>(){});
    }
}
