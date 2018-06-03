package com.redisfpoc.ici.redispoc.config;

import com.redisfpoc.ici.redispoc.model.CustomerJourney;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.redisfpoc.ici.redispoc.queue.MessagePublisher;
import com.redisfpoc.ici.redispoc.queue.MessagePublisherImpl;
import com.redisfpoc.ici.redispoc.queue.MessageSubscriber;

@Configuration
@ComponentScan("com.redisfpoc.ici.redispoc")
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, CustomerJourney> redisTemplate() {
        final RedisTemplate<String, CustomerJourney> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        //template.setValueSerializer(new GenericToStringSerializer<CustomerJourney>(CustomerJourney.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
}