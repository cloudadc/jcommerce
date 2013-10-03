/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_action")
public class OrderAction extends ModelObject {
	
	private Long id;
	
	@Id 
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = -6240513894880133592L;
	public static final int ORDER_UNCONFIRMED = Constants.ORDER_UNCONFIRMED; // 未确认
    public static final int ORDER_CONFIRMED = Constants.ORDER_CONFIRMED; // 已确认
    public static final int ORDER_CANCELED = Constants.ORDER_CANCELED; // 已取消
    public static final int ORDER_INVALID = Constants.ORDER_INVALID; // 无效
    public static final int ORDER_RETURNED = Constants.ORDER_RETURNED; // 退货

    public static final int SHIPPING_UNSHIPPED = Constants.SHIPPING_UNSHIPPED; // 未发货
    public static final int SHIPPING_SHIPPED = Constants.SHIPPING_SHIPPED; // 已发货
    public static final int SHIPPING_RECEIVED = Constants.SHIPPING_RECEIVED; // 已收货
    public static final int SHIPPING_PREPARING = Constants.SHIPPING_PREPARING; // 备货中

    public static final int PAY_UNPAYED = Constants.PAY_UNPAYED; // 未付款
    public static final int PAY_PAYING = Constants.PAY_PAYING; // 付款中
    public static final int PAY_PAYED = Constants.PAY_PAYED; // 已付款

    private Order order;
    private String actionUser;
    private int orderStatus;
    private int shippingStatus;
    private int payStatus;
    private String actionNote;
    private Timestamp logTime;

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "order_id", nullable = true )
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Basic( optional = true )
	@Column( name = "action_user", length = 30  )
    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    @Basic( optional = true )
	@Column( name = "order_status"  )
    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic( optional = true )
	@Column( name = "shipping_status"  )
    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    @Basic( optional = true )
	@Column( name = "pay_status"  )
    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    @Basic( optional = true )
	@Column( name = "action_note", length = 255  )
    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }

    @Basic( optional = true )
	@Column( name = "log_time"  )
    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

}
