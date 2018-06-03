package com.redisfpoc.ici.redispoc.queue;

import com.redisfpoc.ici.redispoc.model.CustomerJourney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisherImpl implements MessagePublisher {
    
    @Autowired
    private RedisTemplate<String, CustomerJourney> redisTemplate;
    @Autowired
    private ChannelTopic topic;


    public MessagePublisherImpl(final RedisTemplate<String, CustomerJourney> redisTemplate, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
