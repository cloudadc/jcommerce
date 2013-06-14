/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

public class Category extends ModelObject {
    
    private Category parent;
    private Set<Category> children = new HashSet<Category>();
    private Set<Goods> goodsList = new HashSet<Goods>();
    private String name;
    private String keywords;
    private String description;
    private int sortOrder;
    private String templateFile;
    private String measureUnit;
    
    /**
     * 是否显示在导航栏
     */
    private boolean showInNavigator;
    private String style;
    private boolean show;
    /**
     * 价格区间个数
     */
    private int grade;
    private Attribute filterAttribute;
    
   public Category getParent() {
        return parent;
    }
    
    public void setParent(Category parent) {
        if (getParent() != null) {
            getParent().removeChild(this);
        }
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }
    
    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        if (children == null) {
            this.children.clear();
        } else {
            this.children = children;
        }
    }

    public void addChild(Category child) {
        children.add(child);
        child.parent = this;
    }
    
    public void removeChild(Category child) {
        children.remove(child);
        child.parent = null;
    }
    
    public Set<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(Set<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public void addGoods(Goods goods) {
        if (!goodsList.contains(goods)) {
            this.goodsList.add(goods);
            goods.addCategory(this);
        }
    }

    public void removeGoods(Goods goods) {
        if (goodsList.contains(goods)) {
            this.goodsList.remove(goods);
            goods.removeCategory(this);
        }
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
            
    public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTemplateFile() {
        return templateFile;
    }
    
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
    
    public String getMeasureUnit() {
        return measureUnit;
    }
    
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    public boolean isShowInNavigator() {
        return showInNavigator;
    }
    
    public void setShowInNavigator(boolean showInNavigator) {
        this.showInNavigator = showInNavigator;
    }
    
    public String getStyle() {
        return style;
    }
    
    public void setStyle(String style) {
        this.style = style;
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Attribute getFilterAttribute() {
        return filterAttribute;
    }

    public void setFilterAttribute(Attribute filterAttribute) {
        this.filterAttribute = filterAttribute;
    }
}
