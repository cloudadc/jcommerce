/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.service.Criteria;

public interface IShopServiceAsync {
    public void updateObject(Long id, BeanObject args, AsyncCallback<Boolean> callback);

    public void updateObject(Long id, BeanObject args, BeanObject args1, AsyncCallback<Boolean> callback);
    public void newObject(BeanObject args, AsyncCallback<String> callback);
    
    public void newObject(BeanObject args, BeanObject args1 ,AsyncCallback<String> callback);
    public void getList(String name, AsyncCallback<List<BeanObject>> callback);
    public void getIndexPagingList(String modelName, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getList(String name, Criteria criteria, AsyncCallback<List<BeanObject>> callback);
    
    public void getList(String name, Criteria criteria, List<String> wantedFields, AsyncCallback<List<BeanObject>> callback);
    
    public void getBean(String name, Long id, AsyncCallback<BeanObject> callback);

    public void getBeans(String name, Long[] ids, AsyncCallback<List<BeanObject>> callback);

    public void countBeans(String name, AsyncCallback<Integer> callback);
    public void countBeans(String name, Criteria criteria, AsyncCallback<Integer> callback);

    public void deleteObject(String name, Long id, AsyncCallback<Boolean> callback);
    public void deleteObjects(String name, List<Long> ids, AsyncCallback<Integer> callback);
    
    public void getPagingList(String modelName, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getPagingList(String modelName, Criteria criteria, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getPagingList(String modelName, Criteria criteria, List<String> wantedFields, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getUserBonusPagingList(Criteria criteria, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getGoodsTypeUnit(boolean needAttrNumber, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void getRegionChildren(String parent_id,AsyncCallback<List<BeanObject>> callback);
    
    public void getRegionAncestors(Long parent_id,AsyncCallback<List<BeanObject>> callback);
        
    public void getPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult<BeanObject>> callback);
  
    public void getMyPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult> callback);
    
    public void getPaymentConfigMeta(String paymentId, AsyncCallback<PaymentConfigMetaForm> callback);
    
    public void installPayment(String paymentCode, AsyncCallback<Boolean> callback);
    
    public void uninstallPayment(String paymentId, AsyncCallback<Boolean> callback);
   
    public void savePayment(Map<String, Object> props, AsyncCallback<Boolean> callback);

    public void getEmailServerSettings( AsyncCallback<HashMap<String, String>> callback );
    
    public void setEmailServerSettings( HashMap<String, String> settings, AsyncCallback<Boolean> callback );
    
    public void sendEmailAndGetState(Map<String, String> email, AsyncCallback<Boolean> callback);
    public void sendTestEmailAndGetState( AsyncCallback<Boolean> callback );
    
    public void receiveNewEmail( AsyncCallback<Boolean> callback );
    public void disposePhotos(BeanObject goods, boolean isGenerateDetails,boolean isGenerateThumbnails, boolean isErroSkip, AsyncCallback<String> callback);
    
    public void disposePictures(BeanObject goods,boolean isGenerateDetails,boolean isGenerateThumbnails, boolean isErroSkip, AsyncCallback<String> callback);
    
//    public void saveRegionArea(BeanObject bean , AsyncCallback<String> callback);

//    public void generateOrderCharts(String type , Date startDate , Date endDate , AsyncCallback<String> callback);
    
    public void generateReport(String name, Map<String, String> params, AsyncCallback<String> callback);
    
    public void getAllDiliveryMethods(PagingLoadConfig config,AsyncCallback<PagingLoadResult<BeanObject>> callback);
    public void getAllPaymentMethods(PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);

    public void Backup(String backUpFileName,AsyncCallback<String> callback);  
    public  void getAllFileInfo(AsyncCallback<List<List<String>>> callback);   
    public void deleteFile(String fileName,AsyncCallback<String> callback);   
    public void restoreFile(String fileName,AsyncCallback<String> callback);  
    public void initialize(AsyncCallback<String> callback);
    
    public void getTreePagingList(String modelName, Criteria criteria,BeanObject loadConfig,AsyncCallback<List<ModelData>> callback);

    public void getModulePagingList(String modelName, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    public void getModuleList(String model,AsyncCallback<List<BeanObject>> callback);

    public void getOrderTemplate(AsyncCallback<String> callback);

    public void purgeGoods(Long id,AsyncCallback<Boolean> callback);
    public void undoDeletedGoods(Long id,AsyncCallback<Boolean> callback);
    
    public void getSystemInfo(AsyncCallback<BeanObject> callback);
    
    public void getBeansFromFile(String modelName, String path, String category, String encoding,AsyncCallback<List<BeanObject>> callback);
}
