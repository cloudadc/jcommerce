/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.GroupGoods;
import com.jcommerce.core.service.Criteria;
public interface GroupGoodsManager extends Manager {
    public GroupGoods initialize(GroupGoods obj);

    public List<GroupGoods> getGroupGoodsList(int firstRow, int maxRow);

    public int getGroupGoodsCount(Criteria criteria);

    public List<GroupGoods> getGroupGoodsList(Criteria criteria);

    public List<GroupGoods> getGroupGoodsList(int firstRow, int maxRow, Criteria criteria);

    public List<GroupGoods> getGroupGoodsList();

    public GroupGoods getGroupGoods(String id);

    public void saveGroupGoods(GroupGoods obj);

    public void removeGroupGoods(String id);
}
