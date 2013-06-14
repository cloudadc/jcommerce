/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Goods extends ModelObject {
	
    @Override
    public ModelObject getParent() {
    	return null;
    }
	
    

    
//    private String url;
//    private String shortStyleName;
	@Persistent
	protected Long longId;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Set<String>categoryIds = new HashSet<String>();
    
//    private Set<Category> categories = new HashSet<Category>();
    
    @Persistent
    private String mainCategoryId;
    
//    private Category mainCategory;
    
    @Persistent(mappedBy="goods")
    private Set<Gallery> galleries = new HashSet<Gallery>();
    
//    private Set<Article> articles;
    
    @Persistent
    private String goodsTypeId;
    
    // uni-direction owned
    @Persistent
    private Set<GoodsAttribute> attributes = new HashSet<GoodsAttribute>();
    
    @Persistent
    private String brandId;
    
//    private Brand brand;
    
//    private GoodsType type;   // GoodsType table list all the types available
    
    @Persistent
    private String goodsSn;
    
    @Persistent
    private String goodsName;
    
    @Persistent
    private String goodsNameStyle;
    
    @Persistent
    private Integer clickCount = 0;
    

    
    @Persistent
    private String providerName;
    
    @Persistent
    private Integer goodsNumber = 0;
    
    @Persistent
    private Double goodsWeight = 0.0;
    
    @Persistent
    private Double marketPrice = 0.0;
    
    @Persistent
    private Double shopPrice = 0.0;
    
    @Persistent
    private Double promotePrice = 0.0;
    
    @Persistent
    private Date promoteStart;
    
    @Persistent
    private Date promoteEnd;
    
    @Persistent
    private Integer warnNumber = 0;
    
    @Persistent
    private String keywords;
    
    @Persistent
    private String brief;
    
    @Persistent
    private String goodsDesc;
    
    @Persistent
    private String thumb;
    
    @Persistent
    private String thumbFileId;
    
    @Persistent
    private DSFile thumbFile;
    
    @Persistent
    private String image;
    
    @Persistent
    private String imageFileId;
    
    @Persistent
    private DSFile imageFile;
    
    @Persistent
    private String originalImage;
    
    @Persistent
    private Boolean realGoods = false;
    
    @Persistent
    private String extensionCode;
    
    @Persistent
    private Boolean onSale = false;
    
    @Persistent
    private Boolean aloneSale = false;
    
    @Persistent
    private Integer integral = 0;
    
    @Persistent
    private Date addTime;
    
    @Persistent
    private Integer sortOrder = 0;  // sortOrder value is used in SQL ORDER BY
    
    @Persistent
    private Boolean deleted = false;
    
    @Persistent
    private Boolean bestSold = false;
    
    @Persistent
    private Boolean neewAdded = false;
    
    @Persistent
    private Boolean hotSold = false;
    
    @Persistent
    private Boolean promoted = false;
    
    
//    private BonusType bonusType;
    @Persistent
    private Date lastUpdate;
    

    
    @Persistent
    private String sellerNote;
    
    @Persistent
    private Integer giveIntegral = 0;
    
    

    

    

    

    

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }


    

 
    

    
    public Date getPromoteStart() {
        return promoteStart;
    }
    
    public void setPromoteStart(Date promoteStart) {
        this.promoteStart = promoteStart;
    }
    
    public Date getPromoteEnd() {
        return promoteEnd;
    }
    
    public void setPromoteEnd(Date promoteEnd) {
        this.promoteEnd = promoteEnd;
    }
    

    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getBrief() {
        return brief;
    }
    
    public void setBrief(String brief) {
        this.brief = brief;
    }
    

    
    public String getThumb() {
        return thumb;
    }
    
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getOriginalImage() {
        return originalImage;
    }
    
    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }


    public String getExtensionCode() {
        return extensionCode;
    }
    
    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }
    

    
    public Date getAddTime() {
        return addTime;
    }
    
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    

    

    

    
    public Date getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    


    public String getSellerNote() {
        return sellerNote;
    }
    
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote;
    }
    

	public Set<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(String mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Set<Gallery> getGalleries() {
		return galleries;
	}

	public void setGalleries(Set<Gallery> galleries) {
		this.galleries = galleries;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public Set<GoodsAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<GoodsAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNameStyle() {
		return goodsNameStyle;
	}

	public void setGoodsNameStyle(String goodsNameStyle) {
		this.goodsNameStyle = goodsNameStyle;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(Double shopPrice) {
		this.shopPrice = shopPrice;
	}

	public Double getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(Double promotePrice) {
		this.promotePrice = promotePrice;
	}

	public Integer getWarnNumber() {
		return warnNumber;
	}

	public void setWarnNumber(Integer warnNumber) {
		this.warnNumber = warnNumber;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getThumbFileId() {
		return thumbFileId;
	}

	public void setThumbFileId(String thumbFileId) {
		this.thumbFileId = thumbFileId;
	}

	public DSFile getThumbFile() {
		return thumbFile;
	}

	public void setThumbFile(DSFile thumbFile) {
		this.thumbFile = thumbFile;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public DSFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(DSFile imageFile) {
		this.imageFile = imageFile;
	}

	public Boolean getRealGoods() {
		return realGoods;
	}

	public void setRealGoods(Boolean realGoods) {
		this.realGoods = realGoods;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public Boolean getAloneSale() {
		return aloneSale;
	}

	public void setAloneSale(Boolean aloneSale) {
		this.aloneSale = aloneSale;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getBestSold() {
		return bestSold;
	}

	public void setBestSold(Boolean bestSold) {
		this.bestSold = bestSold;
	}

	public Boolean getNeewAdded() {
		return neewAdded;
	}

	public void setNeewAdded(Boolean neewAdded) {
		this.neewAdded = neewAdded;
	}

	public Boolean getHotSold() {
		return hotSold;
	}

	public void setHotSold(Boolean hotSold) {
		this.hotSold = hotSold;
	}

	public Boolean getPromoted() {
		return promoted;
	}

	public void setPromoted(Boolean promoted) {
		this.promoted = promoted;
	}

	public Integer getGiveIntegral() {
		return giveIntegral;
	}

	public void setGiveIntegral(Integer giveIntegral) {
		this.giveIntegral = giveIntegral;
	}

	@Override
	public Long getLongId() {
		return longId;
	}

	@Override
	public void setLongId(Long longId) {
		this.longId = longId;
	}






//    public BonusType getBonusType() {
//        return bonusType;
//    }
//
//    public void setBonusType(BonusType bonusType) {
//        this.bonusType = bonusType;
//    }
//
//    public Set<Gallery> getGalleries() {
//        return galleries;
//    }
//
//    public void setGalleries(Set<Gallery> galleries) {
//        this.galleries = galleries;
//    }
//
//    public Set<Article> getArticles() {
//        return articles;
//    }
//
//    public void setArticles(Set<Article> articles) {
//        this.articles = articles;
//    }
//
//    public Set<GoodsAttribute> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(Set<GoodsAttribute> attributes) {
//        this.attributes = attributes;
//    }


}
