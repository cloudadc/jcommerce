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

import com.jcommerce.core.dao.AdminMessageDAO;
import com.jcommerce.core.model.AdminMessage;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AdminMessageManager;

@Service("AdminMessageManager")
public class AdminMessageManagerImpl extends ManagerImpl implements AdminMessageManager {
    private static Log log = LogFactory.getLog(AdminMessageManagerImpl.class);
    
    @Autowired
    private AdminMessageDAO dao;

    public void setAdminMessageDAO(AdminMessageDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AdminMessage initialize(AdminMessage obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAdminMessage(obj.getId());
        }
        return obj;
    }

    public List<AdminMessage> getAdminMessageList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AdminMessage>)list;
    }

    public int getAdminMessageCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AdminMessage> getAdminMessageList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AdminMessage>)list;
    }

    public List<AdminMessage> getAdminMessageList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AdminMessage>)list;
    }

    public List<AdminMessage> getAdminMessageList() {
        return dao.getAdminMessageList();
    }

    public AdminMessage getAdminMessage(Long id) {
        AdminMessage obj = dao.getAdminMessage(id);
        return obj;
    }

    public void saveAdminMessage(AdminMessage obj) {
        dao.saveAdminMessage(obj);
    }

    public void removeAdminMessage(Long id) {
        dao.removeAdminMessage(id);
    }
}
