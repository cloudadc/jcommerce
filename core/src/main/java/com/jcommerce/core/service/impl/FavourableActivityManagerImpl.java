/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.FavourableActivityDAO;
import com.jcommerce.core.model.FavourableActivity;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.FavourableActivityManager;

@Service("favourableActivityManager")
public class FavourableActivityManagerImpl extends ManagerImpl implements FavourableActivityManager {
    private static Log log = LogFactory.getLog(FavourableActivityManagerImpl.class);
    
    @Autowired
    private FavourableActivityDAO dao;

    public void setFavourableActivityDAO(FavourableActivityDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public FavourableActivity initialize(FavourableActivity obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getFavourableActivity(obj.getId());
        }
        return obj;
    }

    public List<FavourableActivity> getFavourableActivityList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<FavourableActivity>)list;
    }

    public int getFavourableActivityCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<FavourableActivity> getFavourableActivityList(Criteria criteria) {
        List list = getList(criteria);
        return (List<FavourableActivity>)list;
    }

    public List<FavourableActivity> getFavourableActivityList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<FavourableActivity>)list;
    }

    public List<FavourableActivity> getFavourableActivityList() {
        return dao.getFavourableActivityList();
    }

    public FavourableActivity getFavourableActivity(String id) {
        FavourableActivity obj = dao.getFavourableActivity(id);
        return obj;
    }

    public void saveFavourableActivity(FavourableActivity obj) {
        dao.saveFavourableActivity(obj);
    }

    public void removeFavourableActivity(String id) {
        dao.removeFavourableActivity(id);
    }
}
