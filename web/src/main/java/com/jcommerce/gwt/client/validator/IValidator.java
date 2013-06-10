/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.validator;

public interface IValidator {
    /**
     * @return error message, null if no error 
     */
    String validate(Object value);
    
    boolean allowEmpty();
}
