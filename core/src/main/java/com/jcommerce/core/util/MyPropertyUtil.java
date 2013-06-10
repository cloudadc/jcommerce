package com.jcommerce.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.ModelObject;

public class MyPropertyUtil {
	private static final Logger log = Logger.getLogger(MyPropertyUtil.class.getName());
	
	// by default only copy simple fields and associated Object's Id, unless requested with wantedFields
    public static Map<String, Object> to2Form(ModelObject obj, List<String> wantedFields) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> props = new HashMap<String, Object>();
       
        try {
        if(wantedFields==null) {
			Map<String, Object> res = new HashMap<String, Object>();
	        PropertyDescriptor srcDescriptors[] = BeanUtilsBean.getInstance().getPropertyUtils()
    			.getPropertyDescriptors(obj);
	        for(PropertyDescriptor pd:srcDescriptors) {
	        	String name = pd.getName();
	        	if ("class".equals(name)) {
	        		continue;
	        	}

	        	Class type = pd.getPropertyType();
//	        	debug("to2Form-- name="+name+", type="+type+", value="+PropertyUtils.getProperty(obj, name));
	        	
	        	if(ModelObject.class.isAssignableFrom(type)) {
//	        		continue;
	        		// return associated object's id
	        		// OpenPersistenceManagerInViewFilter and lazy-loading works here
	        		ModelObject assoc = (ModelObject)PropertyUtils.getProperty(obj, name);
	        		if(assoc!=null) {
	        			res.put(name, assoc.getId());	        			
	        		}
	        	}
	        	else if(Set.class.isAssignableFrom(type)) {
	        		Field field = obj.getClass().getDeclaredField(name);
	        		if(MyPropertyUtil.isFieldCollectionOfModel(field)) {
	        			// ingore collection of Model
	        		} else {
		        		// TODO leon convert from org.datanucleus.sco.backed.HashSet to a normal HashSet
		        		// just to make it work, need refine
		        		Set t = (Set)PropertyUtils.getProperty(obj, name);
		        		Set t2 = new HashSet();
		        		if(t!=null) {
		        			t2.addAll(t);
		        		}
		        		res.put(name, t2);
	        		}
	        	}
	        	
	        	else if(PropertyUtils.isReadable(obj, name)) {
	        		res.put(name, PropertyUtils.getProperty(obj, name));
	        	}
			}
			return res;
        }
        
        else {
        	// not tested yet
            for(String wantedField : wantedFields) {
                String[] chainedNames = StringUtils.split(wantedField, "_");
                int i=1, noOfNames = chainedNames.length;
                ModelObject curObj = obj;
                for(String name: chainedNames) {
                  Object value = PropertyUtils.getProperty(curObj, name);
                  if(i == noOfNames) {
                     if(value instanceof ModelObject || value instanceof Collection) {
                         throw new RuntimeException("reached the end of chainedNames but found no simple value");
                     }
                     if(value!=null) {
                         props.put(wantedField, value);
                         break;
                     }
                  }
                  else {
                      if(value instanceof ModelObject) {
                          curObj = (ModelObject)value;
                      }
                      else {
                          throw new RuntimeException("not reached the end of chainedNames but found a simple value");
                      }
                  }
                  i++;
                }
            }
        }

        return props;
        } catch (Exception ex) {
        	throw new RuntimeException(ex);
        }
    }
	
    
    public static boolean isFieldCollectionOfModel(Field field) {
    	boolean res = true;
    	try {
			
			String type = field.getGenericType().toString();
			String paraType = type.substring(type.indexOf('<')+1, type.indexOf('>'));
			debug("paraType="+paraType);
			if(ModelObject.class.isAssignableFrom(Class.forName(paraType))) {
				res = true;
			} else {
				res = false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
    	
    }
    
    // to be used in DAOImpl.update only
    public static void copySimpleProperties(ModelObject dest, ModelObject orig) {
    	try {
		PropertyDescriptor srcDescriptors[] = BeanUtilsBean.getInstance()
				.getPropertyUtils().getPropertyDescriptors(dest);
		for (PropertyDescriptor pd : srcDescriptors) {
			String name = pd.getName();
			if("class".equals(name)) {
				continue;
			}
			if("id".equals(name)) {
				// TODO hardcoded.  for update, never change Id field
				continue;
			}
			
			Class type = pd.getPropertyType();
			debug("copySimpleProperties--  name=" + name + ", type=" + type);
			if (Collection.class.isAssignableFrom(type)) {
        		Field field = dest.getClass().getDeclaredField(name);
        		if(MyPropertyUtil.isFieldCollectionOfModel(field)) {
        			// ingore collection of Model
        			continue;
        		} else {
        			
	        		PropertyUtils.setProperty(dest, name, PropertyUtils.getProperty(orig, name));
        		}
			}
			else if (ModelObject.class.isAssignableFrom(type)) {
				// we do not support editing of associated object at this point
				// nor pointing to another associated object
				continue;
			}
			else { 
				PropertyUtils.setProperty(dest, name, PropertyUtils.getProperty(orig, name));
			}
			
		} 
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
	}
	
    public static void form2To(ModelObject dest, Map<String, Object> orig) {

    	debug("form2To-- props:"+orig);
        
        try {
       	
        HashMap<String, Object> _props = new HashMap<String, Object>(); 

		PropertyDescriptor srcDescriptors[] = BeanUtilsBean.getInstance()
			.getPropertyUtils().getPropertyDescriptors(dest);
		
		for (PropertyDescriptor pd : srcDescriptors) {
			String fn = pd.getName();
			if("class".equals(fn)) {
				continue;
			}
			Class type = pd.getPropertyType();
            

            debug("fn:"+fn+" type:"+type);
            if (!orig.containsKey(fn)) {
                continue;
            }
            
            Object value = orig.get(fn);
            if (value == null) {
            	_props.put(fn, value);
            	continue;
            }
            
            
            if (ModelObject.class.isAssignableFrom(type)) {
                String bean = type.getSimpleName();
                if (value instanceof String) {
                    // the value is ID
                	debug("form2To-- type="+type);
                	ModelObject mo = (ModelObject)type.newInstance();
                	mo.setId((String)value);
//                    ModelObject mo = getModelObject(bean, (String)value);
                    _props.put(fn, mo);
                } else if (value instanceof Map) {
                    Map<String, Object> cprops = (Map<String, Object>)value;
                    ModelObject mo = (ModelObject)type.newInstance();
                    form2To(mo, cprops);
                    _props.put(fn, mo);             
                } else {
                    throw new RuntimeException("Unknown value type: " + value+","+value.getClass()+" bean:"+bean);
                }    
            } else if (Collection.class.isAssignableFrom(type)) {
                Collection col = null;
                if (value != null) {
            		if(MyPropertyUtil.isFieldCollectionOfModel(dest.getClass().getDeclaredField(fn))) {
            			// NOTE: this means always Add/Update child ModelObject separately
            			continue;
            		}
            		else {
//                      String bean = config.getItemType(obj.getModelName(), fn);
                        if (value instanceof String) {
                        	col = (Collection)PropertyUtils.getProperty(dest, fn);
                        	String val = (String)value;
                        	if(((String)val).indexOf(",")>0) {
                        		// for properties like Goods.categoryIds which is internally list of String, 
                        		// and submitted in the format of comma-separated string 
                        		// TODO escape ","
                				String[] values = ConvertUtil.split(val, ",");
                				col.addAll(Arrays.asList(values));
                			} else {
                				col.add(value);
                			}  
                        	
                        } else if (value instanceof Collection) {
                        	
                            Collection c = (Collection)value;
                            col = (Collection)PropertyUtils.getProperty(dest, fn);
                            debug("size: "+c.size());
                            col.addAll(c);
//                            _props.put(fn, c);
                        }
                        
                    } 
            		
//            		else {
////                      throw new RuntimeException("Unknown value type: " + value+","+value.getClass()+" bean:"+bean);
//              		 throw new RuntimeException("Unknown value type: " + value+","+value.getClass());
//                  }
                } else {
                	// keep the old value
                	col = (Collection)PropertyUtils.getProperty(dest, fn);
                }
//                _props.put(fn, col);
            } else {
           		_props.put(fn, value);
            }
        }
        
        debug("form2To-- _props:"+_props);

            BeanUtils.populate(dest, _props);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    public static void debug(String s) {
    	log.info("in [MyPropertyUtil]: "+s);
    }
}
