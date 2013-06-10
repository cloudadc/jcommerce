/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class ListService extends RemoteService {
    public void listBeans(String model, final Listener listener) {
       listBeans(model, null, listener);
    }
    public abstract class MyProxy extends RpcProxy {
    	Criteria criteria = null;
    	public void setCriteria(Criteria criteria) {
    		this.criteria = criteria;
    	}
    }
    
    public BaseListLoader getLoader(final String model) {
    	return getLoader(model, null,null);
    }
    public BaseListLoader getLoader(final String model,  final Criteria criteria, final List<String> wantedFields) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IShopServiceAsync service = getService();
        MyProxy proxy = new MyProxy() {
            public void load(Object loadConfig, AsyncCallback callback) {
                service.getList(model, criteria, wantedFields, callback);
            }
        };
        proxy.setCriteria(criteria);
        
        // loader
        BaseListLoader loader = new BaseListLoader(proxy);
//        loader.setRemoteSort(true);

        return loader;
    }
    
    public void listBeans(final String model, String field, String value, final Listener listener) {
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(field, Condition.EQUALS, value));
        listBeans(model, criteria, listener);
    }
    
    public void countBeans(final String model, Criteria criteria, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IShopServiceAsync service = getService();
        service.countBeans(model, criteria, new AsyncCallback<Integer>() {
            public synchronized void onSuccess(Integer count) {
                if (listener != null) {
                    listener.onSuccess(count);
                }
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("ListService: getList onFailure(model="+model+", error="+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });                
    }
    
    public void listBeans(final String model, Criteria criteria, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IShopServiceAsync service = getService();
        service.getList(model, criteria, new AsyncCallback<List<BeanObject>>() {
            public synchronized void onSuccess(List<BeanObject> result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("ListService: getList onFailure(model="+model+", error="+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }

    
    public static abstract class Listener {
        public void onSuccess(int count) {
        }
        
        public void onSuccess(List<BeanObject> beans) {
        }
        
        public void onFailure(Throwable caught) {
        }
    }
}
