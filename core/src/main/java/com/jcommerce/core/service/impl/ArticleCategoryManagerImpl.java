/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.ArticleCategoryDAO;
import com.jcommerce.core.model.ArticleCategory;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.ArticleCategoryManager;

@Service("ArticleCategoryManager")
public class ArticleCategoryManagerImpl extends ManagerImpl implements ArticleCategoryManager {
    
    @Autowired
    private ArticleCategoryDAO dao;

    public void setArticleCategoryDAO(ArticleCategoryDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public ArticleCategory initialize(ArticleCategory obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getArticleCategory(obj.getId());
        }
        return obj;
    }

    public List<ArticleCategory> getArticleCategoryList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<ArticleCategory>)list;
    }

    public int getArticleCategoryCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<ArticleCategory> getArticleCategoryList(Criteria criteria) {
        List list = getList(criteria);
        return (List<ArticleCategory>)list;
    }

    public List<ArticleCategory> getArticleCategoryList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<ArticleCategory>)list;
    }

    public List<ArticleCategory> getArticleCategoryList() {
        return dao.getArticleCategoryList();
    }

    public ArticleCategory getArticleCategory(String id) {
        ArticleCategory obj = dao.getArticleCategory(id);
        return obj;
    }

    public void saveArticleCategory(ArticleCategory obj) {
        dao.saveArticleCategory(obj);
    }

    public void removeArticleCategory(String id) {
        dao.removeArticleCategory(id);
    }
}
