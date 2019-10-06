package com.cscie97.store.model.test;

import java.util.Scanner;

import com.cscie97.store.model.*;

public class TestDriver
{
	// Method: Without a script file argument "main" will run manual commands (otherwise will run script file)
    public static void main(String[] args)
    {
    	// Create a command processor
        CommandProcessor cp = new CommandProcessor();
        
        // If no file argument included on command line call processCommand method
        if (args.length == 0)
        {
            String str;    
            
            Scanner input = new Scanner(System.in);           
            
            System.out.print(getMenu());
            
            while (!(str = input.nextLine()).equalsIgnoreCase("q"))
            {   
                // Check if user inputted anything and call command processor if so
                if (str.length() > 0)
                    cp.processCommand(str); 
                
                System.out.println(); 
                
                //System.out.print(getMenu());
                System.out.print("Please enter a command ('q' to quit): ");
            }
            
            // Close scanner
            input.close();            
        }
        
        // If script file argument included on command line call processCommandFile
        else if (args.length == 1)
        {
            // Call command processor with file name
            cp.processCommandFile(args[0]);       
        }    
    }
    
    public static String getMenu()
    {
        // Initialize menu string
        String string = "";
        
        // Output list of commands
        string += "Commands and syntax (use quotes to group words as one entry) --\n";
        string += " 1.) define store <identifier> name <name> address <address>\n";      
        string += "\n";
        string += " Example command: define store store_1 name \"Harvard Square Store\" address \"1400 Mass Avenue,"
                + " Cambridge, MA 02138\"\n\n";                
        string += "Please enter a command ('q' to quit): ";      
        
        return string;
    }
}
