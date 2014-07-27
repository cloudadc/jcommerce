/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.ArticleCategory;

public interface ArticleCategoryDAO extends DAO {
    public List<ArticleCategory> getArticleCategoryList();

    public ArticleCategory getArticleCategory(Long id);

    public void saveArticleCategory(ArticleCategory obj);

    public void removeArticleCategory(Long id);
}
