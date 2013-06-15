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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pack", catalog = "ishop")
public class Pack extends ModelObject {
	
	private static final long serialVersionUID = -5518222336643416511L;
	private String name;
	private String image;
	private int fee;
	private int freeMoney;
	private String description;
	
	private Set<Order> orders = new HashSet<Order>();

	@Basic( optional = true )
	@Column( name = "pack_name", length = 120  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "pack_img", length = 255  )
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic( optional = true )
	@Column( name = "pack_fee"  )
    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Basic( optional = true )
	@Column( name = "free_money"  )
    public int getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(int freeMoney) {
        this.freeMoney = freeMoney;
    }

    @Basic( optional = true )
	@Column( name = "pack_desc", length = 255  )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "pack"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "pack_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}

}
