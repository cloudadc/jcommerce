/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.LinkGoodsDAO;
import com.jcommerce.core.model.LinkGoods;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.LinkGoodsManager;

public class LinkGoodsManagerImpl extends ManagerImpl implements LinkGoodsManager {
    private static Log log = LogFactory.getLog(LinkGoodsManagerImpl.class);
    private LinkGoodsDAO dao;

    public void setLinkGoodsDAO(LinkGoodsDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public LinkGoods initialize(LinkGoods obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getLinkGoods(obj.getId());
        }
        return obj;
    }

    public List<LinkGoods> getLinkGoodsList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<LinkGoods>)list;
    }

    public int getLinkGoodsCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<LinkGoods> getLinkGoodsList(Criteria criteria) {
        List list = getList(criteria);
        return (List<LinkGoods>)list;
    }

    public List<LinkGoods> getLinkGoodsList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<LinkGoods>)list;
    }

    public List<LinkGoods> getLinkGoodsList() {
        return dao.getLinkGoodsList();
    }

    public LinkGoods getLinkGoods(String id) {
        LinkGoods obj = dao.getLinkGoods(id);
        return obj;
    }

    public void saveLinkGoods(LinkGoods obj) {
        dao.saveLinkGoods(obj);
    }

    public void removeLinkGoods(String id) {
        dao.removeLinkGoods(id);
    }
}
