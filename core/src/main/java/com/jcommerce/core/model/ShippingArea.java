/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shipping_area", catalog = "ishop")
public class ShippingArea extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = -6017351801667054609L;
	private String name;
	private Shipping shipping;
	private String config;
	private Set<Region> regions = new HashSet<Region>();

	@Basic( optional = true )
	@Column( name = "shipping_area_name", length = 150  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "shipping_id", nullable = true )
    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @Basic( optional = true )
	@Column( length = 2147483647  )
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @ManyToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinTable(name = "shiparea_regin", joinColumns = { @JoinColumn(name = "shipping_area_id") }, inverseJoinColumns = { @JoinColumn(name = "region_id") })
	@Basic( optional = false )
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
