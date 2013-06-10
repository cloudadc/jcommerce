/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.jcommerce.gwt.client.IShopService;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ISpecialService;
import com.jcommerce.gwt.client.ISpecialServiceAsync;

public class RemoteService {
    public final static String SERVICE = "service";
    public final static String DEFAULTSERVICE = "DefaultService";
    public final static String SPECIALSERVICE = "SpecialService";
    
    public static void init() {
        IShopServiceAsync service = (IShopServiceAsync) GWT.create(IShopService.class);
        String moduleRelativeURL = GWT.getModuleBaseURL() + "ishopService";
        ((ServiceDefTarget)service).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(SERVICE, service);        
        
        ISpecialServiceAsync specialService = (ISpecialServiceAsync) GWT.create(ISpecialService.class);
//        moduleRelativeURL = GWT.getModuleBaseURL() + "customizedService.do";
        moduleRelativeURL = GWT.getModuleBaseURL() + "specialService";
        ((ServiceDefTarget)specialService).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(SPECIALSERVICE, specialService); 
    }
    
    protected IShopServiceAsync getService() {
        return (IShopServiceAsync)Registry.get(SERVICE);
    }
    
    public static ISpecialServiceAsync getSpecialService() {
        ISpecialServiceAsync specialService = (ISpecialServiceAsync)Registry.get(SPECIALSERVICE);
        if(specialService==null) {
            init();
            specialService = (ISpecialServiceAsync)Registry.get(SPECIALSERVICE);
        }
        return specialService;
        
    }
}
