package com.jcommerce.core.module;

import java.util.Map;

public class FieldMetaData implements IFieldMetaData {

    private String lable;
    private String tip;
    private Map<String, String> options; // value->lable
    
    public String getLable() {
        return lable;
    }
    public void setLable(String lable) {
        this.lable = lable;
    }

    public Map<String, String> getOptions() {
        return options;
    }
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("lable: "+lable).append("\n");
        buf.append("tip: "+tip).append("\n");
        buf.append("options:\n");
        if(options==null) {
            buf.append("null");
        }
        else {
            for(String key:options.keySet()) {
                buf.append("key: ").append(key).append(", value:").append(options.get(key)).append("\n");
            }
        }
        return buf.toString();
    }
}
