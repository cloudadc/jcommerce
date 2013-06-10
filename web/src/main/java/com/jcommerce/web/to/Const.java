package com.jcommerce.web.to;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.wrapper.BaseWrapper;

public class Const extends BaseWrapper {
    private static Const inst = new Const();
    
    public final static int DISPLAY_LIST = ShopConfig.DISPLAY_LIST;
    public final static int DISPLAY_GRID = ShopConfig.DISPLAY_GRID;
    public final static int DISPLAY_TEXT = ShopConfig.DISPLAY_TEXT;
    
    public final static int GOODS_TEXTIMAGE = ShopConfig.GOODS_TEXTIMAGE;
    public final static int GOODS_TEXT = ShopConfig.GOODS_TEXT;
    public final static int GOODS_IMAGE = ShopConfig.GOODS_IMAGE;

    public final static int SORT_DESC = ShopConfig.SORT_DESC;
    public final static int SORT_ASC = ShopConfig.SORT_ASC;
    
    public final static int SORT_BY_ONSHELFTIME = ShopConfig.SORT_BY_ONSHELFTIME;
    public final static int SORT_BY_PRICE = ShopConfig.SORT_BY_PRICE;
    public final static int SORT_BY_UPDATE = ShopConfig.SORT_BY_UPDATE;
    
    public final static int ATTR_TEXT = ShopConfig.ATTR_TEXT;
    public final static int ATTR_OPTIONAL = ShopConfig.ATTR_OPTIONAL;
    public final static int ATTR_TEXTAREA = ShopConfig.ATTR_TEXTAREA;
    public final static int ATTR_URL = ShopConfig.ATTR_URL;
    public final static int ATTR_RANGE = ShopConfig.ATTR_RANGE;

    private Const() {
        init();
    }
    
    public static Const getInstance() {
        return inst;
    }

    private void init() {
        Class cls = getClass();
        Field[] flds = cls.getDeclaredFields();
        for (Field f : flds) {
            int mod = f.getModifiers();
            if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
                String name = f.getName();
                try {
                    Object value = f.get(this);
//                    System.out.println("name:"+name+" value:"+value);
                    put(name, value);
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }
}
