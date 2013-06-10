/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.BonusTypeDAO;
import com.jcommerce.core.model.BonusType;

public class BonusTypeDAOImpl extends DAOImpl implements BonusTypeDAO {
    public BonusTypeDAOImpl() {
        modelClass = BonusType.class;
    }

    public List<BonusType> getBonusTypeList() {
        return getList();
    }

    public BonusType getBonusType(String id) {
        return (BonusType)getById(id);
    }

    public void saveBonusType(BonusType obj) {
        save(obj);
    }

    public void removeBonusType(String id) {
        deleteById(id);
    }
}
