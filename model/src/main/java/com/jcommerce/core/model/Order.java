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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_")
public class Order extends ModelObject {
	
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

	private static final long serialVersionUID = -3913187141365126574L;
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
    
    private String SN;
    private User user;
    private int status;         // ORDER_XXX 
    private int shippingStatus; // SHIPPING_XXX
    private int payStatus;      // // PAY_XXX
    private String consignee;
    private String email;
    private Region region;
    private String address;
    private String zip;
    private String phone;
    private String mobile;
    private String signBuilding;
    private String bestTime;
    private String postScript;
    private String packName;
    private String cardName;
    private String cardMessage;
    private String invoicePayee;
    private String invoiceContent;
    private double goodsAmount;
    private double shippingFee;
    private double insureFee;
    private double payFee;
    private Payment payment;
    private Shipping shipping;
    private String howOss;
    private String howSurplus;
    private double moneyPaid;
    private double surplus;
    private int integral;
    private double integralMoney;
    private double orderAmount;
    private double bonusMoney;
    private int fromAD;
    private String referer;
    private Timestamp addTime;
    private Timestamp confirmTime;
    private Timestamp payTime;
    private Timestamp shippingTime;
    private Pack pack;
    private Card card;
    private UserBonus userBonus;
    private String invoiceNO;
    private String invoiceNote;
    private String extensionCode;
    private int extensionId;
    private String toBuyer;
    private String payNote;
    private Agency agency;
    private String invoceType;
    private double tax;
    private boolean separate;
    private double discount;
    private Order parent;
    private double cardFee;
    private double packFee;
    private String shippingName;
    private String payName;
    
    private Set<AffiliateLog> affiliateLogs = new HashSet<AffiliateLog>();
    private Set<Order> orders = new HashSet<Order>();
    private Set<OrderAction> orderActions = new HashSet<OrderAction>();
    private Set<OrderGoods> orderGoodss = new HashSet<OrderGoods>();
    private Set<PayLog> payLogs = new HashSet<PayLog>();
    
    @Basic( optional = true )
	@Column( name = "order_sn", length = 20  )
    public String getSN() {
        return SN;
    }

    public void setSN(String sn) {
        SN = sn;
    }

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
	@Column( name = "order_status"  )
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic( optional = true )
	@Column( name = "shipping_status"  )
    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "region", nullable = true )
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
	@Column( length = 60  )
    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic( optional = true )
	@Column( length = 255  )
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic( optional = true )
	@Column( name = "sign_building", length = 120  )
    public String getSignBuilding() {
        return signBuilding;
    }

    public void setSignBuilding(String signBuilding) {
        this.signBuilding = signBuilding;
    }

    @Basic( optional = true )
	@Column( name = "best_time", length = 120  )
    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    @Basic( optional = true )
	@Column( length = 255  )
    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    @Basic( optional = true )
	@Column( name = "pack_name", length = 120  )
    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    @Basic( optional = true )
	@Column( name = "card_name", length = 120  )
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Basic( optional = true )
	@Column( name = "card_message", length = 255  )
    public String getCardMessage() {
        return cardMessage;
    }

    public void setCardMessage(String cardMessage) {
        this.cardMessage = cardMessage;
    }

    @Basic( optional = true )
	@Column( name = "inv_payee", length = 120  )
    public String getInvoicePayee() {
        return invoicePayee;
    }

    public void setInvoicePayee(String invoicePayee) {
        this.invoicePayee = invoicePayee;
    }

    @Basic( optional = true )
	@Column( name = "inv_content", length = 120  )
    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    @Basic( optional = true )
	@Column( name = "goods_amount"  )
    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    @Basic( optional = true )
	@Column( name = "shipping_fee"  )
    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    @Basic( optional = true )
	@Column( name = "insure_fee"  )
    public double getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(double insureFee) {
        this.insureFee = insureFee;
    }

    @Basic( optional = true )
	@Column( name = "pay_fee"  )
    public double getPayFee() {
        return payFee;
    }

    public void setPayFee(double payFee) {
        this.payFee = payFee;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "payment_id", nullable = true )
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "shipping_id", nullable = true )
    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @Basic( optional = true )
	@Column( name = "how_oss", length = 120  )
    public String getHowOss() {
        return howOss;
    }

    public void setHowOss(String howOss) {
        this.howOss = howOss;
    }

    @Basic( optional = true )
	@Column( name = "how_surplus", length = 120  )
    public String getHowSurplus() {
        return howSurplus;
    }

    public void setHowSurplus(String howSurplus) {
        this.howSurplus = howSurplus;
    }

    @Basic( optional = true )
	@Column( name = "money_paid"  )
    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Basic( optional = true )
	@Column( name = "integral_money"  )
    public double getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(double integralMoney) {
        this.integralMoney = integralMoney;
    }

    @Basic( optional = true )
	@Column( name = "order_amount"  )
    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getBonusMoney() {
        return bonusMoney;
    }

    public void setBonusMoney(double bonus) {
        this.bonusMoney = bonus;
    }

    @Basic( optional = true )
	@Column( name = "from_ad"  )
    public int getFromAD() {
        return fromAD;
    }

    public void setFromAD(int fromAD) {
        this.fromAD = fromAD;
    }

    @Basic( optional = true )
	@Column( length = 255  )
    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Basic( optional = true )
	@Column( name = "add_time" ) 
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Basic( optional = true )
	@Column( name = "confirm_time"  )
    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Basic( optional = true )
	@Column( name = "pay_time"  )
    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    @Basic( optional = true )
	@Column( name = "shipping_time"  )
    public Timestamp getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Timestamp shippingTime) {
        this.shippingTime = shippingTime;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "pack_id", nullable = true )
    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "card_id", nullable = true )
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "bonus_id", nullable = true )
    public UserBonus getUserBonus() {
        return userBonus;
    }

    public void setUserBonus(UserBonus userBonus) {
        this.userBonus = userBonus;
    }

    @Basic( optional = true )
	@Column( name = "invoice_no", length = 50  )
    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    @Basic( optional = true )
	@Column( name = "extension_code", length = 30  )
    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    @Basic( optional = true )
	@Column( name = "extension_id"  )
    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    @Basic( optional = true )
	@Column( name = "to_buyer", length = 255  )
    public String getToBuyer() {
        return toBuyer;
    }

    public void setToBuyer(String toBuyer) {
        this.toBuyer = toBuyer;
    }

    @Basic( optional = true )
	@Column( name = "pay_note", length = 255  )
    public String getPayNote() {
        return payNote;
    }

    public void setPayNote(String payNote) {
        this.payNote = payNote;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "agency_id", nullable = true )
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Basic( optional = true )
	@Column( name = "inv_type", length = 60  )
    public String getInvoceType() {
        return invoceType;
    }

    public void setInvoceType(String invoceType) {
        this.invoceType = invoceType;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Basic( optional = true )
	@Column( name = "is_separate"  )
    public boolean isSeparate() {
        return separate;
    }

    public void setSeparate(boolean separate) {
        this.separate = separate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
    public Order getParent() {
        return parent;
    }

    public void setParent(Order parent) {
        this.parent = parent;
    }

    @Basic( optional = true )
	@Column( name = "card_fee"  )
	public double getCardFee() {
		return cardFee;
	}

	public void setCardFee(double cardFee) {
		this.cardFee = cardFee;
	}

	@Basic( optional = true )
	@Column( name = "pack_fee"  )
	public double getPackFee() {
		return packFee;
	}

	public void setPackFee(double packFee) {
		this.packFee = packFee;
	}

	@Basic( optional = true )
	@Column( name = "shipping_name", length = 120  )
	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	@Basic( optional = true )
	@Column( name = "pay_name", length = 120  )
	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Basic( optional = true )
	@Column( name = "invoice_note", length = 255  )
    public String getInvoiceNote() {
        return invoiceNote;
    }

    public void setInvoiceNote(String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "order"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "order_id", nullable = false  )
	public Set<AffiliateLog> getAffiliateLogs() {
		return this.affiliateLogs;	
	}
    
    public void addAffiliateLog(AffiliateLog affiliateLog) {
		affiliateLog.setOrder(this);
		this.affiliateLogs.add(affiliateLog);
	}
    
    public void setAffiliateLogs(final Set<AffiliateLog> affiliateLog) {
		this.affiliateLogs = affiliateLog;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "order_id", nullable = false  )
	public Set<Order> getOrders() {
		return orders;
	}
    
    public void addOrder(Order order) {
		order.setParent(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "order"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "order_id", nullable = false  )
	public Set<OrderAction> getOrderActions() {
		return this.orderActions;
	}
    
    public void addOrderAction(OrderAction orderAction) {
		orderAction.setOrder(this);
		this.orderActions.add(orderAction);
	}
    
    public void setOrderActions(final Set<OrderAction> orderAction) {
		this.orderActions = orderAction;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "order"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "order_id", nullable = false  )
	public Set<OrderGoods> getOrderGoodss() {
		return this.orderGoodss;	
	}
    
    public void addOrderGoods(OrderGoods orderGoods) {
		orderGoods.setOrder(this);
		this.orderGoodss.add(orderGoods);
	}
    
    public void setOrderGoodss(final Set<OrderGoods> orderGoods) {
		this.orderGoodss = orderGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "order"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "order_id", nullable = false  )
	public Set<PayLog> getPayLogs() {
		return this.payLogs;
	}
    
    public void addPayLog(PayLog payLog) {
		payLog.setOrder(this);
		this.payLogs.add(payLog);
	}
    
    public void setPayLogs(final Set<PayLog> payLog) {
		this.payLogs = payLog;
	}
}
