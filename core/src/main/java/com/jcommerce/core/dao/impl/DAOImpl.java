/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.ModelObject;

@Transactional 
public class DAOImpl implements DAO {
	
    protected Log log = LogFactory.getLog(getClass());

    protected Class modelClass = null;
    
    public List getList(int firstRow, int maxRow) {
        return getList("from " + modelClass.getSimpleName(), firstRow, maxRow);
    }
    
    @Autowired
    private SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public List getList(final String hsql, final int firstRow, final int maxRow) {
		System.out.println(" hsql:" + hsql);
        if (firstRow < 0 || maxRow <=0) {
			throw new IllegalArgumentException("firstRow=" + firstRow + " maxRow=" + maxRow);
        }
        
        Query query = getCurrentSession().createQuery(hsql);
        query.setFirstResult(firstRow);
        query.setMaxResults(maxRow);
        List list = query.list();
        return list;
  
    }   
    
	public int getCount(String hql) {
		String sql = "select count(*) " + hql;
		Query query = getCurrentSession().createQuery(sql);
		Number count = (Number) query.iterate().next();
		return count.intValue();
	}
    
    public List getList(String hql){          
    	Query query = getCurrentSession().createQuery(hql);
        List list = query.list();
        return list;
    }
    
    public List getList(){        
        Query query = getCurrentSession().createQuery("from " + modelClass.getSimpleName());
        List list = query.list();
        return list;
    }
    
    public ModelObject getById(int id){        
        return getById(new Integer(id));
    }
    
    public ModelObject getById(Serializable id){
    	
		return (ModelObject) getCurrentSession().get(modelClass, id);
    }
    
    public void save(ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
        
		ModelObject _obj = (ModelObject) getCurrentSession().merge(obj);
		setId(obj, getId(_obj));
	}
    
	public boolean deleteById(Serializable id) {
		ModelObject obj = getById(id);
		if (obj == null) {
			throw new RuntimeException("Object not found for ID: " + id);
		}

		getCurrentSession().delete(obj);
		return true;
	}   
    
    public boolean delete(ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
        
        getCurrentSession().delete(obj);
        return true;
    }    
    
    public void deleteAll(Collection<ModelObject> objs) {
        if (objs == null) {
            throw new IllegalArgumentException("objs = null");
        }
        for(ModelObject obj : objs) {
        	delete(obj);
        }
        
    }    
    
    public Class getModelClass() {
        if (modelClass == null) {
            throw new RuntimeException("modelClass = null: "+getClass().getName());
        }
        
        return modelClass;
    }
    
    /**
     * Require all ModelObject have a getId() or getID() method
     */
    private Serializable getId(ModelObject obj) {
        return obj.getId();
    }
    
    private void setId(ModelObject obj, Serializable id) {
        obj.setId((String)id);
    }
}
