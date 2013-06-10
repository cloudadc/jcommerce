/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.CartDAO;
import com.jcommerce.core.model.Cart;

public class CartDAOImpl extends DAOImpl implements CartDAO {
    public CartDAOImpl() {
        modelClass = Cart.class;
    }

    public List<Cart> getCartList() {
        return getList();
    }

    public Cart getCart(String id) {
        return (Cart)getById(id);
    }

    public void saveCart(Cart obj) {
        save(obj);
    }

    public void removeCart(String id) {
        deleteById(id);
    }
}
