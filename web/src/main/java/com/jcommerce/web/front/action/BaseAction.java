package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_ADDRESS;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_COPYRIGHT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_MSN;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_NAME;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_POSTCODE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_QQ;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SERVICE_EMAIL;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SERVICE_PHONE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SKYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_WW;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_YM;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.ArticleManager;
import com.jcommerce.core.service.CategoryManager;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.core.service.ShopConfigManager;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.util.IConstants;
import com.jcommerce.core.util.ResourceUtil;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.core.wrapper.ShopConfigWrapper;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.component.ComponentUrl;
import com.jcommerce.web.component.Navigator;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.Const;
import com.jcommerce.web.to.GoodsListWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.HelpCat;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.to.HelpCat.HelpItem;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibInsert;
import com.jcommerce.web.util.LibMain;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction extends ActionSupport implements IPageConstants, IWebConstants, IConstants{
//	private static Map<String, String> constants = new HashMap<String, String>();
	private static final Logger log = Logger.getLogger(BaseAction.class.getName());

	private IShippingMetaManager shippingMetaManager;
	private IPaymentMetaManager paymentMetaManager;
	private IWebManager webManager;
	private CategoryManager categoryManager;
    private ArticleManager articleManager;
	private ShopConfigManager shopConfigManager;
	private Long queryStartTime = 0L;
	private String historyList = "";

	public String getHistoryList() {
		return historyList;
	}
	public void setHistoryList(String historyList) {
		this.historyList = historyList;
	}
	public void debug(String s) {
		log.info(" in [BaseAction]: "+s );
	}
	@Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
	
	public JSONObject getReqAsJSON(HttpServletRequest request, String paraName) {
		try {
//			InputStream input = request.getInputStream();
//			byte[] rawReq = IOUtils.toByteArray(input);
//			String reqStr = new String(rawReq, ENC);
			String reqStr = request.getParameter(paraName);
			debug("in [getReqAsJSON]: reqStr="+reqStr);
			JSONObject req = new JSONObject(reqStr);
			return req;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
//	static {
//		constants.put(KEY_KEYWORDS, "gCloudShop演示站");
//		constants.put(KEY_DESCRIPTION, "gCloudShop演示站");
//		constants.put(KEY_PAGE_TITLE, "gCloudShop演示站 - Powered by gCloudShop");
//		// TODO
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//	}
	
	public void setPageMeta(HttpServletRequest request) {
        request.setAttribute(KEY_KEYWORDS, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_KEYWORDS));
        request.setAttribute(KEY_DESCRIPTION, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_DESC));
        request.setAttribute(KEY_PAGE_TITLE, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_TITLE)+" Powered by gCloudShop");
	}
	
	public void includePageFooter(HttpServletRequest request) {
        // page_footer.ftl
		ShopConfigWrapper shopConfig = getCachedShopConfig();
        request.setAttribute("copyright", shopConfig.getString(CFG_KEY_SHOP_COPYRIGHT));
        request.setAttribute("shopAddress", shopConfig.getString(CFG_KEY_SHOP_ADDRESS));
        request.setAttribute("shopPostcode", shopConfig.getString(CFG_KEY_SHOP_POSTCODE));
        if(!shopConfig.getString(CFG_KEY_SHOP_SERVICE_PHONE).equals("")){
        	request.setAttribute("servicePhone", shopConfig.getString(CFG_KEY_SHOP_SERVICE_PHONE)); 
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_SERVICE_EMAIL).equals("")){
        	request.setAttribute("serviceEmail", shopConfig.getString(CFG_KEY_SHOP_SERVICE_EMAIL));
        } 
        
        if(!shopConfig.getString(CFG_KEY_SHOP_QQ).equals("")){
        	request.setAttribute("qq", shopConfig.getString(CFG_KEY_SHOP_QQ).split(","));
        }else{
        	request.setAttribute("qq", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_WW).equals("")){
        	request.setAttribute("ww", shopConfig.getString(CFG_KEY_SHOP_WW).split(","));
        }else{
        	request.setAttribute("ww", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_YM).equals("")){
        	request.setAttribute("ym", shopConfig.getString(CFG_KEY_SHOP_YM).split(","));
        }else{
        	request.setAttribute("ym", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_MSN).equals("")){
        	request.setAttribute("msn", shopConfig.getString(CFG_KEY_SHOP_MSN).split(","));
        }else{
        	request.setAttribute("msn", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_SKYPE).equals("")){
        	request.setAttribute("skype", shopConfig.getString(CFG_KEY_SHOP_SKYPE).split(","));
        }else{
        	request.setAttribute("skype", new String[0]);
        }
        request.setAttribute("ecsUsername", "ecsUsername");
        request.setAttribute("ecsCssPath", "style.css");
        request.setAttribute("ecsVersion", "1.0");
        request.setAttribute("licensed", "licensed");
        request.setAttribute("feedUrl", "feedUrl");
	}
	public Lang getLangMap(HttpServletRequest request) {
		// default
		String locale = Locale.CHINESE.toString();  
		String reqLocale = (String)request.getParameter(KEY_LOCALE);
		debug("reqLocale: "+reqLocale);
		if(reqLocale!=null) {
			locale = reqLocale;
		}else {
			String sessionLocale = (String)request.getSession().getAttribute(KEY_LOCALE);
			debug("sessionLocale: "+sessionLocale);
			if(sessionLocale!=null) {
				locale = sessionLocale;
			}
		}
		Locale loc = null;
		try {
			loc = ResourceUtil.parseLocale(locale);
		} catch (Exception ex) {
			// invalid locale string. should avoid
			ex.printStackTrace();
		}
		
		// remember the choice
		request.getSession().setAttribute(KEY_LOCALE, locale);
		
		Lang.setCurrentLocale(loc);
		Lang lang = (Lang)request.getAttribute("lang");
		if(lang == null) {
			lang = Lang.getInstance();
			request.setAttribute("lang", lang);
		}
		return lang;
	}


	
    public void includePromotionInfo(HttpServletRequest request) {
    	// promotion_info.ftl
//        request.setAttribute("promotionInfo", new HashMap());
    }


	public void includeCart() {
		String res = LibInsert.insertCartInfo(SpringUtil.getCartManager(), getRequest());
		// TODO need convert insert clause to a variable during transform to .ftl
		getRequest().setAttribute("insertCartInfo", res);
	}
	public void includeCategoryTree(HttpServletRequest request) {

        List<CategoryWrapper> list = LibGoods.getCategoriesTree(null);
        request.setAttribute("categories", list);		
	}

	public void includeMemberInfo() {
		String userId = (String)getSession().getAttribute(KEY_USER_ID);
		if(userId != null) {
			getRequest().setAttribute("userInfo", LibMain.getUserInfo(userId, SpringUtil.getUserManager()));
			
		}
		else {
			
		}
		
	}
	public void includeHistory(HttpServletRequest request) {
		Map<String,GoodsWrapper> viewHistory = (HashMap<String,GoodsWrapper>)getSession().getAttribute("viewHistory");
	    
		if( viewHistory != null && viewHistory.size() > 0 ){
	    	StringBuffer sb = new StringBuffer();
	    	
	    	for (GoodsWrapper goodsWrapper : viewHistory.values()) {
	    		Map map = new HashMap();
	    		map.put("gid", goodsWrapper.getPkId());
	    		String shortName = goodsWrapper.getName().length() > 10 ? goodsWrapper.getName().substring(0, 10)+"..." : goodsWrapper.getName();
	    		String goodsUrl = LibCommon.buildUri("goods", map , "" , 0 , 0);
	    		sb.append("<li><a href='" + goodsUrl + "' title='" + goodsWrapper.getName() + "'>" + shortName + "</a></li>");
	    		/* for ecshop 2.7.0
	    		sb.append("<ul class='clearfix'><li class='goodsimg'><a href='"+goodsUrl+"' target='_blank'><img src='"+goodsWrapper.getThumb()+"' alt='"+goodsWrapper.getName()+"' class='B_blue' /></a></li><li><a href='"+goodsUrl+"' target='_blank' title='"+goodsWrapper.getName()+"'>"+shortName+"</a><br />"+Lang.getInstance().getString("shopPrice")+"<font class='f1'>"+goodsWrapper.getShopPriceFormated()+"</font><br /></li></ul>");
				*/
			}
	    	historyList = sb.toString();
	    }
	}
	public void includeComments(HttpServletRequest request) {
	}
    public void includeRecommendBest(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("bestGoods", new GoodsListWrapper(SpringUtil.getGoodsManager().getBestSoldGoodsCriteria()));
    }
    public void includeRecommendHot(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("hotGoods", new GoodsListWrapper(SpringUtil.getGoodsManager().getHotSoldGoodsCriteria()));
    }
    public void includeRecommendNew(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("newGoods", new GoodsListWrapper(SpringUtil.getGoodsManager().getNewGoodsCriteria()));
    }
    public void setCatRecSign(HttpServletRequest request) {
    	// refer to logic at index.php line 60
        request.setAttribute("catRecSign", 0);
        // refer to logic at index.php line 121
        request.setAttribute("catRec", new String[2]);
    }
//    private List<Goods> getPartBestSoldGoods() {
//        List<Goods> list = SpringUtil.getGoodsManager().getBestSoldGoodsList(5);
//        return wrapGoods(list);
//         
//    }
//    private List<Goods> getPartHotSoldGoods() {
//        List<Goods> list = SpringUtil.getGoodsManager().getHotSoldGoodsList(5);
//        return wrapGoods(list);
//
//    }
//    private List<Goods> getPartNewlyAddedGoods() {
//        List<Goods> list = SpringUtil.getGoodsManager().getNewGoodsList(5);
//        return wrapGoods(list);
//
//    }
    private List wrapGoods(List<Goods> list) {
    	return WrapperUtil.wrap(list, GoodsWrapper.class);
    }
    
    public void includeComment (int type, String id) {
    	LibInsert.insertComments(type, id, getRequest(), getCachedShopConfig());
    }
    
    public void includeUrHere() {
    	// provide a default value to avoid freeMarker error
    	// however this could be override by calling LibMain.assignUrHere from Action
    	
    	getRequest().setAttribute("urHere", "TODO: urHere");
    }
    
	public void includeFilterAttr() {
		// TODO includeFilterAttr
	}
	public void includePriceGrade() {
		// TODO includePriceGrade
	}
	public void includeHelp(HttpServletRequest request) {
		List<HelpCat> helps = new ArrayList<HelpCat>();
		List<HelpItem> item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=agpnY2xvdWRzaG9wchoLEgdBcnRpY2xlIg1fMzAzMDY4OTY0NjMxDA", "订购方式", "订购方式"));
		item.add(new HelpItem("article.action?id=1", "购物流程", "购物流程"));
		item.add(new HelpItem("article.action?id=1", "售后流程", "售后流程"));
		helps.add(new HelpCat("新手上路", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "如何分辨水货手机", "如何分辨水货手机"));
		item.add(new HelpItem("article.action?id=1", "如何享受全国联保", "如何享受全国联保"));
		item.add(new HelpItem("article.action?id=1", "如何分辨原装电池", "如何分辨原装电池"));
		helps.add(new HelpCat("手机常识", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "支付方式说明", "支付方式说明"));
		item.add(new HelpItem("article.action?id=1", "配送支付智能查询", "配送支付智能查询"));
		item.add(new HelpItem("article.action?id=1", "货到付款区域", "货到付款区域"));
		helps.add(new HelpCat("配送与支付", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "产品质量保证", "产品质量保证"));
		item.add(new HelpItem("article.action?id=1", "售后服务保证", "售后服务保证"));
		item.add(new HelpItem("article.action?id=1", "退换货原则", "退换货原则"));
		helps.add(new HelpCat("服务保证", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "投诉与建议", "投诉与建议"));
		item.add(new HelpItem("article.action?id=1", "选机咨询", "选机咨询"));
		item.add(new HelpItem("article.action?id=1", "网站故障报告", "网站故障报告"));
		helps.add(new HelpCat("联系我们", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "我的订单", "我的订单"));
		item.add(new HelpItem("article.action?id=1", "我的收藏", "我的收藏"));
		item.add(new HelpItem("article.action?id=1", "资金管理", "资金管理"));
		helps.add(new HelpCat("会员中心", item));
		
		request.setAttribute("helps", helps);
		
		
	}
	public void includePageHeader(HttpServletRequest request) {
        
		includeMemberInfo();
		// Navigator ............
		Lang lang = Lang.getInstance();

		Navigator nav = new Navigator();
//		List<Nav> navList = defaultManager.getList(ModelNames.NAV, null);
//		for (Nav navInfo : navList) {
//			if(navInfo.getType().equals("top")){
//				nav.addTop(new ComponentUrl(navInfo.getUrl(),lang.getString(navInfo.getName()),navInfo.getOpennew().intValue()));
//			}
//			else if(navInfo.getType().equals("middle")){
//				nav.addMiddle(new ComponentUrl(navInfo.getUrl(),lang.getString(navInfo.getName()),navInfo.getOpennew().intValue()));
//			}
//			else if(navInfo.getType().equals("bottom")){
//				nav.addBottom(new ComponentUrl(navInfo.getUrl(),lang.getString(navInfo.getName()),navInfo.getOpennew().intValue()));
//			}
//		}
		
        nav.addTop(new ComponentUrl("flow.action", getText(lang.getString("viewCart")), 1));
//        nav.addTop(new ComponentUrl("user.action", getText(lang.getString("userCenter")), 1));
        nav.addTop(new ComponentUrl("pick_out.action", getText(lang.getString("pickOut")), 1));
        
        
//        nav.addTop(new ComponentUrl("group_by.action", getText(lang.getString("groupBuy")), 1));
//        nav.addTop(new ComponentUrl("snatch.action", getText(lang.getString("snatch")), 1));
        
        nav.addTop(new ComponentUrl("tag_cloud.action", getText(lang.getString("tagCloud")), 1));
        
        
        nav.addBottom(new ComponentUrl("article.action?id=1", getText("免责条款"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=2", getText("隐私保护"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=3", getText("咨询热点"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=4", getText("联系我们"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=5", getText("公司简介"), 1));
        nav.addBottom(new ComponentUrl("wholesale.action", getText("批发方案"), 1));
        nav.addBottom(new ComponentUrl("myship.action", getText("配送方式"), 1));
        
        
        
        nav.getConfig().put("index", 1);

//        nav.addMiddle(new ComponentUrl("home.action", getText("home_title"), true, true));
//        nav.addMiddle(new ComponentUrl("home.action", "TODOtitle", 1, 1));
        List<Category> categoryList = SpringUtil.getCategoryManager().getCategoryList();
        Iterator<Category> it = categoryList.iterator();
        while (it.hasNext()) {
            Category cat = it.next();
            if (cat.isShowInNavigator())
                nav.addMiddle(new ComponentUrl("category.action?id="+cat.getId(), cat.getName(), 1, 0));
        }

        request.setAttribute("navigatorList", nav);	
        
        request.setAttribute("categoryList", getCategoryList(categoryList));
        
        // Search key words ..........
        ArrayList searchKeywords = new ArrayList();
        request.setAttribute("searchkeywords", searchKeywords);
        request.setAttribute("searchKeywords", "");
	}
    private String getCategoryList(List<Category> list) {
    	StringBuffer buf = new StringBuffer();
    	int i=1;
    	for(Category category:list) {
    		String catId = category.getId();
    		buf.append("<option value=\"").append(catId).append("\" >").append(category.getName()).append("</option>").append("\r\n");
    		i++;
    	}
    	return buf.toString();
    }
	public void setShowMarketplace(HttpServletRequest request) {
        request.setAttribute("showMarketprice", true);		
	}
	public void setNowtime(HttpServletRequest request) {
        request.setAttribute("nowTime", new Date().toString());		
	}
	
	public void setSessionUser(HttpServletRequest request) {
        User user = new User();
        user.setName("Guest");
        request.getSession().setAttribute("user_info", user);
	}
    protected void initPager(HttpServletRequest request) {
        Pager pager = new Pager();
        request.setAttribute("pager", pager);
    }
    
    public HttpServletRequest getRequest() {
        ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
        return request;
    }
    public HttpServletResponse getResponse() {
        ActionContext ctx = ActionContext.getContext();        
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);        
        return response;
    }
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
    public void beforeExecute(){
    	queryStartTime = System.currentTimeMillis();
    	
    	HttpServletRequest request = getRequest();
//      HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
		HttpSession session = request.getSession();
		
        request.setAttribute("Const", Const.getInstance());
        		
		String queryString = request.getQueryString();
		String requestURL = request.getRequestURI();
		String reqStr = StringUtils.isBlank(queryString) ? requestURL : requestURL+"?"+queryString;
		
		debug("currentQueryString=["+reqStr+"]");
		debug("lastQueryString=["+session.getAttribute("currentQueryString")+"]");
		session.setAttribute("lastQueryString", session.getAttribute("currentQueryString"));
		session.setAttribute("currentQueryString", reqStr);
		
		
		Object obj = getSession().getAttribute("WW_TRANS_I18N_LOCALE");
		debug("locale: "+obj);
		
	  getLangMap(request);
      setPageMeta(request);
      includeHelp(request);
      includeUrHere();
      includePageFooter(request);
      
      includePageHeader(request);
      includeCart();
      setSessionUser(request);
      
      // just empty
      ShopConfigWrapper shopConfig = getCachedShopConfig();
      request.setAttribute("cfg", shopConfig);
      // TODO
      request.setAttribute("shopName", shopConfig.get(CFG_KEY_SHOP_NAME));
      request.setAttribute("pointsName", shopConfig.get(IShopConfigMeta.CFG_KEY_INTEGRAL_NAME));
      
//      setShowMarketplace(request);
      	assignSmartyGlobal();
    }
    
	public String execute() throws Exception {
		
		log.entering(BaseAction.class.getName(), "execute");
		try {
			beforeExecute();
			String returnValue = onExecute();
			afterExecute();
			
			log.exiting(BaseAction.class.getName(), "execute");
			return returnValue;
			
		} catch (RuntimeException re) {
			log.throwing(BaseAction.class.getName(), "execute", re);
			throw re;
		} catch (Exception ex) {
			log.throwing(BaseAction.class.getName(), "execute", ex);
			throw new RuntimeException(ex);
		}
	}
	public abstract String onExecute() throws Exception; 
	public void afterExecute(){
		new LibInsert().insertQueryTime(SpringUtil.getSessionManager(), queryStartTime,getRequest());
	}

	public void assignSmartyGlobal() {
		HttpServletRequest request = getRequest();
        Map<String, Object> smarty = new HashMap<String, Object>();

        Map<String, String> server = new HashMap<String, String>();
        server.put("PHP_SELF", getSelfURL());        
        smarty.put("server", server);
        
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("userName", getSession().getAttribute(KEY_USER_NAME));
        String email = (String)getSession().getAttribute(KEY_USER_EMAIL);
        session.put("email",  email==null? "" : email);
        smarty.put("session", session);

        request.setAttribute("smarty", smarty);
	}
	
	protected String getSelfURL() {
		return "";
	}
	
//	public String getConstants(String key) {
//		return constants.get(key);
//	}
	
	public ShopConfigWrapper getCachedShopConfig() {
		String locale = (String)getSession().getAttribute(KEY_LOCALE);
//		if(locale == null){
//			locale = getRequest().getLocale().toString();
//		}
		return getShopConfigManager().getCachedShopConfig(locale);
	}
	
	public IWebManager getWebManager() {
		return webManager;
	}
	public void setWebManager(IWebManager webManager) {
		this.webManager = webManager;
	}
	
	public CategoryManager getCategoryManager() {
        return categoryManager;
    }
	
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
    
    public Map<String, Object> getMyParameters() {
//		return parameters;
		Map parameters = ActionContext.getContext().getParameters();
		return parameters;
	}
	
	public String getMyParameter(String key) {
		String res = null;
		Map<String, Object> parameters = getMyParameters();
		Object obj = parameters.get(key);
		if(obj instanceof String[]) {
			res = ((String[])obj)[0];
		}
		else if(obj instanceof String) {
			res = (String)obj;
		}
		return res;
	}
	public IPaymentMetaManager getPaymentMetaManager() {
		return paymentMetaManager;
	}
	public void setPaymentMetaManager(IPaymentMetaManager paymentMetaManager) {
		this.paymentMetaManager = paymentMetaManager;
	}
	public IShippingMetaManager getShippingMetaManager() {
		return shippingMetaManager;
	}
	public void setShippingMetaManager(IShippingMetaManager shippingMetaManager) {
		this.shippingMetaManager = shippingMetaManager;
	}
	public ShopConfigManager getShopConfigManager() {
		return shopConfigManager;
	}
	public void setShopConfigManager(ShopConfigManager shopConfigManager) {
		this.shopConfigManager = shopConfigManager;
	}
    public ArticleManager getArticleManager() {
        return articleManager;
    }
    public void setArticleManager(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }
	
	// well, probably we need not this. 
	// simply call method for each included library
//	public void executeAction(String actionName) {
//		BaseAction ba = (BaseAction)getSpringContext().getBean(actionName);
//	}
//	WebApplicationContext springContext = null;
//	public WebApplicationContext getSpringContext() {
//		if(springContext==null) {
//			ServletContext sc = ServletActionContext.getServletContext();
//			springContext = WebApplicationContextUtils.getWebApplicationContext(sc);
//		}
//		return springContext;
//	}

	
}
