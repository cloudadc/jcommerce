package com.jcommerce.core.model;

import java.io.Serializable;


/**
 * Base class for Model objects.  This is basically for the toString, equals
 * and hashCode methods.
 *
 */
public class ModelObject implements Serializable {
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
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this,
//                ToStringStyle.MULTI_LINE_STYLE);
//    }
//
//    public boolean equals(Object o) {
//        return EqualsBuilder.reflectionEquals(this, o);
//    }

//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
}
