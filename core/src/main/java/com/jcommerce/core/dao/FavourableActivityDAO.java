/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.FavourableActivity;

public interface FavourableActivityDAO extends DAO {
    public List<FavourableActivity> getFavourableActivityList();

    public FavourableActivity getFavourableActivity(Long id);

    public void saveFavourableActivity(FavourableActivity obj);

    public void removeFavourableActivity(Long id);
}
