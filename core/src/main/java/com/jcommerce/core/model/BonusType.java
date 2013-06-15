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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "bonus_type", catalog = "ishop")
public class BonusType extends ModelObject {

	private static final long serialVersionUID = 4046153307294398999L;
	public final static int SEND_BY_USER = 0; // 按用户发放
    public final static int SEND_BY_GOODS = 1; // 按商品发放
    public final static int SEND_BY_ORDER = 2; // 按订单发放
    public final static int SEND_BY_PRINT = 3; // 线下发放
    
    private Set<Goods> goodss = new HashSet<Goods>();
    
    private String name;
    private double money;
    private double minAmount;
    private double maxAmount;
    private int sendType;
    // 最小订单金额      只有商品总金额达到这个数的订单才能使用这种红包
    private double minGoodsAmount;
    private Timestamp sendStart;
    private Timestamp sendEnd;
    private Timestamp useStart;
    private Timestamp useEnd;
    
    private Set<UserBonus> userBonuses = new HashSet<UserBonus>();
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "bonusType"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "type_id", nullable = false  )
	public Set<Goods> getGoodss() {
		return this.goodss;
		
	}
    
    public void addGoods(Goods goods) {
		goods.setBonusType(this);
		this.goodss.add(goods);
	}
    
    public void setGoodss(final Set<Goods> goods) {
		this.goodss = goods;
	}
       
    @Basic( optional = true )
	@Column( name = "type_name", length = 60  )
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Basic( optional = true )
	@Column( name = "type_money"  )
    public double getMoney() {
        return money;
    }
    
    public void setMoney(double money) {
        this.money = money;
    }
    
    @Basic( optional = true )
	@Column( name = "min_amount"  )
    public double getMinAmount() {
        return minAmount;
    }
    
    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    @Basic( optional = true )
	@Column( name = "max_amount"  )
    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Basic( optional = true )
	@Column( name = "send_type"  )
    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    @Basic( optional = true )
	@Column( name = "min_goods_amount"  )
    public double getMinGoodsAmount() {
        return minGoodsAmount;
    }

    public void setMinGoodsAmount(double minGoodsAmount) {
        this.minGoodsAmount = minGoodsAmount;
    }

    @Basic( optional = true )
	@Column( name = "send_start_date"  )
    public Timestamp getSendStart() {
        return sendStart;
    }

    public void setSendStart(Timestamp sendStart) {
        this.sendStart = sendStart;
    }

    @Basic( optional = true )
	@Column( name = "send_end_date"  )
    public Timestamp getSendEnd() {
        return sendEnd;
    }
  
    public void setSendEnd(Timestamp sendEnd) {
        this.sendEnd = sendEnd;
    }

    @Basic( optional = true )
	@Column( name = "use_start_date"  )
    public Timestamp getUseStart() {
        return useStart;
    }

    public void setUseStart(Timestamp useStart) {
        this.useStart = useStart;
    }

    @Basic( optional = true )
	@Column( name = "use_end_date"  )
    public Timestamp getUseEnd() {
        return useEnd;
    }

    public void setUseEnd(Timestamp useEnd) {
        this.useEnd = useEnd;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "type"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "type_id", nullable = false  )
	public Set<UserBonus> getUserBonuses() {
		return this.userBonuses;
	}
    
    public void addUserBonus(UserBonus userBonus) {
		userBonus.setType(this);
		this.userBonuses.add(userBonus);
	}
    
    public void setUserBonuses(final Set<UserBonus> userBonus) {
		this.userBonuses = userBonus;
	}
}
