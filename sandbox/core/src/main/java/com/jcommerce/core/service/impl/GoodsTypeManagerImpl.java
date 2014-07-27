/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.GoodsTypeDAO;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsTypeManager;

@Service("GoodsTypeManager")
public class GoodsTypeManagerImpl extends ManagerImpl implements GoodsTypeManager {
    private static Log log = LogFactory.getLog(GoodsTypeManagerImpl.class);
    
    @Autowired
    private GoodsTypeDAO dao;

    public void setGoodsTypeDAO(GoodsTypeDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public GoodsType initialize(GoodsType obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGoodsType(obj.getId());
        }
        return obj;
    }

    public List<GoodsType> getGoodsTypeList(int firstRow, int maxRow) {
//        List list = getList(firstRow, maxRow);
//        return (List<GoodsType>)list;
    	return getGoodsTypeList(firstRow, maxRow, null);
    }

    public int getGoodsTypeCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<GoodsType> getGoodsTypeList(Criteria criteria) {
//        List list = getList(criteria);
//        return (List<GoodsType>)list;
    	return getGoodsTypeList(-1,-1, criteria);
    }

    public List<GoodsType> getGoodsTypeList(int firstRow, int maxRow, Criteria criteria) {
    	System.out.println("here");
//        List list = getList(firstRow, maxRow, criteria);
//        return (List<GoodsType>)list;
    	
    	// by liyong
    	// this method is actually called from PagingListAction.getPagingList(String modelName, Criteria criteria, PagingLoadConfig pgc)
    	String whereClause = getWhereClause(criteria);
    	return dao.getGoodsTypeListWithAttrCount(firstRow, maxRow, whereClause);
    }

    public List<GoodsType> getGoodsTypeList() {
//        return dao.getGoodsTypeList();
    	// by liyong
    	return getGoodsTypeList(-1,-1, null);
    }

    public GoodsType getGoodsType(Long id) {
        GoodsType obj = dao.getGoodsType(id);
        return obj;
    }

    public void saveGoodsType(GoodsType obj) {
        dao.saveGoodsType(obj);
    }

    public void removeGoodsType(Long id) {
        dao.removeGoodsType(id);
    }
}
