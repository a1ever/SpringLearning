package ru.verbitskiy.services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OwnerKafkaProducer implements KafkaProducer {
    private final ReplyingKafkaTemplate<String, Object, Object> ownerReplyingTemplate;

    public OwnerKafkaProducer(ReplyingKafkaTemplate<String, Object, Object> replyingOwnerTemplate) {
        this.ownerReplyingTemplate = replyingOwnerTemplate;
    }

    public Object kafkaRequestReply(String key, Object request) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner.send", key, request);
        RequestReplyFuture<String, Object, Object> replyFuture = ownerReplyingTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture()
                .get(100, TimeUnit.SECONDS);
        return replyFuture.get(100, TimeUnit.SECONDS).value();
    }
}
