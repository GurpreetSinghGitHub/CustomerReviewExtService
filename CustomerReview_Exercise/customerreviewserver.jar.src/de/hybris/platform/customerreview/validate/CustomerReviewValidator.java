package de.hybris.platform.customerreview.validate;

import de.hybris.platform.jalo.JaloInvalidParameterException;

/**
 * Public interface for all customer review valdators 
 *
 */
public interface CustomerReviewValidator {

	public void validate(Double rating, String comment) throws JaloInvalidParameterException;
}
