/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

public class PropertyCreateAction extends PropertyAction {
    public PropertyCreateAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public String newObject(String modelName, Map<String, String> props) {
        System.out.println("newObject("+modelName);
        try {
            // remove the id field. 
            props.remove("id");
            props.remove("Id");
            props.remove("ID");
            
            ModelObject model = (ModelObject)Class.forName("com.jcommerce.core.model."+modelName).newInstance();
            copyProperties(props, model);
            System.out.println("-----1-------"+props);

            String id = saveObject(modelName, model);
            System.out.println("-----5-------"+id);
            return id;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }    
}
