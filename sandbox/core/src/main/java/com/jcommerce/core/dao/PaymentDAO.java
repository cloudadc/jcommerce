/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Payment;

public interface PaymentDAO extends DAO {
    public List<Payment> getPaymentList();

    public Payment getPayment(Long id);

    public void savePayment(Payment obj);

    public void removePayment(Long id);
}
