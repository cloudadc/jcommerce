/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart", catalog = "ishop")
public class Cart extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
	private static final long serialVersionUID = 9116340581945936142L;
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

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "session_id", nullable = true )
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "goods_id", nullable = true )
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Basic( optional = true )
	@Column( name = "goods_attr", length = 255  )
    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

    @Basic( optional = true )
	@Column( name = "goods_sn", length = 2147483647  )
    public String getGoodsSN() {
        return goodsSN;
    }

    public void setGoodsSN(String goodsSN) {
        this.goodsSN = goodsSN;
    }

    @Basic( optional = true )
	@Column( name = "goods_name", length = 255  )
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Basic( optional = true )
	@Column( name = "goods_weight"  )
    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    @Basic( optional = true )
	@Column( name = "market_price"  )
    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Basic( optional = true )
	@Column( name = "goods_price"  )
    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Basic( optional = true )
	@Column( name = "goods_number"  )
    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    @Basic( optional = true )
	@Column( name = "is_real"  )
    public boolean isRealGoods() {
        return realGoods;
    }

    public void setRealGoods(boolean realGoods) {
        this.realGoods = realGoods;
    }

    @Basic( optional = true )
	@Column( name = "extension_code", length = 255  )
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

    @Basic( optional = true )
	@Column( name = "rec_type"  )
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic( optional = true )
	@Column( name = "is_gift"  )
    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    @Basic( optional = true )
	@Column( name = "can_handsel"  )
    public boolean isHandSelectable() {
        return handSelectable;
    }

    public void setHandSelectable(boolean handSelectable) {
        this.handSelectable = handSelectable;
    }

    @Basic( optional = true )
	@Column( name = "goods_thumb", length = 255  )
    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }    
}
