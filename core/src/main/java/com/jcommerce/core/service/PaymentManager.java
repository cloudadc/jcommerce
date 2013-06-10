/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.Criteria;
public interface PaymentManager extends Manager {
    public Payment initialize(Payment obj);

    public List<Payment> getPaymentList(int firstRow, int maxRow);

    public int getPaymentCount(Criteria criteria);

    public List<Payment> getPaymentList(Criteria criteria);

    public List<Payment> getPaymentList(int firstRow, int maxRow, Criteria criteria);

    public List<Payment> getPaymentList();

    public Payment getPayment(String id);

    public void savePayment(Payment obj);

    public void removePayment(String id);
}
