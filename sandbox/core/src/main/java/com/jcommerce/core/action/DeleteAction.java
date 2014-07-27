/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.service.Manager;

public class DeleteAction extends Action {
    public DeleteAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    public boolean delete(String bean, String id) {
        Manager manager = getManager(bean);
        String methodName = config.getRemoveMethod(bean);
        try {
            Method method = manager.getClass().getMethod(methodName, new Class[] {String.class});
            if (method == null) {
                System.out.println("Method not found: "+methodName);
            } else {
                method.invoke(manager, id);
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
        return true;
    }
}
