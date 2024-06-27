package ru.verbitskiy.services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KittyKafkaProducer implements KafkaProducer {
    private ReplyingKafkaTemplate<String, Object, Object> catReplyingTemplate;

    @Autowired
    public void setCatReplyingTemplate(/*@Qualifier("replyingKittyTemplate") */ReplyingKafkaTemplate<String, Object, Object> replyingKittyTemplate) {
        this.catReplyingTemplate = replyingKittyTemplate;
    }

    public Object kafkaRequestReply(String key, Object request) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>("kitty.send", key, request);
        RequestReplyFuture<String, Object, Object> replyFuture = catReplyingTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture()
                .get(100, TimeUnit.SECONDS);
        return replyFuture.get(100, TimeUnit.SECONDS).value();
    }
}
