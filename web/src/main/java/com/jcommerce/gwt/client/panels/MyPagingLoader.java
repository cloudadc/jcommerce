package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.DataProxy;
import com.extjs.gxt.ui.client.data.DataReader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.service.Criteria;

public class MyPagingLoader extends BasePagingLoader {

    public MyPagingLoader(DataProxy proxy) {
        super(proxy);
        // TODO Auto-generated constructor stub
    }

    public MyPagingLoader(DataProxy proxy, DataReader reader) {
        super(proxy, reader);
        // TODO Auto-generated constructor stub
    }
    
    MyProxy proxy;
//    public MyPagingLoader(Criteria criteria) {
//        super(proxy);
//        proxy = new MyProxy() {
//            public void load(Object loadConfig, AsyncCallback callback) {
//                onload(loadConfig, callback);
//            }
//        };
//        
//    }

    public void onLoad(Object loadConfig, AsyncCallback callback) {
        
    }
    
    public abstract class MyProxy extends RpcProxy {
        Criteria criteria = null;
        public void setCriteria(Criteria criteria) {
            this.criteria = criteria;
        }
    }
}
