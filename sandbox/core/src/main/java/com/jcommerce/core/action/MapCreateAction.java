/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

public class MapCreateAction extends MapAction {
    public MapCreateAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public String newObject(String modelName, Map<String, Object> props) {
        System.out.println("newObject("+modelName);
        // remove the id field. 
        props.remove("id");
        props.remove("Id");
        props.remove("ID");
        
        ModelObject model = createModelObject(modelName);
        if (model == null) {
            return null;
        }
        
        copyProperties(props, model);
        System.out.println("-----1-------"+props);

        String id = saveObject(modelName, model);
        System.out.println("-----5-------"+id);
        return id;
    }    
}
