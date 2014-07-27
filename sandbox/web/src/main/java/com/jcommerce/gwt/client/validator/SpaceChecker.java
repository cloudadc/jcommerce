/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.validator;

public class SpaceChecker implements IValidator {
    private String name;
    
    public SpaceChecker(String name) {
        this.name = name;
    }
    
    public String validate(Object value) {
        if (value == null) {
            return "Value needed for " + name;
        }
        
        String s = value.toString();
        if (s.trim().length() == 0) {
            return "Value needed for " + name;
        }
        
        return null;
    }

    public boolean allowEmpty() {
        return false;
    }
}
