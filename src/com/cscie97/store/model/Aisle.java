package com.cscie97.store.model;

import java.util.LinkedHashMap;

public class Aisle
{       
    /* API Variables */
    
    enum Location 
    { 
        floor, storeroom; 
    }
    
    private String number;
    private String name;
    private String description;
    private Location location;
    private LinkedHashMap<String, Shelf> shelves;
    
    /* Constructor */
    
    public Aisle(String number, String name, String description, Location location)
    {
        this.number = number;
        this.name = name;
        this.description = description;
        this.location = location;
        shelves = new LinkedHashMap<String, Shelf>();
    }
    
    /* *
     * Utility Methods
     */
 
    // Checks a string if it's a Location enum
    public static boolean containsLocEnum(String testString)
    {
        for (Location location : Location.values())
        {
            if (location.name().equals(testString))
            {
                return true;
            }
        }

        return false;
    }

    /* *
     * Getters and Setters
     */
    
    public String getNumber()
    {
        return number;
    }

    public String getName() 
    {
        return name;
    }   

    public String getDescription() 
    {
        return description;
    }   

    public Location getLocation() 
    {
        return location;
    }    

    public LinkedHashMap<String, Shelf> getShelves() 
    {
        return shelves;
    }    
}
