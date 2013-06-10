/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.service.Criteria;
public interface ArticleManager extends Manager {
    public Article initialize(Article obj);

    public List<Article> getArticleList(int firstRow, int maxRow);

    public int getArticleCount(Criteria criteria);

    public List<Article> getArticleList(Criteria criteria);

    public List<Article> getArticleList(int firstRow, int maxRow, Criteria criteria);

    public List<Article> getArticleList();

    public Article getArticle(String id);

    public void saveArticle(Article obj);

    public void removeArticle(String id);
}
