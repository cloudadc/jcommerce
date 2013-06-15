package com.jcommerce.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_feed", catalog = "ishop")
public class UserFeed extends ModelObject {

	private static final long serialVersionUID = -3938981382116493310L;

	private String feedId; 

    private String userId; 

    private String valueId; 

    private String goodsId; 

    private int feedType = 0; 

    private boolean isFeed=false; 

	public UserFeed() {
	}

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public boolean getIsFeed() {
        return isFeed;
    }

    public void setIsFeed(boolean isFeed) {
        this.isFeed = isFeed;
    }
}
