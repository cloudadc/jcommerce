/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.jcommerce.core.action.BeanConfig;
import com.jcommerce.core.action.MapAction;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Manager;
import com.jcommerce.gwt.client.form.BeanObject;

public class PagingListAction extends MapAction {
    public PagingListAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, List<String> wantedFields, PagingLoadConfig pgc) {
        List<BeanObject> list = new ArrayList<BeanObject>();
        
        Manager manager = getManager(modelName);
        String methodName = config.getListMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[]{int.class, int.class, Criteria.class});
            if (method == null) {
                throw new RuntimeException("Method with paging not found: "+methodName);
            }
            
            List ret = (List)method.invoke(manager, pgc.getOffset(), pgc.getLimit(), criteria);
            for (Iterator it = ret.iterator(); it.hasNext();) {
                ModelObject model = (ModelObject) it.next();
                try {
                    Map<String, Object> props = getProperties(model, wantedFields);
                    list.add(new BeanObject(model.getClass().getSimpleName(), props));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        if (pgc.getSortInfo().getSortField() != null) {
            final String sortField = pgc.getSortInfo().getSortField();
            if (sortField != null) {
              Collections.sort(list, pgc.getSortInfo().getSortDir().comparator(new Comparator() {
                public int compare(Object o1, Object o2) {
                  Object s1 = ((BeanObject)o1).get(sortField);
                  Object s2 = ((BeanObject)o2).get(sortField);
                  if (s1 instanceof Comparable && s2 instanceof Comparable) {
                      return ((Comparable)s1).compareTo((Comparable)s2);
                  }
                  if (s1 != null && s2 != null) {
                      return s1.toString().compareTo(s2.toString());
                  } else if (s2 != null) {
                      return -1;  
                  }
                  return 0;
                }
              }));
            }
          }

          return new BasePagingLoadResult(list, pgc.getOffset(), getCount(modelName, criteria));    
    }
    
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, PagingLoadConfig pgc) {
    	return getPagingList(modelName, criteria, null, pgc);
    }
    
    private int getCount(String modelName, com.jcommerce.core.service.Criteria criteria) {
        Manager manager = getManager(modelName);
        String methodName = config.getCountMethod(modelName);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[]{Criteria.class});
            if (method == null) {
                throw new RuntimeException("Method with paging not found: "+methodName);
            }
            
            Integer count = (Integer)method.invoke(manager, criteria);
            return count.intValue();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
