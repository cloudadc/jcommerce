package com.jcommerce.gwt.client.model;

public interface IAttribute {
    public static final int TYPE_ONLY = 0; 
    public static final int TYPE_SINGLE = 1; 
    public static final int TYPE_MULTIPLE = 2;

    public static final int INDEX_NEEDNOT = 0; 
    public static final int INDEX_KEYWORD = 1; 
    public static final int INDEX_RANGE = 2;
    
    public static final boolean ISLINKED_TRUE = true;
    public static final boolean ISLINKED_FALSE = false;
     
    public static final int TYPE_NEEDNOTSELECT = 0; 
    public static final int TYPE_NEEDSELECT = 1; 

    public static final int INPUT_SINGLELINETEXT = 0;
    public static final int INPUT_MULTIPLELINETEXT = 2;
    public static final int INPUT_CHOICE = 1;

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
}
