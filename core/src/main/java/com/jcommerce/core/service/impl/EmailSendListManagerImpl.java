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

import com.jcommerce.core.dao.EmailSendListDAO;
import com.jcommerce.core.model.EmailSendList;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.EmailSendListManager;

@Service("EmailSendListManager")
public class EmailSendListManagerImpl extends ManagerImpl implements EmailSendListManager {
    private static Log log = LogFactory.getLog(EmailSendListManagerImpl.class);
    
    @Autowired
    private EmailSendListDAO dao;

    public void setEmailSendListDAO(EmailSendListDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public EmailSendList initialize(EmailSendList obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getEmailSendList(obj.getId());
        }
        return obj;
    }

    public List<EmailSendList> getEmailSendListList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<EmailSendList>)list;
    }

    public int getEmailSendListCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<EmailSendList> getEmailSendListList(Criteria criteria) {
        List list = getList(criteria);
        return (List<EmailSendList>)list;
    }

    public List<EmailSendList> getEmailSendListList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<EmailSendList>)list;
    }

    public List<EmailSendList> getEmailSendListList() {
        return dao.getEmailSendListList();
    }

    public EmailSendList getEmailSendList(String id) {
        EmailSendList obj = dao.getEmailSendList(id);
        return obj;
    }

    public void saveEmailSendList(EmailSendList obj) {
        dao.saveEmailSendList(obj);
    }

    public void removeEmailSendList(String id) {
        dao.removeEmailSendList(id);
    }
}
