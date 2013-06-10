/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.AdminAction;
import com.jcommerce.core.service.Criteria;
public interface AdminActionManager extends Manager {
    public AdminAction initialize(AdminAction obj);

    public List<AdminAction> getAdminActionList(int firstRow, int maxRow);

    public int getAdminActionCount(Criteria criteria);

    public List<AdminAction> getAdminActionList(Criteria criteria);

    public List<AdminAction> getAdminActionList(int firstRow, int maxRow, Criteria criteria);

    public List<AdminAction> getAdminActionList();

    public AdminAction getAdminAction(String id);

    public void saveAdminAction(AdminAction obj);

    public void removeAdminAction(String id);
}
