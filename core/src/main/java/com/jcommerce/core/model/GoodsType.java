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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "goods_type", catalog = "ishop")
public class GoodsType extends ModelObject {
	
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

	private static final long serialVersionUID = -8336026671358179797L;
	// liyong: keep same as in client side model constants, i.e. IGoodsType
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String ENABLED = "enabled";
	public static final String ATTRIBUTEGROUP = "attributeGroup";
	public static final String ATTRCOUNT = "attrcount";
	
	private String name;
	private boolean enabled;
	private Set<Attribute> attributes = new HashSet<Attribute>();
	private int attrCount;
	
	private Set<Goods> goodss = new HashSet<Goods>();
	
	
    /**
     * ',' separated groups
     */
	private String attributeGroup;

	@Basic( optional = true )
	@Column( name = "type_name", length = 60  )
	public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic( optional = true )
	@Column( name = "attr_group", length = 255  )
    public String getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(String attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public String[] getAttributeGroups() {
        if (attributeGroup == null) {
            return new String[0];
        }
        
        return attributeGroup.split(",");
    }

    public void setAttributeGroups(String[] groups) {
        StringBuffer sb = new StringBuffer();
        if (groups != null) {
            for (int i = 0; i < groups.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(groups[i]);
            }
        }
        setAttributeGroup(sb.toString());
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "goodsType"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "cat_id", nullable = false  )
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public int getAttrCount() {
		return attrCount;
	}

	public void setAttrCount(int attrcount) {
		this.attrCount = attrcount;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "type"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "cat_id", nullable = false  )
	public Set<Goods> getGoodss() {
		return this.goodss;
		
	}
	
	public void addGoods(Goods goods) {
		goods.setType(this);
		this.goodss.add(goods);
	}
	
	public void setGoodss(final Set<Goods> goods) {
		this.goodss = goods;
	}
}
