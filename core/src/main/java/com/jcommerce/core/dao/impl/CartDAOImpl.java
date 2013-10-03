/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.CartDAO;
import com.jcommerce.core.model.Cart;

@Repository
@SuppressWarnings("unchecked")
public class CartDAOImpl extends DAOImpl implements CartDAO {
    public CartDAOImpl() {
        modelClass = Cart.class;
    }

    public List<Cart> getCartList() {
        return getList();
    }

    public Cart getCart(Long id) {
        return (Cart)getById(id);
    }

    public void saveCart(Cart obj) {
        save(obj);
    }

    public void removeCart(Long id) {
        deleteById(id);
    }
}
