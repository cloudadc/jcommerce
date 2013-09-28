package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.web.util.WebFormatUtils;

public class ShippingWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = 5397967596176479865L;

	String configure;

    double shippingFee;
    double freeMoney;

    Shipping shipping;

    protected Object getWrapped() {
        return getShipping();
    }

    public ShippingWrapper(ModelObject shipping) {
        super();
        this.shipping = (Shipping) shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }

    // for template
    public String getShippingId() {
        return getShipping().getId();
    }

    public String getShippingName() {
        return getShipping().getName();
    }

    public String getShippingDesc() {
        return getShipping().getDescription();
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getFreeMoney() {
        return WebFormatUtils.priceFormat(freeMoney);
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public String getFormatedShippingFee() {
        return WebFormatUtils.priceFormat(shippingFee);
    }

    public String getFormatedInsure() {
        String insure = getShipping().getInsure();
        if (insure == null) {
            insure = "0";
        }
        String res = insure.indexOf("%") >= 0 ? insure : WebFormatUtils.priceFormat(insure);
        return res;
    }

    public String getConfigure() {
        return configure;
    }

    public void setConfigure(String configure) {
        this.configure = configure;
    }

    public boolean getSupportCod() {
        return getShipping().isSupportCod();
    }

}
