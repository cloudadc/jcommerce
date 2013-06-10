/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class DeleteService extends RemoteService {
    public void deleteBeans(String model, List<String> ids, final BatchDeleteListener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        if (ids == null) {
            throw new RuntimeException("ids = null");
        }
        
        final IShopServiceAsync service = getService();
        service.deleteObjects(model, ids, new AsyncCallback<Integer>() {
            public synchronized void onSuccess(Integer successCount) {
                if (listener != null) {
                    listener.onSuccess(successCount);
                }
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("getList onFailure("+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }
    public void deleteBean(String model, String id, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        if (id == null) {
            throw new RuntimeException("id = null");
        }
        
        final IShopServiceAsync service = getService();
        service.deleteObject(model, id, new AsyncCallback<Boolean>() {
            public synchronized void onSuccess(Boolean success) {
                if (listener != null) {
                    listener.onSuccess(success);
                }
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("getList onFailure("+caught);
                
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
    public static abstract class BatchDeleteListener {
        public abstract void onSuccess(Integer successCount);
        
        public void onFailure(Throwable caught) {
        }
    }
}
