/**
 * @author KingZhao
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

public class ShopConfig extends ModelObject {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStoreRange() {
		return storeRange;
	}

	public void setStoreRange(String storeRange) {
		this.storeRange = storeRange;
	}

	public String getStoreDir() {
		return storeDir;
	}

	public void setStoreDir(String storeDir) {
		this.storeDir = storeDir;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
