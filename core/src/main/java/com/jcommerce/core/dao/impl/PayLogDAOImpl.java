/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.PayLogDAO;
import com.jcommerce.core.model.PayLog;

@Repository
@SuppressWarnings("unchecked")
public class PayLogDAOImpl extends DAOImpl implements PayLogDAO {
    public PayLogDAOImpl() {
        modelClass = PayLog.class;
    }

    public List<PayLog> getPayLogList() {
        return getList();
    }

    public PayLog getPayLog(Long id) {
        return (PayLog)getById(id);
    }

    public void savePayLog(PayLog obj) {
        save(obj);
    }

    public void removePayLog(Long id) {
        deleteById(id);
    }
}
