/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.FavourableActivity;
import com.jcommerce.core.service.Criteria;
public interface FavourableActivityManager extends Manager {
    public FavourableActivity initialize(FavourableActivity obj);

    public List<FavourableActivity> getFavourableActivityList(int firstRow, int maxRow);

    public int getFavourableActivityCount(Criteria criteria);

    public List<FavourableActivity> getFavourableActivityList(Criteria criteria);

    public List<FavourableActivity> getFavourableActivityList(int firstRow, int maxRow, Criteria criteria);

    public List<FavourableActivity> getFavourableActivityList();

    public FavourableActivity getFavourableActivity(String id);

    public void saveFavourableActivity(FavourableActivity obj);

    public void removeFavourableActivity(String id);
}
