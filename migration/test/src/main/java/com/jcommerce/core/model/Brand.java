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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "brand", catalog = "ishop")
public class Brand extends ModelObject {
	
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
    
	private static final long serialVersionUID = -4502123851073639357L;
	private String name;
    private String logo;
    private String description;
    private String siteUrl;
    private boolean show;
    private int sortOrder;
    
    private Set<Goods> goodss = new HashSet<Goods>();

    @Basic( optional = true )
	@Column( name = "brand_name", length = 60  )
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Basic( optional = true )
	@Column( name = "brand_logo", length = 80  )
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    @Basic( optional = true )
	@Column( name = "brand_desc", length = 2147483647  )
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Basic( optional = true )
	@Column( name = "site_url", length = 255  )
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    @Basic( optional = true )
	@Column( name = "is_show"  )
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    @Basic( optional = true )
	@Column( name = "sort_order"  )
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "brand"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "brand_id", nullable = false  )
	public Set<Goods> getGoodss() {
		return this.goodss;	
	}
    
    public void addGoods(Goods goods) {
		goods.setBrand(this);
		this.goodss.add(goods);
	}
    
    public void setGoodss(final Set<Goods> goods) {
		this.goodss = goods;
	}
}
