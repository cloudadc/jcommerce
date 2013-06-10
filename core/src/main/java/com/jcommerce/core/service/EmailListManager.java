/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.EmailList;
import com.jcommerce.core.service.Criteria;
public interface EmailListManager extends Manager {
    public EmailList initialize(EmailList obj);

    public List<EmailList> getEmailListList(int firstRow, int maxRow);

    public int getEmailListCount(Criteria criteria);

    public List<EmailList> getEmailListList(Criteria criteria);

    public List<EmailList> getEmailListList(int firstRow, int maxRow, Criteria criteria);

    public List<EmailList> getEmailListList();

    public EmailList getEmailList(String id);

    public void saveEmailList(EmailList obj);

    public void removeEmailList(String id);
}
