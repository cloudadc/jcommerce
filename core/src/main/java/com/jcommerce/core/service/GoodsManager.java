/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Goods;

public interface GoodsManager extends Manager {
    public Goods initialize(Goods obj);

    public List<Goods> getGoodsList(int firstRow, int maxRow);
    
    public int getGoodsCount();
    
    public int getGoodsCount(Criteria criteria);
    
    public List<Goods> getGoodsList(Criteria criteria);
    
    public List<Goods> getGoodsList(int firstRow, int maxRow, Criteria criteria);
    
    public List<Goods> getGoodsList();

    public Goods getGoods(String id);

    public void saveGoods(Goods obj);

    public void removeGoods(String id);
    
    public Criteria getBestSoldGoodsCriteria();
    
    public Criteria getHotSoldGoodsCriteria();
    
    public Criteria getNewGoodsCriteria();

    public List<Goods> getBestSoldGoodsList();

    public List<Goods> getNewGoodsList();

    public List<Goods> getHotSoldGoodsList();
    
    public List<Goods> getGoodsListByIds (List<String> ids);
    
    public void purgeGoods(String id);
    
    public void undoDeletedGoods(String id);
}
