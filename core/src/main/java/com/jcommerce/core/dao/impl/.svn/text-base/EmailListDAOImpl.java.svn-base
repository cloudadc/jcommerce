/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.EmailListDAO;
import com.jcommerce.core.model.EmailList;

public class EmailListDAOImpl extends DAOImpl implements EmailListDAO {
    public EmailListDAOImpl() {
        modelClass = EmailList.class;
    }

    public List<EmailList> getEmailListList() {
        return getList();
    }

    public EmailList getEmailList(String id) {
        return (EmailList)getById(id);
    }

    public void saveEmailList(EmailList obj) {
        save(obj);
    }

    public void removeEmailList(String id) {
        deleteById(id);
    }
}
