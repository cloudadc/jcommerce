/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.GoodsArticle;

public interface GoodsArticleDAO extends DAO {
    public List<GoodsArticle> getGoodsArticleList();

    public GoodsArticle getGoodsArticle(String id);

    public void saveGoodsArticle(GoodsArticle obj);

    public void removeGoodsArticle(String id);
}
