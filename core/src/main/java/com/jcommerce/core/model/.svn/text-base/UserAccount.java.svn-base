/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class UserAccount extends ModelObject {
    
	public static final int TYPE_SAVING = Constants.SURPLUS_SAVE; // 为帐户冲值
    public static final int TYPE_DRAWING = Constants.SURPLUS_RETURN; // 从帐户提款

    public static final int PAY_BALANCE = 1; // 余额支付
    public static final int PAY_BANK_REMIT = 2; // 银行汇款转账
    public static final int PAY_POST_REMIT = 3; // 邮局汇款

    public static final int PS_UNCOMFIRM = 1; 
    public static final int PS_COMPLETE = 2; 
    public static final int PS_CANCEL = 3; 

    private User user;
    private String adminUser;
    private double amount;
    private Timestamp addTime;
    private Timestamp paidTime;
    private String adminNote;
    private String userNote;
    private int processType;
    private String payment;
    private boolean paid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Timestamp paidTime) {
        this.paidTime = paidTime;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
