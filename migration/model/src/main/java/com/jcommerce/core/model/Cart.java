/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

public class Cart extends ModelObject {
    public static final int TYPE_GENERAL_GOODS = Constants.CART_GENERAL_GOODS; // 普通商品
    public static final int TYPE_GROUP_BUY_GOODS = Constants.CART_GROUP_BUY_GOODS; // 团购商品
    public static final int TYPE_AUCTION_GOODS = Constants.CART_AUCTION_GOODS; // 拍卖商品
    public static final int TYPE_SNATCH_GOODS = Constants.CART_SNATCH_GOODS; // 夺宝奇兵
    
    private User user;
    private Session session;
    private Goods goods;
    private String goodsSN;
    private String goodsName;
    private String goodsThumb;
    private double marketPrice;
    private double goodsPrice;
    private double goodsWeight;
    private int goodsNumber;
    // String goodsAttribute;
    private boolean realGoods;
    private String extensionCode;
    private Cart parent;
    private int type;   // TYPE_xxx
    private boolean gift;
    private boolean handSelectable;
    private String goodsAttribute;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

    public String getGoodsSN() {
        return goodsSN;
    }

    public void setGoodsSN(String goodsSN) {
        this.goodsSN = goodsSN;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public boolean isRealGoods() {
        return realGoods;
    }

    public void setRealGoods(boolean realGoods) {
        this.realGoods = realGoods;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public Cart getParent() {
        return parent;
    }

    public void setParent(Cart parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public boolean isHandSelectable() {
        return handSelectable;
    }

    public void setHandSelectable(boolean handSelectable) {
        this.handSelectable = handSelectable;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }    
}
