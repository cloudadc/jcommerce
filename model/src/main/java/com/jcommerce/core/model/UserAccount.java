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
@Table(name = "user_account")
public class UserAccount extends ModelObject {
	
	private String id;
    
	@Id 
	@GeneratedValue
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = 571376475294276199L;
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

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "user_id", nullable = true )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic( optional = true )
	@Column( name = "admin_user", length = 255  )
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

    @Basic( optional = true )
	@Column( name = "admin_note", length = 255  )
    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    @Basic( optional = true )
	@Column( name = "user_note", length = 255  )
    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    @Basic( optional = true )
	@Column( name = "process_type"  )
    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }

    @Basic( optional = true )
	@Column( length = 90  )
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
