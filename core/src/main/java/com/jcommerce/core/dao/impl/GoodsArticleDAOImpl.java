/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.GoodsArticleDAO;
import com.jcommerce.core.model.GoodsArticle;

public class GoodsArticleDAOImpl extends DAOImpl implements GoodsArticleDAO {
    public GoodsArticleDAOImpl() {
        modelClass = GoodsArticle.class;
    }

    public List<GoodsArticle> getGoodsArticleList() {
        return getList();
    }

    public GoodsArticle getGoodsArticle(String id) {
        return (GoodsArticle)getById(id);
    }

    public void saveGoodsArticle(GoodsArticle obj) {
        save(obj);
    }

    public void removeGoodsArticle(String id) {
        deleteById(id);
    }
}
