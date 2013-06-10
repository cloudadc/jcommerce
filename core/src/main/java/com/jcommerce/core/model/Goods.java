/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Goods extends ModelObject {
    private Set<Category> categories = new HashSet<Category>();
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
    private Set<Gallery> galleries = new HashSet<Gallery>();
    private Set<Article> articles;
    private Set<GoodsAttribute> attributes = new HashSet<GoodsAttribute>();
    
    public String getSN() {
        return SN;
    }
    
    public void setSN(String sn) {
        SN = sn;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNameStyle() {
        return nameStyle;
    }
    
    public void setNameStyle(String nameStyle) {
        this.nameStyle = nameStyle;
    }
    
    public int getClickCount() {
        return clickCount;
    }
    
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
    
    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

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

    public Brand getBrand() {
        return brand;
    }
    
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getMarketPrice() {
        return marketPrice;
    }
    
    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }
    
    public double getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(double shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    public double getPromotePrice() {
        return promotePrice;
    }
    
    public void setPromotePrice(double promotePrice) {
        this.promotePrice = promotePrice;
    }
    
    public Timestamp getPromoteStart() {
        return promoteStart;
    }
    
    public void setPromoteStart(Timestamp promoteStart) {
        this.promoteStart = promoteStart;
    }
    
    public Timestamp getPromoteEnd() {
        return promoteEnd;
    }
    
    public void setPromoteEnd(Timestamp promoteEnd) {
        this.promoteEnd = promoteEnd;
    }
    
    public int getWarnNumber() {
        return warnNumber;
    }
    
    public void setWarnNumber(int warnNumber) {
        this.warnNumber = warnNumber;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    public boolean isOnSale() {
        return onSale;
    }
    
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
    
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
    
    public Timestamp getAddTime() {
        return addTime;
    }
    
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
    
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public boolean isBestSold() {
        return bestSold;
    }
    
    public void setBestSold(boolean bestSold) {
        this.bestSold = bestSold;
    }
    
    public boolean isNewAdded() {
        return newAdded;
    }
    
    public void setNewAdded(boolean newAdded) {
        this.newAdded = newAdded;
    }
    
    public boolean isHotSold() {
        return hotSold;
    }
    
    public void setHotSold(boolean hotSold) {
        this.hotSold = hotSold;
    }
    
    public boolean isPromoted() {
        return promoted;
    }
    
    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
    
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public String getSellerNote() {
        return sellerNote;
    }
    
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote;
    }
    
    public int getGiveIntegral() {
        return giveIntegral;
    }
    
    public void setGiveIntegral(int giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public Set<Gallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(Set<Gallery> galleries) {
        this.galleries = galleries;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<GoodsAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<GoodsAttribute> attributes) {
        this.attributes = attributes;
    }
    
}
