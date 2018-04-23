package com.ford.ici.redispoc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;
import com.ford.ici.redispoc.config.RedisConfig;

import com.ford.ici.redispoc.model.CustomerJourney;

import java.util.*;
import javax.annotation.PostConstruct;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "CustomerJourney";
    
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    
    public void add(final CustomerJourney customerJourney) {
        hashOperations.put(KEY, customerJourney.getId(), customerJourney.getValue());
    }

    public void bulkAdd(Map H) {
        hashOperations.putAll(KEY,H);
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }
    
    public CustomerJourney findCustomerJourney(final String id){
        String hv = (hashOperations.get(KEY, id)).toString();
		System.out.println("retrieval complete="+System.currentTimeMillis());
		CustomerJourney mv = new CustomerJourney(id,hv);
		return mv;
    }
	public Map findKeys(String id){
		//Set<String> redisKeys = redisTemplate.keys("he*");
       // System.out.println("inside find keys8");
        /*ScanOptions options = ScanOptions.scanOptions().match("he").count(5).build();
        //System.out.println(options.toOptionString());



		// Store the keys in a cursor
       Cursor<Map.Entry<byte[], byte[]>> entries = hashOperations.scan(KEY.getBytes(), options);
       // Cursor<Map.Entry<byte[], byte[]>> entries = jd.getConnection().hScan(KEY.getBytes(), options);
        System.out.println("entrie13"+entries);
        List<String> result = new ArrayList<String>();
        if(entries!=null){
            System.out.println("notnull");
            while(entries.hasNext()){
                Map.Entry<byte[], byte[]> entry = entries.next();
                byte[] actualValue = entry.getValue();
                System.out.println("entries="+actualValue);
                result.add(new String(actualValue));
            }
        }*/
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
		/*List<String> keysList = new ArrayList<>();
		Iterator<String> it = redisKeys.iterator();
		while (it.hasNext()) {
			   String data = it.next();
			   System.out.println ("keys="+data);
			   keysList.add(data);
		}
		return keysList;*/
        
    }
    
    public Map<Object, Object> findAllCustomerJourneys(){
        return hashOperations.entries(KEY);
    }

  
}
