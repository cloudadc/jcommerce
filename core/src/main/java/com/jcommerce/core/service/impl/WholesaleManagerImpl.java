/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.WholesaleDAO;
import com.jcommerce.core.model.Wholesale;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.WholesaleManager;

@Service("WholesaleManager")
public class WholesaleManagerImpl extends ManagerImpl implements WholesaleManager {
    
	private static Log log = LogFactory.getLog(WholesaleManagerImpl.class);
    
    @Autowired
    private WholesaleDAO dao;

    public void setWholesaleDAO(WholesaleDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Wholesale initialize(Wholesale obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getWholesale(obj.getId());
        }
        return obj;
    }

    public List<Wholesale> getWholesaleList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Wholesale>)list;
    }

    public int getWholesaleCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Wholesale> getWholesaleList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Wholesale>)list;
    }

    public List<Wholesale> getWholesaleList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Wholesale>)list;
    }

    public List<Wholesale> getWholesaleList() {
        return dao.getWholesaleList();
    }

    public Wholesale getWholesale(String id) {
        Wholesale obj = dao.getWholesale(id);
        return obj;
    }

    public void saveWholesale(Wholesale obj) {
        dao.saveWholesale(obj);
    }

    public void removeWholesale(String id) {
        dao.removeWholesale(id);
    }
}
