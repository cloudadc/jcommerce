package com.jcommerce.gwt.client.model;


/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
public interface IOrderInfo extends IModelObject {
   
   // relation
    public static final int OS_UNCONFIRMED = 0; // 未确认
    public static final int OS_CONFIRMED = 1; // 已确认
    public static final int OS_CANCELED = 2; // 已取消
    public static final int OS_INVALID = 3; // 无效
    public static final int OS_RETURNED = 4; // 退货
    
    public static final int SS_UNSHIPPED = 0; // 未发货
    public static final int SS_SHIPPED = 1; // 已发货
    public static final int SS_RECEIVED = 2; // 已收货
    public static final int SS_PREPARING = 3; // 备货中

    public static final int PS_UNPAYED = 0; // 未付款
    public static final int PS_PAYING = 1; // 付款中
    public static final int PS_PAYED = 2; // 已付款      
  // fields
  public static final String ORDER_ID = "id"; 
  public static final String ORDER_SN = "SN"; 
  public static final String USER_ID = "user"; 
  public static final String ORDER_STATUS = "status"; 
  public static final String SHIPPING_STATUS = "shippingStatus"; 
  public static final String PAY_STATUS = "payStatus"; 
  public static final String CONSIGNEE = "consignee"; 
//  public static final String COUNTRY = "country"; 
//  public static final String PROVINCE = "province"; 
//  public static final String CITY = "city"; 
//  public static final String DISTRICT = "district";
  public static final String REGION = "region";
  public static final String ADDRESS = "address"; 
  public static final String ZIPCODE = "zip"; 
  public static final String TEL = "tel"; 
  public static final String MOBILE = "mobile"; 
  public static final String EMAIL = "email"; 
  public static final String BEST_TIME = "bestTime"; 
  public static final String SIGN_BUILDING = "signBuilding"; 
  public static final String POSTSCRIPT = "postscript"; 
  public static final String SHIPPING_ID = "shipping"; 
  public static final String SHIPPING_NAME = "shippingName"; 
  public static final String PAY_ID = "payment"; 
  public static final String PAY_NAME = "payName"; 
  public static final String HOW_OOS = "howOss"; 
  public static final String HOW_SURPLUS = "howSurplus"; 
  public static final String PACK_NAME = "packName"; 
  public static final String CARD_NAME = "cardName"; 
  public static final String CARD_MESSAGE = "cardMessage"; 
  public static final String INV_PAYEE = "invoicePayee"; 
  public static final String INV_CONTENT = "invoiceContent"; 
  public static final String GOODS_AMOUNT = "goodsAmount"; 
  public static final String SHIPPING_FEE = "shippingFee"; 
  public static final String INSURE_FEE = "insureFee"; 
  public static final String PAY_FEE = "payFee"; 
  public static final String PACK_FEE = "packFee"; 
  public static final String CARD_FEE = "cardFee"; 
  public static final String MONEY_PAID = "moneyPaid"; 
  public static final String SURPLUS = "surplus"; 
  public static final String INTEGRAL = "integral"; 
  public static final String INTEGRAL_MONEY = "integralMoney"; 
  public static final String BONUS = "bonusMoney"; 
  public static final String ORDER_AMOUNT = "orderAmount"; 
  public static final String FROM_AD = "fromAd"; 
  public static final String REFERER = "referer"; 
  public static final String ADD_TIME = "addTime"; 
  public static final String CONFIRM_TIME = "confirmTime"; 
  public static final String PAY_TIME = "payTime"; 
  public static final String SHIPPING_TIME = "shippingTime"; 
  public static final String PACK_ID = "pack"; 
  public static final String CARD_ID = "card"; 
  public static final String BONUS_ID = "userBonus"; 
  public static final String INVOICE_NO = "invoiceNO"; 
  public static final String EXTENSION_CODE = "extensionCode"; 
  public static final String EXTENSION_ID = "extensionId"; 
  public static final String TO_BUYER = "toBuyer"; 
  public static final String PAY_NOTE = "payNote"; 
  public static final String AGENCY_ID = "agency"; 
  public static final String INV_TYPE = "invoceType"; 
  public static final String TAX = "tax"; 
  public static final String IS_SEPARATE = "separate"; 
  public static final String PARENT_ID = "parent"; 
  public static final String DISCOUNT = "discount"; 


}
