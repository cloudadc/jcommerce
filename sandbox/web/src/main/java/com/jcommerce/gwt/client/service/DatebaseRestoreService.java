package com.jcommerce.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class DatebaseRestoreService  extends RemoteService {

 public void getAllFileInfo(final Listener listener){
		 
		 
		 final IShopServiceAsync service = getService();
	        
		 service.getAllFileInfo(new AsyncCallback<List<List<String>>>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				 if (listener != null) {
	                    listener.onFailure(caught);
	             }
			}

	
			public void onSuccess(List<List<String>> result) {
				// TODO Auto-generated method stub
				 if (listener != null) {
	                    listener.onSuccess(result);
	             }
			}
		});
	        
	 }
		 
 	public void deleteFile(String fileName,final deleteFileListener listener){
 		 final IShopServiceAsync service = getService();
 		 service.deleteFile(fileName, new AsyncCallback<String>() {

			
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				 if (listener != null) {
	                    listener.onFailure(caught);
	             }
			}

		
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				 if (listener != null) {
	                    listener.onSuccess(result);
	             }
			}
		});
 		
 		
 		
 	}
	 
 	public void restoreFile(String fileName,final restoreFileListener listener){
		 final IShopServiceAsync service = getService();
		 service.restoreFile(fileName,new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				if (listener != null) {
                    listener.onFailure(caught);
				}
			}

			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				 if (listener != null) {
	                    listener.onSuccess(result);
	             }
			}
		});
	}
	 
	    public static abstract class Listener {
	        
	        public abstract void onSuccess(List<List<String>> success);
	        
	        
	        public void onFailure(Throwable caught) {
	        }
	    }
	        
	     
	    
	    public static abstract class deleteFileListener {
	        
	        public abstract void onSuccess(String success);
	        
	        
	        public void onFailure(Throwable caught) {
	        }
	    }
	        
	
	    public static abstract class restoreFileListener {
	        
	        public abstract void onSuccess(String success);
	        
	        
	        public void onFailure(Throwable caught) {
	        }
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
