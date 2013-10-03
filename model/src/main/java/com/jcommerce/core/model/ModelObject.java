package com.jcommerce.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * Base class for Model objects.  This is basically for the toString, equals
 * and hashCode methods.
 *
 */
public abstract class ModelObject implements Serializable {

	private static final long serialVersionUID = -8291709224651447583L;
	
	public String getModelId(){
		return getId().toString();
	}
	
	public void setModelId(String id){
		setId(Long.valueOf(id));
	} 
	
	public abstract Long getId();
	public abstract void setId(Long id);
	
//	private String id;
//    
//	@Id 
//	@Basic( optional = false )
//	@Column( name = "id", nullable = false, length = 32  )
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

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
