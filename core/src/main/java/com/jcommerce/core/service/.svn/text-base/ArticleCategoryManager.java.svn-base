/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.ArticleCategory;
import com.jcommerce.core.service.Criteria;
public interface ArticleCategoryManager extends Manager {
    public ArticleCategory initialize(ArticleCategory obj);

    public List<ArticleCategory> getArticleCategoryList(int firstRow, int maxRow);

    public int getArticleCategoryCount(Criteria criteria);

    public List<ArticleCategory> getArticleCategoryList(Criteria criteria);

    public List<ArticleCategory> getArticleCategoryList(int firstRow, int maxRow, Criteria criteria);

    public List<ArticleCategory> getArticleCategoryList();

    public ArticleCategory getArticleCategory(String id);

    public void saveArticleCategory(ArticleCategory obj);

    public void removeArticleCategory(String id);
}
