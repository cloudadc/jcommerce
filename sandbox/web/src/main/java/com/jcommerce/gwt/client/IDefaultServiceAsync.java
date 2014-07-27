/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Criteria;

public interface IDefaultServiceAsync {
    public void updateObject(String id, BeanObject args, AsyncCallback<Boolean> callback);

    public void newObject(BeanObject args, AsyncCallback<String> callback);
    
    public void getList(String name, AsyncCallback<List<BeanObject>> callback);
    
    public void getList(String name, Criteria criteria, AsyncCallback<List<BeanObject>> callback);
    
    public void getList(String name, Criteria criteria, Map<String,List<String>> wantedFields, AsyncCallback<List<BeanObject>> callback);
    
    public void getBean(String name, String id, AsyncCallback<BeanObject> callback);

    public void getBeans(String name, String[] ids, AsyncCallback<List<BeanObject>> callback);

    public void deleteObject(String name, String id, AsyncCallback<Boolean> callback);
    public void deleteObjects(String name, List<String> ids, AsyncCallback<Integer> callback);
    
    public void getPagingList(String modelName, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getPagingList(String modelName, Criteria criteria, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getPagingList(String modelName, Criteria criteria, Map<String,List<String>> wantedFields, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
//    public void getGoodsTypeUnit(boolean needAttrNumber, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
//    
//    public void regionChildList(String parent_id,AsyncCallback<List<BeanObject>> callback);
//    
//    public void getPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult<BeanObject>> callback);
//    public void getMyPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult> callback);
//    
//    public void getPaymentConfigMeta(int paymentId, AsyncCallback<PaymentConfigMetaForm> callback);
//    
//    public void installPayment(String paymentCode, AsyncCallback<Boolean> callback);
//    public void uninstallPayment(int paymentId, AsyncCallback<Boolean> callback);
//    public void savePayment(Map<String, Object> props, AsyncCallback<Boolean> callback);
}
