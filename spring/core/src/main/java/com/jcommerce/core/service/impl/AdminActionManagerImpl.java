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

import com.jcommerce.core.dao.AdminActionDAO;
import com.jcommerce.core.model.AdminAction;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.AdminActionManager;

@Service("AdminActionManager")
public class AdminActionManagerImpl extends ManagerImpl implements AdminActionManager {
    private static Log log = LogFactory.getLog(AdminActionManagerImpl.class);
    
    @Autowired
    private AdminActionDAO dao;

    public void setAdminActionDAO(AdminActionDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public AdminAction initialize(AdminAction obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getAdminAction(obj.getId());
        }
        return obj;
    }

    public List<AdminAction> getAdminActionList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<AdminAction>)list;
    }

    public int getAdminActionCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<AdminAction> getAdminActionList(Criteria criteria) {
        List list = getList(criteria);
        return (List<AdminAction>)list;
    }

    public List<AdminAction> getAdminActionList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<AdminAction>)list;
    }

    public List<AdminAction> getAdminActionList() {
        return dao.getAdminActionList();
    }

    public AdminAction getAdminAction(Long id) {
        AdminAction obj = dao.getAdminAction(id);
        return obj;
    }

    public void saveAdminAction(AdminAction obj) {
        dao.saveAdminAction(obj);
    }

    public void removeAdminAction(Long id) {
        dao.removeAdminAction(id);
    }
}
