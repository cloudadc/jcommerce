/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.UserBonusDAO;
import com.jcommerce.core.model.UserBonus;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.UserBonusManager;

@Service("userBonusManager")
public class UserBonusManagerImpl extends ManagerImpl implements UserBonusManager {
    private static Log log = LogFactory.getLog(UserBonusManagerImpl.class);
    
    @Autowired
    private UserBonusDAO dao;

    public void setUserBonusDAO(UserBonusDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public UserBonus initialize(UserBonus obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getUserBonus(obj.getId());
        }
        return obj;
    }

    public List<UserBonus> getUserBonusList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<UserBonus>)list;
    }

    public int getUserBonusCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<UserBonus> getUserBonusList(Criteria criteria) {
        List list = getList(criteria);
        return (List<UserBonus>)list;
    }

    public List<UserBonus> getUserBonusList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<UserBonus>)list;
    }

    public List<UserBonus> getUserBonusList() {
        return dao.getUserBonusList();
    }

    public UserBonus getUserBonus(String id) {
        UserBonus obj = dao.getUserBonus(id);
        return obj;
    }

    public void saveUserBonus(UserBonus obj) {
        dao.saveUserBonus(obj);
    }

    public void removeUserBonus(String id) {
        dao.removeUserBonus(id);
    }
}
