/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.service.Criteria;
public interface GoodsAttributeManager extends Manager {
    public GoodsAttribute initialize(GoodsAttribute obj);

    public List<GoodsAttribute> getGoodsAttributeList(int firstRow, int maxRow);

    public int getGoodsAttributeCount(Criteria criteria);

    public List<GoodsAttribute> getGoodsAttributeList(Criteria criteria);

    public List<GoodsAttribute> getGoodsAttributeList(int firstRow, int maxRow, Criteria criteria);

    public List<GoodsAttribute> getGoodsAttributeList();

    public GoodsAttribute getGoodsAttribute(String id);

    public void saveGoodsAttribute(GoodsAttribute obj);

    public void removeGoodsAttribute(String id);
}
