/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_bonus", catalog = "ishop")
public class UserBonus extends ModelObject {
	
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
    
	private static final long serialVersionUID = 8549774792735918439L;
	private BonusType type;
	private String bonusSN;
//	private User user;
	private Timestamp usedTime;
//	private Order order;
	private boolean emailed;
	
	private Set<Order> orders = new HashSet<Order>();

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "bonus_type_id", nullable = true )
    public BonusType getType() {
        return type;
    }

    public void setType(BonusType type) {
        this.type = type;
    }

    @Basic( optional = true )
	@Column( name = "bonus_sn", length = 255  )
    public String getBonusSN() {
        return bonusSN;
    }

    public void setBonusSN(String bonusSN) {
        this.bonusSN = bonusSN;
    }

    public Timestamp getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Timestamp usedTime) {
        this.usedTime = usedTime;
    }

    public boolean isEmailed() {
        return emailed;
    }

    public void setEmailed(boolean emailed) {
        this.emailed = emailed;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "userBonus"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "bonus_id", nullable = false  )
	public Set<Order> getOrders() {
		return orders;
	}
    
    public void addOrder(Order order) {
		order.setUserBonus(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}

}
