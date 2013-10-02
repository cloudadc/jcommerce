package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Constants;
import com.jcommerce.gwt.client.panels.article.ArticleCatListPanel;
import com.jcommerce.gwt.client.panels.article.ArticleCatPanel;
import com.jcommerce.gwt.client.panels.article.ArticleListPanel;
import com.jcommerce.gwt.client.panels.article.ArticlePanel;
import com.jcommerce.gwt.client.panels.data.ExportPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.panels.goods.AttributePanel;
import com.jcommerce.gwt.client.panels.goods.BrandInfo;
import com.jcommerce.gwt.client.panels.goods.BrandPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryListPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryPanel;
import com.jcommerce.gwt.client.panels.goods.CommentListPanel;
import com.jcommerce.gwt.client.panels.goods.CommentPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsList;
import com.jcommerce.gwt.client.panels.goods.GoodsTypeInfo;
import com.jcommerce.gwt.client.panels.goods.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.goods.NewGoods;
import com.jcommerce.gwt.client.panels.member.NewUsers;
import com.jcommerce.gwt.client.panels.member.ShippingAddressPanel;
import com.jcommerce.gwt.client.panels.member.UserListPanel;
import com.jcommerce.gwt.client.panels.order.ConsigneePanel;
import com.jcommerce.gwt.client.panels.order.MergeOrderPanel;
import com.jcommerce.gwt.client.panels.order.OrderDetailPanel;
import com.jcommerce.gwt.client.panels.order.OrderFeePanel;
import com.jcommerce.gwt.client.panels.order.OrderGoodsPanel;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;
import com.jcommerce.gwt.client.panels.order.OrderPayPanel;
import com.jcommerce.gwt.client.panels.order.OrderShippingPanel;
import com.jcommerce.gwt.client.panels.order.OrderUserPanel;
import com.jcommerce.gwt.client.panels.order.SearchOrderPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminList;
import com.jcommerce.gwt.client.panels.privilege.AdminUserPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel;
import com.jcommerce.gwt.client.panels.system.RegionListPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel;

/**
 * Constants used throughout the showcase.
 */

// public interface IShopConstants extends Constants, GoodsConstants,
// AttributeInfo.Constants, NewGoods.Constants, GoodsList.Constants,
// ShopConfigConstants,OrderConstants,RegionConstants,
// GoodsTypeListPanel.Constants, RegionListPanel.Constants,
// RegionPanel.Constants, ShippingAreaListPanel.Constants,
// ShippingMetaListPanel.Constants, PaymentMetaListPanel.Constants {
public interface IShopConstants extends Constants, GoodsConstants, ShopConfigConstants, OrderConstants,
        RegionConstants, AttributeListPanel.Constants, OrderGoodsPanel.Constants, NewGoods.Constants,
        PaymentMetaListPanel.Constants, GoodsTypeInfo.Constants, OrderUserPanel.Constants, GoodsList.Constants,
        RegionPanel.Constants, ShippingMetaListPanel.Constants, ShippingAreaListPanel.Constants,
        BrandPanel.Constants, GoodsTypePanel.Constants, BrandInfo.Constants, CategoryPanel.Constants,
        CategoryListPanel.Constants, AttributePanel.Constants, RegionListPanel.Constants, UserListPanel.Constants,
        NewUsers.Constants, AdminUserPanel.Constants, AdminList.Constants, ShippingAddressPanel.Constants,
        ArticleCatListPanel.Constants, ArticleListPanel.Constants, ArticlePanel.Constants, ImportPanel.Constants,
        ExportPanel.Constants, OrderListPanel.Constants, CommentListPanel.Constants, CommentPanel.Constants,
        SearchOrderPanel.Constants, ArticleCatPanel.Constants, MergeOrderPanel.Constants, OrderDetailPanel.Constants,
        ConsigneePanel.Constants, OrderShippingPanel.Constants, OrderPayPanel.Constants, OrderFeePanel.Constants {

    boolean useJDO = true;
    /**
     * The available style themes that the user can select.
     */
    String[] STYLE_THEMES = { "standard", "chrome", "dark" };

    String mainMenuTitle();
    
    String promotionGoods();

    /**
     * @return the title of the application
     */
    String ManagementCenter();

    String mainTitle();

    String categoryGoods();

    String categoryOrder();

    String categoryMember();

    String categorySystem();

    String categoryUser();

    String categoryPrivilege();

    String categoryStatistics();

    String categoryDatabase();

    String categoryData();

    /*
     * add
     */
    String categoryArticle();

    /*
     * add
     */
    String mainLinkHomepage();

    String mainLinkGuide();

    String mainLinkNotepad();

    String mainLinkRefresh();

    String mainLinkPersonalSetting();

    String mainLinkComment();

    String mainLinkView();

    String mainLinkCalc();

    String mainLinkAbout();

    String emailManager();

    String emailServerSetting();

    String mainCommandClearCache();

    String mainCommandExit();

    String navHome();

    String navSetting();

    String navGoodsList();

    String navOrderList();

    String navComment();

    String navMemberList();

    String edit();

    String delete();

    String deleteSelected();

    String save();

    String add();

    String reset();

    String action();

    String ok();

    String OperationFailure();

    String OperationSuccessful();

    String search();

    String EmailSendType_Subscribe();

    String deleteConfirmTitle();

    String deleteConfirmContent();
}
