/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;


/**
 * list all goods type
 */
public class GoodsType extends ModelObject {
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
	
	
    /**
     * ',' separated groups
     */
	private String attributeGroup;

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
}
