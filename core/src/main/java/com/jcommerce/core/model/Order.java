/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class Order extends ModelObject {
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
    
    public String getSN() {
        return SN;
    }

    public void setSN(String sn) {
        SN = sn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSignBuilding() {
        return signBuilding;
    }

    public void setSignBuilding(String signBuilding) {
        this.signBuilding = signBuilding;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardMessage() {
        return cardMessage;
    }

    public void setCardMessage(String cardMessage) {
        this.cardMessage = cardMessage;
    }

    public String getInvoicePayee() {
        return invoicePayee;
    }

    public void setInvoicePayee(String invoicePayee) {
        this.invoicePayee = invoicePayee;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(double insureFee) {
        this.insureFee = insureFee;
    }

    public double getPayFee() {
        return payFee;
    }

    public void setPayFee(double payFee) {
        this.payFee = payFee;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public String getHowOss() {
        return howOss;
    }

    public void setHowOss(String howOss) {
        this.howOss = howOss;
    }

    public String getHowSurplus() {
        return howSurplus;
    }

    public void setHowSurplus(String howSurplus) {
        this.howSurplus = howSurplus;
    }

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

    public double getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(double integralMoney) {
        this.integralMoney = integralMoney;
    }

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

    public int getFromAD() {
        return fromAD;
    }

    public void setFromAD(int fromAD) {
        this.fromAD = fromAD;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Timestamp getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Timestamp shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public UserBonus getUserBonus() {
        return userBonus;
    }

    public void setUserBonus(UserBonus userBonus) {
        this.userBonus = userBonus;
    }

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public String getToBuyer() {
        return toBuyer;
    }

    public void setToBuyer(String toBuyer) {
        this.toBuyer = toBuyer;
    }

    public String getPayNote() {
        return payNote;
    }

    public void setPayNote(String payNote) {
        this.payNote = payNote;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

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

    public Order getParent() {
        return parent;
    }

    public void setParent(Order parent) {
        this.parent = parent;
    }

	public double getCardFee() {
		return cardFee;
	}

	public void setCardFee(double cardFee) {
		this.cardFee = cardFee;
	}

	public double getPackFee() {
		return packFee;
	}

	public void setPackFee(double packFee) {
		this.packFee = packFee;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getInvoiceNote() {
        return invoiceNote;
    }

    public void setInvoiceNote(String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }
}
