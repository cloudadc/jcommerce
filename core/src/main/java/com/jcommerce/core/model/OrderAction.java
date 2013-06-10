/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class OrderAction extends ModelObject {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

}
