/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.FavourableActivityDAO;
import com.jcommerce.core.model.FavourableActivity;

@Repository
@SuppressWarnings("unchecked")
public class FavourableActivityDAOImpl extends DAOImpl implements FavourableActivityDAO {
    public FavourableActivityDAOImpl() {
        modelClass = FavourableActivity.class;
    }

    public List<FavourableActivity> getFavourableActivityList() {
        return getList();
    }

    public FavourableActivity getFavourableActivity(String id) {
        return (FavourableActivity)getById(id);
    }

    public void saveFavourableActivity(FavourableActivity obj) {
        save(obj);
    }

    public void removeFavourableActivity(String id) {
        deleteById(id);
    }
}
