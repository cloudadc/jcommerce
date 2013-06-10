/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class UpdateService extends RemoteService {
    public void updateBean(String id, BeanObject bean, final Listener listener) {
        if (id == null) {
            throw new RuntimeException("id = null");
        }
        
        if (bean == null) {
            throw new RuntimeException("bean = null");
        }
        
        final IShopServiceAsync service = getService();
        service.updateObject(id, bean, new AsyncCallback<Boolean>() {
            public synchronized void onSuccess(Boolean success) {
                System.out.println("update onSuccess( "+success);

                if (listener != null) {
                    listener.onSuccess(success);
                }
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("update onFailure("+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }
    
    public static abstract class Listener {
        public abstract void onSuccess(Boolean success);
        
        public void onFailure(Throwable caught) {
        }
    }
}
