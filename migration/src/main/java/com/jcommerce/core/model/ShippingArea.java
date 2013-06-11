/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_ShippingArea")
public class ShippingArea extends ModelObject {

	private static final long serialVersionUID = -6017351801667054609L;
	private String name;
	private Shipping shipping;
	private String config;
	private Set<Region> regions = new HashSet<Region>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        if (regions == null) {
            this.regions.clear();
        } else {
            this.regions = regions;
        }
    }
    
    public void addRegion(Region region) {
        if (!regions.contains(region)) {
            this.regions.add(region);
        }
    }
    
    public void removeRegion(Region region) {
        if (regions.contains(region)) {
            this.regions.remove(region);
        }
    }
}
