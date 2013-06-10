/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.service.Criteria;
public interface CategoryManager extends Manager {
    public Category initialize(Category obj);

    public List<Category> getCategoryList(int firstRow, int maxRow);

    public int getCategoryCount(Criteria criteria);

    public List<Category> getCategoryList(Criteria criteria);

    public List<Category> getCategoryList(int firstRow, int maxRow, Criteria criteria);

    /**
    The result is sorted to make parent followed by its children. 
    <ul>
    <li>c1</li>
    <li>&nbsp;c11</li>
    <li>&nbsp;c12</li>
    <li>&nbsp;&nbsp;c121</li>
    <li>c2</li>
    <li>&nbsp;c21</li>
    <li>&nbsp;&nbsp;c211</li>
    <li>&nbsp;c22</li>
    <li>c3</li>
    </ul>
    */
    public List<Category> getCategoryList();
    
    public Category getCategory(String id);

    public void saveCategory(Category obj);

    public void removeCategory(String id);

    /**
     * Get the top category, the category that has no parent, list 
     * @return
     */
    public List<Category> getTopCategoryList();
}
