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

import com.jcommerce.core.dao.UserRankDAO;
import com.jcommerce.core.model.UserRank;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.UserRankManager;

@Service("UserRankManager")
public class UserRankManagerImpl extends ManagerImpl implements UserRankManager {
    private static Log log = LogFactory.getLog(UserRankManagerImpl.class);
    
    @Autowired
    private UserRankDAO dao;

    public void setUserRankDAO(UserRankDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public UserRank initialize(UserRank obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getUserRank(obj.getId());
        }
        return obj;
    }

    public List<UserRank> getUserRankList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<UserRank>)list;
    }

    public int getUserRankCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<UserRank> getUserRankList(Criteria criteria) {
        List list = getList(criteria);
        return (List<UserRank>)list;
    }

    public List<UserRank> getUserRankList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<UserRank>)list;
    }

    public List<UserRank> getUserRankList() {
        return dao.getUserRankList();
    }

    public UserRank getUserRank(String id) {
        UserRank obj = dao.getUserRank(id);
        return obj;
    }

    public void saveUserRank(UserRank obj) {
        dao.saveUserRank(obj);
    }

    public void removeUserRank(String id) {
        dao.removeUserRank(id);
    }
}
