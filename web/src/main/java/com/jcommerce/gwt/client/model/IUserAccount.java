package com.jcommerce.gwt.client.model;


/**
 * @author monkey
 */
public class IUserAccount {

	public static final String ID = "id";
	public static final String USER = "user"; 
	public static final String ADMINUSER = "adminUser"; 
	public static final String AMOUNT = "amount"; 
	public static final String ADDTIME = "addTime"; 
	public static final String PAIDTIME = "paidTime"; 
	public static final String ADMINNOTE = "adminNote"; 
	public static final String USERNOTE = "userNote"; 
	public static final String PROCESSTYPE = "processType"; 
	public static final String PAYMENT = "payment"; 
	public static final String PAID = "paid";

	public static final int TYPE_SAVING = 0; 
    public static final int TYPE_DRAWING = 1; 
    
    public static final int PAY_BALANCE = 1; // 余额支付
    public static final int PAY_BANK_REMIT = 2; // 银行汇款转账
    public static final int PAY_POST_REMIT = 3; // 邮局汇款

    public static final int PS_UNCOMFIRM = 1; 
    public static final int PS_COMPLETE = 2; 
    public static final int PS_CANCEL = 3; 
}
