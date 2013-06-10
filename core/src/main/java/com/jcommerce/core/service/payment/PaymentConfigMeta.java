package com.jcommerce.core.service.payment;

import java.io.Serializable;
import java.util.Map;

import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.PaymentConfigFieldMeta;

public class PaymentConfigMeta extends Payment implements Serializable{
    //extends BaseModelData 
    // constant must follow the same as com.jcommerce.gwt.client.panels.leontest.PaymentConfigMeta !!!!!
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String PAYFEE = "pay_fee";
    public static final String ISCOD = "is_cod";
    public static final String ISONLINE = "is_online";
    public static final String ISENABLED = "is_enabled";
    public static final String DESC = "desc";
    public static final String ORDER = "pay_order";
    public static final String INSTALL = "install";
    public static final String FIELDMETAS = "fieldMetas";
    public static final String FIELDVALUES = "fieldValues";
    
//    private int id;
//    private String name;
//    private String code;
//    private String description;
//    private String payFee;
//    private boolean isCod;
//    private boolean isOnline;
//    private boolean isEnabled;
    private Map<String, PaymentConfigFieldMeta> fieldMetas;
    private Map<String, String> fieldValues;

    public Map<String, PaymentConfigFieldMeta> getFieldMetas() {
        return fieldMetas;
    }

    public void setFieldMetas(Map<String, PaymentConfigFieldMeta> fieldMetas) {
        this.fieldMetas = fieldMetas;
    }

    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }
//  public String getCode() {
//  return code;
//}
//
//public void setCode(String code) {
//  this.code = code;
//}
//
//public String getDescription() {
//  return description;
//}
//
//public void setDescription(String description) {
//  this.description = description;
//}
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public boolean isCod() {
//        return isCod;
//    }
//
//    public void setCod(boolean isCod) {
//        this.isCod = isCod;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setEnabled(boolean isEnabled) {
//        this.isEnabled = isEnabled;
//    }
//
//    public boolean isOnline() {
//        return isOnline;
//    }
//
//    public void setOnline(boolean isOnline) {
//        this.isOnline = isOnline;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPayFee() {
//        return payFee;
//    }
//
//    public void setPayFee(String payFee) {
//        this.payFee = payFee;
//    }

    public PaymentConfigMeta() {
        
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("id: "+getId()).append("\n");
        buf.append("name: "+getName()).append("\n");
        buf.append("code: "+getCode()).append("\n");
        buf.append("description: "+getDescription()).append("\n");
        buf.append("payFee: "+getFee()).append("\n");
        buf.append("isCod: "+isCod()).append("\n");
        buf.append("isOnline: "+isOnline()).append("\n");
        buf.append("isEnabled: "+isEnabled()).append("\n");
        
        buf.append("fieldValues: \n");
        if(getFieldValues()==null) {
            buf.append("null\n");
        }
        else {
            for(String key:getFieldValues().keySet()) {
                buf.append("key: ").append(key).append(", value: ").append(getFieldValues().get(key)).append("\n");
            }
        }
        
        buf.append("fieldMetas: \n");
        if(getFieldMetas() == null) {
            buf.append("null");
        }
        else {
            buf.append("{\n");
            for(String key:getFieldMetas().keySet()) {
                PaymentConfigFieldMeta fieldMeta = getFieldMetas().get(key);
                buf.append("key=").append(key).append("\n");
                buf.append("value={").append(fieldMeta.toString()).append("}");
            }
            buf.append("}\n");
            
        }
        
        return buf.toString();
    }

//    
//    public Map<String, PaymentConfigFieldMeta> getFieldMetas() {
//        return get(FIELDMETAS);
//    }
//
//    public void setFieldMetas(Map<String, PaymentConfigFieldMeta> fieldMetas) {
//        set(FIELDMETAS, fieldMetas);
//    }
//
//    public Map<String, String> getFieldValues() {
//        return get(FIELDVALUES);
//    }
//
//    public void setFieldValues(Map<String, String> fieldValues) {
//        set(FIELDVALUES, fieldValues);
//    }
//    public String getCode() {
//        return get(CODE);
//    }
//
//    public void setCode(String code) {
//        set(CODE, code);
//    }
//
//    public String getDescription() {
//        return get(DESC);
//    }
//
//    public void setDescription(String description) {
//        set(DESC, description);
//    }
//
//    public int getId() {
//        return get(ID);
//    }
//
//    public void setId(int id) {
//        set(ID, id);
//    }
//
//    public boolean isCod() {
//        return get(ISCOD);
//    }
//
//    public void setCod(boolean isCod) {
//        set(ISCOD, isCod);
//    }
//
//    public boolean isEnabled() {
//        return get(ISENABLED);
//    }
//
//    public void setEnabled(boolean isEnabled) {
//        set(ISENABLED, isEnabled);
//    }
//
//    public boolean isOnline() {
//        return get(ISONLINE);
//    }
//
//    public void setOnline(boolean isOnline) {
//        set(ISONLINE, isOnline);
//    }
//
//    public String getName() {
//        return get(NAME);
//    }
//
//    public void setName(String name) {
//        set(NAME, name);
//    }
//
//    public String getPayFee() {
//        return get(PAYFEE);
//    }
//
//    public void setPayFee(String payFee) {
//        set(PAYFEE, payFee);
//    }
    
    
    
}
