/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.GoodsType;

public interface GoodsTypeDAO extends DAO {
    public List<GoodsType> getGoodsTypeList();

    public GoodsType getGoodsType(String id);

    public void saveGoodsType(GoodsType obj);

    public void removeGoodsType(String id);
    
    public List<GoodsType> getGoodsTypeListWithAttrCount(int firstRow, int maxRow, String whereClause);
}
