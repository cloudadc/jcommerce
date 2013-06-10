package com.jcommerce.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jcommerce.core.service.ArticleCategoryManager;
import com.jcommerce.core.service.ArticleManager;
import com.jcommerce.core.service.AttributeManager;
import com.jcommerce.core.service.BrandManager;
import com.jcommerce.core.service.CartManager;
import com.jcommerce.core.service.CategoryManager;
import com.jcommerce.core.service.CollectGoodsManager;
import com.jcommerce.core.service.CommentManager;
import com.jcommerce.core.service.GalleryManager;
import com.jcommerce.core.service.GoodsAttributeManager;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.service.GoodsTypeManager;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.core.service.OrderActionManager;
import com.jcommerce.core.service.OrderGoodsManager;
import com.jcommerce.core.service.OrderManager;
import com.jcommerce.core.service.PaymentManager;
import com.jcommerce.core.service.RegionManager;
import com.jcommerce.core.service.SessionManager;
import com.jcommerce.core.service.ShippingAreaManager;
import com.jcommerce.core.service.ShippingManager;
import com.jcommerce.core.service.ShopConfigManager;
import com.jcommerce.core.service.UserAddressManager;
import com.jcommerce.core.service.UserBonusManager;
import com.jcommerce.core.service.UserManager;
import com.jcommerce.core.service.UserRankManager;
import com.jcommerce.core.service.shipping.IShippingMetaManager;

public class SpringUtil implements ApplicationContextAware {
	
    private static ApplicationContext applicationContext;
    
    public void setApplicationContext(ApplicationContext arg0)
        throws BeansException {
        SpringUtil.applicationContext = arg0;
    }
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
    
    public static ShopConfigManager getShopConfigManager() {
		return (ShopConfigManager)getBean("ShopConfigManager");
	}
    
    public static IShippingMetaManager getShippingMetaManager(){
    	return (IShippingMetaManager)getBean("ShippingMetaManager");
    }

    public static IWebManager getWebManager() {
    	return (IWebManager)getBean("WebManager");
    }

//    public static AreaRegionManager getAreaRegionManager(){
//        return (AreaRegionManager)getBean("AreaRegionManager");
//    }

    public static ShippingManager getShippingManager(){
        return (ShippingManager)getBean("ShippingManager");
    }

    public static ShippingAreaManager getShippingAreaManager(){
        return (ShippingAreaManager)getBean("ShippingAreaManager");
    }
    
    public static PaymentManager getPaymentManager() {
        return (PaymentManager)getBean("PaymentManager");
    }

    public static CartManager getCartManager() {
        return (CartManager)getBean("CartManager");
    }

    public static SessionManager getSessionManager() {
        if (applicationContext == null) {
            return null;
        }
        
        return (SessionManager)getBean("SessionManager");
    }

    public static RegionManager getRegionManager() {
        return (RegionManager)getBean("RegionManager");
    }

    public static UserManager getUserManager() {
        return (UserManager)getBean("UserManager");
    }

    public static UserRankManager getUserRankManager() {
        return (UserRankManager)getBean("UserRankManager");
    }

    public static UserAddressManager getUserAddressManager() {
        return (UserAddressManager)getBean("UserAddressManager");
    }

    public static UserBonusManager getUserBonusManager() {
        return (UserBonusManager)getBean("UserBonusManager");
    }

    public static OrderManager getOrderManager() {
        return (OrderManager)getBean("OrderManager");
    }

    public static OrderActionManager getOrderActionManager() {
        return (OrderActionManager)getBean("OrderActionManager");
    }

    public static OrderGoodsManager getOrderGoodsManager() {
        return (OrderGoodsManager)getBean("OrderGoodsManager");
    }

    public static BrandManager getBrandManager() {
        return (BrandManager)getBean("BrandManager");
    }

    public static AttributeManager getAttributeManager() {
        return (AttributeManager)getBean("AttributeManager");
    }

    public static GoodsManager getGoodsManager() {
        return (GoodsManager)getBean("GoodsManager");
    }

    public static GalleryManager getGalleryManager() {
        return (GalleryManager)getBean("GalleryManager");
    }

    public static GoodsAttributeManager getGoodsAttributeManager() {
        return (GoodsAttributeManager)getBean("GoodsAttributeManager");
    }

    public static GoodsTypeManager getGoodsTypeManager() {
        return (GoodsTypeManager)getBean("GoodsTypeManager");
    }

    public static CommentManager getCommentManager() {
        return (CommentManager)getBean("CommentManager");
    }

    public static ArticleManager getArticleManager() {
        return (ArticleManager)getBean("ArticleManager");
    }

    public static ArticleCategoryManager getArticleCategoryManager() {
        return (ArticleCategoryManager)getBean("ArticleCategoryManager");
    }

    public static CollectGoodsManager getCollectGoodsManager() {
        return (CollectGoodsManager)getBean("CollectGoodsManager");
    }

    public static CategoryManager getCategoryManager() {
        return (CategoryManager)getBean("CategoryManager");
    }

}
