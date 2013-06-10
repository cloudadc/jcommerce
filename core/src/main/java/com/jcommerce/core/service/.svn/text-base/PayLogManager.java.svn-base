/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.PayLog;
import com.jcommerce.core.service.Criteria;
public interface PayLogManager extends Manager {
    public PayLog initialize(PayLog obj);

    public List<PayLog> getPayLogList(int firstRow, int maxRow);

    public int getPayLogCount(Criteria criteria);

    public List<PayLog> getPayLogList(Criteria criteria);

    public List<PayLog> getPayLogList(int firstRow, int maxRow, Criteria criteria);

    public List<PayLog> getPayLogList();

    public PayLog getPayLog(String id);

    public void savePayLog(PayLog obj);

    public void removePayLog(String id);
}
