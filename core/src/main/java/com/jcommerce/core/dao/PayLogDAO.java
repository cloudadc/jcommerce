/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.PayLog;

public interface PayLogDAO extends DAO {
    public List<PayLog> getPayLogList();

    public PayLog getPayLog(String id);

    public void savePayLog(PayLog obj);

    public void removePayLog(String id);
}
