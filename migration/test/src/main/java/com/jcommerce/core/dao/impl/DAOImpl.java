package com.jcommerce.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.ModelObject;

public class DAOImpl implements DAO {

	protected Class modelClass = null;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<DAO> getList(String hql) {
		return null;
	}

	public void save(ModelObject obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj = null");
		}
//		ModelObject _obj = (ModelObject) getHibernateTemplate().merge(obj);
//		setId(obj, getId(_obj));
//		Session session = sessionFactory.openSession();
//		session.getTransaction().begin();
//		session.save(obj);
//		session.getTransaction().commit();
	}

	private Serializable getId(ModelObject obj) {
		return obj.getId();
	}
	
	private void setId(ModelObject obj, Serializable id) {
        obj.setId((String)id);
    }
	
	public ModelObject getById(Serializable id){
		return (ModelObject) sessionFactory.openSession().get(modelClass, id);
//        return (ModelObject)getHibernateTemplate().get(modelClass, id);
    }
	
	public boolean deleteById(Serializable id) {
        ModelObject obj = getById(id);
        if (obj == null) {
            return false;
        }
        sessionFactory.openSession().delete(obj);
        return true;
    } 

}
