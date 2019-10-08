package com.cscie97.store.model;

public class Customer
{
    /* API Variables */
    
    enum Type 
    { 
        registered, guest; 
    }
    
    private String id;
    private String firstName;
    private String lastName;
    private Type type;
    private String emailAddress;
    private String account;
    private String location;
    
    /* Constructor */
    
    public Customer(String id, String firstName, String lastName, Type type, String emailAddress, String account)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.emailAddress = emailAddress;
        this.account = account;
    } 
    
    /* *
     * Utility Methods
     */
    
    // Checks a string if it's a Type enum
    public static boolean containsTypeEnum(String testString)
    {
        for (Type type : Type.values())
        {
            if (type.name().equals(testString))
            {
                return true;
            }
        }

        return false;
    }

    /* *
     * Getters and Setters
     */
    
    public String getId() 
    {
        return id;
    }   

    public String getFirstName() 
    {
        return firstName;
    }
    
    public String getLastName() 
    {
        return lastName;
    }   

    public Type getType() 
    {
        return type;
    }   

    public String getEmailAddress() 
    {
        return emailAddress;
    }   

    public String getAccount() 
    {
        return account;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }       
}
