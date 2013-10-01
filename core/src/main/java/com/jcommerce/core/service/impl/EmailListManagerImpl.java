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

import com.jcommerce.core.dao.EmailListDAO;
import com.jcommerce.core.model.EmailList;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.EmailListManager;

@Service("EmailListManager")
public class EmailListManagerImpl extends ManagerImpl implements EmailListManager {
    private static Log log = LogFactory.getLog(EmailListManagerImpl.class);
    
    @Autowired
    private EmailListDAO dao;

    public void setEmailListDAO(EmailListDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public EmailList initialize(EmailList obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getEmailList(obj.getId());
        }
        return obj;
    }

    public List<EmailList> getEmailListList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<EmailList>)list;
    }

    public int getEmailListCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<EmailList> getEmailListList(Criteria criteria) {
        List list = getList(criteria);
        return (List<EmailList>)list;
    }

    public List<EmailList> getEmailListList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<EmailList>)list;
    }

    public List<EmailList> getEmailListList() {
        return dao.getEmailListList();
    }

    public EmailList getEmailList(String id) {
        EmailList obj = dao.getEmailList(id);
        return obj;
    }

    public void saveEmailList(EmailList obj) {
        dao.saveEmailList(obj);
    }

    public void removeEmailList(String id) {
        dao.removeEmailList(id);
    }
}
