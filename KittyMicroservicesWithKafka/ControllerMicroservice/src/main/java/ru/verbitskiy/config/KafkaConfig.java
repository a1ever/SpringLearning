    package ru.verbitskiy.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
    import org.springframework.kafka.core.ProducerFactory;
    import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
    import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

    import java.time.Duration;

    @Configuration
    public class KafkaConfig {
        @Bean
        public ConcurrentMessageListenerContainer<String, Object> replyKittyContainer(
                ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
            ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer("kitty.reply");
            repliesContainer.getContainerProperties().setGroupId("kitty");
            repliesContainer.setAutoStartup(false);
            return repliesContainer;
        }

        @Bean
        public ReplyingKafkaTemplate<String, Object, Object> replyingKittyTemplate(
                ProducerFactory<String, Object> pf,
                ConcurrentMessageListenerContainer<String, Object> replyKittyContainer) {
            ReplyingKafkaTemplate<String, Object, Object> replyTemplate = new ReplyingKafkaTemplate<>(pf, replyKittyContainer);
            replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(10));
            replyTemplate.setSharedReplyTopic(true);
            return replyTemplate;
        }

        @Bean
        public ConcurrentMessageListenerContainer<String, Object> replyOwnerContainer(
                ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
            ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer("owner.reply");
            repliesContainer.getContainerProperties().setGroupId("owner");
            repliesContainer.setAutoStartup(false);
            return repliesContainer;
        }

        @Bean
        public ReplyingKafkaTemplate<String, Object, Object> replyingOwnerTemplate(
                ProducerFactory<String, Object> pf,
                ConcurrentMessageListenerContainer<String, Object> replyOwnerContainer) {
            ReplyingKafkaTemplate<String, Object, Object> replyTemplate = new ReplyingKafkaTemplate<>(pf, replyOwnerContainer);
            replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(10));
            replyTemplate.setSharedReplyTopic(true);
            return replyTemplate;
        }
    }
