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

import com.jcommerce.core.dao.MemberPriceDAO;
import com.jcommerce.core.model.MemberPrice;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.MemberPriceManager;

@Service("memberPriceManager")
public class MemberPriceManagerImpl extends ManagerImpl implements MemberPriceManager {
    private static Log log = LogFactory.getLog(MemberPriceManagerImpl.class);
    
    @Autowired
    private MemberPriceDAO dao;

    public void setMemberPriceDAO(MemberPriceDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public MemberPrice initialize(MemberPrice obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getMemberPrice(obj.getId());
        }
        return obj;
    }

    public List<MemberPrice> getMemberPriceList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<MemberPrice>)list;
    }

    public int getMemberPriceCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<MemberPrice> getMemberPriceList(Criteria criteria) {
        List list = getList(criteria);
        return (List<MemberPrice>)list;
    }

    public List<MemberPrice> getMemberPriceList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<MemberPrice>)list;
    }

    public List<MemberPrice> getMemberPriceList() {
        return dao.getMemberPriceList();
    }

    public MemberPrice getMemberPrice(String id) {
        MemberPrice obj = dao.getMemberPrice(id);
        return obj;
    }

    public void saveMemberPrice(MemberPrice obj) {
        dao.saveMemberPrice(obj);
    }

    public void removeMemberPrice(String id) {
        dao.removeMemberPrice(id);
    }
}
