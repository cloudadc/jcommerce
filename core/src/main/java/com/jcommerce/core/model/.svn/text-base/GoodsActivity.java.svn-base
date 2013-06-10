/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class GoodsActivity extends ModelObject {
    
	public static final int TYPE_SNATCH = Constants.GOODSACT_SNATCH;
    public static final int TYPE_GROUP_BUY = Constants.GOODSACT_GROUP_BUY;
    public static final int TYPE_AUCTION = Constants.GOODSACT_AUCTION;
    public static final int TYPE_POINTS_BUY = Constants.GOODSACT_POINTS_BUY;
    
    private String name;
    private String description;
    private int type;
    private Timestamp startTime;
    private Timestamp endTime;
    private Goods goods;
    private boolean finished;
    private String extraInfo;

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

}
