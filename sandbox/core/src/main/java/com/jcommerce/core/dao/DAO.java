package com.jcommerce.core.dao;

import java.util.Collection;
import java.util.List;

import com.jcommerce.core.model.ModelObject;

/**
 * Data Access Object (DAO) interface. Common methods for each interface
 * could be added here.
 *
 * @author Bob Chen
 */
public interface DAO {
    public List getList(String hql, int firstRow, int maxRow);

    public List getList(int firstRow, int maxRow);
    
    public int getCount(String hql);
    
    public List<DAO> getList(String hql);
    
    public void deleteAll(Collection<ModelObject> objs);
    
    public Class getModelClass();
}
