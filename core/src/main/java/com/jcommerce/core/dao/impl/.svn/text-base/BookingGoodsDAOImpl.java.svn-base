/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.BookingGoodsDAO;
import com.jcommerce.core.model.BookingGoods;

public class BookingGoodsDAOImpl extends DAOImpl implements BookingGoodsDAO {
    public BookingGoodsDAOImpl() {
        modelClass = BookingGoods.class;
    }

    public List<BookingGoods> getBookingGoodsList() {
        return getList();
    }

    public BookingGoods getBookingGoods(String id) {
        return (BookingGoods)getById(id);
    }

    public void saveBookingGoods(BookingGoods obj) {
        save(obj);
    }

    public void removeBookingGoods(String id) {
        deleteById(id);
    }
}
