/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.LinkGoods;
import com.jcommerce.core.service.Criteria;
public interface LinkGoodsManager extends Manager {
    public LinkGoods initialize(LinkGoods obj);

    public List<LinkGoods> getLinkGoodsList(int firstRow, int maxRow);

    public int getLinkGoodsCount(Criteria criteria);

    public List<LinkGoods> getLinkGoodsList(Criteria criteria);

    public List<LinkGoods> getLinkGoodsList(int firstRow, int maxRow, Criteria criteria);

    public List<LinkGoods> getLinkGoodsList();

    public LinkGoods getLinkGoods(String id);

    public void saveLinkGoods(LinkGoods obj);

    public void removeLinkGoods(String id);
}
