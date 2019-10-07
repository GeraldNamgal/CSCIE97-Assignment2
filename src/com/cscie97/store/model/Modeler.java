package com.cscie97.store.model;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Modeler
{
    /* API Variables */
	
    private LinkedHashMap<String, Store> stores;
    private LinkedHashMap<String, Product> products;	
    private LinkedHashMap<String, Customer> customers;
	
    /* Constructor */
	
    public Modeler()
    {
        stores = new LinkedHashMap<String, Store>();
        products = new LinkedHashMap<String, Product>();
        customers = new LinkedHashMap<String, Customer>();
    }
    
    /* *
     * API Methods
     */ 
    
    public Store defineStore(String id, String name, String address)
    {
        // Check that store id is unique
        if (stores.containsKey(id))
        {
            try
            {
                throw new ModelerException("define store", "store id already exists; store not created");
            }
            
            catch (ModelerException duplicateIdException)
            {
                System.out.println();
                System.out.print(duplicateIdException.getMessage());      
                return null;
            }
        }
        
        Store store = new Store(id, name, address);
        
        // Add store to store list 
        if (store != null)
            stores.put(store.getId(), store);           
        
        return stores.get(store.getId());
    } 
    
    public void showStore(String storeId)
    {
        // TODO       
        
        // Get store
        Store store = stores.get(storeId);
        
        // Check that store exists
        if (store == null)
        {
            try
            {
                throw new ModelerException("show store", "store not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        /* Gather and print store's information */
        
        // TODO: Initialize string of customer information for everyone currently in the store
        LinkedHashMap<String, Customer> storeCustomers = store.getCustomers();
        String customersString = "";
        int counter = 1;
        
        // If there are customers
        if (storeCustomers.size() != 0)
        {
            Customer customer;             
            for (Entry<String, Customer> customerEntry : storeCustomers.entrySet())
            {
                customer = customerEntry.getValue();
    
                customersString += "\n    " + counter + ".)";
    
                counter++;
            }
        }
        
        // If there are no customers
        else
            customersString += "\n    None";
        
        // TODO: Initialize string of store's aisle information
        LinkedHashMap<String, Aisle> aisles = store.getAisles();
        String aislesString = "";
        counter = 1;
        
        // If there are aisles
        if (aisles.size() != 0)
        {
            Aisle aisle;                
            for (Entry<String, Aisle> aisleEntry : aisles.entrySet())
            {
                aisle = aisleEntry.getValue();
    
                aislesString += "\n    " + counter + ".)" + " number = " + aisle.getNumber() + "; name = " + aisle.getName()
                        + "; description = " + aisle.getDescription() + "; location = " + aisle.getLocation();
    
                counter++;
            }
        }
        
        // If there are no aisles
        else
            aislesString += "\n    None";
        
        // Combine strings together with other information included
        String string;
        string = "\nStore \"" + store.getId() + "\" information --\n" + " - name = "
                + store.getName() + "\n - address = " + store.getAddress() + "\n - customers ="
                + customersString + "\n - aisles =" + aislesString + "\n";           

        // Print string to stdout
        System.out.print(string);                    
    }
    
    public Aisle defineAisle(String storeAisleLoc, String name, String description, String location)
    {
        // Split storeAisleLoc on colon
        String[] splitLoc = storeAisleLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        
        // Check that store exists
        if (stores.containsKey(storeId) == false)
        {
            try
            {
                throw new ModelerException("define aisle", "store not found; aisle not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that aisle number is unique
        if (stores.get(storeId).getAisles().containsKey(aisleNumber))
        {
            try
            {
                throw new ModelerException("define aisle", "aisle number already exists; aisle not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that location input is valid enum
        if (!Aisle.containsLocEnum(location))
        {
            try
            {
                throw new ModelerException("define aisle", "location (case-sensitive) not found; aisle not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }      
        
        // Change location string to an enum
        Aisle.Location locationEnum = Aisle.Location.valueOf(location);
        
        Aisle aisle = new Aisle(aisleNumber, name, description, locationEnum);
        
        // Add aisle to store's aisle list
        if (aisle != null)
            stores.get(storeId).getAisles().put(aisle.getNumber(), aisle);        
        
        return stores.get(storeId).getAisles().get(aisle.getNumber());
    }
    
    public void showAisle(String storeAisleLoc)
    {
        // TODO
        
        
    }
    
    public Shelf defineShelf(String storeAisleShelfLoc, String name, String level, String description, String temperature)
    {       
        // TODO?
        
        // Split storeAisleShelfLoc on colon
        String[] splitLoc = storeAisleShelfLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        String shelfId = splitLoc[2];
        
        // Check that store exists
        if (stores.containsKey(storeId) == false)
        {
            try
            {
                throw new ModelerException("define shelf", "store not found; shelf not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that aisle exists
        if (stores.get(storeId).getAisles().containsKey(aisleNumber) == false)
        {
            try
            {
                throw new ModelerException("define shelf", "aisle not found; shelf not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that shelf id is unique
        if (stores.get(storeId).getAisles().get(aisleNumber).getShelves().containsKey(shelfId))
        {
            try
            {
                throw new ModelerException("define shelf", "shelf id already exists; shelf not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that level input is a valid enum
        if (!Shelf.containsLevelEnum(level))
        {
            try
            {
                throw new ModelerException("define shelf", "level (case-sensitive) not found; shelf not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that temperature input is a valid enum
        if (!Shelf.containsTempEnum(temperature))
        {
            try
            {
                throw new ModelerException("define shelf", "temperature (case-sensitive) not found; shelf not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Change level string to an enum
        Shelf.Level levelEnum = Shelf.Level.valueOf(level);
        
        // Change temperature string to an enum
        Shelf.Temperature temperatureEnum = Shelf.Temperature.valueOf(temperature);
        
        Shelf shelf = new Shelf(shelfId, name, levelEnum, description, temperatureEnum);        
        
        // Add shelf to aisle's shelf list     
        if (shelf != null)
            stores.get(storeId).getAisles().get(aisleNumber).getShelves().put(shelf.getId(), shelf);        
        
        return stores.get(storeId).getAisles().get(aisleNumber).getShelves().get(shelf.getId());       
    }
        
    /* *
     * Utility Methods
     */      
    
    // (???) TODO
}
