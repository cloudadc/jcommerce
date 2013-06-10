/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.MailTemplateDAO;
import com.jcommerce.core.model.MailTemplate;

public class MailTemplateDAOImpl extends DAOImpl implements MailTemplateDAO {
    public MailTemplateDAOImpl() {
        modelClass = MailTemplate.class;
    }

    public List<MailTemplate> getMailTemplateList() {
        return getList();
    }

    public MailTemplate getMailTemplate(String id) {
        return (MailTemplate)getById(id);
    }

    public void saveMailTemplate(MailTemplate obj) {
        save(obj);
    }

    public void removeMailTemplate(String id) {
        deleteById(id);
    }
}
