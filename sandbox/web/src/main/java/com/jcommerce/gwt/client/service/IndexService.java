package com.jcommerce.gwt.client.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class IndexService extends RemoteService{
	public BasePagingLoader getLoader(final String model) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IShopServiceAsync service = getService();
        MyProxy proxy = new MyProxy() {
            public void load(Object loadConfig, AsyncCallback callback) {
            	           	            	
                service.getIndexPagingList(model,(PagingLoadConfig) loadConfig, callback);
            }
        };
       
        
        // loader
        BasePagingLoader loader = new BasePagingLoader(proxy);
        loader.setRemoteSort(true);

        return loader;
    }
	   
	   public static abstract class Listener {
	        public abstract void onSuccess(List<BeanObject> beans);
	        
	        public void onFailure(Throwable caught) {
	        }
	    }
	   public abstract class MyProxy extends RpcProxy {
	    	
	    	
	   }
}
