package com.jcommerce.gwt.client.model;

import java.util.Map;

import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.form.BeanObject;

public class AttributeForm extends BeanObject{
	
//    public static final int TYPE_NEEDNOTSELECT = 0; 
//    public static final int TYPE_NEEDSELECT = 1; 
//
//    public static final int INPUT_SINGLELINETEXT = 0;
//    public static final int INPUT_MULTIPLELINETEXT = 2;
//    public static final int INPUT_CHOICE = 1;
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String GOODSTYPE = "goodsType";
	public static final String INPUTTYPE = "inputType";
    public static final String TYPE = "type";
    public static final String VALUES = "values";
    public static final String INDEX = "index";
    public static final String SORTORDER = "sortOrder";
    public static final String LINKED = "linked";
    public static final String GROUP = "group";
    
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
