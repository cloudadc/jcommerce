/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.CategoryDAO;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.service.CategoryManager;

@Service("CategoryManager")
public class CategoryManagerImpl extends ManagerImpl implements CategoryManager {
    private static Log log = LogFactory.getLog(CategoryManagerImpl.class);
    
    @Autowired
    private CategoryDAO dao;

    public void setCategoryDAO(CategoryDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Category initialize(Category obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getCategory(obj.getId());
        }
        return obj;
    }

    public List<Category> getCategoryList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Category>)list;
    }

    public int getCategoryCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Category> getCategoryList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Category>)list;
    }

    public List<Category> getCategoryList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Category>)list;
    }

    public List<Category> getCategoryList() {
        List<Category> cs = dao.getCategoryList();        
        List<Category> top = new ArrayList<Category>();
        for (Iterator it = cs.iterator(); it.hasNext();) {
            Category cat = (Category) it.next();
            if (cat.getParent() == null && !top.contains(cat)) {
                top.add(cat);
            }
        }       
        // sort the result to be:
        // c1
        // c11
        // c12
        // c121
        // c2
        // c21
        // c211
        // c22
        // c3
        //
        // parent followed by its children
        List<Category> sorted = new ArrayList<Category>();
        for (Category cat : top) {
            addCategory(sorted, cat);
        }
        System.out.println("sorted:"+sorted.size());

        return sorted;
    }

    public Category getCategory(String id) {
        Category obj = dao.getCategory(id);
        return obj;
    }

    public void saveCategory(Category obj) {
        dao.saveCategory(obj);
    }

    public void removeCategory(String id) {
        dao.removeCategory(id);
    }

    public List<Category> getTopCategoryList() {
        List<Category> list = getCategoryList();
        List<Category> top = new ArrayList<Category>();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Category cat = (Category) it.next();
            if (cat.getParent() == null && !top.contains(cat)) {
                top.add(cat);
            }
        }
        return top;
    }
    
    private void addCategory(List<Category> cats, Category cat) {
        cats.add(cat);
        Set<Category> chds = cat.getChildren();
        if (chds != null && chds.size() > 0) {
            for (Category chd : chds) {
                addCategory(cats, chd);
            }
        }
    }
}
