package com.cscie97.store.model;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Modeler
{
    /* API Variables */
	
    private LinkedHashMap<String, Store> stores;
    private LinkedHashMap<String, Product> products;	
    private LinkedHashMap<String, Customer> customers;
    private LinkedHashMap<String, Inventory> inventories;
    private LinkedHashMap<String, Basket> activeBaskets;
	
    /* Constructor */
	
    public Modeler()
    {
        stores = new LinkedHashMap<String, Store>();
        products = new LinkedHashMap<String, Product>();
        customers = new LinkedHashMap<String, Customer>();
        inventories = new LinkedHashMap<String, Inventory>();
        activeBaskets = new LinkedHashMap<String, Basket>();
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
        
        // If there are active customers
        if (storeCustomers.size() > 0)
        {
            Customer customer;             
            for (Entry<String, Customer> customerEntry : storeCustomers.entrySet())
            {
                customer = customerEntry.getValue();
    
                customersString += "\n    " + counter + ".)";
    
                counter++;
            }
        }
        
        // If there are no active customers
        else
            customersString += " None";
        
        // Initialize string of store's aisle information
        LinkedHashMap<String, Aisle> aisles = store.getAisles();
        String aislesString = "";
        counter = 1;
        
        // If there are aisles
        if (aisles.size() > 0)
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
            aislesString += " None";
        
        // Initialize string of store's inventory information
        LinkedHashMap<String, Inventory> inventories = store.getInventories();
        String inventoriesString = "";
        counter = 1;
        
        // If there are inventories
        if (inventories.size() > 0)
        {
            Inventory inventory;
            for (Entry<String, Inventory> inventoryEntry : inventories.entrySet())
            {
                inventory = inventoryEntry.getValue();
                
                inventoriesString += "\n    " + counter + ".)" + " inventoryId = " + inventory.getInventoryId() + "; location = "
                        + inventory.getLocation() + "; capacity = " + inventory.getCapacity() + "; count = " + inventory.getCount()
                        + "; productId = " + inventory.getProductId();
                
                counter++;
            }
        }
        
        // If there are no inventories
        else
            inventoriesString += " None";
        
        // TODO: Get sensor information
        
        // TODO: Get appliance information
        
        // TODO: Combine strings together with other store information included
        String string;
        string = "\nStore \"" + store.getId() + "\" information --\n" + " - name = "
                + store.getName() + "\n - address = " + store.getAddress() + "\n - active customers ="
                + customersString + "\n - aisles =" + aislesString + "\n - inventories =" + inventoriesString + "\n";           

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
        // Split storeAisleLoc on colon
        String[] splitLoc = storeAisleLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        
        // Get store
        Store store = stores.get(storeId);
        
        // Check that store exists
        if (store == null)
        {
            try
            {
                throw new ModelerException("show aisle", "store not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that aisle exists
        if (!store.getAisles().containsKey(aisleNumber))
        {
            try
            {
                throw new ModelerException("show aisle", "aisle not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        Aisle aisle = store.getAisles().get(aisleNumber);
        
        // Initialize shelves information string
        LinkedHashMap<String, Shelf> shelves = store.getAisles().get(aisleNumber).getShelves();
        String shelvesString = "";
        int counter = 1;
        
        // If there are shelves
        if (shelves.size() > 0)
        {
            Shelf shelf;
            for (Entry<String, Shelf> shelfEntry : shelves.entrySet())
            {
                shelf = shelfEntry.getValue();
                
                shelvesString += "\n    " + counter + ".)" + " id = " + shelf.getId() + "; name = "
                        + shelf.getName() + "; level = " + shelf.getLevel() + "; description = " + shelf.getDescription()
                        + "; temperature = " + shelf.getTemperature();
                
                counter++;
            }
        }
        
        // If there are no shelves
        else
            shelvesString += " None";
        
        // Combine shelf string with other aisle information
        String string;
        string = "\nAisle \"" + storeAisleLoc + "\" information --\n" + " - name = "
                + aisle.getName() + "\n - description = " + aisle.getDescription() + "\n - location = "
                + aisle.getLocation() + "\n - shelves =" + shelvesString + "\n";           

        // Print string to stdout
        System.out.print(string);
    }
    
    public Shelf defineShelf(String storeAisleShelfLoc, String name, String level, String description, String temperature)
    { 
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
    
    public void showShelf(String storeAisleShelfLoc)
    {      
        // Split storeAisleShelfLoc on colon
        String[] splitLoc = storeAisleShelfLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        String shelfId = splitLoc[2];
        
        // Check that store exists
        if (!stores.containsKey(storeId))
        {
            try
            {
                throw new ModelerException("show shelf", "store not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that aisle exists
        if (!stores.get(storeId).getAisles().containsKey(aisleNumber))
        {
            try
            {
                throw new ModelerException("show shelf", "aisle not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that shelf exists
        if (!stores.get(storeId).getAisles().get(aisleNumber).getShelves().containsKey(shelfId))
        {
            try
            {
                throw new ModelerException("show shelf", "shelf not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Initialize string of store's inventory information
        LinkedHashMap<String, Inventory> inventories = stores.get(storeId).getInventories();
        String inventoriesString = "";
        int counter = 1;
        
        // If there are inventories
        if (inventories.size() > 0)
        {
            Inventory inventory;
            
            // Only get particular shelf's inventory           
            for (Entry<String, Inventory> inventoryEntry : inventories.entrySet())
            {
                inventory = inventoryEntry.getValue();
                
                // Split inventory location to get shelf
                String[] splitInvLoc = inventory.getLocation().split(":");                
                String invAisleNumber = splitInvLoc[1];
                String invShelfId = splitInvLoc[2];
                
                if(invAisleNumber.equals(aisleNumber) && invShelfId.equals(shelfId))
                {
                    inventoriesString += "\n    " + counter + ".)" + " inventoryId = " + inventory.getInventoryId() + "; location = "
                            + inventory.getLocation() + "; capacity = " + inventory.getCapacity() + "; count = " + inventory.getCount()
                            + "; productId = " + inventory.getProductId();
                    
                    counter++;
                }
            }
            
            // If no inventory for shelf found
            if (inventoriesString.equals(""))
                inventoriesString += " None";
        }
        
        // If there are no inventories
        else
            inventoriesString += " None";
        
        // Combine inventories string with shelf's other information
        String string;
        Shelf shelf = stores.get(storeId).getAisles().get(aisleNumber).getShelves().get(shelfId);
        string = "\nShelf \"" + storeAisleShelfLoc + "\" information --\n" + " - name = "
                + shelf.getName() + "\n - level = " + shelf.getLevel() + "\n - description = "
                + shelf.getDescription() + "\n - temperature = " + shelf.getTemperature()
                + "\n - inventories =" + inventoriesString + "\n";           

        // Print string to stdout
        System.out.print(string);
    }
    
    public Inventory defineInventory(String id, String storeAisleShelfLoc, Integer capacity, Integer count, String productId)
    {        
        // Check that count is within valid range
        if ((count < 0) || (count > capacity))
        {
            try
            {
                throw new ModelerException("define inventory", "count is not valid (between 0 and capacity); inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Split storeAisleShelfLoc on colon
        String[] splitLoc = storeAisleShelfLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        String shelfId = splitLoc[2];        
        
        // Check that id is unique
        if (inventories.containsKey(id))
        {
            try
            {
                throw new ModelerException("define inventory", "id already exists; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that store exists
        if (!stores.containsKey(storeId))
        {
            try
            {
                throw new ModelerException("define inventory", "store not found; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that aisle exists
        if (!stores.get(storeId).getAisles().containsKey(aisleNumber))
        {
            try
            {
                throw new ModelerException("define inventory", "aisle not found; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that shelf exists
        if (!stores.get(storeId).getAisles().get(aisleNumber).getShelves().containsKey(shelfId))
        {
            try
            {
                throw new ModelerException("define inventory", "shelf not found; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }        
        
        // Check that productId exists
        if (!products.containsKey(productId))
        {
            try
            {
                throw new ModelerException("define inventory", "product not found; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Make sure shelf temperature can support product's temperature
        if (stores.get(storeId).getAisles().get(aisleNumber).getShelves().get(shelfId).getTemperature()
                != products.get(productId).getTemperature())
        {
            try
            {
                throw new ModelerException("define inventory", "shelf and product temperatures don't match; inventory not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        Inventory inventory = new Inventory(id, storeAisleShelfLoc, capacity, count, productId);        
                
        if (inventory != null)
        {
            // Add inventory id and its associated store to inventories list
            inventories.put(inventory.getInventoryId(), inventory);
            
            // Add inventory object to its associated store's inventory
            stores.get(storeId).getInventories().put(inventory.getInventoryId(), inventory);
        }  
        
        return stores.get(storeId).getInventories().get(inventory.getInventoryId());
    }
    
    public void showInventory(String id)
    {        
        // Initialize string of store's inventory information
        Inventory inventory = inventories.get(id);
        String inventoryString = "";       
        
        // Check if inventory exists
        if (inventory == null)
        {
            try
            {                
                throw new ModelerException("show inventory", "inventory not found");
            }
            
            catch (ModelerException exception)
            {                
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }            
            
        // Concatenate inventory's information     
        inventoryString += "\nInventory \"" + id + "\" information --\n" + " - location = " + inventory.getLocation() + "\n - capacity = " 
                + inventory.getCapacity() + "\n - count = " + inventory.getCount() + "\n - productId = "+ inventory.getProductId() + "\n";
        
        // Print string to stdout
        System.out.print(inventoryString);
    }
    
    public void updateInventory(String id, Integer amount)
    {       
        Inventory inventory = inventories.get(id);
        
        // Check if inventory found
        if (inventory == null)
        {
            try
            {                
                throw new ModelerException("update inventory", "inventory not found");
            }
            
            catch (ModelerException exception)
            {                
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check if update amount plus count is greater than capacity or less than 0       
        if (((amount + inventory.getCount()) > inventory.getCapacity()) || ((amount + inventory.getCount()) < 0))
        {
            try
            {                
                throw new ModelerException("update inventory", "update would put count outside of valid range; update rejected");
            }
            
            catch (ModelerException exception)
            {                
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }       
        
        inventory.updateCount(amount);
    }
    
    public Product defineProduct(String productId, String name, String description, String size, String category, Integer unitPrice, String temperature)
    {
        // Check that productId is unique
        if (products.containsKey(productId))
        {
            try
            {                
                throw new ModelerException("define product", "productId already exists; product not created");
            }
            
            catch (ModelerException exception)
            {                
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that temperature is enum
        if (!Shelf.containsTempEnum(temperature))
        {
            try
            {
                throw new ModelerException("define product", "temperature (case-sensitive) invalid; product not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Change temperature string to an enum
        Shelf.Temperature temperatureEnum = Shelf.Temperature.valueOf(temperature);
        
        Product product = new Product(productId, name, description, size, category, unitPrice, temperatureEnum);
        
        // Add to list of products
        if (product != null)
            products.put(product.getProductId(), product);
        
        return products.get(product.getProductId());
    }
    
    public void showProduct(String id)
    {
        // Get product
        Product product = products.get(id);            
        
        // Check if product was found
        if (product == null)
        {
            try
            {                
                throw new ModelerException("show product", "product not found");
            }
            
            catch (ModelerException exception)
            {                
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }            
            
        // Concatenate product's information     
        String productString = "\nProduct \"" + id + "\" information --\n" + " - name = " + product.getName() + "\n - description = " 
                + product.getDescription() + "\n - size = " + product.getSize() + "\n - category = "+ product.getCategory()
                + "\n - unit_price = " + product.getUnitPrice() + "\n - temperature = " + product.getTemperature() + "\n";
        
        // Print string to stdout
        System.out.print(productString);       
    }
    
    public Customer defineCustomer(String id, String firstName, String lastName, String type, String emailAddress, String account)
    {
        // Check if customer id is unique
        if (customers.containsKey(id))
        {
            try
            {
                throw new ModelerException("define customer", "id already exists; customer not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check if type is a valid enum
        if (!Customer.containsTypeEnum(type))
        {
            try
            {
                throw new ModelerException("define customer", "type (case-sensitive) not found; customer not created");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Convert type to enum
        Customer.Type typeEnum = Customer.Type.valueOf(type);
        
        Customer customer = new Customer(id, firstName, lastName, typeEnum, emailAddress, account);
        
        // Add to list of customers
        if (customer != null)
            customers.put(customer.getId(), customer);
            
        return customers.get(customer.getId());
    }
        
    public void showCustomer(String id)
    {
        Customer customer = customers.get(id);
        
        // Check if customer exists
        if (customer == null)
        {
            try
            {
                throw new ModelerException("show customer", "customer not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }    
        
        // Create customer's information string    
        String customerString = "\nCustomer \"" + customer.getId() + "\" information --\n" + " - first name = " + customer.getFirstName()
                + "\n - last name = " + customer.getLastName() + "\n - type = " + customer.getType() + "\n - emailAddress = "
                + customer.getEmailAddress() + "\n - account = " + customer.getAccount() + "\n - location = " + customer.getLocation() + "\n";
        
        // Print string to stdout
        System.out.print(customerString); 
    }
    
    public void updateCustomer(String id, String storeAisleLoc)
    {
        // Check that customer exists
        Customer customer = customers.get(id);
        
        if (customer == null)
        {
            try
            {
                throw new ModelerException("update customer", "customer not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Split storeAisleLoc on colon
        String[] splitLoc = storeAisleLoc.split(":");
        String storeId = splitLoc[0];
        String aisleNumber = splitLoc[1];
        
        // Check that store exists
        if (!stores.containsKey(storeId))
        {
            try
            {
                throw new ModelerException("update customer", "store not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that aisle exists
        if (!stores.get(storeId).getAisles().containsKey(aisleNumber))
        {
            try
            {
                throw new ModelerException("update customer", "aisle not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        customer.setLocation(storeAisleLoc);
    }
    
    public Basket getCustomerBasket(String customerId)
    {
        // TODO
        
        Customer customer = customers.get(customerId);
        
        // Check that customer exists
        if (customer == null)
        {
            try
            {
                throw new ModelerException("get customer basket", "customer not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        // Check that customer is registered (not a guest)
        if (!customer.getType().equals(Customer.Type.registered))
        {
            try
            {
                throw new ModelerException("get customer basket", "unregistered customer; request denied");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return null;
            }
        }
        
        Basket basket = activeBaskets.get(customerId);
        
        // If customer basket wasn't found in activeBaskets list
        if (basket == null)
        {
            // Create a new basket
            basket = new Basket(customerId);
            
            // Add basket to list of active baskets
            if (basket != null)
                activeBaskets.put(basket.getId(), basket);
        }
        
        return basket;
    }
    
    public void addBasketItem(String customerId, String productId, Integer itemCount)
    {
        // TODO
        
        // Check that itemCount is greater than 0
        if (itemCount < 1)
        {
            try
            {
                throw new ModelerException("add basket item", "item_count must be 1 or greater; item not added");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that basket exists
        if (!activeBaskets.containsKey(customerId))
        {
            try
            {
                throw new ModelerException("add basket item", "basket not found; item not added");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that customer is in a store
        if (customers.get(customerId).getLocation() == null)
        {
            try
            {
                throw new ModelerException("add basket item", "customer is not in a store; item not added");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Check that product is sold at the particular store and its capacity is >= to itemCount        
        boolean hasProduct = false;
        int capacity = 0;
        Store store = stores.get(customers.get(customerId).getLocation().split(":")[0]);
        LinkedHashMap<String, Inventory> storeInventories = store.getInventories();
                
        Inventory inventory;              
        for (Entry<String, Inventory> inventoryEntry : storeInventories.entrySet())
        {
            inventory = inventoryEntry.getValue();

            if (inventory.getProductId().equals(productId))
            {
                hasProduct = true;                
                capacity += inventory.getCapacity();      
            }
        }
        
        if (!hasProduct)
        {
            try
            {
                throw new ModelerException("add basket item", "item not found; item not added");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // If max number of the item store sells is less than what customer wants to put in basket
        if (capacity < itemCount)
        {
            try
            {
                throw new ModelerException("add basket item", "not enough items available; item not added");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Get customer's basket to add item
        Basket basket = activeBaskets.get(customerId);
        LinkedHashMap<String, Integer> basketItems = basket.getBasketItems();
        
        // If basket doesn't already contain product
        if (!basketItems.containsKey(productId))
            basketItems.put(productId, itemCount);
        
        // Else add itemCount to the current count of the product
        else
        {            
            Integer currentItemCount = basketItems.get(productId);
            itemCount += currentItemCount;
            basketItems.put(productId, itemCount);
        }     
    }
    
    public void removeBasketItem(String customerId, String productId, Integer itemCount)
    {
        // TODO
        
        // Check that itemCount is greater than 0
        
    }
    
    public void clearBasket(String customerId)
    {
        // TODO
        
        // Check if basket has items
    }
    
    public void showBasketItems(String customerId)
    {
        // TODO
      
        Basket basket = activeBaskets.get(customerId);
        
        // Check if basket exists
        if (basket == null)
        {
            try
            {
                throw new ModelerException("show basket items", "basket not found");
            }
            
            catch (ModelerException exception)
            {
                System.out.println();
                System.out.print(exception.getMessage());      
                return;
            }
        }
        
        // Initialize string for basket items info
        String itemsString = "";        
        
        if (basket.getBasketItems().size() > 0)
        {
            String productId;
            Integer count;
            int counter = 1;
            for (Entry<String, Integer> integerEntry : basket.getBasketItems().entrySet())
            {
                productId = integerEntry.getKey();
                count = integerEntry.getValue();
                
                itemsString += "\n " + counter + ".)" + " productId = " + productId + " : count = " + count;
                
                counter++;            
            }
        }
        
        // If basket has no items
        else
            itemsString += " Basket empty";
        
        // TODO: Combine itemsString with header text
        String string;
        string = "\nBasket \"" + basket.getId() + "\" items --" + itemsString + "\n";           

        // Print string to stdout
        System.out.print(string); 
    }
    
    /* *
     * Utility Methods
     */      
    
    private void customerEnters(Store store, Customer customer)
    {
        // TODO
    }
    
    private void customerExits(Store store, Customer customer)
    {
        // TODO
    }
}
