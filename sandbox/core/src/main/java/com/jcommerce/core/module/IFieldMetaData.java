/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.io.Serializable;
import java.util.Map;

public interface IFieldMetaData extends Serializable {
    public String getLable();
    public void setLable(String lable);

    public Map<String, String> getOptions();
    public void setOptions(Map<String, String> options);
    public String getTip();
    public void setTip(String tip);
}
