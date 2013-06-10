/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AdminMessage;
import com.jcommerce.core.service.Criteria;
public interface AdminMessageManager extends Manager {
    public AdminMessage initialize(AdminMessage obj);

    public List<AdminMessage> getAdminMessageList(int firstRow, int maxRow);

    public int getAdminMessageCount(Criteria criteria);

    public List<AdminMessage> getAdminMessageList(Criteria criteria);

    public List<AdminMessage> getAdminMessageList(int firstRow, int maxRow, Criteria criteria);

    public List<AdminMessage> getAdminMessageList();

    public AdminMessage getAdminMessage(String id);

    public void saveAdminMessage(AdminMessage obj);

    public void removeAdminMessage(String id);
}
