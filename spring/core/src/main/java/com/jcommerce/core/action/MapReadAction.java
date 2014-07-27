/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

public class MapReadAction extends MapAction {
    public MapReadAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public Map<String, Object> getBean(String modelName, String id) {
        System.out.println("getBean("+modelName);
        Map<String, Object> data = new HashMap<String, Object>();
        
        if (id.equals("")) {
            new Exception("id:"+id).printStackTrace();
        }
        
        ModelObject model = getModelObject(modelName, id);
        try {
            if (model != null) {
                data = getProperties(model);
            } else {
                throw new RuntimeException("model == null when modelName="+modelName+" id="+id);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }        
        return data;
    }
}
