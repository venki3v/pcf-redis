package com.ford.ici.redispoc.repository;

import java.util.Map;
import java.util.Set;
import java.util.List;

import com.ford.ici.redispoc.model.CustomerJourney;

public interface RedisRepository {

    /**
     * Return all movies
     */
    Map<Object, Object> findAllCustomerJourneys();

    /**
     * Add key-value pair to Redis.
     */
    void bulkAdd(Map customerJourney);

    /**

    /**
     * Add key-value pair to Redis.
     */
    void add(CustomerJourney customerJourney);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String id);
    
    /**
     * find a movie
     */
    CustomerJourney findCustomerJourney(String id);
	
	Map findKeys(String id);
    
}

