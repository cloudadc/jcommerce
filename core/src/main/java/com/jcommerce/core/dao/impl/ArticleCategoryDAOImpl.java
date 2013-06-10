/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.ArticleCategoryDAO;
import com.jcommerce.core.model.ArticleCategory;

public class ArticleCategoryDAOImpl extends DAOImpl implements ArticleCategoryDAO {
    public ArticleCategoryDAOImpl() {
        modelClass = ArticleCategory.class;
    }

    public List<ArticleCategory> getArticleCategoryList() {
        return getList();
    }

    public ArticleCategory getArticleCategory(String id) {
        return (ArticleCategory)getById(id);
    }

    public void saveArticleCategory(ArticleCategory obj) {
        save(obj);
    }

    public void removeArticleCategory(String id) {
        deleteById(id);
    }
}
