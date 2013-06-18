package com.jcommerce.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.ModelObject;

public class DAOImpl extends HibernateDaoSupport implements DAO {

	protected Class modelClass = null;

	public List<DAO> getList(String hql) {
		return getHibernateTemplate().find(hql);
	}

	public void save(ModelObject obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj = null");
		}
		ModelObject _obj = (ModelObject) getHibernateTemplate().merge(obj);
		setId(obj, getId(_obj));
	}

	private Serializable getId(ModelObject obj) {
		return obj.getId();
	}
	
	private void setId(ModelObject obj, Serializable id) {
        obj.setId((String)id);
    }
	
	public ModelObject getById(Serializable id){
        return (ModelObject)getHibernateTemplate().get(modelClass, id);
    }
	
	public boolean deleteById(Serializable id) {
        ModelObject obj = getById(id);
        if (obj == null) {
            return false;
        }
        getHibernateTemplate().delete(obj);
        return true;
    } 

}
