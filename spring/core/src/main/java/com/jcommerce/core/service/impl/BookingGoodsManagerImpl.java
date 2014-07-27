/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.BookingGoodsDAO;
import com.jcommerce.core.model.BookingGoods;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.BookingGoodsManager;

@Service("BookingGoodsManager")
public class BookingGoodsManagerImpl extends ManagerImpl implements BookingGoodsManager {
    private static Log log = LogFactory.getLog(BookingGoodsManagerImpl.class);
    
    @Autowired
    private BookingGoodsDAO dao;

    public void setBookingGoodsDAO(BookingGoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public BookingGoods initialize(BookingGoods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getBookingGoods(obj.getId());
        }
        return obj;
    }

    public List<BookingGoods> getBookingGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<BookingGoods>)list;
    }

    public int getBookingGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<BookingGoods> getBookingGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<BookingGoods>)list;
    }

    public List<BookingGoods> getBookingGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<BookingGoods>)list;
    }

    public List<BookingGoods> getBookingGoodsList() {
        return dao.getBookingGoodsList();
    }

    public BookingGoods getBookingGoods(Long id) {
        BookingGoods obj = dao.getBookingGoods(id);
        return obj;
    }

    public void saveBookingGoods(BookingGoods obj) {
        dao.saveBookingGoods(obj);
    }

    public void removeBookingGoods(Long id) {
        dao.removeBookingGoods(id);
    }
}
