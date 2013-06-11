/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_Shipping")
public class Shipping extends ModelObject {

	private static final long serialVersionUID = -7896034244403964599L;

	private Set<ShippingArea> shippingAreas = new HashSet<ShippingArea>();        
    
	private String id;
	private String name;
	private String code;
	private String description;
	private String insure;
	private boolean supportCod;
	private boolean enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public boolean isSupportCod() {
        return supportCod;
    }

    public void setSupportCod(boolean supportCod) {
        this.supportCod = supportCod;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany
    public Set<ShippingArea> getShippingAreas() {
        return shippingAreas;
    }

    public void setShippingAreas(Set<ShippingArea> shippingAreas) {
        this.shippingAreas = shippingAreas;
    }
}
