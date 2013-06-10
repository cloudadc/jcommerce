/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.Application.ApplicationListener;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.panels.article.ArticleCatListPanel;
import com.jcommerce.gwt.client.panels.article.ArticleCatPanel;
import com.jcommerce.gwt.client.panels.article.ArticleCatogory;
import com.jcommerce.gwt.client.panels.article.ArticleList;
import com.jcommerce.gwt.client.panels.article.ArticleListPanel;
import com.jcommerce.gwt.client.panels.article.ArticlePanel;
import com.jcommerce.gwt.client.panels.article.CommentInfo;
import com.jcommerce.gwt.client.panels.article.NewArticle;
import com.jcommerce.gwt.client.panels.article.NewArticleCat;
import com.jcommerce.gwt.client.panels.article.NewTag;
import com.jcommerce.gwt.client.panels.article.TagManager;
import com.jcommerce.gwt.client.panels.article.UserComments;
import com.jcommerce.gwt.client.panels.data.DatabaseBackUp;
import com.jcommerce.gwt.client.panels.data.DatabaseInitialize;
import com.jcommerce.gwt.client.panels.data.DatabaseRestore;
import com.jcommerce.gwt.client.panels.data.ExportPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel;
import com.jcommerce.gwt.client.panels.email.EmailQueueManager;
import com.jcommerce.gwt.client.panels.email.EmailReceiver;
import com.jcommerce.gwt.client.panels.email.EmailServerSetting;
import com.jcommerce.gwt.client.panels.email.EmailSubscribeManager;
import com.jcommerce.gwt.client.panels.email.MagazineManager;
import com.jcommerce.gwt.client.panels.email.NewEmail;
import com.jcommerce.gwt.client.panels.email.NewMagazine;
import com.jcommerce.gwt.client.panels.goods.AttributeInfo;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.panels.goods.AttributePanel;
import com.jcommerce.gwt.client.panels.goods.BrandInfo;
import com.jcommerce.gwt.client.panels.goods.BrandPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryInfo;
import com.jcommerce.gwt.client.panels.goods.CategoryListPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryPanel;
import com.jcommerce.gwt.client.panels.goods.CommentListPanel;
import com.jcommerce.gwt.client.panels.goods.CommentPanel;
import com.jcommerce.gwt.client.panels.goods.DeletedGoodsList;
import com.jcommerce.gwt.client.panels.goods.GenerateGoodsCode;
import com.jcommerce.gwt.client.panels.goods.GoodsBatchUpload;
import com.jcommerce.gwt.client.panels.goods.GoodsList;
import com.jcommerce.gwt.client.panels.goods.GoodsTypeInfo;
import com.jcommerce.gwt.client.panels.goods.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.goods.NewAttribute;
import com.jcommerce.gwt.client.panels.goods.NewBrand;
import com.jcommerce.gwt.client.panels.goods.NewCategory;
import com.jcommerce.gwt.client.panels.goods.NewGoods;
import com.jcommerce.gwt.client.panels.goods.NewGoodsType;
import com.jcommerce.gwt.client.panels.goods.NewVirtualCard;
import com.jcommerce.gwt.client.panels.goods.Picturesdisposer;
import com.jcommerce.gwt.client.panels.goods.UpdateGoodsOnSellAuto;
import com.jcommerce.gwt.client.panels.goods.VirtualCardList;
import com.jcommerce.gwt.client.panels.member.AddApply;
import com.jcommerce.gwt.client.panels.member.AssignRole;
import com.jcommerce.gwt.client.panels.member.CheckBounds;
import com.jcommerce.gwt.client.panels.member.EditMemberInfo;
import com.jcommerce.gwt.client.panels.member.MemberAddresses;
import com.jcommerce.gwt.client.panels.member.MemberApplication;
import com.jcommerce.gwt.client.panels.member.MemberLevelList;
import com.jcommerce.gwt.client.panels.member.MemberList;
import com.jcommerce.gwt.client.panels.member.MemberMessage;
import com.jcommerce.gwt.client.panels.member.MemberMessageReply;
import com.jcommerce.gwt.client.panels.member.NewMemberLevel;
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
import com.jcommerce.gwt.client.panels.order.OrderStatistics;
import com.jcommerce.gwt.client.panels.order.OrderUserPanel;
import com.jcommerce.gwt.client.panels.order.PrintTemplatePanel;
import com.jcommerce.gwt.client.panels.order.SearchOrderPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminList;
import com.jcommerce.gwt.client.panels.privilege.AdminLog;
import com.jcommerce.gwt.client.panels.privilege.AdminUserPanel;
import com.jcommerce.gwt.client.panels.privilege.NewAdminUser;
import com.jcommerce.gwt.client.panels.promote.AuctionListPanel;
import com.jcommerce.gwt.client.panels.promote.BonusListPanel;
import com.jcommerce.gwt.client.panels.promote.CardListPanel;
import com.jcommerce.gwt.client.panels.promote.FavourableActivityListPanel;
import com.jcommerce.gwt.client.panels.promote.GroupBuyListPanel;
import com.jcommerce.gwt.client.panels.promote.ModifyAuctionPanel;
import com.jcommerce.gwt.client.panels.promote.ModifyFavourableActivityListPanel;
import com.jcommerce.gwt.client.panels.promote.ModifySnatchPanel;
import com.jcommerce.gwt.client.panels.promote.ModifyWholesalePanel;
import com.jcommerce.gwt.client.panels.promote.NewAuctionPanel;
import com.jcommerce.gwt.client.panels.promote.NewBonusPanel;
import com.jcommerce.gwt.client.panels.promote.NewCardPanel;
import com.jcommerce.gwt.client.panels.promote.NewFavourableActivityPanel;
import com.jcommerce.gwt.client.panels.promote.NewGroupBuyPanel;
import com.jcommerce.gwt.client.panels.promote.NewPackPanel;
import com.jcommerce.gwt.client.panels.promote.NewSnatchPanel;
import com.jcommerce.gwt.client.panels.promote.NewSpecialActivityPanel;
import com.jcommerce.gwt.client.panels.promote.NewWholesalePanel;
import com.jcommerce.gwt.client.panels.promote.PackListPanel;
import com.jcommerce.gwt.client.panels.promote.SearchAuctionPanel;
import com.jcommerce.gwt.client.panels.promote.SearchSnatchPanel;
import com.jcommerce.gwt.client.panels.promote.SnatchListPanel;
import com.jcommerce.gwt.client.panels.promote.SpecialActivityListPanel;
import com.jcommerce.gwt.client.panels.promote.WholesaleListPanel;
import com.jcommerce.gwt.client.panels.statistics.GuestStatisticsReport;
import com.jcommerce.gwt.client.panels.statistics.SaleRankReport;
import com.jcommerce.gwt.client.panels.statistics.SaleStatisticsReport;
import com.jcommerce.gwt.client.panels.statistics.SalesReport;
import com.jcommerce.gwt.client.panels.statistics.UserRankReport;
import com.jcommerce.gwt.client.panels.statistics.VisitSoldReport;
import com.jcommerce.gwt.client.panels.system.AgencyList;
import com.jcommerce.gwt.client.panels.system.DistrictList;
import com.jcommerce.gwt.client.panels.system.NewAgency;
import com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaPanel;
import com.jcommerce.gwt.client.panels.system.RegionListPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaPanel;
import com.jcommerce.gwt.client.panels.system.ShippingInstaller;
import com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingTemplatePanel;
import com.jcommerce.gwt.client.panels.system.ShopSetup;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class iShop implements EntryPoint, GWT.UncaughtExceptionHandler, ApplicationListener{
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
    private Application app = new Application();
    private static iShop me;
    
    /**
     * A mapping of history tokens to their associated menu items.
     */
    private Map<String, TreeItem> pageClassItems = new HashMap<String, TreeItem>();
    /**
     * A mapping of menu items to the widget display when the item is selected.
     */
   
    private Map<TreeItem, PageState> itemStates = new HashMap<TreeItem, PageState>();
  
    public Map<String, ContentWidget> pageRegistry = new HashMap<String, ContentWidget>();
    
    public static iShop getInstance() {
    	return me;
    }
    
	public void onHistoryChanged(String fullHistoryToken) {
		try {
			System.out.println("--- hyperLinkHandler");
			String[] parsed = PageState
					.parseFullHistoryToken(fullHistoryToken);
			String pageClassName = parsed[0];
			String stateStr = parsed[1];
			System.out.println("pageClassStr: " + pageClassName
					+ ", stateStr: " + stateStr);

			TreeItem item = pageClassItems.get(pageClassName);
			if (item != null) {
				app.getMainMenu().setSelectedItem(item, false);
				app.getMainMenu().ensureSelectedItemVisible();
			}

			ContentWidget page = getPage(pageClassName);

			if (page != null) {
				PageState state = PageState.curState;
				if (state.getPageInstance() != null) {
				    page = state.getPageInstance();
				}
//				if (state != null) {
					state.fromHistoryToken(stateStr);
//				}
			    page.setCurState(state);
				displayContentWidget(page);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void onUncaughtException(Throwable e) {
	    e.printStackTrace();
		Window.alert("error: " + e.getMessage());
	}
	
	public void onMenuItemSelected(TreeItem item) {
		PageState state = itemStates.get(item);
		// && !content.equals(app.getContent())
		if (state != null) {
			state.execute();
		}
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		me = this;
		GWT.setUncaughtExceptionHandler(this);
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String fullHistoryToken = event.getValue(); 
				onHistoryChanged(fullHistoryToken);
			}
		});

		RemoteService.init();

		// Generate the source code and css for the examples
		// GWT.create(GeneratorInfo.class);

		// Swap out the style sheets for the RTL versions if needed.
		// updateStyleSheets();

		// Create the application
		setupTitlePanel();
		setupMainLinks();
		setupOptionsPanel();
		setupMainMenu();
		setupNavigationPanel();
		app.setListener(this);
		// need add app first, to ensure the other widgets to be rendered when
		// added to app
		RootPanel.getBodyElement().setAttribute("style", "Padding:0;margin:0;");
		RootPanel.get().add(app);

		String initToken = History.getToken();
		if (initToken.length() > 0) {
			// historyListener.onHistoryChanged(initToken);
			History.newItem(initToken);
		} else {
			// Use the first token available
			TreeItem firstItem = app.getMainMenu().getItem(0).getChild(0);
			PageState state = itemStates.get(firstItem);
			if (state != null)
				state.execute();
		}
		
	}

    // ensure all instances be created and put in pageRegistry when first-time
    // accessed
    // and all following access will retrieve the cached instance in
    // pageRegistry
    public ContentWidget getPage(String pageClassName) {
        ContentWidget page = null;
//        page = pageRegistry.get(pageClassName);
        if (page == null) {
            
            // TODO use GWT.create to allow plugin of different implementation?
            // what's the condition?
            // TODO have a generator to generate these codes based on all page
            // classes we have in project
//            if (pageClassName.equals(GoodsPanel.class.getName())) {
//                // page = (ContentWidget)GWT.create(GoodsTypeList.class);
//                page = GoodsPanel.getInstance();
            if (pageClassName.equals(GoodsList.class.getName())) {
                page = new GoodsList();
            } else if (pageClassName.equals(NewGoodsType.class.getName())) {
                page = new NewGoodsType();
            } else if (pageClassName.equals(AttributePanel.class.getName())) {
                page = AttributePanel.getInstance();
            } else if (pageClassName.equals(AttributeListPanel.class.getName())) {
                page = AttributeListPanel.getInstance();
            } else if (pageClassName.equals(GoodsTypePanel.class.getName())) {
                page = new GoodsTypePanel();                
            } else if (pageClassName.equals(PaymentMetaListPanel.class.getName())) {
                page = PaymentMetaListPanel.getInstance();
            } else if (pageClassName.equals(PaymentMetaPanel.class.getName())) {
                page = PaymentMetaPanel.getInstance();
			} else if (pageClassName.equals(AttributeInfo.class.getName())) {
				page = new AttributeInfo();
			} else if (pageClassName.equals(BrandInfo.class.getName())) {
				page = new BrandInfo();
			} else if (pageClassName.equals(GenerateGoodsCode.class.getName())) {
                page = GenerateGoodsCode.getInstance();
            } else if (pageClassName.equals(Picturesdisposer.class.getName())) {
                page = Picturesdisposer.getInstance();
            } else if (pageClassName.equals(CategoryInfo.class.getName())) {
				page = new CategoryInfo();
			} else if (pageClassName.equals(CommentInfo.class.getName())) {
				page = new CommentInfo();
            } else if (pageClassName.equals(RegionListPanel.class.getName())) {
                page = RegionListPanel.getInstance(); 
			} else if (pageClassName.equals(GoodsTypeInfo.class.getName())) {
				page = new GoodsTypeInfo();
			} else if (pageClassName.equals(NewAttribute.class.getName())) {
				page = new NewAttribute();
            } else if (pageClassName.equals(NewBrand.class.getName())) {
                page = NewBrand.getInstance();
            } else if (pageClassName.equals(NewCategory.class.getName())) {
                page = NewCategory.getInstance();
            } else if (pageClassName.equals(CategoryListPanel.class.getName())) {
                page = CategoryListPanel.getInstance();
            } else if (pageClassName.equals(CategoryPanel.class.getName())) {
                    page = CategoryPanel.getInstance();
            } else if (pageClassName.equals(ImportPanel.class.getName())) {
                page = ImportPanel.getInstance();   
            } else if (pageClassName.equals(ExportPanel.class.getName())) {
                page = ExportPanel.getInstance();               
			} else if (pageClassName.equals(NewUsers.class.getName())) {
				page = new NewUsers();
            } else if (pageClassName.equals(MemberList.class.getName())) {
                page = new MemberList();
            } else if (pageClassName.equals(MemberMessage.class.getName())) {
                page = new MemberMessage();
            } else if (pageClassName.equals(MemberApplication.class.getName())) {
                page = new MemberApplication();
            } else if (pageClassName.equals(AddApply.class.getName())) {
                page = new AddApply();
            } else if (pageClassName.equals(AdminList.class.getName())) {
                page = new AdminList();
            } else if (pageClassName.equals(AdminLog.class.getName())) {
                page = new AdminLog();
            } else if (pageClassName.equals(AgencyList.class.getName())) {
                page = new AgencyList();
            } else if (pageClassName.equals(NewMemberLevel.class.getName())) {
                page = NewMemberLevel.getInstance();
            } else if (pageClassName.equals(NewMemberLevel.class.getName())) {
                page = NewMemberLevel.getInstance();
            } else if (pageClassName.equals(MemberLevelList.class.getName())) {
                page = new MemberLevelList();
            } else if (pageClassName.equals(EditMemberInfo.class.getName())) {
                page = new EditMemberInfo();
            } else if (pageClassName.equals(MemberAddresses.class.getName())) {
                page = new MemberAddresses();
            } else if (pageClassName.equals(CommentListPanel.class.getName())) {
                page = CommentListPanel.getInstance();
            } else if (pageClassName.equals(CommentPanel.class.getName())) {
                page = CommentPanel.getInstance();
			} else if (pageClassName.equals(MemberAddresses.class.getName())) {
				page = new MemberAddresses();
            } else if (pageClassName.equals(NewGoods.class.getName())) {
                page = new NewGoods();
//            } else if (pageClassName.equals(GoodsListPanel.class.getName())) {
//                page = GoodsListPanel.getInstance();
            } else if (pageClassName.equals(BrandPanel.class.getName())) {
                page = BrandPanel.getInstance();                
            } else if (pageClassName.equals(OrderStatistics.class.getName())) {
                page = new OrderStatistics();
			} else if (pageClassName.equals(ShopSetup.class.getName())) {
				page = new ShopSetup();
            } else if (pageClassName.equals(Success.class.getName())) {
                page = Success.getInstance();
			} else if (pageClassName.equals(UserComments.class.getName())) {
				page = new UserComments();
            } else if (pageClassName.equals(RegionPanel.class.getName())) {
                page = RegionPanel.getInstance();
            } else if (pageClassName.equals(OrderUserPanel.class.getName())) {
                page = OrderUserPanel.getInstance();
            } else if (pageClassName.equals(OrderGoodsPanel.class.getName())) {
                page = OrderGoodsPanel.getInstance();
            } else if (pageClassName.equals(OrderShippingPanel.class.getName())) {
                page = OrderShippingPanel.getInstance();
            } else if (pageClassName.equals(OrderPayPanel.class.getName())) {
                page = OrderPayPanel.getInstance();
            } else if (pageClassName.equals(OrderFeePanel.class.getName())) {
                page = OrderFeePanel.getInstance();
            } else if (pageClassName.equals(OrderListPanel.class.getName())) {
                page = OrderListPanel.getInstance();            
            } else if (pageClassName.equals(SearchOrderPanel.class.getName())) {
                page = SearchOrderPanel.getInstance();          
            } else if (pageClassName.equals(MergeOrderPanel.class.getName())) {
                page = MergeOrderPanel.getInstance();           
            } else if (pageClassName.equals(OrderDetailPanel.class.getName())) {
                page = OrderDetailPanel.getInstance();          
            } else if (pageClassName.equals(ShippingMetaListPanel.class.getName())) {
                page = ShippingMetaListPanel.getInstance();
            } else if (pageClassName.equals(ShippingTemplatePanel.class.getName())) {
                page = ShippingTemplatePanel.getInstance();
            } else if(pageClassName.equals(ShippingAreaListPanel.class.getName())) {
                page = ShippingAreaListPanel.getInstance();
            } else if(pageClassName.equals(ShippingAreaPanel.class.getName())){
                page = new ShippingAreaPanel();
            } else if(pageClassName.equals(UserListPanel.class.getName())){
                page = UserListPanel.getInstance();
            } else if(pageClassName.equals(ShippingAddressPanel.class.getName())) {
                page = ShippingAddressPanel.getInstance();
            } else if(pageClassName.equals(ArticleCatListPanel.class.getName())){
                page = ArticleCatListPanel.getInstance();
            } else if(pageClassName.equals(ArticleCatPanel.class.getName())){
                page = ArticleCatPanel.getInstance();
            } else if(pageClassName.equals(ArticleListPanel.class.getName())){
                page = ArticleListPanel.getInstance();
//            } else if(pageClassName.equals(AdminListPanel.class.getName())){
//                page = AdminListPanel.getInstance();
            } else if(pageClassName.equals(AdminUserPanel.class.getName())){
                page = AdminUserPanel.getInstance();
            } else if(pageClassName.equals(ArticlePanel.class.getName())){
                page = ArticlePanel.getInstance();
            } else if (pageClassName.equals(EmailServerSetting.class.getName())) {
                page = new EmailServerSetting();
            } else if (pageClassName.equals(MemberMessageReply.class.getName())) {
                page = new MemberMessageReply();
            } else if (pageClassName.equals(CheckBounds.class.getName())) {
                page = new CheckBounds();
            } else if (pageClassName.equals(EmailSubscribeManager.class.getName())) {
                page = new EmailSubscribeManager();
//            } else if (pageClassName.equals(DiliveryMethodSet.class.getName())) {
//                page = new DiliveryMethodSet();
            } else if (pageClassName.equals(ShippingInstaller.class.getName())) {
                page = new ShippingInstaller();
            } else if (pageClassName.equals(MagazineManager.class.getName())) {
                page = new MagazineManager();
            } else if (pageClassName.equals(NewMagazine.class.getName())) {
                page = new NewMagazine();
            } else if (pageClassName.equals(EmailQueueManager.class.getName())) {
                page = new EmailQueueManager();
            } else if (pageClassName.equals(EmailReceiver.class.getName())) {
                page = new EmailReceiver();
            } else if (pageClassName.equals(NewEmail.class.getName())) {
                page = new NewEmail();
            } else if (pageClassName.equals(NewAdminUser.class.getName())) {
                page = new NewAdminUser();
            } else if (pageClassName.equals(AssignRole.class.getName())) {
                page = new AssignRole();
            } else if (pageClassName.equals(NewAgency.class.getName())){
                page = new NewAgency();
            } else if(pageClassName.equals(DistrictList.class.getName())){
                page = new DistrictList();
            } else if (pageClassName.equals(TagManager.class.getName())) {
                page = new TagManager();
            } else if (pageClassName.equals(NewTag.class.getName())) {
                page = NewTag.getInstance();
            } else if (pageClassName.equals(NewArticle.class.getName())) {
                page = new NewArticle();
            } else if (pageClassName.equals(DatabaseBackUp.class.getName())) {
                page = new DatabaseBackUp();
            } else if (pageClassName.equals(DatabaseRestore.class.getName())) {
                page = new DatabaseRestore();
            } else if (pageClassName.equals(DatabaseInitialize.class.getName())) {
                page = new DatabaseInitialize();
            } else if (pageClassName.equals(SalesReport.class.getName())) {
                page = new SalesReport();
            } else if (pageClassName.equals(ConsigneePanel.class.getName())) {
                page = ConsigneePanel.getInstance();
            } else if (pageClassName.equals(UpdateGoodsOnSellAuto.class.getName())) {
                page = UpdateGoodsOnSellAuto.getInstance();
            } else if (pageClassName.equals(PrintTemplatePanel.class.getName())) {
                page = PrintTemplatePanel.getInstance();
            } else if (pageClassName.equals(DeletedGoodsList.class.getName())) {
                page = new DeletedGoodsList();
            } else if (pageClassName.equals(VirtualCardList.class.getName())) {
                page = new VirtualCardList();
            } else if (pageClassName.equals(NewVirtualCard.class.getName())) {
                page = new NewVirtualCard();
            } else if (pageClassName.equals(ArticleCatogory.class.getName())) {
                page = new ArticleCatogory();
            } else if (pageClassName.equals(ArticleList.class.getName())) {
                page = new ArticleList();
            } else if (pageClassName.equals(NewArticleCat.class.getName())) {
                page = new NewArticleCat();
            } else if (pageClassName.equals(PackListPanel.class.getName())) {
                page = new PackListPanel();
            } else if (pageClassName.equals(NewPackPanel.class.getName())) {
                page = new NewPackPanel();
            } else if (pageClassName.equals(BonusListPanel.class.getName())) {
                page = new BonusListPanel();
            } else if (pageClassName.equals(NewBonusPanel.class.getName())) {
                page = new NewBonusPanel();
            } else if (pageClassName.equals(GoodsBatchUpload.class.getName())) {
                page = new GoodsBatchUpload();
            } else if (pageClassName.equals(SaleRankReport.class.getName())) {
            	page = new SaleRankReport();
            } else if (pageClassName.equals(UserRankReport.class.getName())) {
            	page = new UserRankReport();
            } else if (pageClassName.equals(SaleStatisticsReport.class.getName())) {
            	page = new SaleStatisticsReport();
            } else if (pageClassName.equals(GuestStatisticsReport.class.getName())) {
            	page = new GuestStatisticsReport();
            } else if (pageClassName.equals(VisitSoldReport.class.getName())) {
            	page = new VisitSoldReport();
            } else if (pageClassName.equals(SnatchListPanel.class.getName())) {
                page = new SnatchListPanel();
            } else if (pageClassName.equals(NewSnatchPanel.class.getName())) {
                page = new NewSnatchPanel();
            }  else if (pageClassName.equals(ModifySnatchPanel.class.getName())) {
                page = new ModifySnatchPanel();
            } else if (pageClassName.equals(SearchSnatchPanel.class.getName())) {
                page = new SearchSnatchPanel();
            } else if (pageClassName.equals(CardListPanel.class.getName())) {
                page = new CardListPanel();
            } else if (pageClassName.equals(NewCardPanel.class.getName())) {
                page = new NewCardPanel();
            }   else if (pageClassName.equals(GroupBuyListPanel.class.getName())) {
                page = new GroupBuyListPanel();
            } else if (pageClassName.equals(NewGroupBuyPanel.class.getName())) {
                page = new NewGroupBuyPanel();
            } else if (pageClassName.equals(SpecialActivityListPanel.class.getName())) {
                page = new SpecialActivityListPanel();
            } else if (pageClassName.equals(NewSpecialActivityPanel.class.getName())) {
                page = new NewSpecialActivityPanel();
            }else if (pageClassName.equals(AuctionListPanel.class.getName())) {
                page = new AuctionListPanel();
            } else if (pageClassName.equals(NewAuctionPanel.class.getName())) {
                page = new NewAuctionPanel();
            }  else if (pageClassName.equals(ModifyAuctionPanel.class.getName())) {
                page = new ModifyAuctionPanel();
            } else if (pageClassName.equals(SearchAuctionPanel.class.getName())) {
                page = new SearchAuctionPanel();
            } else if (pageClassName.equals(FavourableActivityListPanel.class.getName())) {
                page = new FavourableActivityListPanel();
            }else if (pageClassName.equals(NewFavourableActivityPanel.class.getName())) {
                page = new NewFavourableActivityPanel();
            }  else if (pageClassName.equals(ModifyFavourableActivityListPanel.class.getName())) {
                page = new ModifyFavourableActivityListPanel();
            }else if (pageClassName.equals(WholesaleListPanel.class.getName())) {
                page = new WholesaleListPanel();
            } else if (pageClassName.equals(NewWholesalePanel.class.getName())) {
                page = new NewWholesalePanel();
            }else if (pageClassName.equals(ModifyWholesalePanel.class.getName())) {
                page = new ModifyWholesalePanel();
            }
            
            if (page != null) {
                pageRegistry.put(pageClassName, page);
            } else {
                throw new RuntimeException(
                        "cannot find page: "
                                + pageClassName
                                + ", a page has to be first registered in pageRegistry before being used ");
            }
        }

        return page;
    }
	
    /**
     * Create the main links at the top of the application.
     * 
     * @param constants
     *            the constants with text
     */
    private void setupMainLinks() {
        // Link to GWT Homepage	
    	app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkGuide() + "</a>"));		
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkNotepad() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkRefresh() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkPersonalSetting() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkComment() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkView() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkCalc() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkAbout() + "</a>"));

    }

    /**
     * Setup all of the options in the main menu.
     * 
     * @param constants
     *            the constant values to use
     */
    private void setupMainMenu() {
        Tree mainMenu = app.getMainMenu();      
        
        TreeItem catGoods = mainMenu.addItem(Resources.constants.categoryGoods());
//      setupMainMenuOption(catGoods, new GoodsTypeListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new GoodsList.State(), Resources.images.catWidgets());
      	setupMainMenuOption(catGoods, new BrandInfo.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new NewGoods.State(), Resources.images.catWidgets());   
		setupMainMenuOption(catGoods, new CategoryInfo.State(), Resources.images.catWidgets());
		setupMainMenuOption(catGoods, new UserComments.State(), Resources.images.catWidgets());		
//        setupMainMenuOption(catGoods, new BrandListPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catGoods,new GoodsTypeInfo.State(),Resources.images.catWidgets());
        setupMainMenuOption(catGoods,new GenerateGoodsCode.State(),Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new Picturesdisposer.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods,new TagManager.State(),Resources.images.catWidgets());
        setupMainMenuOption(catGoods,new UpdateGoodsOnSellAuto.State(),Resources.images.catWidgets());
        setupMainMenuOption(catGoods,new DeletedGoodsList.State(),Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new VirtualCardList.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new NewVirtualCard.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new GoodsBatchUpload.State(), Resources.images.catWidgets());
		
        TreeItem promote = mainMenu.addItem("促销管理 ");
        setupMainMenuOption(promote, new SnatchListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new PackListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new BonusListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new CardListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new GroupBuyListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new SpecialActivityListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new AuctionListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new FavourableActivityListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(promote, new WholesaleListPanel.State(),Resources.images.catWidgets());
        
		TreeItem catOrder = mainMenu.addItem(Resources.constants.categoryOrder());
        setupMainMenuOption(catOrder, new OrderListPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catOrder, new OrderUserPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catOrder, new SearchOrderPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catOrder, new MergeOrderPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(catOrder, new PrintTemplatePanel.State(), Resources.images.catWidgets());
				
        TreeItem statisticsForm = mainMenu.addItem(Resources.constants.categoryStatistics());
        setupMainMenuOption(statisticsForm,new SalesReport.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new OrderStatistics.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new SaleRankReport.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new UserRankReport.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new SaleStatisticsReport.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new GuestStatisticsReport.State(),Resources.images.catWidgets());
        setupMainMenuOption(statisticsForm,new VisitSoldReport.State(),Resources.images.catWidgets());
        
        TreeItem user = mainMenu.addItem(Resources.constants.categoryMember());
        setupMainMenuOption(user, new UserListPanel.State(), Resources.images.catWidgets());
//        setupMainMenuOption(user, new UserPanel.State(), Resources.images.catWidgets());
//        setupMainMenuOption(user, new MemberList.State(), Resources.images.catWidgets());
        setupMainMenuOption(user, new NewUsers.State(), Resources.images.catWidgets());
        setupMainMenuOption(user, new MemberLevelList.State(), Resources.images.catWidgets());
        setupMainMenuOption(user, new MemberMessage.State(), Resources.images.catWidgets());
        setupMainMenuOption(user, new MemberApplication.State(), Resources.images.catWidgets());

        TreeItem system = mainMenu.addItem(Resources.constants.categorySystem());
        setupMainMenuOption(system,new ShopSetup.State(),Resources.images.catWidgets());
//		setupMainMenuOption(system, new ShopConfigPanel.State(), Resources.images.catWidgets());
//		setupMainMenuOption(system, new RegionPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(system, new PaymentMetaListPanel.State(),Resources.images.catWidgets()); 
		setupMainMenuOption(system, new ShippingMetaListPanel.State(),Resources.images.catWidgets());   
//        setupMainMenuOption(system, new RegionListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(system, new DistrictList.State(),Resources.images.catWidgets());
//        setupMainMenuOption(system, new DiliveryMethodSet.State(),Resources.images.catWidgets());
        
        TreeItem email = mainMenu.addItem(Resources.constants.emailManager());
        setupMainMenuOption(email, new EmailServerSetting.State(),Resources.images.catWidgets());
        setupMainMenuOption(email, new EmailSubscribeManager.State(),Resources.images.catWidgets());
        setupMainMenuOption(email, new MagazineManager.State(),Resources.images.catWidgets());
        setupMainMenuOption(email, new EmailQueueManager.State(),Resources.images.catWidgets());
//        setupMainMenuOption(email, new EmailReceiver.State(),Resources.images.catWidgets());
//        setupMainMenuOption(email, new NewEmail.State(),Resources.images.catWidgets());

        TreeItem article = mainMenu.addItem(Resources.constants.categoryArticle());
//        setupMainMenuOption(article, new ArticleCatListPanel.State(),Resources.images.catWidgets());
//        setupMainMenuOption(article, new ArticleListPanel.State(),Resources.images.catWidgets());
        setupMainMenuOption(article, new ArticleList.State(),Resources.images.catWidgets());
        setupMainMenuOption(article, new NewArticle.State(),Resources.images.catWidgets());
        setupMainMenuOption(article, new ArticleCatogory.State(),Resources.images.catWidgets());
        setupMainMenuOption(article, new NewArticleCat.State(),Resources.images.catWidgets());
		
//        TreeItem catData = mainMenu.addItem(Resources.constants.categoryData());
//        setupMainMenuOption(catData, new ImportPanel.State(), Resources.images.catWidgets());        
//        setupMainMenuOption(catData, new ExportPanel.State(), Resources.images.catWidgets());

        TreeItem database = mainMenu.addItem(Resources.constants.categoryDatabase());
        setupMainMenuOption(database,new DatabaseBackUp.State(),Resources.images.catWidgets());
        setupMainMenuOption(database,new DatabaseRestore.State(),Resources.images.catWidgets());
        setupMainMenuOption(database,new DatabaseInitialize.State(),Resources.images.catWidgets());
        
        TreeItem privilege = mainMenu.addItem(Resources.constants.categoryPrivilege());      
//        setupMainMenuOption(privilege, new AdminListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(privilege, new AdminList.State(), Resources.images.catWidgets());
        setupMainMenuOption(privilege, new AdminLog.State(), Resources.images.catWidgets());
        setupMainMenuOption(privilege, new AgencyList.State(), Resources.images.catWidgets());
    }

    /**
     * Add an option to the main menu.
     * 
     * @param parent
     *            the {@link TreeItem} that is the option
     * @param content
     *            the {@link ContentWidget} to display when selected
     * @param image
     *            the icon to display next to the {@link TreeItem}
     */
//    private void setupMainMenuOption(TreeItem parent, ContentWidget content,
//            AbstractImagePrototype image) {
//        // Create the TreeItem
//        TreeItem option = parent.addItem(image.getHTML() + " "
//                + content.getName());
//
//        // Map the item to its history token and content widget
//        itemWidgets.put(option, content);
//        itemTokens.put(getContentWidgetToken(content), option);
//    }

    private void setupMainMenuOption(TreeItem parent, PageState state,
            AbstractImagePrototype image) {
        // Create the TreeItem
        TreeItem option = parent.addItem(image.getHTML() + " "
                + state.getMenuDisplayName());

        // Map the item to its history token and content widget
        itemStates.put(option, state);
        pageClassItems.put(state.getPageClassName(), option);
    }
    
    /**
     * Create the options that appear next to the title.
     */
    private void setupOptionsPanel() {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        if (LocaleInfo.getCurrentLocale().isRTL()) {
          vPanel.getElement().setAttribute("align", "left");
        } else {
          vPanel.getElement().setAttribute("align", "right");
        }
        app.setOptionsWidget(vPanel);

        // Add the option to change the locale
        final Button exitButton = new Button();       
        final Button clearButton = new Button();
        HorizontalPanel localeWrapper = new HorizontalPanel();  
        clearButton.setText(Resources.constants.mainCommandClearCache());
        clearButton.addStyleName("cl_button");
        exitButton.setText(Resources.constants.mainCommandExit());
        exitButton.addStyleName("ex_button"); 
        exitButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				GWTHttpDynaForm form = new GWTHttpDynaForm();
				form.setUrl("/adminLogin.do?action=logout");
				form.submit();
			}});
        localeWrapper.add(clearButton);
        localeWrapper.add(exitButton);
        vPanel.add(localeWrapper);
       
      }

    /**
     * Create the title bar at the top of the application.
     * 
     */
    private void setupTitlePanel() {         

        // Add the title and some images to the title bar
    	com.google.gwt.user.client.ui.HorizontalPanel titlePanel = new com.google.gwt.user.client.ui.HorizontalPanel();
        titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        titlePanel.add(Resources.images.gwtLogo().createImage());          
        app.setTitleWidget(titlePanel);
    }

    
    /**
     * Create the navigation bar at the top of the application.
     * 
     */private void setupNavigationPanel(){
    	HorizontalPanel naviPanel = new HorizontalPanel();
    	//naviPanel.setHorizontalAlign(horizontalAlign)
    	app.setNavigationWidget(naviPanel);
    	final Button home = new Button();    	
    	home.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				
			}
    		
    	});
    	final Button setnavi = new Button();
    	final Button goodslist = new Button();
    	final Button orderlist = new Button();
    	final Button Comments = new Button();
    	final Button userList = new Button();

    	home.setText(Resources.constants.navHome());
    	setnavi.setText(Resources.constants.navSetting());
    	goodslist.setText(Resources.constants.navGoodsList());
    	goodslist.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                PageState state = new GoodsList.State();
                state.execute();
            }
        });
    	orderlist.setText(Resources.constants.navOrderList());
    	orderlist.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                PageState state = new OrderListPanel.State();
                state.execute();
            }
        });
    	Comments.setText(Resources.constants.navComment());
    	Comments.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                PageState state = new UserComments.State();
                state.execute();
            }
        });
    	userList.setText(Resources.constants.navMemberList());
    	userList.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
				PageState state = new UserListPanel.State();
				state.execute();
			}
    	});
    	home.addStyleName("Nav_button");
    	setnavi.addStyleName("Nav_button");
    	goodslist.addStyleName("Nav_button");
    	orderlist.addStyleName("Nav_button");
    	Comments.addStyleName("Nav_button");
    	userList.addStyleName("Nav_button");
    	naviPanel.add(home);
    	naviPanel.add(setnavi);
    	naviPanel.add(goodslist);
    	naviPanel.add(orderlist);
    	naviPanel.add(Comments);
    	naviPanel.add(userList);    	
    }
    /**
     * Set the content to the {@link ContentWidget}.
     * 
     * @param content
     *            the {@link ContentWidget} to display
     */
    private void displayContentWidget(ContentWidget content) {
        if (content != null) {
        	app.setContent(content);
        	if (content.getButtonText() != null) {
        	    app.setButton(content.getButtonText(), content.getButtonListener());
        	} else {
        	    app.setButton(null, null);
        	}
            content.refresh();
        }        
    }
}
