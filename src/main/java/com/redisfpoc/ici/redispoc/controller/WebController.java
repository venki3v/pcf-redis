package com.redisfpoc.ici.redispoc.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import com.redisfpoc.ici.redispoc.model.CustomerJourney;
import com.redisfpoc.ici.redispoc.repository.RedisRepository;

@Controller
@RequestMapping("/")
public class WebController {
    
    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /*@RequestMapping("/keys")
    public @ResponseBody Map<Object, Object> keys() {
        return redisRepository.findAllCustomerJourneys();
    }*/
	@RequestMapping("/findkey/{keyID}")
    public @ResponseBody
    List<CustomerJourney> findkeyID(HttpServletRequest request) {
		System.out.println("retrieval start="+System.currentTimeMillis());
		String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
		//System.out.println("keyid="+keyID);
		String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		System.out.println("keyid="+keyID);
        return redisRepository.findCustomerJourney(keyID);
    }
	/*@RequestMapping("/search/{keyID}")
    public @ResponseBody Map findwildkeys(HttpServletRequest request) {
        System.out.println("retrieval start in controller="+System.currentTimeMillis());
        String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
        //System.out.println("keyid="+keyID);
        String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		//System.out.println("retrieval wildkey start in controller="+System.currentTimeMillis());
		//String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
		//System.out.println("keyid="+keyID);
		//String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		//System.out.println("keyid="+keyID);
        return redisRepository.findKeys(keyID);
    }*/

  /*  @RequestMapping("/values")
    public @ResponseBody Map<String, String> findAll() {
        Map<Object, Object> aa = redisRepository.findAllCustomerJourneys();
        Map<String, String> map = new HashMap<String, String>();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, aa.get(key).toString());
        }
        return map;
    }*/

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(
        @RequestParam String id,
        @RequestParam String value,
        @RequestParam String key ) {

        System.out.println("iddfdf="+id);
        System.out.println("value="+value);
        System.out.println("custid="+key);
        CustomerJourney custJourney = new CustomerJourney(id, value);
        
        redisRepository.add(key,custJourney);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/bulkAdd", method = RequestMethod.GET)
    public ResponseEntity<String> addbulk() {
        for (int x = 10003; x <= 100002; x++) {
            //System.out.println("in outerloop="+x);
            String KEY = "CUST"+x;
            List<CustomerJourney> custList = new ArrayList() ;
            for (int y = 1; y <= 2; y++) {
                //System.out.println("in inner loop="+y);
                String id = "CUSTID" + x + "INTR" + "00" + y + System.currentTimeMillis();
                //carid = carid+1;
                Random r = new Random();
                int Low = 20;
                int High = 40;
                int Result = r.nextInt(High - Low) + Low;
                String value = "tirepressure:" + Result + ",carid:car00" + x;
                CustomerJourney cust = new CustomerJourney(id, value);
                custList.add(cust);
                //System.out.println("test size="+custList.size());
            }

            redisRepository.bulkAdd(KEY, custList);
            custList = null;
        }

       /* System.out.println("map size="+custMap.size());
        for (Map.Entry<String, List> entry : custMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            redisRepository.bulkAdd(entry.getKey(), (List) entry.getValue());
        }*/

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisRepository.delete(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @RequestMapping(value = "/memInfo", method = RequestMethod.GET)
    public @ResponseBody  String memInfo() {
       System.out.println(redisRepository.memoryInfo().toString());
        return redisRepository.memoryInfo().toString();

    }
}
