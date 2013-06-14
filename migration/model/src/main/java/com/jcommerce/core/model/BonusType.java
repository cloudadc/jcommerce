/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class BonusType extends ModelObject {
    public final static int SEND_BY_USER = 0; // 按用户发放
    public final static int SEND_BY_GOODS = 1; // 按商品发放
    public final static int SEND_BY_ORDER = 2; // 按订单发放
    public final static int SEND_BY_PRINT = 3; // 线下发放
    
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
       
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getMoney() {
        return money;
    }
    
    public void setMoney(double money) {
        this.money = money;
    }
    
    public double getMinAmount() {
        return minAmount;
    }
    
    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public double getMinGoodsAmount() {
        return minGoodsAmount;
    }

    public void setMinGoodsAmount(double minGoodsAmount) {
        this.minGoodsAmount = minGoodsAmount;
    }

    public Timestamp getSendStart() {
        return sendStart;
    }

    public void setSendStart(Timestamp sendStart) {
        this.sendStart = sendStart;
    }

    public Timestamp getSendEnd() {
        return sendEnd;
    }

    public void setSendEnd(Timestamp sendEnd) {
        this.sendEnd = sendEnd;
    }

    public Timestamp getUseStart() {
        return useStart;
    }

    public void setUseStart(Timestamp useStart) {
        this.useStart = useStart;
    }

    public Timestamp getUseEnd() {
        return useEnd;
    }

    public void setUseEnd(Timestamp useEnd) {
        this.useEnd = useEnd;
    }
}
