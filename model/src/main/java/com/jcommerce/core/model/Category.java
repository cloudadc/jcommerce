/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends ModelObject {
	
private String id;
    
	@Id 
	@GeneratedValue
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
	private static final long serialVersionUID = 7140077625195113814L;
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
    
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
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
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "cat_id", nullable = false  )
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
    
    @ManyToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "categories"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
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

    @Basic( optional = true )
	@Column( name = "cat_name", length = 90  )
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
	@Column( name = "cat_desc", length = 255  )
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
	@Column( name = "template_file", length = 255  )
	public String getTemplateFile() {
        return templateFile;
    }
    
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
    
    @Basic( optional = true )
	@Column( name = "measure_unit", length = 15  )
    public String getMeasureUnit() {
        return measureUnit;
    }
    
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    @Basic( optional = true )
	@Column( name = "show_in_nav"  )
    public boolean isShowInNavigator() {
        return showInNavigator;
    }
    
    public void setShowInNavigator(boolean showInNavigator) {
        this.showInNavigator = showInNavigator;
    }
    
    @Basic( optional = true )
	@Column( length = 150  )
    public String getStyle() {
        return style;
    }
    
    public void setStyle(String style) {
        this.style = style;
    }
    
    @Basic( optional = true )
	@Column( name = "is_show"  )
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

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "filter_attr", nullable = true )
    public Attribute getFilterAttribute() {
        return filterAttribute;
    }

    public void setFilterAttribute(Attribute filterAttribute) {
        this.filterAttribute = filterAttribute;
    }
}
