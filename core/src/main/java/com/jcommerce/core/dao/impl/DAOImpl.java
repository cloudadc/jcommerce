/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.ModelObject;

public class DAOImpl extends HibernateDaoSupport implements DAO {
    protected Log log = LogFactory.getLog(getClass());

    protected Class modelClass = null;
    
    public List getList(int firstRow, int maxRow) {
        return getList("from " + modelClass.getSimpleName(), firstRow, maxRow);
    }
    
    public List getList(final String hsql, final int firstRow, final int maxRow) {
        System.out.println(" hsql:"+hsql);
        if (firstRow < 0 || maxRow <=0) {
            throw new IllegalArgumentException("firstRow="+firstRow+" maxRow="+maxRow);
        }
        
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session s) throws HibernateException,
                    SQLException {
                Query query = s.createQuery(hsql);
                query.setFirstResult(firstRow);
                query.setMaxResults(maxRow);
                List list = query.list();
                return list;
            }
        });
    }   
    
    public int getCount(String hql){  
        Number count = (Number)getHibernateTemplate().find("select count(*) "+hql).listIterator().next();
        return count.intValue();
    }
    
    public List getList(String hql){          
        System.out.println(" hsql:"+hql);
        return getHibernateTemplate().find(hql);
    }
    
    public List getList(){        
        return getHibernateTemplate().find("from " + modelClass.getSimpleName());
    }
    
    public ModelObject getById(int id){        
        return getById(new Integer(id));
    }
    
    public ModelObject getById(Serializable id){
        return (ModelObject)getHibernateTemplate().get(modelClass, id);
    }
    
    public void save(ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
        
//        if (getById(getId(obj)) == null) {
//            Serializable id = getHibernateTemplate().save(obj);
//            setId(obj, id);
//        } else {
//            getHibernateTemplate().update(obj);
//        }
        ModelObject _obj = (ModelObject)getHibernateTemplate().merge(obj);
        setId(obj, getId(_obj));
    }    

    /*public boolean deleteById(String id) {
        return deleteById(new Integer(id));
    }*/
    
    public boolean deleteById(Serializable id) {
        ModelObject obj = getById(id);
        if (obj == null) {
//            throw new RuntimeException("Object not found for ID: "+id);
            return false;
        }
        
        getHibernateTemplate().delete(obj);
        return true;
    }    
    
    public boolean delete(ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
        
        getHibernateTemplate().delete(obj);
        return true;
    }    
    
    public void deleteAll(Collection<ModelObject> objs) {
        if (objs == null) {
            throw new IllegalArgumentException("objs = null");
        }
        
        getHibernateTemplate().deleteAll(objs);
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
