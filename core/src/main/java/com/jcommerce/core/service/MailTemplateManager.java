/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.MailTemplate;
import com.jcommerce.core.service.Criteria;
public interface MailTemplateManager extends Manager {
    public MailTemplate initialize(MailTemplate obj);

    public List<MailTemplate> getMailTemplateList(int firstRow, int maxRow);

    public int getMailTemplateCount(Criteria criteria);

    public List<MailTemplate> getMailTemplateList(Criteria criteria);

    public List<MailTemplate> getMailTemplateList(int firstRow, int maxRow, Criteria criteria);

    public List<MailTemplate> getMailTemplateList();

    public MailTemplate getMailTemplate(String id);

    public void saveMailTemplate(MailTemplate obj);

    public void removeMailTemplate(String id);
}
