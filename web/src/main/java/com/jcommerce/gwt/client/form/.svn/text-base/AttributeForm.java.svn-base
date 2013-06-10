package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.model.IAttribute;

public class AttributeForm extends BeanObject implements IAttribute{
	
//    public static final int TYPE_NEEDNOTSELECT = 0; 
//    public static final int TYPE_NEEDSELECT = 1; 
//
//    public static final int INPUT_SINGLELINETEXT = 0;
//    public static final int INPUT_MULTIPLELINETEXT = 2;
//    public static final int INPUT_CHOICE = 1;
	

    
    public AttributeForm(){
    	super();
    }
    
    public AttributeForm (String modelName, Map<String, Object> values) {
        super(modelName, values);
    }
    
    public void validate() throws ValidationException{
    	StringBuffer errorBuf = new StringBuffer();
    	
    	String goodsType = getString(GOODSTYPE);
    	if(goodsType == null || goodsType.trim().length() == 0) {
    		errorBuf.append("必须选择所属商品类型").append("\r\n");
    		
    	}
    	String name = getString(NAME);
    	if(name==null || name.trim().length()==0) {
    		errorBuf.append("属性名称不能为空").append("\r\n");
    	}
    	if(errorBuf.length()>0) {
    		throw new ValidationException(errorBuf.toString().trim());
    	}
    }
}
