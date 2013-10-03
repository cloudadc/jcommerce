/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Attribute;

public interface AttributeDAO extends DAO {
    public List<Attribute> getAttributeList();

    public Attribute getAttribute(Long id);

    public void saveAttribute(Attribute obj);

    public void removeAttribute(Long id);
}
