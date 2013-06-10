/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.EmailSendList;
import com.jcommerce.core.service.Criteria;
public interface EmailSendListManager extends Manager {
    public EmailSendList initialize(EmailSendList obj);

    public List<EmailSendList> getEmailSendListList(int firstRow, int maxRow);

    public int getEmailSendListCount(Criteria criteria);

    public List<EmailSendList> getEmailSendListList(Criteria criteria);

    public List<EmailSendList> getEmailSendListList(int firstRow, int maxRow, Criteria criteria);

    public List<EmailSendList> getEmailSendListList();

    public EmailSendList getEmailSendList(String id);

    public void saveEmailSendList(EmailSendList obj);

    public void removeEmailSendList(String id);
}
