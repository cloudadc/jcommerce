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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "goods", catalog = "ishop")
public class Goods extends ModelObject {
	
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

	private static final long serialVersionUID = -247792556865375989L;
	
    private String SN;
    private String name;
    private String nameStyle;
    private int clickCount;
    private Brand brand;
    private String providerName;
    private int number;
    private double weight;
    private double marketPrice;
    private double shopPrice;
    private double promotePrice;
    private Timestamp promoteStart;
    private Timestamp promoteEnd;
    private int warnNumber;
    private String keywords;
    private String brief;
    private String description;
    private String thumb;
    private String image;
    private String originalImage;
    private boolean realGoods;
    private String extensionCode;
    private boolean onSale;
    private boolean aloneSale;
    private int integral;
    private Timestamp addTime;
    private int sortOrder;  // sortOrder value is used in SQL ORDER BY
    private boolean deleted;
    private boolean bestSold;
    private boolean newAdded;
    private boolean hotSold;
    private boolean promoted;
    private BonusType bonusType;
    private Timestamp lastUpdate;
    private GoodsType type;   // GoodsType table list all the types available
    private String sellerNote;
    private int giveIntegral;
    private Category mainCategory;
     
    private Set<Category> categories = new HashSet<Category>();
    private Set<Gallery> galleries = new HashSet<Gallery>();
    private Set<GoodsAttribute> attributes = new HashSet<GoodsAttribute>();
    private Set<BookingGoods> bookingGoodss = new HashSet<BookingGoods>();
    private Set<Cart> carts = new HashSet<Cart>();
    private Set<CollectGoods> collectGoodss = new HashSet<CollectGoods>();
    private Set<GoodsActivity> goodsActivities = new HashSet<GoodsActivity>();
    private Set<GoodsArticle> goodsArticles = new HashSet<GoodsArticle>();
    private Set<GroupGoods> groupGoodss = new HashSet<GroupGoods>();
    private Set<LinkGoods> linkGoodss = new HashSet<LinkGoods>();
    private Set<MemberPrice> memberPrices = new HashSet<MemberPrice>();
    private Set<OrderGoods> orderGoodss = new HashSet<OrderGoods>();
    private Set<Tag> tags = new HashSet<Tag>();
    private Set<VirtualCard> virtualCards = new HashSet<VirtualCard>();
    private Set<Wholesale> wholesales = new HashSet<Wholesale>();
    
    @Basic( optional = true )
	@Column( name = "goods_sn", length = 60  )
    public String getSN() {
        return SN;
    }
    
    public void setSN(String sn) {
        SN = sn;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_name", length = 120  )
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_name_style", length = 60  )
    public String getNameStyle() {
        return nameStyle;
    }
    
    public void setNameStyle(String nameStyle) {
        this.nameStyle = nameStyle;
    }
    
    @Basic( optional = true )
	@Column( name = "click_count"  )
    public int getClickCount() {
        return clickCount;
    }
    
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
    
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "cat_id", nullable = true )
    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goodsList"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
            category.addGoods(this);
        }
    }

    public void removeCategory(Category category) {
        if (categories.contains(category)) {
            categories.remove(category);
            removeCategory(category);
        }
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "brand_id", nullable = true )
    public Brand getBrand() {
        return brand;
    }
    
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Basic( optional = true )
	@Column( name = "provider_name", length = 100  )
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Basic( optional = true )
	@Column( name = "goods_number"  )
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_weight"  )
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
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
	@Column( name = "shop_price"  )
    public double getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(double shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    @Basic( optional = true )
	@Column( name = "promote_price"  )
    public double getPromotePrice() {
        return promotePrice;
    }
    
    public void setPromotePrice(double promotePrice) {
        this.promotePrice = promotePrice;
    }
    
    @Basic( optional = true )
	@Column( name = "promote_start_date"  )
    public Timestamp getPromoteStart() {
        return promoteStart;
    }
    
    public void setPromoteStart(Timestamp promoteStart) {
        this.promoteStart = promoteStart;
    }
    
    @Basic( optional = true )
	@Column( name = "promote_end_date"  )
    public Timestamp getPromoteEnd() {
        return promoteEnd;
    }
    
    public void setPromoteEnd(Timestamp promoteEnd) {
        this.promoteEnd = promoteEnd;
    }
    
    @Basic( optional = true )
	@Column( name = "warn_number"  )
    public int getWarnNumber() {
        return warnNumber;
    }
    
    public void setWarnNumber(int warnNumber) {
        this.warnNumber = warnNumber;
    }
    
    @Basic( optional = true )
	@Column( length = 255  )
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_brief", length = 255  )
    public String getBrief() {
        return brief;
    }
    
    public void setBrief(String brief) {
        this.brief = brief;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_desc", length = 2147483647  )
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_thumb", length = 255  )
    public String getThumb() {
        return thumb;
    }
    
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
    @Basic( optional = true )
	@Column( name = "goods_img", length = 255  )
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    @Basic( optional = true )
	@Column( name = "original_img", length = 255  )
    public String getOriginalImage() {
        return originalImage;
    }
    
    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
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
	@Column( name = "extension_code", length = 30  )
    public String getExtensionCode() {
        return extensionCode;
    }
    
    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }
    
    @Basic( optional = true )
	@Column( name = "is_on_sale"  )
    public boolean isOnSale() {
        return onSale;
    }
    
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
    
    @Basic( optional = true )
	@Column( name = "is_alone_sale"  )
    public boolean isAloneSale() {
        return aloneSale;
    }
    
    public void setAloneSale(boolean aloneSale) {
        this.aloneSale = aloneSale;
    }
    
    public int getIntegral() {
        return integral;
    }
    
    public void setIntegral(int integral) {
        this.integral = integral;
    }
    
    @Basic( optional = true )
	@Column( name = "add_time"  )
    public Timestamp getAddTime() {
        return addTime;
    }
    
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
    
    @Basic( optional = true )
	@Column( name = "sort_order"  )
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    @Basic( optional = true )
	@Column( name = "is_delete"  )
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    @Basic( optional = true )
	@Column( name = "is_best"  )
    public boolean isBestSold() {
        return bestSold;
    }
    
    public void setBestSold(boolean bestSold) {
        this.bestSold = bestSold;
    }
    
    @Basic( optional = true )
	@Column( name = "is_new"  )
    public boolean isNewAdded() {
        return newAdded;
    }
    
    public void setNewAdded(boolean newAdded) {
        this.newAdded = newAdded;
    }
    
    @Basic( optional = true )
	@Column( name = "is_hot"  )
    public boolean isHotSold() {
        return hotSold;
    }
    
    public void setHotSold(boolean hotSold) {
        this.hotSold = hotSold;
    }
    
    @Basic( optional = true )
	@Column( name = "is_promote"  )
    public boolean isPromoted() {
        return promoted;
    }
    
    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
    
    @Basic( optional = true )
	@Column( name = "last_update"  )
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "goods_type", nullable = true )
    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    @Basic( optional = true )
	@Column( name = "seller_note", length = 255  )
    public String getSellerNote() {
        return sellerNote;
    }
    
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote;
    }
    
    @Basic( optional = true )
	@Column( name = "give_integral"  )
    public int getGiveIntegral() {
        return giveIntegral;
    }
    
    public void setGiveIntegral(int giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "bonus_type_id", nullable = true )
    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
    public Set<Gallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(Set<Gallery> galleries) {
        this.galleries = galleries;
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
    public Set<GoodsAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<GoodsAttribute> attributes) {
        this.attributes = attributes;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<BookingGoods> getBookingGoodss() {
		return this.bookingGoodss;	
	}
    
    public void addBookingGoods(BookingGoods bookingGoods) {
		bookingGoods.setGoods(this);
		this.bookingGoodss.add(bookingGoods);
	}
    
    public void setBookingGoodss(final Set<BookingGoods> bookingGoods) {
		this.bookingGoodss = bookingGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<Cart> getCarts() {
		return this.carts;	
	}
    
    public void addCart(Cart cart) {
		cart.setGoods(this);
		this.carts.add(cart);
	}
    
    public void setCarts(final Set<Cart> cart) {
		this.carts = cart;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<CollectGoods> getCollectGoodss() {
		return collectGoodss;
	}
    
    public void addCollectGoods(CollectGoods collectGoods) {
		collectGoods.setGoods(this);
		this.collectGoodss.add(collectGoods);
	}
    
    public void setCollectGoodss(final Set<CollectGoods> collectGoods) {
		this.collectGoodss = collectGoods;
	}

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<GoodsActivity> getGoodsActivities() {
		return this.goodsActivities;
	}
    
    public void addGoodsActivity(GoodsActivity goodsActivity) {
		goodsActivity.setGoods(this);
		this.goodsActivities.add(goodsActivity);
	}
    
    public void setGoodsActivities(final Set<GoodsActivity> goodsActivity) {
		this.goodsActivities = goodsActivity;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<GoodsArticle> getGoodsArticles() {
		return this.goodsArticles;
	}
    
    public void addGoodsArticle(GoodsArticle goodsArticle) {
		goodsArticle.setGoods(this);
		this.goodsArticles.add(goodsArticle);
	}
    
    public void setGoodsArticles(final Set<GoodsArticle> goodsArticle) {
		this.goodsArticles = goodsArticle;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<GroupGoods> getGroupGoodss() {
		return this.groupGoodss;	
	}
    
    public void addGroupGoods(GroupGoods groupGoods) {
		groupGoods.setGoods(this);
		this.groupGoodss.add(groupGoods);
	}
    
    public void setGroupGoodss(final Set<GroupGoods> groupGoods) {
		this.groupGoodss = groupGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<LinkGoods> getLinkGoodss() {
		return this.linkGoodss;	
	}
    
    public void addLinkGoods(LinkGoods linkGoods) {
		linkGoods.setGoods(this);
		this.linkGoodss.add(linkGoods);
	}
    
    public void setLinkGoodss(final Set<LinkGoods> linkGoods) {
		this.linkGoodss = linkGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<MemberPrice> getMemberPrices() {
		return this.memberPrices;	
	}
    
    public void addMemberPrice(MemberPrice memberPrice) {
		memberPrice.setGoods(this);
		this.memberPrices.add(memberPrice);
	}
    
    public void setMemberPrices(final Set<MemberPrice> memberPrice) {
		this.memberPrices = memberPrice;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<OrderGoods> getOrderGoodss() {
		return this.orderGoodss;	
	}
    
    public void addOrderGoods(OrderGoods orderGoods) {
		orderGoods.setGoods(this);
		this.orderGoodss.add(orderGoods);
	}
    
    public void setOrderGoodss(final Set<OrderGoods> orderGoods) {
		this.orderGoodss = orderGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<Tag> getTags() {
		return this.tags;	
	}
    
    public void addTag(Tag tag) {
		tag.setGoods(this);
		this.tags.add(tag);
	}
    
    public void setTags(final Set<Tag> tag) {
		this.tags = tag;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<VirtualCard> getVirtualCards() {
		return this.virtualCards;	
	}
    
    public void addVirtualCard(VirtualCard virtualCard) {
		virtualCard.setGoods(this);
		this.virtualCards.add(virtualCard);
	}
    
    public void setVirtualCards(final Set<VirtualCard> virtualCard) {
		this.virtualCards = virtualCard;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goods"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "goods_id", nullable = false  )
	public Set<Wholesale> getWholesales() {
		return this.wholesales;
	}
    
    public void addWholesale(Wholesale wholesale) {
		wholesale.setGoods(this);
		this.wholesales.add(wholesale);
	}
    
    public void setWholesales(final Set<Wholesale> wholesale) {
		this.wholesales = wholesale;
	}

}
