/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.PaymentDAO;
import com.jcommerce.core.model.Payment;

@Repository
@SuppressWarnings("unchecked")
public class PaymentDAOImpl extends DAOImpl implements PaymentDAO {
    public PaymentDAOImpl() {
        modelClass = Payment.class;
    }

    public List<Payment> getPaymentList() {
        return getList();
    }

    public Payment getPayment(String id) {
        return (Payment)getById(id);
    }

    public void savePayment(Payment obj) {
        save(obj);
    }

    public void removePayment(String id) {
        deleteById(id);
    }
}
