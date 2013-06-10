package com.jcommerce.gwt.client.util;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.jcommerce.gwt.client.service.Criteria;


public class MyListLoader<D extends ListLoadResult<?>> extends BaseListLoader<D> {

	  public MyListLoader(MyRpcProxy proxy) {
		    super(proxy);
	  }
	  
	  public void setCriteria(Criteria criteria) {
		  MyRpcProxy proxy = (MyRpcProxy)getProxy();
		  proxy.setCriteria(criteria);
	  }
	  
}
