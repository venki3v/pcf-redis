package com.redisfpoc.ici.redispoc.repository;

import java.util.Map;
import java.util.List;
import java.util.Properties;

import com.redisfpoc.ici.redispoc.model.CustomerJourney;

public interface RedisRepository {

    /**
     * Return all movies
     */
   // Map<Object, Object> findAllCustomerJourneys();

    /**
     * Add key-value pair to Redis.
     */
    void bulkAdd(String k,List customerJourney);

    /**

    /**
     * Add key-value pair to Redis.
     */
    void add(String k,CustomerJourney customerJourney);

    /**
     * Delete a key-value pair in Redis.
     */
    //void delete(String id);
    
    /**
     * find a movie
     */
    List<CustomerJourney> findCustomerJourney(String id);
	
	//Map findKeys(String id);

     String memoryInfo() ;
    
}

