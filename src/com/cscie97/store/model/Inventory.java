/* *
 * Gerald Arocena
 * CSCI E-97
 * Professor: Eric Gieseke
 * Assignment 2 
 */

package com.cscie97.store.model;

public class Inventory
{
    /* API Variables */
    
    private String inventoryId;
    private String location;
    private Integer capacity;
    private Integer count;
    private String productId;
    
    /* Constructor */
    
    public Inventory(String id, String location, Integer capacity, Integer count, String productId)
    {
        inventoryId = id;
        this.location = location;
        this.capacity = capacity;
        this.count = count;
        this.productId = productId;
    }
    
    /* API Methods */
    
    public void updateCount(Integer updateAmount)
    {
        count += updateAmount;
    }

    /* Getters and Setters */
    
    public String getInventoryId()
    {
        return inventoryId;
    }    

    public String getLocation() 
    {
        return location;
    }   
   

    public Integer getCapacity() 
    {
        return capacity;
    }   

    public Integer getCount() 
    {
        return count;
    }   

    public String getProductId() 
    {
        return productId;
    }   
}
