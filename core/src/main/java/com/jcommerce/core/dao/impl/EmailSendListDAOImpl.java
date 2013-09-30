/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.EmailSendListDAO;
import com.jcommerce.core.model.EmailSendList;

@Repository
@SuppressWarnings("unchecked")
public class EmailSendListDAOImpl extends DAOImpl implements EmailSendListDAO {
    public EmailSendListDAOImpl() {
        modelClass = EmailSendList.class;
    }

    public List<EmailSendList> getEmailSendListList() {
        return getList();
    }

    public EmailSendList getEmailSendList(String id) {
        return (EmailSendList)getById(id);
    }

    public void saveEmailSendList(EmailSendList obj) {
        save(obj);
    }

    public void removeEmailSendList(String id) {
        deleteById(id);
    }
}
