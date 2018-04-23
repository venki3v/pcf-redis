package com.ford.ici.redispoc.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;

import com.ford.ici.redispoc.model.CustomerJourney;
import com.ford.ici.redispoc.repository.RedisRepository;

@Controller
@RequestMapping("/")
public class WebController {
    
    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/keys")
    public @ResponseBody Map<Object, Object> keys() {
        return redisRepository.findAllCustomerJourneys();
    }
	
	@RequestMapping("/findkey/{keyID}")
    public @ResponseBody CustomerJourney findkeyID(HttpServletRequest request) {
		System.out.println("retrieval start="+System.currentTimeMillis());
		String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
		//System.out.println("keyid="+keyID);
		String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		System.out.println("keyid="+keyID);
        return redisRepository.findCustomerJourney(keyID);
    }
	@RequestMapping("/search/{keyID}")
    public @ResponseBody Map findwildkeys(HttpServletRequest request) {
        System.out.println("retrieval start in controller="+System.currentTimeMillis());
        String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
        //System.out.println("keyid="+keyID);
        String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		System.out.println("retrieval wildkey start in controller="+System.currentTimeMillis());
		//String requesturi =  new UrlPathHelper().getPathWithinApplication(request);
		//System.out.println("keyid="+keyID);
		//String keyID = requesturi.substring(requesturi.lastIndexOf("/") + 1);
		//System.out.println("keyid="+keyID);
        return redisRepository.findKeys(keyID);
    }

    @RequestMapping("/values")
    public @ResponseBody Map<String, String> findAll() {
        Map<Object, Object> aa = redisRepository.findAllCustomerJourneys();
        Map<String, String> map = new HashMap<String, String>();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, aa.get(key).toString());
        }
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(
        @RequestParam String key,
        @RequestParam String value) {
        
        CustomerJourney movie = new CustomerJourney(key, value);
        
        redisRepository.add(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisRepository.delete(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
