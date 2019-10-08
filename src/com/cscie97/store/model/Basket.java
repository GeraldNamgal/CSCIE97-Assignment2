package com.cscie97.store.model;

import java.util.LinkedHashMap;

public class Basket
{
    /* API Variables */
    
    private String id;
    private LinkedHashMap<String, Integer> basketItems;    
    
    /* Constructor */
    
    public Basket(String customerId)
    {
        this.id = customerId;
        basketItems = new LinkedHashMap<String, Integer>();
    }  
    
    /* *
     * Getters and Setters
     */   
    
    public String getId() 
    {
        return id;
    }
    
    public LinkedHashMap<String, Integer> getBasketItems() 
    {
        return basketItems;
    }   
}
