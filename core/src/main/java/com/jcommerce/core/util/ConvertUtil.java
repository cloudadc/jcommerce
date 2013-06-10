package com.jcommerce.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Utility class to convert one object to another.
 * <p>
 * <a href="ConvertUtil.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible </a>
 */
public final class ConvertUtil {

	
	public static String[] split(String src, String delim) {
		List<String> tokens = new ArrayList<String>();
		int delimLenth = delim.length();
		int index=0, lastindex = 0;
		while(true) {
//			System.out.println("lastindex="+lastindex);
			index = src.indexOf(delim, lastindex);
//			System.out.println("index="+index);
			if(index<0) {
				tokens.add(src.substring(lastindex));
				break;
			} else {
				String t = src.substring(lastindex, index);
//				System.out.println("t="+t);
				tokens.add(t);
				lastindex = index+delimLenth;
			}
		}
		
		return tokens.toArray(new String[0]);
	}
	//~ Static fields/initializers
    // =============================================

    private static Log log = LogFactory.getLog(ConvertUtil.class);

    //~ Methods
    // ================================================================

    /**
     * Method to convert a ResourceBundle to a Map object.
     * 
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map convertBundleToMap(ResourceBundle rb) {
        Map map = new HashMap();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }

    public static List convertSetToList(Set set) {
    	List list = new ArrayList();
    	if(set==null||set.isEmpty()){
    		return list;
    	}
    	list.addAll(set);
        return list;
    }
    
    public static Map convertListToMap(List list) {
        Map map = new LinkedHashMap();

//        for (Iterator it = list.iterator(); it.hasNext();) {
//            LabelValue option = (LabelValue) it.next();
//            map.put(option.getLabel(), option.getValue());
//        }

        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * 
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            props.put(key, rb.getString(key));
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     * 
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            Map map = convertBundleToMap(rb);

            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) { 
            log.error("Exception occured populating object: " + e.getMessage());
        }

        return obj;
    }
 
    /**
     * Convenience method to convert a form to a POJO and back again
     * 
     * @param o the object to tranfer properties from
     * @return converted object
     */
   /* public static Object convert(Object o) throws ConvertException {
        if (o == null) {
            return null;
        }
        try {
            Object target = getOpposingObject(o);
            BeanUtils.copyProperties(target, o);
            return target;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

/*    public static Object convert(Object o, Class targetClass) throws Exception {
        if (o == null) {
            return null;
        }
        Object target = targetClass.newInstance();
        BeanUtils.copyProperties(target, o);
        return target;
    }*/
/*
    public static Object convert(Object o, String targetClassName)
            throws Exception {
        if (o == null) {
            return null;
        }
        Class obj = Class.forName(targetClassName);
        Object target = obj.newInstance();
        BeanUtils.copyProperties(target, o);
        return target;
    }*/

    public static Object convert(Object o, Object target)
            throws ConvertException {
        if (o == null) {
            return null;
        }
        try {
            BeanUtils.copyProperties(target, o);
            return target;
        } catch (IllegalAccessException e) {
            throw new ConvertException("Bean Convert Failure",e);
        } catch (InvocationTargetException e) {
            throw new ConvertException("Bean Convert Failure",e);
        }
    }

    public static Object convertOmit(Object src, Object dest, String[] omit)
            throws ConvertException {
        try {
            return BeanUtilsProxy.getInstance().convertOmit(dest, src, omit);
        } catch (IllegalAccessException e) {
            throw new ConvertException("Bean Convert Failure",e);
        } catch (InvocationTargetException e) {
            throw new ConvertException("Bean Convert Failure",e);
        }

    }

    /**
     * Convenience method to convert Lists of objects to a list of objects in
     * different class. Also checks for and formats dates.
     * 
     * @param o
     * @return @throws Exception
     */
    public static List convert(List list, Class classObj)
            throws ConvertException {
        if (list == null) {
            return null;
        }
        try {
            List targetList = new ArrayList(list.size());
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                Object target = classObj.newInstance();
                convert(o, target);
                targetList.add(target);
            }
            return targetList;
        } catch (InstantiationException e) {
            throw new ConvertException("Bean Convert Failure",e);
        } catch (IllegalAccessException e) {
            throw new ConvertException("Bean Convert Failure",e);
        }
    }

    /**
     * Convenience method to convert Lists (in a Form) from POJOs to Forms. Also
     * checks for and formats dates.
     * 
     * @param o
     * @return @throws Exception
     */
    /*public static Object convertLists(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        Object target = null;

        PropertyDescriptor[] origDescriptors = PropertyUtils
                .getPropertyDescriptors(o);

        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();

            if (origDescriptors[i].getPropertyType().equals(List.class)) {
                List list = (List) PropertyUtils.getProperty(o, name);
                for (int j = 0; j < list.size(); j++) {
                    Object origin = list.get(j);
                    target = convert(origin);
                    list.set(j, target);
                }
                PropertyUtils.setProperty(o, name, list);
            }
        }
        return o;
    }*/

}