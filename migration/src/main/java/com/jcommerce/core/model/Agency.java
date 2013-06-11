/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_Agency")
public class Agency extends ModelObject {
 
	private static final long serialVersionUID = 7320919343871010164L;
	private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
