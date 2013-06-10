/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.BookingGoods;
import com.jcommerce.core.service.Criteria;
public interface BookingGoodsManager extends Manager {
    public BookingGoods initialize(BookingGoods obj);

    public List<BookingGoods> getBookingGoodsList(int firstRow, int maxRow);

    public int getBookingGoodsCount(Criteria criteria);

    public List<BookingGoods> getBookingGoodsList(Criteria criteria);

    public List<BookingGoods> getBookingGoodsList(int firstRow, int maxRow, Criteria criteria);

    public List<BookingGoods> getBookingGoodsList();

    public BookingGoods getBookingGoods(String id);

    public void saveBookingGoods(BookingGoods obj);

    public void removeBookingGoods(String id);
}
