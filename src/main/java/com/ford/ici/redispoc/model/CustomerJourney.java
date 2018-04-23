package com.ford.ici.redispoc.model;

import java.io.Serializable;

public class CustomerJourney implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String value;
    public CustomerJourney(String id, String value){
        this.id=id;
        this.value=value;
        
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return "Movie{" + "Customer id=" +id + '\''  + ", Customer value =" + value + "}";
    }

}
