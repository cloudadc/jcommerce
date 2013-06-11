package com.jcommerce.core.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Base class for Model objects.  This is basically for the toString, equals
 * and hashCode methods.
 *
 */
public class ModelObject implements Serializable {

	private static final long serialVersionUID = -8291709224651447583L;
	
	@Id    
    @GeneratedValue 
	private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return getClass().getSimpleName();
    }

}
