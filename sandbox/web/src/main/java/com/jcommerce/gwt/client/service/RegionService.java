package com.jcommerce.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class RegionService extends RemoteService{
	
	public void getRegionChildList(String id , final Listener listener){
		
		final IShopServiceAsync service = getService();
		
		service.getRegionChildren(id , new AsyncCallback<List<BeanObject>>() {
			
			public void onSuccess(List<BeanObject> result) {
				if(result != null){
					listener.onSuccess(result);
				}
			}
			
			public void onFailure(Throwable caught) {
				System.out.println("Error:"+caught);
			}
		});
	}
	
    public void getRegionAncestors(Long id , final Listener listener){
        
        final IShopServiceAsync service = getService();
        
        service.getRegionAncestors(id , new AsyncCallback<List<BeanObject>>() {
            
            public void onSuccess(List<BeanObject> result) {
                if(result != null){
                    listener.onSuccess(result);
                }
            }
            
            public void onFailure(Throwable caught) {
                System.out.println("Error:"+caught);
            }
        });
    }

    public static abstract class Listener{
		public abstract void  onSuccess(List<BeanObject> beans);
        
        public void onFailure(Throwable caught) {
        }
	}
}
