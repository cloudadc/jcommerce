package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.DataReader;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ISpecialServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.RemoteService;


public class MyPagingListService extends RemoteService {

    public BaseListLoader getLoader(final String model) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        /**
         * This class is just an attempt to use DataReader to convert data.
         * It works well, however, we decide not to adopt this way in our application.
         */
        
        // to bypass a compile error when build with ant
        //     [javac] D:\JCommerce\JCommerceGae\admin\com\jcommerce\gwt\client\panels\MyPagingListService.java:60: 
        //      type parameter com.extjs.gxt.ui.client.data.ListLoadResult is not within its bound
        
//        final ISpecialServiceAsync service = getSpecialService();
//        MyProxy<ListLoadResult> proxy = new MyProxy<ListLoadResult>() {
//            public void load(Object loadConfig, AsyncCallback<ListLoadResult> callback) {
//                service.getMyPaymentMetaList((ListLoadConfig)loadConfig, callback);
//            }
//        };
//        
//        // convert from ListLoadResult<Map<String, Object>> to ListLoadResult<BeanObject>
//        DataReader<ListLoadResult> reader = new DataReader<ListLoadResult>() {
//
//            public ListLoadResult<BeanObject> read(Object loadConfig, Object data) {
//                System.out.println("my reader: "+data.getClass().getName());
//                List<BeanObject> destdatas = new ArrayList<BeanObject>();
//                ListLoadResult<Map<String, Object>> casteddata = (ListLoadResult)data;
//                List<Map<String, Object>> origdatas = casteddata.getData();
//                for(Map<String, Object> origdata:origdatas) {
//                    destdatas.add(new BeanObject(ModelNames.PAYMENT_META, origdata));
//                }
//                
//                ListLoadResult<BeanObject> res = new BaseListLoadResult<BeanObject>(destdatas);
//                return res;
//            }
//  
//        };
//        // loader
//        BaseListLoader loader = new BaseListLoader<ListLoadResult>(proxy, reader);
//        loader.setRemoteSort(true);
//        return loader;
        
        return null;
    }


    
    public abstract class MyProxy<D> extends RpcProxy<D> {
        Criteria criteria = null;
        public void setCriteria(Criteria criteria) {
            this.criteria = criteria;
        }
    }
    
    
}
