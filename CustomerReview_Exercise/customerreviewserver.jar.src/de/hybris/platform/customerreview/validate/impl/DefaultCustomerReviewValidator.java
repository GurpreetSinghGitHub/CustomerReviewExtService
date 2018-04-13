package de.hybris.platform.customerreview.validate.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import de.hybris.platform.customerreview.constants.CustomerReviewConstants;
import de.hybris.platform.customerreview.validate.CustomerReviewValidator;
// Trying to reuse existing exception; it will show an error as source code is not given
import de.hybris.platform.jalo.JaloInvalidParameterException;

/**
 * Validator to validate a customer review
 *
 */
public class DefaultCustomerReviewValidator implements CustomerReviewValidator {

	// Curse string should be injected externally
	String curseString;

	@Override
	public void validate(Double rating, String comment) throws JaloInvalidParameterException {
		
	    // if rating is less than zero (assuming min rating in this case), generate exception
		// instead of MINRATING, a constant of ZERO could also be used here
		if(rating < CustomerReviewConstants.getInstance().MINRATING)
		   throw new JaloInvalidParameterException("Rating cannot be less than " + CustomerReviewConstants.getInstance().MINRATING);
		    
	    if(null!=comment) {
	       // Split the incoming comment for whitespaces - space, tab, newline
	 	   String[] words = comment.split("\\s+");
	 	   // Get the list of curse strings
	 	   String[] curses;
	 	   if(null!=curseString)
	 		   curses = curseString.split(",");
	 	   boolean cursePresent;
	 	   // Check if any of curse strings is present in comment
	 	   if(null!=curses && curses.length>0) {
		 	   for(String curseWord:curses) {
		 		   // Using streams API to count number of occurrences of a curse
		 		   // Ignore any special characters like comma, apostrophe, double-quotes; more characters can be listed in this list
		 		   // If we know the CPU cores for sure, we could also use parallel streams
		 		  cursePresent = Arrays.asList(words).stream().filter(w->w.replaceAll(",\'\"", "").equalsIgnoreCase(curseWord)).findFirst().isPresent();
		 		   // Even if 1 occurrence of a curse is found, throw an exception 
		 		   if(cursePresent) {
		 			   throw new JaloInvalidParameterException("User comment is not in acceptable language");
		 		   }
		 	   }
	 	   }
	    }
	}

	public String getCurseString() {
		return curseString;
	}

	public void setCurseString(String curseString) {
		this.curseString = curseString;
	}
}
