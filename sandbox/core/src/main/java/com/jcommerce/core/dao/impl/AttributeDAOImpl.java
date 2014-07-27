/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AttributeDAO;
import com.jcommerce.core.model.Attribute;

@Repository
@SuppressWarnings("unchecked")
public class AttributeDAOImpl extends DAOImpl implements AttributeDAO {
    public AttributeDAOImpl() {
        modelClass = Attribute.class;
    }

    public List<Attribute> getAttributeList() {
        return getList();
    }

    public Attribute getAttribute(Long id) {
        return (Attribute)getById(id);
    }

    public void saveAttribute(Attribute obj) {
        save(obj);
    }

    public void removeAttribute(Long id) {
        deleteById(id);
    }
}
