/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class RegionAreaService extends RemoteService {
	
	public void createBean(BeanObject bean, final Listener listener) {
        if (bean == null) {
            throw new RuntimeException("bean = null");
        }
        
        IShopServiceAsync service = getService();
        
//        service.saveRegionArea(bean, new AsyncCallback<String>() {
//            public synchronized void onSuccess(String id) {
//            }
//
//			public void onFailure(Throwable caught) {
//			}
//        });
    }
    
    public static abstract class Listener {
        public abstract void onSuccess(String id);
        
        public void onFailure(Throwable caught) {
        }
    }
}
