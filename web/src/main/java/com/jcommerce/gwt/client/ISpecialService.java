package com.jcommerce.gwt.client;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingConfigMetaForm;
import com.jcommerce.gwt.client.service.Criteria;

public interface ISpecialService extends RemoteService {
    
    public PagingLoadResult<BeanObject> getGoodsTypeListWithAttrCount(String modelName, 
            Criteria criteria, List<String> wantedFields, PagingLoadConfig config);
    
    public String newOrder(BeanObject obj);
    
    public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config);
    public ListLoadResult getMyPaymentMetaList(ListLoadConfig config);
    public PaymentConfigMetaForm getPaymentConfigMeta(String paymentId);
    
    public Boolean installPayment(String paymentCode);
    public Boolean uninstallPayment(String paymentId);
    public Boolean savePayment(Map<String, String> props);
    
    public ListLoadResult<ShippingConfigMetaForm> getCombinedShippingMetaList(ListLoadConfig config);
    public ShippingConfigMetaForm getShippingConfigMeta(String shippingId);
    public ShippingAreaMetaForm getShippingAreaMeta(String shippingAreaId, String shippingId);
    public ListLoadResult<BeanObject> getAreaRegionListWithName(String shippingAreaId);
    
    public Boolean installShipping(String shippingCode);
    public Boolean uninstallShipping(String shippingId);
    public Boolean saveShipping(ShippingConfigMetaForm props);
    
    public ListLoadResult<BeanObject> getShippingAreaWithRegionNames(String shippingId, ListLoadConfig pgc);
    public Boolean saveShippingArea(BeanObject shippingArea);
    
    public SortedMap<Integer, List<BeanObject>> getCombinedShopConfigMetaMap();
    public Boolean saveShopConfig(Map<String, BeanObject> formData);
    public Map<String,String> getAdminUserInfo();
    public Map<String, String> deserialize(String serializedConfig);
    public double calculate(String shippingCode, Double goodsWeight, Double goodsAmount, Map<String, String> configValues);
    public Map<String, Map<String, String>> getAttributeMap(String id);
    public Map<String, String> getShippingConfig(String shippingId);
//    public Map<String, String> getShippingFee(String userId, String shippingId, String orderId);
//    public Map<String, Object> getPayFee(String payId, String orderId, String userId);
    public Map<String, String> getOrderFee(String orderId, String shipping, String payId);
    public boolean deleteOrder(String orderId);
    public boolean mergeOrder(String fromId, String toId);
}
