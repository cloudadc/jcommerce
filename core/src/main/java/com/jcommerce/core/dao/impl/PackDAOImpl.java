/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.PackDAO;
import com.jcommerce.core.model.Pack;

@Repository
@SuppressWarnings("unchecked")
public class PackDAOImpl extends DAOImpl implements PackDAO {
    public PackDAOImpl() {
        modelClass = Pack.class;
    }

    public List<Pack> getPackList() {
        return getList();
    }

    public Pack getPack(String id) {
        return (Pack)getById(id);
    }

    public void savePack(Pack obj) {
        save(obj);
    }

    public void removePack(String id) {
        deleteById(id);
    }
}
