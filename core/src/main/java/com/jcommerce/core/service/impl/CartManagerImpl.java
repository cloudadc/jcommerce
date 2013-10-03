/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.CartDAO;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.CartManager;

@Service("CartManager")
public class CartManagerImpl extends ManagerImpl implements CartManager {
    private static Log log = LogFactory.getLog(CartManagerImpl.class);
    
    @Autowired
    private CartDAO dao;

    public void setCartDAO(CartDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Cart initialize(Cart obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getCart(obj.getId());
        }
        return obj;
    }

    public List<Cart> getCartList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Cart>)list;
    }

    public int getCartCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Cart> getCartList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Cart>)list;
    }

    public List<Cart> getCartList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Cart>)list;
    }

    public List<Cart> getCartList() {
        return dao.getCartList();
    }

    public Cart getCart(Long id) {
        Cart obj = dao.getCart(id);
        return obj;
    }

    public void saveCart(Cart obj) {
        dao.saveCart(obj);
    }

    public void removeCart(Long id) {
        dao.removeCart(id);
    }
    
    public List<Cart> getCartList(Session session) {
        String hql = null;
        if (session.getUser() != null) {
            hql = "from "+dao.getModelClass().getSimpleName()+" where user="+session.getUser().getId();
        } else {
            hql = "from "+dao.getModelClass().getSimpleName()+" where session='"+session.getId()+"'";
        }
        
        List list = dao.getList(hql);
        return (List<Cart>)list;
    }    

    public void removeCarts(Session session) {
        List list = getCartList(session);
        if (list != null) {
            dao.deleteAll((Collection<ModelObject>)list);
        }
    }
    
    public void removeCarts(List<Cart> carts) {
        dao.deleteAll((Collection<ModelObject>)(List)carts);
    }
}
