/**
 * @author KingZhao
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shop_config", catalog = "ishop")
public class ShopConfig extends ModelObject {
	
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

	private static final long serialVersionUID = 8079659670356189625L;
	public final static int DISPLAY_LIST = 0;
    public final static int DISPLAY_GRID = 1;
    public final static int DISPLAY_TEXT = 2;

    public final static int GOODS_TEXTIMAGE = 0;
    public final static int GOODS_TEXT = 1;
    public final static int GOODS_IMAGE = 2;

    public final static int SORT_DESC = 0;
    public final static int SORT_ASC = 1;
    
    public final static int SORT_BY_ONSHELFTIME = 0;
    public final static int SORT_BY_PRICE = 1;
    public final static int SORT_BY_UPDATE = 2;
    
    public final static int ATTR_TEXT = 1;
    public final static int ATTR_RANGE = 2;
    public final static int ATTR_OPTIONAL = 3;
    public final static int ATTR_TEXTAREA = 4;
    public final static int ATTR_URL = 5;
    
	private ShopConfig parent;
	private Set<ShopConfig> children = new HashSet<ShopConfig>();
	private String code;
	private String type;
	private String storeRange;
	private String storeDir;
	private String value;
	private int sortOrder;

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
	public ShopConfig getParent() {
		return parent;
	}

	public void setParent(ShopConfig parent) {
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
	@Column( nullable = false  )
	public Set<ShopConfig> getChildren() {
		return children;
	}

	public void setChildren(Set<ShopConfig> children) {
		if (children == null) {
			this.children.clear();
		} else {
			this.children = children;
		}
	}

	public void addChild(ShopConfig child) {
		children.add(child);
		child.parent = this;
	}

	public void removeChild(ShopConfig child) {
		children.remove(child);
		child.parent = null;
	}

	@Basic( optional = true )
	@Column( length = 30  )
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic( optional = true )
	@Column( length = 10  )
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic( optional = true )
	@Column( name = "store_range", length = 255  )
	public String getStoreRange() {
		return storeRange;
	}

	public void setStoreRange(String storeRange) {
		this.storeRange = storeRange;
	}

	@Basic( optional = true )
	@Column( name = "store_dir", length = 255  )
	public String getStoreDir() {
		return storeDir;
	}

	public void setStoreDir(String storeDir) {
		this.storeDir = storeDir;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Basic( optional = true )
	@Column( name = "sort_order"  )
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
