package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Payment;

public class PaymentWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = -6926305057213301409L;
	Payment payment;
	@Override
	protected Object getWrapped() {
		return getPayment();
	}
	public PaymentWrapper(ModelObject payment) {
		super();
		this.payment = (Payment)payment;
		
		put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ getPayment().getFee()  + "</span>");
	}
	
	public Payment getPayment() {
		return payment;
	}
	
    // for template
    public String getPayId() {
    	return getPayment().getId();
    }
    
    public String getPayName() {
        return getPayment().getName();
    }
    
    public String getPayDesc() {
        return getPayment().getDescription();
    }
    
    public boolean isCod() {
        return getPayment().isCod();
    }

    public boolean isOnline() {
        return getPayment().isOnline();
    }

    public String getFormatedPayFee(){
    	return "<span id=\"ECS_CODFEE\">"+ getPayment().getFee()  + "</span>";
    }
}
