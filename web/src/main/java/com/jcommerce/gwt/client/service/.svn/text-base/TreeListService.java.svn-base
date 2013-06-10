package com.jcommerce.gwt.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import com.extjs.gxt.ui.client.data.BaseTreeLoader;

import com.extjs.gxt.ui.client.data.ModelData;

import com.extjs.gxt.ui.client.data.RpcProxy;



import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;


public class TreeListService extends RemoteService{
	public BaseTreeLoader getLoader(final String model, final List<String> wantedFields) {

		return getLoader(model, null, wantedFields);
	}
   
   public BaseTreeLoader getLoader(final String model,  final Criteria criteria, final List<String> wantedFields) {
       if (model == null) {
           throw new RuntimeException("model = null");
       }
       
       final IShopServiceAsync service = getService();
       RpcProxy<List<BeanObject>> proxy = new RpcProxy<List<BeanObject>>() {
           public void load(Object loadConfig, final AsyncCallback<List<BeanObject>> callback) {
        	   if(loadConfig == null){
        		   
        		   service.getTreePagingList(model, criteria, (BeanObject)loadConfig, new AsyncCallback<List<ModelData>>() {

       				
       				public void onFailure(Throwable caught) {
       					// TODO Auto-generated method stub
       					
       				}

       				
       				public void onSuccess(List<ModelData> result) {
       					List<BeanObject> finalResult = new ArrayList<BeanObject>();
       					for (Iterator<ModelData> it = result.iterator(); it.hasNext();) {
       						
       						BeanObject parent = (BeanObject) it.next();	
       						if(parent.getParent()==null){
       							setChildren(parent,result);
       							finalResult.add(parent);
       						}
       						
       						
       					}
       					callback.onSuccess(finalResult);
       				}
        		   });
        	   }else{
        		  
        		   BeanObject model = (BeanObject) loadConfig;
        		   
        		   List<BeanObject> finalResult = new ArrayList<BeanObject>();
        		   List<ModelData> result = model.getChildren();
        		  
  					for (Iterator it = result.iterator(); it.hasNext();) {
  						BeanObject parent = (BeanObject) it.next();
  						
   						//setChildren(parent,result);
   						finalResult.add(parent);
   						
  					}
                   callback.onSuccess(finalResult);  
        	   }
              
            	   
              
           }
       };
       
       
       // loader
       BaseTreeLoader<BeanObject> loader = new BaseTreeLoader<BeanObject>(proxy);
       //loader.setRemoteSort(true);
             
       return loader;
   }
   public void setChildren(BeanObject parent,List<ModelData> result){
	   List<ModelData> childList = new ArrayList<ModelData>();
		String childIds = (String)parent.getProperties().get("children");
		
		if(childIds != null){
			List<String> children =Arrays.asList(childIds.split(","));																		
			if (children != null && children.size()>0) {
			
				for(Iterator<ModelData> it1 = result.iterator(); it1.hasNext();){
					BeanObject child = (BeanObject) it1.next();
					String id = child.getString("id");
					if(children.contains(id)){
						child.setParent(parent);
						setChildren(child,result);
						childList.add(child);
					}
				}
				parent.setChildren(childList);
			}
		}
   }
   public BaseTreeLoader getLoader(final String model) {

   		return getLoader(model, null, null);
   }

   	public BaseTreeLoader getLoader(final String model, final Criteria criteria) {

   		return getLoader(model, criteria, null);
   	}
   
  
   
}
