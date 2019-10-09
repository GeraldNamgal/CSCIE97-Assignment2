package com.cscie97.store.model;

import java.util.LinkedHashMap;

public class Store
{
    /* API Variables */
    
    private String id;
    private String name;
    private String address;
    private LinkedHashMap<String, Customer> customers;
    private LinkedHashMap<String, Aisle> aisles;    
    private LinkedHashMap<String, Inventory> inventories;
    private LinkedHashMap<String, Sensor> devices;   
    
    /* Constructor */
    
    public Store(String id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        customers = new LinkedHashMap<String, Customer>();
        aisles = new LinkedHashMap<String, Aisle>();
        inventories = new LinkedHashMap<String, Inventory>();
        devices = new LinkedHashMap<String, Sensor>();                
    }

    /* *
     * Getters and Setters
     */
    
    public String getId()
    {
        return id;
    }    

    public String getName()
    {
        return name;
    }    

    public String getAddress()
    {
        return address;
    }   

    public LinkedHashMap<String, Customer> getCustomers()
    {
        return customers;
    }    

    public LinkedHashMap<String, Aisle> getAisles()
    {
        return aisles;
    }   

    public LinkedHashMap<String, Inventory> getInventories()
    {
        return inventories;
    }    

    public LinkedHashMap<String, Sensor> getDevices()
    {
        return devices;
    }  
}
