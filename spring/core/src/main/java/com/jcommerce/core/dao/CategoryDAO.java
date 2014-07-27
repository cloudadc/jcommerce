/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Category;

public interface CategoryDAO extends DAO {
    public List<Category> getCategoryList();

    public Category getCategory(Long id);

    public void saveCategory(Category obj);

    public void removeCategory(Long id);
}
