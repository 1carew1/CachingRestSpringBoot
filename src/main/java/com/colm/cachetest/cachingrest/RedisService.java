package com.colm.cachetest.cachingrest;


import com.colm.cachetest.cachingrest.redis.RedisReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private ApplicationContext appContext;

    @Bean
    StringRedisTemplate template (RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    RedisMessageListenerContainer container (RedisConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter (RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    RedisReceiver receiver (CountDownLatch latch) {
        return new RedisReceiver(latch);
    }

    @Bean
    CountDownLatch latch () {
        return new CountDownLatch(1);
    }

    public void sendMessage (String messageToSend) {
        StringRedisTemplate template = appContext.getBean(StringRedisTemplate.class);
        CountDownLatch latch = appContext.getBean(CountDownLatch.class);
        log.info("Sending message...");
        template.convertAndSend("chat", messageToSend);
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Error sending Message", e);
        }
    }

}
