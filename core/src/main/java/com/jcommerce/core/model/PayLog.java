/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

public class PayLog extends ModelObject {
    public static final int PAY_ORDER = Constants.PAY_ORDER; // 订单支付
    public static final int PAY_SURPLUS = Constants.PAY_SURPLUS; // 会员预付款
    
    private Order order;
    private double orderAmount;
    private int orderType;
    private boolean paid;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
