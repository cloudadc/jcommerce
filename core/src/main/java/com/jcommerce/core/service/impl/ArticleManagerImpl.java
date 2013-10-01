/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.ArticleDAO;
import com.jcommerce.core.model.Article;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.ArticleManager;

@Service("ArticleManager")
public class ArticleManagerImpl extends ManagerImpl implements ArticleManager {
    private static Log log = LogFactory.getLog(ArticleManagerImpl.class);
    
    @Autowired
    private ArticleDAO dao;

    public void setArticleDAO(ArticleDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Article initialize(Article obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getArticle(obj.getId());
        }
        return obj;
    }

    public List<Article> getArticleList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Article>)list;
    }

    public int getArticleCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Article> getArticleList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Article>)list;
    }

    public List<Article> getArticleList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Article>)list;
    }

    public List<Article> getArticleList() {
        return dao.getArticleList();
    }

    public Article getArticle(String id) {
        Article obj = dao.getArticle(id);
        return obj;
    }

    public void saveArticle(Article obj) {
        dao.saveArticle(obj);
    }

    public void removeArticle(String id) {
        dao.removeArticle(id);
    }
}
