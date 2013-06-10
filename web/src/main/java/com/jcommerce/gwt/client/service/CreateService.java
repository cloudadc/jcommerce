/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ISpecialServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class CreateService extends RemoteService {
    public void createBean(BeanObject bean, final Listener listener) {
        if (bean == null) {
            throw new RuntimeException("bean = null");
        }
        
        IShopServiceAsync service = getService();
        service.newObject(bean, new AsyncCallback<String>() {
            public synchronized void onSuccess(String id) {
                System.out.println("newObject onSuccess( "+id);

                if (listener != null) {
                    listener.onSuccess(id);
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
    
    public void createOrder(BeanObject bean, final Listener listener){
        if (bean == null) {
            throw new RuntimeException("bean = null");
        }
        ISpecialServiceAsync service = getSpecialService();
        
        service.newOrder(bean, new AsyncCallback<String>(){

			public void onFailure(Throwable caught) {
                System.out.println("update onFailure("+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
			}


			public void onSuccess(String result) {
                System.out.println("newOrder onSuccess( "+result);

                if (listener != null) {
                    listener.onSuccess(result);
                }
			}});
    }
    
    public static abstract class Listener {
        public abstract void onSuccess(String id);
        
        public void onFailure(Throwable caught) {
        }
    }
}
