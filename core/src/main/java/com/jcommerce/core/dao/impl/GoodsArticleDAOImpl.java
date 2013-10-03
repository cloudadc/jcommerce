/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GoodsArticleDAO;
import com.jcommerce.core.model.GoodsArticle;

@Repository
@SuppressWarnings("unchecked")
public class GoodsArticleDAOImpl extends DAOImpl implements GoodsArticleDAO {
    public GoodsArticleDAOImpl() {
        modelClass = GoodsArticle.class;
    }

    public List<GoodsArticle> getGoodsArticleList() {
        return getList();
    }

    public GoodsArticle getGoodsArticle(Long id) {
        return (GoodsArticle)getById(id);
    }

    public void saveGoodsArticle(GoodsArticle obj) {
        save(obj);
    }

    public void removeGoodsArticle(Long id) {
        deleteById(id);
    }
}
