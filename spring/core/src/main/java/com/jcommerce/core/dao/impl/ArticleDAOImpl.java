/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.ArticleDAO;
import com.jcommerce.core.model.Article;

@Repository
@SuppressWarnings("unchecked")
public class ArticleDAOImpl extends DAOImpl implements ArticleDAO {
    public ArticleDAOImpl() {
        modelClass = Article.class;
    }

    public List<Article> getArticleList() {
        return getList();
    }

    public Article getArticle(Long id) {
        return (Article)getById(id);
    }

    public void saveArticle(Article obj) {
        save(obj);
    }

    public void removeArticle(Long id) {
        deleteById(id);
    }
}
