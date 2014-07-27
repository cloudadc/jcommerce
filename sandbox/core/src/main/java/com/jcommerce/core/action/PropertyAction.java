/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

public class PropertyAction extends Action {
    public PropertyAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    protected Map<String, String> getProperties(ModelObject obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, String> props = new HashMap<String, String>();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0 ; i < fields.length ; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            
            String name = fields[i].getName();
            Object value = getBeanProperty(obj, name);
            if (value instanceof java.util.Date) {
                props.put(name, ""+((java.util.Date)value).getTime());
            } else if (value instanceof ModelObject) {
                String id = getId((ModelObject)value);
                props.put(name, id);
            } else if (value instanceof Collection) {
                String s = null;
                for (Iterator itv = ((Collection)value).iterator(); itv.hasNext();) {
                    ModelObject o = (ModelObject) itv.next();
                    String id = getId(o);
                    if (s == null) {
                        s = id;
                    } else {
                        s += "," + id;
                    }
                }
                props.put(name, s);
            } else if (value != null){
                props.put(name, value.toString());
            }
        }

        return props;
    }        

    protected void copyProperties(Map<String, String> props, ModelObject obj) {
//        System.out.println("props:"+props);
        HashMap<String, Object> _props = new HashMap<String, Object>(); 
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fn = field.getName();
            Class type = field.getType(); 
            
            boolean bb = "attributes".equals(fn);
//            System.out.println("fn:"+fn+" field.getType():"+field.getType());
            if (bb) 
                System.out.println();
            if (!props.containsKey(fn)) {
                continue;
            }
            
            String value = props.get(fn);
            if (value == null) {
                continue;
            }

            if (type.equals(String.class)) {
                _props.put(fn, value);
            } else if (value.trim().length() > 0) {
                if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    _props.put(fn, new Boolean(value));
                } else if (type.equals(double.class) || type.equals(Double.class)) {
                    _props.put(fn, new Double(value));
                } else if (type.equals(int.class) || type.equals(Integer.class)) {
                    _props.put(fn, new Integer(value));
                } else if (type.equals(Timestamp.class)) {
                    _props.put(fn, new Timestamp(new Long(value).longValue()));
                } else if (type.equals(Time.class)) {
                    _props.put(fn, new Time(new Long(value).longValue()));
                } else if (type.equals(Date.class)) {
                    _props.put(fn, new Date(new Long(value).longValue()));
                } else if (type.equals(java.util.Date.class)) {
                    _props.put(fn, new java.util.Date(new Long(value).longValue()));
                } else if (ModelObject.class.isAssignableFrom(type)) {
                    // the value is ID
                    String bean = type.getSimpleName();
                    ModelObject mo = getModelObject(bean, value);
                    _props.put(fn, mo);
                } else if (Collection.class.isAssignableFrom(type)) {
                    Set set = new HashSet();
                    if (value != null) {
                        // the value is ID1,ID2,...,IDn
                        String[] ids = value.split(",");
                        for (int j = 0; j < ids.length; j++) {
                            String bean = config.getItemType(obj.getClass().getSimpleName(), fn);
                            ModelObject mo = getModelObject(bean, ids[j]);
                            System.out.println("<<< bean:"+bean+" mo:"+mo);
                            set.add(mo);
                        }
                    }
                    _props.put(fn, set);
                }
            }
        }
        
        System.out.println("_props:"+_props);
        try {
            BeanUtils.populate(obj, _props);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    } 
}
