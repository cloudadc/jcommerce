/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.util.MyRpcProxy;

public class BonusService extends RemoteService {
	
    public BasePagingLoader<PagingLoadResult<BeanObject>> getLoader(final Criteria criteria) {
        final String model = ModelNames.BONUSTYPE;
        
        final IShopServiceAsync service = getService();
        MyRpcProxy<PagingLoadResult<BeanObject>> proxy = new MyRpcProxy<PagingLoadResult<BeanObject>>() {
            public void load(Object loadConfig, AsyncCallback callback) {
                service.getUserBonusPagingList(criteria, (PagingLoadConfig) loadConfig, callback);
            }
        };
        proxy.setCriteria(criteria);
        
        // loader
        BasePagingLoader<PagingLoadResult<BeanObject>> loader = new BasePagingLoader<PagingLoadResult<BeanObject>>(proxy);
        loader.setRemoteSort(true);

        return loader;
    }
    
    public abstract class MyProxy extends RpcProxy {
    	Criteria criteria = null;
    	public void setCriteria(Criteria criteria) {
    		this.criteria = criteria;
    	}
    }
    
}
