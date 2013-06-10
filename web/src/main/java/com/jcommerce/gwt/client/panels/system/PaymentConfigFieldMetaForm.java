package com.jcommerce.gwt.client.panels.system;

import java.io.Serializable;
import java.util.Map;

import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IPaymentConfigFieldMeta;

public class PaymentConfigFieldMetaForm extends BeanObject implements Serializable, IPaymentConfigFieldMeta {

    public PaymentConfigFieldMetaForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    
//    private String lable;
//    private String tip;
//    private Map<String, String> options; // value->lable
    
//    public String getLable() {
//        return lable;
//    }
//    public void setLable(String lable) {
//        this.lable = lable;
//    }
//
//
//    public Map<String, String> getOptions() {
//        return options;
//    }
//    public void setOptions(Map<String, String> options) {
//        this.options = options;
//    }
//    public String getTip() {
//        return tip;
//    }
//    public void setTip(String tip) {
//        this.tip = tip;
//    }

    
    public String getLable() {
        return get(LABEL);
    }
    public void setLable(String label) {
        set(LABEL, label);
    }


    public Map<String, String> getOptions() {
        return get(OPTIONS);
    }
    public void setOptions(Map<String, String> options) {
        set(OPTIONS, options);
    }
    public String getTip() {
        return get(TIP);
    }
    public void setTip(String tip) {
        set(TIP, tip);
    }
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("lable: "+getLable()).append("\n");
        buf.append("tip: "+getTip()).append("\n");
        buf.append("options:\n");
        if(getOptions()==null) {
            buf.append("null");
        }
        else {
            for(String key:getOptions().keySet()) {
                buf.append("key: ").append(key).append(", value:").append(getOptions().get(key)).append("\n");
            }
        }
        return buf.toString();
    }

}
