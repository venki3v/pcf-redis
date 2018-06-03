package com.redisfpoc.ici.redispoc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import com.redisfpoc.ici.redispoc.model.CustomerJourney;

import java.util.*;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    //private static final String KEY = "CustomerJourney";
    

    

    @Autowired
    private RedisTemplate<String, CustomerJourney> redisTemplate;

   // @Autowired
   // private  JedisConnectionFactory jedisConnectionFactory;


    public void add(String key, final CustomerJourney customerJourney) {
       // System.out.println("inside repo add");
        redisTemplate.opsForList().leftPush(key,customerJourney);
       // System.out.println("after repo add");
    }

    public void bulkAdd(String key, List H) {
       // System.out.println("list size="+H.size());
        redisTemplate.opsForList().leftPushAll(key, H);
    }

   /* public void delete(final String id) {
        listOperations.;
    }*/
    
    public List<CustomerJourney> findCustomerJourney(String key){
        long k =  redisTemplate.opsForList().size(key);
        List<CustomerJourney> custList =  redisTemplate.opsForList().range(key,0,k);
        System.out.println("retrieval end="+System.currentTimeMillis());
        return custList;
    }
	/*public Map findKeys(String id){

        System.out.println("key retrieval start in repo="+System.currentTimeMillis());
        Map<Object, Object> custMap = hashOperations.entries(KEY);

        //System.out.println("entrie15"+custMap);
        Map<Object, Object> result   = new HashMap<Object, Object>();
        if(custMap!=null){
            for (Map.Entry<Object, Object> entry : custMap.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if (entry.getKey().toString().startsWith((id))) {
                    final Object put = result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        System.out.println("key retrieval complete in repo="+System.currentTimeMillis());
        custMap = null;
        return result;

        
    }*/
    
    /*public Map<Object, Object> findAllCustomerJourneys(){
        return hashOperations.entries(KEY);
    }*/

    public String memoryInfo() {
        // System.out.println("list size="+H.size());
        Long   k = redisTemplate.getConnectionFactory().getConnection().dbSize();
        System.out.println ("total number of keys="+k);
       //RedisConnection jd =jedisConnectionFactory.getConnection();

        String response = redisTemplate.getConnectionFactory().getConnection().info().toString()+k;
       // jd.close();
        return response;
    }

  
}
