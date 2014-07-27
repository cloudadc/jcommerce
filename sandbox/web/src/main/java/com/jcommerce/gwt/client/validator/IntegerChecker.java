/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.validator;

public class IntegerChecker implements IValidator {
    private String name;
    private int minValue;
    private boolean canBeSpace;
    
    public IntegerChecker(String name, int minValue, boolean canBeSpace) {
        this.name = name;
        this.minValue = minValue;
        this.canBeSpace = canBeSpace;
    }
    
    public String validate(Object value) {
        if (value == null) {
            if (canBeSpace) {
                return null;
            }
            return "Value needed for " + name;
        }
        
        String s = value.toString();
        s = s.trim();
        if (s.length() == 0) {
            if (canBeSpace) {
                return null;
            }
            return "Value needed for " + name;
        }

        try {
            int i = Integer.parseInt(s);
            if (i < minValue) {
                return "The minimum value for " + name + " is: " + minValue;
            }
        } catch (NumberFormatException e) {
            return "Invalid integer value for " + name + " : " + s;
        }
        
        return null;
    }

    public boolean allowEmpty() {
        return canBeSpace;
    }
}
