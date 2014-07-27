/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Criteria;

public interface IDefaultService extends RemoteService {
    public BeanObject getBean(String bean, String id);
    
    public List<BeanObject> getBeans(String bean, String[] ids);
    
    public List<BeanObject> getList(String bean);
    
    public List<BeanObject> getList(String bean, Criteria criteria);
    
    public List<BeanObject> getList(String bean, Criteria criteria, Map<String,List<String>> wantedFields);
    
    public String newObject(BeanObject obj);
    
    public boolean updateObject(String id, BeanObject obj);
    
    public boolean deleteObject(String bean, String id);
    public int deleteObjects(String bean, List<String> ids);
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, PagingLoadConfig config);
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, PagingLoadConfig config);

    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, Map<String,List<String>> wantedFields, PagingLoadConfig config);
    
//    public PagingLoadResult<BeanObject> getGoodsTypeUnit(boolean needAttrNumber, PagingLoadConfig config);
    
//    public List<BeanObject> regionChildList(String parent_id);
    
//    public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config);
//    public ListLoadResult getMyPaymentMetaList(ListLoadConfig config);
//    
//    public PaymentConfigMetaForm getPaymentConfigMeta(int paymentId);
//    public boolean installPayment(String paymentCode);
//    public boolean uninstallPayment(int paymentId);
//    public boolean savePayment(Map<String, Object> props);
    
}
