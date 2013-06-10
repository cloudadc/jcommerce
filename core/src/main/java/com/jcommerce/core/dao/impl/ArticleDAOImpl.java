/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.ArticleDAO;
import com.jcommerce.core.model.Article;

public class ArticleDAOImpl extends DAOImpl implements ArticleDAO {
    public ArticleDAOImpl() {
        modelClass = Article.class;
    }

    public List<Article> getArticleList() {
        return getList();
    }

    public Article getArticle(String id) {
        return (Article)getById(id);
    }

    public void saveArticle(Article obj) {
        save(obj);
    }

    public void removeArticle(String id) {
        deleteById(id);
    }
}
