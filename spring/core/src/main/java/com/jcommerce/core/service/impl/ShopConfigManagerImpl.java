/**
* @Author: KingZhao
*          Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.ShopConfigDAO;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.ShopConfigManager;
import com.jcommerce.core.util.ResourceUtil;
import com.jcommerce.core.wrapper.ShopConfigWrapper;

@Service("ShopConfigManager")
public class ShopConfigManagerImpl extends ManagerImpl implements ShopConfigManager {
    private static Log log = LogFactory.getLog(ShopConfigManagerImpl.class);
    
    @Autowired
    private ShopConfigDAO dao;

    public void setShopConfigDAO(ShopConfigDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public ShopConfig initialize(ShopConfig obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getShopConfig(obj.getId());
        }
        return obj;
    }

    public List<ShopConfig> getShopConfigList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<ShopConfig>)list;
    }

    public int getShopConfigCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<ShopConfig> getShopConfigList(Criteria criteria) {
        List list = getList(criteria);
        return (List<ShopConfig>)list;
    }

    public List<ShopConfig> getShopConfigList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<ShopConfig>)list;
    }

    public List<ShopConfig> getShopConfigList() {
        return dao.getShopConfigList();
    }

    public ShopConfig getShopConfig(Long id) {
        ShopConfig obj = dao.getShopConfig(id);
        return obj;
    }

    public void saveShopConfig(ShopConfig obj) {
        dao.saveShopConfig(obj);
    }

    public void removeShopConfig(Long id) {
        dao.removeShopConfig(id);
    }

    public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap(String locale) {
//      SortedMap<Integer, List<ShopConfigMeta>> res = new TreeMap<Integer, List<ShopConfigMeta>>();
        SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap = getDefaultShopConfigMap(ResourceUtil.parseLocale(locale));
        try {
            List<ShopConfig> scs = getShopConfigList(null);
            Map<String, ShopConfig> values = new HashMap<String, ShopConfig>();
            for(ShopConfig sc : scs) {
                String code = sc.getCode();
                values.put(code, sc);
            }
            
            for(Integer key : defaultShopConfigMap.keySet()) {
                List<ShopConfigMeta> defaultList = defaultShopConfigMap.get(key);
                for(ShopConfigMeta defaultt : defaultList) {
                    String code = defaultt.getCode();
                    ShopConfig sc = values.get(code);
                    if(sc!=null) {
                        defaultt.setPkId(sc.getId());
                        defaultt.setValue(sc.getValue());
                    }
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return defaultShopConfigMap;
    }
    
    public Boolean saveShopConfig(List<ShopConfig> tos) {
        try {
            for(ShopConfig to : tos) {
                Long pkId = to.getId();
                String code = to.getCode();
                String value = to.getValue();
                if(pkId == null) {
                	ShopConfig po = new ShopConfig();
                    po.setCode(code);
                    po.setValue(value);
                    saveShopConfig(po);
                } else {
                    ShopConfig po = getShopConfig(pkId);
                    po.setValue(value);
                    saveShopConfig(po);
                }
            }
            
            isCacheValid = false;
            
            return true;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        
    }
    
    public boolean isCacheValid=false;
    public ShopConfigWrapper cachedScw = new ShopConfigWrapper();
    public ShopConfigWrapper getCachedShopConfig(String locale) {
        try {
            
            if (!isCacheValid) {
                cachedScw.clear();
                // concurrent access entering this block will cause problem
                // however it's ok since ShopConfig won't be often changed.
                
                // another way is to store the cache in session so that concurrent users won't affect each other
                // however that would be more expensive and maynot 100% solve the problem, 
                // (confliction happens when concurrent read/write DS happens and we may not have transaction protect)
                SortedMap<Integer, List<ShopConfigMeta>> map = getCombinedShopConfigMetaMap(locale);
                for (List<ShopConfigMeta> list : map.values()) {
                    for (ShopConfigMeta scm : list) {
                        String code = scm.getCode();
                        String value = scm.getValue();
                        cachedScw.put(code, value);
                    }
                }
                isCacheValid = true;
            }
            return cachedScw;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    private static SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap;
    
//  private static final SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap_en;
//  private static final SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap_zh;
    
    static {
        // TODO support two languages at this time
//      defaultShopConfigMap_en = initDefaultShopConfigMap(Locale.ENGLISH);
//      defaultShopConfigMap_zh = initDefaultShopConfigMap(Locale.CHINESE);
    }
    
    public static SortedMap<Integer, List<ShopConfigMeta>> getDefaultShopConfigMap(Locale locale) {
//      if(locale.equals(Locale.ENGLISH)) {
//          return defaultShopConfigMap_en;
//      }
//      else {
//          return defaultShopConfigMap_zh;
//      }
        if(defaultShopConfigMap==null) {
            defaultShopConfigMap = initDefaultShopConfigMap(locale);
        }
        return defaultShopConfigMap;
        
    }
    
    public static SortedMap<Integer, List<ShopConfigMeta>> initDefaultShopConfigMap(Locale locale) {
        /*
         *  we define the data structure in order to keep the order it displayed on GUI. there are three concerns: 
         *   1. order of groups
         *   2. order of fields
         *   3. order of selection options | radio/checkbox buttons
         */
        
        SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap = new TreeMap<Integer, List<ShopConfigMeta>>();
        List<ShopConfigMeta> metaList = null;
        ResourceBundle bundle = ResourceUtil.getShopConfigResource(locale);
        
        // TODO use and i18n of tip string (last parameter of ShopConfigMeta)
        metaList = new ArrayList<ShopConfigMeta>();
//      metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, IShopConfigMeta.CFG_KEY_SHOP_NAME, "gCouldShop", IShopConfigMeta.CFG_TYPE_TEXT, "商店名称", null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_NAME, "gCouldShop", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_NAME"), null, null));
//      metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, IShopConfigMeta.CFG_KEY_SHOP_TITLE, "gCouldShop 演示站", IShopConfigMeta.CFG_TYPE_TEXT, "商店标题", null, "商店的标题将显示在浏览器的标题栏"));       
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_TITLE, "gCouldShop 演示站", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_TITLE"), null, bundle.getString("CFG_KEY_SHOP_TITLE_NOTICE")));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_DESC, "gCouldShop 演示站", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_DESC"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_KEYWORDS, "gCouldShop 演示站", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_KEYWORDS"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_COUNTRY, "1", IShopConfigMeta.CFG_TYPE_OPTIONS, bundle.getString("CFG_KEY_SHOP_COUNTRY"), 
                new String[][]{new String[]{"1",bundle.getString("CFG_KEY_SHOP_COUNTRY_CHINA")}, new String[]{"2",bundle.getString("CFG_KEY_SHOP_COUNTRY_AMERICA")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_ADDRESS, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_ADDRESS"), null , null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_POSTCODE, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_POSTCODE"), null , null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_QQ, "800120110,10001", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_QQ"), null , bundle.getString("CFG_KEY_SHOP_QQ_NOTICE")));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_WW, "911119991", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_WW"), null, bundle.getString("CFG_KEY_SHOP_WW_NOTICE")));  
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_SKYPE, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SKYPE"), null, bundle.getString("CFG_KEY_SHOP_SKYPE_NOTICE")));  
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_YM, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_YM"), null, bundle.getString("CFG_KEY_SHOP_YM_NOTICE")));   
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_MSN, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_MSN"), null, bundle.getString("CFG_KEY_SHOP_MSN_NOTICE")));    
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_SERVICE_EMAIL, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SERVICE_EMAIL"), null, null));   
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_SERVICE_PHONE, "", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SERVICE_PHONE"), null, null));   
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_USER_NOTICE, 
                "用户中心公告！"   , IShopConfigMeta.CFG_TYPE_TEXTAREA, bundle.getString("CFG_KEY_USER_NOTICE"), null, bundle.getString("CFG_KEY_USER_NOTICE_NOTICE")));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_NOTICE, 
                "欢迎光临手机网,我们的宗旨：诚信经营、服务客户！<MARQUEE onmouseover=this.stop() onmouseout=this.start() scrollAmount=3><U><FONT color=red><P>咨询电话010-10124444  010-21252454 8465544</P></FONT></U></MARQUEE>"
                , IShopConfigMeta.CFG_TYPE_TEXTAREA, bundle.getString("CFG_KEY_SHOP_NOTICE"), null, bundle.getString("CFG_KEY_SHOP_NOTICE_NOTICE")));
//      metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, IShopConfigMeta.CFG_KEY_SHOP_COUNTRY, "", IShopConfigMeta.CFG_TYPE_OPTIONS, "所在国家", null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_COPYRIGHT, "Copyright © 2008-2009 GCSHOP 版权所有，并保留所有权利。", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_COPYRIGHT"), null, null)); 
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_REG_CLOSED, "0", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOP_REG_CLOSED"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
        
        defaultShopConfigMap.put(1, metaList);

        
        metaList = new ArrayList<ShopConfigMeta>();
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_BASIC"), IShopConfigMeta.CFG_KEY_COMMENT_CHECK, "0", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_COMMENT_CHECK"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_BASIC"), IShopConfigMeta.CFG_KEY_INTEGRAL_NAME, "积分", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_INTEGRAL_NAME"), null,  bundle.getString("CFG_KEY_INTEGRAL_NAME_NOTICE")));
        defaultShopConfigMap.put(2, metaList);
        
        metaList = new ArrayList<ShopConfigMeta>();
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_TIME_FORMAT"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_COMMENTS_NUMBER, "5", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_COMMENTS_NUMBER"), null, bundle.getString("CFG_KEY_COMMENTS_NUMBER_NOTICE")));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_HISTORY_NUMBER, "5", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_HISTORY_NUMBER"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_COLLECTION_NUMBER, "5", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_COLLECTION_NUMBER"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_ARTICLE_NUMBER, "8", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_ARTICLE_NUMBER"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_PAGE_SIZE, "10", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_PAGE_SIZE"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE, "shop_price", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
                new String[][]{new String[]{"goods_id",bundle.getString("CFG_SORT_GOODS_ID")}, new String[]{"shop_price",bundle.getString("CFG_SORT_SHOP_PRICE")}, new String[]{"last_update",bundle.getString("CFG_SORT_LAST_UPDATE")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD, "ASC", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
                new String[][]{new String[]{"DESC",bundle.getString("CFG_SORT_DESC")}, new String[]{"ASC",bundle.getString("CFG_SORT_ASC")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE, "grid", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
                new String[][]{new String[]{"list",bundle.getString("CFG_SHOW_LIST")}, new String[]{"grid",bundle.getString("CFG_SHOW_GRID")}, new String[]{"text",bundle.getString("CFG_SHOW_TEXT")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_1, "国家", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_1"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_2, "省", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_2"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_3, "市", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_3"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_4, "区", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_4"), null, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_PAGE_STYLE, "0", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_PAGE_STYLE"), 
                new String[][]{new String[]{"0",bundle.getString("CFG_PAGE_STYLE_1")}, new String[]{"1",bundle.getString("CFG_PAGE_STYLE_2")}}, null));
        defaultShopConfigMap.put(3, metaList);
        
        metaList = new ArrayList<ShopConfigMeta>();
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_CART_CONFIRM, "3", IShopConfigMeta.CFG_TYPE_OPTIONS, bundle.getString("CFG_KEY_CART_CONFIRM"), 
                new String[][]{new String[]{"1",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_1")}, new String[]{"2",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_2")},
                new String[]{"3",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_3")}, new String[]{"4",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_4")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_ONE_STEP_BUY, "1", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_ONE_STEP_BUY"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_ANONYMOUS_BUY, "1", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_ANONYMOUS_BUY"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_SHOW_GOODS_IN_CART, "3", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODS_IN_CART"), new String[][]{new String[]{"1",bundle.getString("CFG_SHOW_GOODS_IN_CART_TEXT")}, new String[]{"2",bundle.getString("CFG_SHOW_GOODS_IN_CART_IMG")}, new String[]{"3",bundle.getString("CFG_SHOW_GOODS_IN_CART_BOTH")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_SHOW_GOODS_ATTRIBUTE, "0", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODS_ATTRIBUTE"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
        defaultShopConfigMap.put(4, metaList);
        
        metaList = new ArrayList<ShopConfigMeta>();
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOODS"), IShopConfigMeta.CFG_KEY_SHOW_GOODSWEIGHT, "1", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODSWEIGHT"), 
                new String[][]{new String[]{"1",bundle.getString("CFG_DISPLAY")}, new String[]{"0",bundle.getString("CFG_CONCEAL")}}, null));
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOODS"), IShopConfigMeta.CFG_KEY_SHOW_MARKETPRICE, "1", IShopConfigMeta.CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_MARKETPRICE"), 
                new String[][]{new String[]{"1",bundle.getString("CFG_DISPLAY")}, new String[]{"0",bundle.getString("CFG_CONCEAL")}}, null));   
        defaultShopConfigMap.put(5, metaList);
        
        metaList = new ArrayList<ShopConfigMeta>();
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOOGLE_PRODUCT_SEARCH"), IShopConfigMeta.CFG_KEY_GOOGLE_ACCOUNT, "jcommerce.test@gmail.com", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_GOOGLE_ACCOUNT"), null, bundle.getString("CFG_KEY_GOOGLE_ACCOUNT_NOTICE")));  
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOOGLE_PRODUCT_SEARCH"), IShopConfigMeta.CFG_KEY_GOOGLE_PASSWORD, "jcommercetest", IShopConfigMeta.CFG_TYPE_PASSWORD, bundle.getString("CFG_KEY_GOOGLE_PASSWORD"), null, null));    
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOOGLE_PRODUCT_SEARCH"), IShopConfigMeta.CFG_KEY_GOOGLE_DEVELOPER_KEY, "ABQIAAAAdum_4yYDkId337SD8heFChQeIQHYpA6H9kmimIu2ECi1AyhB0xQelDpqayXYHPdu9okteNGwcvBoZQ", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_GOOGLE_DEVELOPER_KEY"), null, null)); 
        metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOOGLE_PRODUCT_SEARCH"), IShopConfigMeta.CFG_KEY_GOOGLE_WEBSITEPATH, "http://gc-shop.appspot.com", IShopConfigMeta.CFG_TYPE_TEXT, bundle.getString("CFG_KEY_GOOGLE_WEBSITEPATH"), null, null)); 
        defaultShopConfigMap.put(6, metaList);
        return defaultShopConfigMap;
    }

    interface IShopConfigMeta {
        
        public static final String CODE = "code";
        
        public static final String STORE_RANGE = "storeRange";
        public static final String TYPE = "type";
        public static final String LABEL = "label";
        public static final String GROUP = "group";
        public static final String TIP = "tip";
        
        public static final String PK_ID = "pkId";
        public static final String VALUE = "value";
        
        // TODO externalize the text string for g18n
    //  public static final String CFG_GROUP_SHOP_INFO = "网店信息";
    //  public static final String CFG_GROUP_BASIC = "基本设置";
    //  public static final String CFG_GROUP_DISPLAY = "显示设置";
    //  public static final String CFG_GROUP_SHOPPING_FLOW = "购物流程";
    //  public static final String CFG_GROUP_GOODS = "商品显示";
        
        // NOTE:  these keys are used as both roles: 
        // 1. retrieve the value stored
        // 2. to fetch the i11n lable string
        
        // shop_info
        public static final String CFG_KEY_SHOP_NAME = "shop_name";
        public static final String CFG_KEY_SHOP_TITLE = "shop_title";
        public static final String CFG_KEY_SHOP_DESC = "shop_desc";
        public static final String CFG_KEY_SHOP_KEYWORDS = "shop_keywords";
        public static final String CFG_KEY_SHOP_NOTICE = "shop_notice";
        public static final String CFG_KEY_SHOP_COUNTRY = "shop_country";
        public static final String CFG_KEY_SHOP_QQ = "qq";
        public static final String CFG_KEY_SHOP_WW = "ww";
        public static final String CFG_KEY_SHOP_YM = "ym";
        public static final String CFG_KEY_SHOP_MSN = "msn";
        public static final String CFG_KEY_SHOP_SKYPE = "skype";
        public static final String CFG_KEY_SHOP_COPYRIGHT = "copyright";
        public static final String CFG_KEY_SHOP_ADDRESS = "shop_address";
        public static final String CFG_KEY_SHOP_POSTCODE = "shop_postcode";
        public static final String CFG_KEY_SHOP_SERVICE_PHONE = "service_phone";
        public static final String CFG_KEY_SHOP_SERVICE_EMAIL = "service_email";
        public static final String CFG_KEY_USER_NOTICE = "user_notice";
        public static final String CFG_KEY_SHOP_REG_CLOSED = "shop_reg_closed";
        
        //basic
        public static final String CFG_KEY_COMMENT_CHECK = "comment_check";
        public static final String CFG_KEY_INTEGRAL_NAME = "integral_name";
        
        // goods
        public static final String CFG_KEY_SHOW_MARKETPRICE = "showMarketprice";
        public static final String CFG_KEY_SHOW_GOODSWEIGHT = "showGoodsweight";
        
        // shopping_flow
        public static final String CFG_KEY_CART_CONFIRM = "cart_confirm";
        public static final String CFG_KEY_ONE_STEP_BUY = "one_step_buy";
        public static final String CFG_KEY_ANONYMOUS_BUY = "anonymous_buy";
        public static final String CFG_KEY_SHOW_GOODS_IN_CART = "show_goods_in_cart";
        public static final String CFG_KEY_SHOW_GOODS_ATTRIBUTE = "show_goods_attribute";
        
        
        // display
        public static final String CFG_KEY_TIME_FORMAT = "time_format";
        public static final String CFG_KEY_COMMENTS_NUMBER = "comments_number";
        public static final String CFG_KEY_HISTORY_NUMBER = "history_number";
        public static final String CFG_KEY_ARTICLE_NUMBER = "article_number";
        public static final String CFG_KEY_PAGE_SIZE = "page_size";
        public static final String CFG_KEY_SORT_ORDER_TYPE = "sort_order_type";
        public static final String CFG_KEY_SORT_ORDER_METHOD = "sort_order_method";
        public static final String CFG_KEY_SHOW_ORDER_TYPE = "show_order_type";
        public static final String CFG_KEY_NAME_OF_REGION_1 = "name_of_region_1";
        public static final String CFG_KEY_NAME_OF_REGION_2 = "name_of_region_2";
        public static final String CFG_KEY_NAME_OF_REGION_3 = "name_of_region_3";
        public static final String CFG_KEY_NAME_OF_REGION_4 = "name_of_region_4";
        public static final String CFG_KEY_COLLECTION_NUMBER = "collection_number";
        public static final String CFG_KEY_PAGE_STYLE = "page_style";
        
        //google product search
        public static final String CFG_KEY_GOOGLE_ACCOUNT = "google_account";
        public static final String CFG_KEY_GOOGLE_PASSWORD = "google_password";
        public static final String CFG_KEY_GOOGLE_DEVELOPER_KEY = "developer_key";
        public static final String CFG_KEY_GOOGLE_WEBSITEPATH = "web_site_path";
        
        
        public static final String CFG_TYPE_SELECT = "select";
        public static final String CFG_TYPE_TEXT = "text";
        public static final String CFG_TYPE_TEXTAREA = "textArea";
        public static final String CFG_TYPE_OPTIONS = "options";
        public static final String CFG_TYPE_PASSWORD = "password";
    }
}
