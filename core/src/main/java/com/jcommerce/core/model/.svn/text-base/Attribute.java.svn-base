/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

public class Attribute extends ModelObject {
    
	public static final int TYPE_NEEDNOTSELECT = 0; 
    public static final int TYPE_NEEDSELECT = 1; 

    public static final int INPUT_SINGLELINETEXT = 0;
    public static final int INPUT_MULTIPLELINETEXT = 2;
    public static final int INPUT_CHOICE = 1;
    
    private GoodsType goodsType;
    private String name;
    private int inputType;

    /**
     * 购买商品时是否需要选择该属性的值
     */
    private int type;
    private String values;
    /**
     * 能否进行检索. 0 - can not search, >0 - can search 
     */
    private int index;
    private int sortOrder;
    /**
     * 相同属性值的商品是否关联
     */
    private boolean linked;
    private int group;

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

}
