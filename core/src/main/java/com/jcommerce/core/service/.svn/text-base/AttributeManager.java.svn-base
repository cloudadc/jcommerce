/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.service.Criteria;
public interface AttributeManager extends Manager {
    public Attribute initialize(Attribute obj);

    public List<Attribute> getAttributeList(int firstRow, int maxRow);

    public int getAttributeCount(Criteria criteria);

    public List<Attribute> getAttributeList(Criteria criteria);

    public List<Attribute> getAttributeList(int firstRow, int maxRow, Criteria criteria);

    public List<Attribute> getAttributeList();

    public Attribute getAttribute(String id);

    public void saveAttribute(Attribute obj);

    public void removeAttribute(String id);
}
