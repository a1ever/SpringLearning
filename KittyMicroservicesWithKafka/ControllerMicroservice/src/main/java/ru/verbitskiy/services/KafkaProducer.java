package ru.verbitskiy.services;

public interface KafkaProducer {
    Object kafkaRequestReply(String key, Object request) throws Exception;
}
