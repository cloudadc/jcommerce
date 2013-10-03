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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "attribute")
public class Attribute extends ModelObject {
	
	private Long id;
	
	@Id 
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = 2974594682841895389L;
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
    
    private Set<Category> categories = new HashSet<Category>();
    
    private Set<GoodsAttribute> goodsAttributes = new HashSet<GoodsAttribute>();

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "cat_id", nullable = true )
    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    @Basic( optional = true )
	@Column( name = "attr_name", length = 60  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "attr_input_type"  )
    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    @Basic( optional = true )
	@Column( name = "attr_type"  )
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic( optional = true )
	@Column( name = "attr_values", length = 2147483647  )
    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Basic( optional = true )
	@Column( name = "attr_index"  )
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
	@Column( name = "is_linked"  )
    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    @Basic( optional = true )
	@Column( name = "attr_group"  )
    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "filterAttribute"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "attr_id", nullable = false  )
	public Set<Category> getCategories() {
		return this.categories;
	}
    
    public void addCategory(Category category) {
		category.setFilterAttribute(this);
		this.categories.add(category);
	}
    
    public void setCategories(final Set<Category> category) {
		this.categories = category;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "attribute"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "attr_id", nullable = false  )
	public Set<GoodsAttribute> getGoodsAttributes() {
		return this.goodsAttributes;
	}
    
    public void addGoodsAttribute(GoodsAttribute goodsAttribute) {
		goodsAttribute.setAttribute(this);
		this.goodsAttributes.add(goodsAttribute);
	}
    
    public void setGoodsAttributes(final Set<GoodsAttribute> goodsAttribute) {
		this.goodsAttributes = goodsAttribute;
	}

}
