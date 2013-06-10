/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.GoodsActivity;
import com.jcommerce.core.service.Criteria;
public interface GoodsActivityManager extends Manager {
    public GoodsActivity initialize(GoodsActivity obj);

    public List<GoodsActivity> getGoodsActivityList(int firstRow, int maxRow);

    public int getGoodsActivityCount(Criteria criteria);

    public List<GoodsActivity> getGoodsActivityList(Criteria criteria);

    public List<GoodsActivity> getGoodsActivityList(int firstRow, int maxRow, Criteria criteria);

    public List<GoodsActivity> getGoodsActivityList();

    public GoodsActivity getGoodsActivity(String id);

    public void saveGoodsActivity(GoodsActivity obj);

    public void removeGoodsActivity(String id);
}
