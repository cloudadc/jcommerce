/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.model.Session;

public interface CartManager extends Manager {
    public Cart initialize(Cart obj);

    public List<Cart> getCartList(int firstRow, int maxRow);

    public int getCartCount(Criteria criteria);

    public List<Cart> getCartList(Criteria criteria);

    public List<Cart> getCartList(int firstRow, int maxRow, Criteria criteria);

    public List<Cart> getCartList();

    public Cart getCart(Long id);

    public void saveCart(Cart obj);

    public void removeCart(Long id);
    
    public List<Cart> getCartList(Session session);

    public void removeCarts(Session session);
    
    public void removeCarts(List<Cart> carts);
}
