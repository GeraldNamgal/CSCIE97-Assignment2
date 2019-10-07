package com.cscie97.store.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandProcessor
{
    /* Variables */

    private Modeler modeler;
    private int lineNum = 0;
    
    /* *
     * API Methods
     */

    public void processCommand(String command)
    {       
        // Create Modeler if it doesn't exist
        if (modeler == null)
            modeler = new Modeler();
    	
        parseAndProcess(command);
    }
    
    // Referenced https://www.journaldev.com/709/java-read-file-line-by-line
    public void processCommandFile(String commandFile)
    {       
        // Create Modeler if it doesn't exist
        if (modeler == null)
            modeler = new Modeler();    	
    	
        // Check if the file is empty
        try
        {
            File newFile = new File(commandFile);
            if (newFile != null)
            {
                if (newFile.length() == 0)
                    throw new CommandProcessorException("in processCommandFile method", "file is empty");
            }
        }

        catch (CommandProcessorException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        // Read file
        try
        {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(commandFile));
            String line = reader.readLine();

            while (line != null)
            {
                // Counter up lineNum
                lineNum++;

                // Call parseAndProcess method if line isn't empty
                if (line.length() > 0)
                    parseAndProcess(line);

                // Read next line
                line = reader.readLine();
            }

            reader.close();
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    /* *
     * Modeler-ish API Utility Methods
     */

    // (???) TODO
    
    /* *
     * Other Utility Methods
     */

    public void parseAndProcess(String input)
    {
        // Trim leading and trailing whitespace
        String trimmedInput = input.trim();

        // Check if input is a comment
        if (trimmedInput.charAt(0) == '#')
        {
            System.out.println(trimmedInput);
            return;
        }

        // Delimit input string on whitespace and add each value to array
        String[] splitInputArr = trimmedInput.split("\\s+");
        
        /* If input contained quotes, then validate their correct usage and fix array - code block BEGINNING */
        
        boolean openQuote = false;
        ArrayList<Integer> indicesOfOpeningQuotes = new ArrayList<Integer>();
        ArrayList<Integer> indicesOfClosingQuotes = new ArrayList<Integer>();
        
        for (int i = 0; i < splitInputArr.length; i++)
        {
            // If a stand-alone quote, must check if it's an opening or closing quote
            if ((splitInputArr[i].length() == 1) && (splitInputArr[i].charAt(0) == '"'))
            {
                if (openQuote == false)
                {
                    indicesOfOpeningQuotes.add(i);
                    openQuote = true;
                }

                else
                {
                    indicesOfClosingQuotes.add(i);
                    openQuote = false;
                }
            }    
	        
            // If not a stand-alone quote
	    else
	    {
	       	// Checks string if has a quote as first character
	        if (splitInputArr[i].charAt(0) == '"')
	        {
	            if (openQuote == true)
	            {
	               	try
	                {
	                    if (lineNum == 0)
	                        throw new CommandProcessorException("in processCommand method", "missing closing quote in user input");
	
                            else
	                        throw new CommandProcessorException("in processCommandFile method", "missing closing quote in user input", lineNum);            
	                }
	                    
	                catch (CommandProcessorException exception)
	                {
	                    if (lineNum == 0)
	                    {
	                        System.out.println("-: " + trimmedInput);
	                        System.out.println();
	                        System.out.println(exception.getMessage());	                            
	                        return;
	                    }
	            
	                    else
	                    {
	                        System.out.println("-: " + trimmedInput);
	                        System.out.println();
	                        System.out.println(exception.getMessageLine());	                           
	                        return;
	                    }
	                }
	            }
	              
	            else
	            {
	             	indicesOfOpeningQuotes.add(i);
	              	openQuote = true;
	            }
	        }
	
	        // Checks string if has a quote as last character
	        if (splitInputArr[i].charAt(splitInputArr[i].length() - 1) == '"')
	        {
	            if (openQuote == true)
	            {
	              	indicesOfClosingQuotes.add(i);
	               	openQuote = false;
	            }
	              
	            else
	            {
	               	try
	                {
	                    if (lineNum == 0)
	                        throw new CommandProcessorException("in processCommand method", "missing opening quote in user input");
	
	                    else
	                        throw new CommandProcessorException("in processCommandFile method", "missing opening quote in user input", lineNum);            
	                }
	                    
	                catch (CommandProcessorException exception)
	                {
	                    if (lineNum == 0)
	                    {
	                        System.out.println("-: " + trimmedInput);
	                        System.out.println();
	                        System.out.println(exception.getMessage());	                            
	                        return;
	                    }
	            
	                    else
	                    {
	                        System.out.println("-: " + trimmedInput);
	                        System.out.println();
	                        System.out.println(exception.getMessageLine());	                            
	                        return;
	                    }
	                }
	            }
	        }
	    }
        }
        
        // If there is an ultimate open quote without a matching closing quote then throw exception 
        if (openQuote == true)
        {
            try
            {
                if (lineNum == 0)
                    throw new CommandProcessorException("in processCommand method", "missing closing quote in user input");

                else
                    throw new CommandProcessorException("in processCommandFile method", "missing closing quote in user input", lineNum);            
            }
            
            catch (CommandProcessorException exception)
            {
                if (lineNum == 0)
                {
                    System.out.println("-: " + trimmedInput);
                    System.out.println();
                    System.out.println(exception.getMessage());                    
                    return;
                }
    
                else
                {
                    System.out.println("-: " + trimmedInput);
                    System.out.println();
                    System.out.println(exception.getMessageLine());                    
                    return;
                }
            }
        }
        
        // If input had quotes, string quoted input(s) back together
        if (indicesOfOpeningQuotes.size() > 0)
        {       	
            // Create a modified splitInputArr, named splitStringQuotesArr, with quoted input(s) back together
            ArrayList<String> splitStringQuotesArr = new ArrayList<String>();
            
            // Initialize index counter for opening and closing quotes arrays
            int index = 0;
        	
            // Initialize a quote string
            String quote = "";
        	
            // Loop through splitInputArr to create new splitStringQuotesArr string array
            for (int i = 0; i < splitInputArr.length; i++)
	    {
        	// If found all quotes then just transfer the element to new array
        	if (index >= indicesOfOpeningQuotes.size())        			
        	    splitStringQuotesArr.add(splitInputArr[i]);        		
        		
       		else
       		{
       		    if (openQuote == false)
       		    {
       		        if (i == indicesOfOpeningQuotes.get(index))       				
       		            openQuote = true;
	        			        			
	        	else	        			
	        	    splitStringQuotesArr.add(splitInputArr[i]);	        			
       		    }
	        			
       		    if (openQuote == true)
       		    {
       		        // If element contains the closing quote
       		        if (i == indicesOfClosingQuotes.get(index))
       		        {
       		            // Append element to quote string
       		            quote += splitInputArr[i];
    			        		
       		            // Remove quotes from quote and trim its whitespace
       		            StringBuffer sbf = new StringBuffer(quote);
       		            quote = sbf.deleteCharAt(0).toString();
       		            sbf = new StringBuffer(quote);
       		            quote = sbf.deleteCharAt(quote.length() - 1).toString();
       		            quote = quote.trim();
    			        		
       		            // Add quote to new array
       		            splitStringQuotesArr.add(quote);			        		
    			        		
       		            // Set openQuote to false, increment counter, and reset quote string since found closing quote
       		            openQuote = false;			        		
       		            index++;
       		            quote = "";
       		        }
    		        			        	
       		        else
       		        {
       		            // Append element to quote string with a space added
       		            quote += splitInputArr[i] + " ";
       		        }
       		    }
       		}
	    }    	
        	
            // Redefine splitInputArr with new array
            splitInputArr = new String[splitStringQuotesArr.size()]; 
            splitStringQuotesArr.toArray(splitInputArr);        	
        }
        
        /* code block END ("If input contained quotes, then validate their correct usage and fix array") */              
        
        /* TODO: Continue parsing for valid DSL commands for CLI */
                	 
        if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("store")
                && (splitInputArr.length == 7) && splitInputArr[3].equalsIgnoreCase("name")
                && splitInputArr[5].equalsIgnoreCase("address"))
        {
            System.out.println("-: " + trimmedInput);
            modeler.defineStore(splitInputArr[2], splitInputArr[4], splitInputArr[6]);
            System.out.println();
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("store")
                && (splitInputArr.length == 3))
        {
            System.out.println("-: " + trimmedInput);
            modeler.showStore(splitInputArr[2]);
            System.out.println();
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("aisle")
                && (splitInputArr.length == 9) && splitInputArr[3].equalsIgnoreCase("name")
                && splitInputArr[5].equalsIgnoreCase("description") && splitInputArr[7].equalsIgnoreCase("location")
                && (splitInputArr[2].split(":").length == 2))
        {
            System.out.println("-: " + trimmedInput);
            modeler.defineAisle(splitInputArr[2], splitInputArr[4], splitInputArr[6], splitInputArr[8]);
            System.out.println();
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("aisle"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("shelf")
                && (splitInputArr.length == 11) && splitInputArr[3].equalsIgnoreCase("name")
                && splitInputArr[5].equalsIgnoreCase("level") && splitInputArr[7].equalsIgnoreCase("description")
                && splitInputArr[9].equalsIgnoreCase("temperature") && (splitInputArr[2].split(":").length == 3))
        {
            System.out.println("-: " + trimmedInput);
            modeler.defineShelf(splitInputArr[2], splitInputArr[4], splitInputArr[6], splitInputArr[8], splitInputArr[10]);
            System.out.println();
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("shelf"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("inventory"))
        {
            // TODO: Check that integers are integers...
        }
    
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("inventory"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("update") &&  splitInputArr[1].equalsIgnoreCase("inventory"))
        {
            // TODO: Check that integer is integer...
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("product"))
        {
            // TODO: Check that integers are integers...
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("product"))
        {
            
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("customer"))
        {
   	        
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("update") &&  splitInputArr[1].equalsIgnoreCase("customer"))
        {
   	        
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("customer"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("get_customer_basket"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("add_basket_item"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("remove_basket_item"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("clear_basket"))
        {
            
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("basket_items"))
        {
   	        
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("device"))
        {
   	        
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("device"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("create") &&  splitInputArr[1].equalsIgnoreCase("event"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("define") &&  splitInputArr[1].equalsIgnoreCase("device"))
        {
            
        }
    	
        else if (splitInputArr[0].equalsIgnoreCase("show") &&  splitInputArr[1].equalsIgnoreCase("device"))
        {
            
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("create") &&  splitInputArr[1].equalsIgnoreCase("event"))
        {
   	        
        }
    	
   	else if (splitInputArr[0].equalsIgnoreCase("create") &&  splitInputArr[1].equalsIgnoreCase("command"))
        {
   	        
        }
    	
    	// Throw CommandProcessorException if input syntax could not be matched to a valid command
        else
        {
            try
            {
                if (lineNum == 0)
                    throw new CommandProcessorException("in processCommand method", "invalid DSL command input");

                else
                    throw new CommandProcessorException("in processCommandFile method", "invalid DSL command input", lineNum);
            }

            catch (CommandProcessorException exception)
            {
                if (lineNum == 0)
                {
                    System.out.println("-: " + trimmedInput);
                    System.out.println();
                    System.out.println(exception.getMessage());
                }

                else
                {
                    System.out.println("-: " + trimmedInput);
                    System.out.println();
                    System.out.println(exception.getMessageLine());
                }
            }
        }                     
    }
}
