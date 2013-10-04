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
import com.google.gwt.user.client.rpc.RemoteService;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.service.Criteria;

public interface IShopService extends RemoteService {
    public BeanObject getBean(String bean, Long id);
    
    public List<BeanObject> getBeans(String bean, Long[] ids);
    public int countBeans(String name, Criteria criteria);
    public int countBeans(String name);
    
    public List<BeanObject> getList(String bean);
    
    public PagingLoadResult<BeanObject> getIndexPagingList(String modelName, PagingLoadConfig config);
    public List<BeanObject> getList(String bean, Criteria criteria);
    
    public List<BeanObject> getList(String bean, Criteria criteria, List<String> wantedFields);
    
    public String newObject(BeanObject obj);
    
    public String newObject(BeanObject obj,BeanObject obj1);
    public boolean updateObject(Long id, BeanObject obj);
    public boolean updateObject(Long id, BeanObject obj, BeanObject obj1);
    
    public boolean deleteObject(String bean, Long id);
    public int deleteObjects(String bean, List<Long> ids);

    public PagingLoadResult<BeanObject> getPagingList(String modelName, PagingLoadConfig config);
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, PagingLoadConfig config);

    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, List<String> wantedFields, PagingLoadConfig config);
    public PagingLoadResult<BeanObject> getUserBonusPagingList(Criteria criteria, PagingLoadConfig config);
    
    public PagingLoadResult<BeanObject> getGoodsTypeUnit(boolean needAttrNumber, PagingLoadConfig config);
    
    public List<BeanObject> getRegionChildren(String id);
    
    public List<BeanObject> getRegionAncestors(Long id);
    
    public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config);
    public ListLoadResult<?> getMyPaymentMetaList(ListLoadConfig config);
    
    public PaymentConfigMetaForm getPaymentConfigMeta(String paymentId);
    
    public boolean installPayment(String paymentCode);
    public boolean uninstallPayment(String paymentId);
    public boolean savePayment(Map<String, Object> props);
    
    //added to do email server setting
    public HashMap<String, String> getEmailServerSettings();
    
    public boolean setEmailServerSettings(HashMap<String, String> settings);
    
    public boolean sendTestEmailAndGetState();
    
    public boolean sendEmailAndGetState(Map<String, String> email);
    
    public boolean receiveNewEmail();
    
    public String disposePhotos(BeanObject goods, boolean isGenerateDetails,boolean isGenerateThumbnails, boolean isErroSkip);
    public String disposePictures(BeanObject goods, boolean isGenerateDetails,boolean isGenerateThumbnails, boolean isErroSkip);

    public List<ModelData> getTreePagingList(String modelName, Criteria criteria,BeanObject loadConfig);
    public PagingLoadResult<BeanObject> getAllDiliveryMethods(PagingLoadConfig config);
    public PagingLoadResult<BeanObject> getAllPaymentMethods(PagingLoadConfig config);
    public PagingLoadResult<BeanObject> getModulePagingList(String modelName);
    public List<BeanObject> getModuleList(String model);

    //bakup the data of database
    public String Backup(String backUpFileName);
    public List<List<String>> getAllFileInfo();
    public String deleteFile(String fileName);
    //restore the database based on a data file
    public String restoreFile(String fileName);
    //initialize the database
    public String initialize();
    
    public String generateReport(String name, Map<String, String> params);
    
    public String getOrderTemplate();   

    public boolean purgeGoods(Long id);
    public boolean undoDeletedGoods(Long id);
    
    public BeanObject getSystemInfo();
    public List<BeanObject> getBeansFromFile(String modelName, String path, String category, String encoding);
}
