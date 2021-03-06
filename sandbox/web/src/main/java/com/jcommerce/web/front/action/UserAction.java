package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.CollectGoods;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.model.UserRank;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.OrderManager;
import com.jcommerce.core.service.UserManager;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.ICollectGood;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.Affiliate;
import com.jcommerce.web.to.CollectGoodsWrapper;
import com.jcommerce.web.to.CommentWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.OrderGoodsWrapper;
import com.jcommerce.web.to.OrderWrapper;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.util.ConstantsMappingUtils;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.LibOrder;
import com.jcommerce.web.util.LibTransaction;
import com.jcommerce.web.util.PrintfFormat;
import com.jcommerce.web.util.WebFormatUtils;


public class UserAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [UserAction]: "+s );
	}
	
	@Override
	protected String getSelfURL() {
		return URLConstants.ACTION_USER;
	}
	
	public static final String RES_USER_PASSPORT = "user_passport";
	public static final String RES_USER_CLIPS = "user_clips";
	public static final String RES_IS_REGISTERED = "is_registered";
	public static final String RES_USER_TRANSACTION = "user_transaction";
	public static final String RES_CHECK_EMAIL = "check_email";
	public static final String RES_COLLECT = "collect";
	public static final String RES_RETURN_TO_CART = "return_to_cart";

	
	private String username;
	private String password;
	private String email;
	private String captcha;
	private String old_password;
	private String new_password;
	private String comfirm_password;
	private int sex;
	private String birthday;
	UserManager userManager;

	private Map<String, String> other;
	private String action;
	
	private InputStream isRegistered;
	private InputStream isUsed;
	private InputStream collectGoods;
	private InputStream returnToCart;
	
	
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");
			HttpServletRequest request = getRequest();
			
			LibMain.assignUrHere(request, "", Lang.getInstance().getString("userCenter"));
			
			userManager = SpringUtil.getUserManager();
			
			String userId = (String)getSession().getAttribute(KEY_USER_ID);

			action = request.getParameter("act");
			if(action==null) {
				action = "default";
			}
			else {
				action = action.trim();
			}
			// 不需要登录的操作或自己验证是否登录（如ajax处理）的act
			String[] s = new String[]{
					"login","act_login","register","act_register","act_edit_password",
						"get_password","send_pwd_email","password", "signin", 
						"add_tag", "collect", "return_to_cart", "logout", 
						"email_list", "validate_email", "send_hash_mail", 
						"order_query", "is_registered", "check_email"};

			List<String> notLoginArr = Arrays.asList(s);
			
			/* 显示页面的action列表 */
			s = new String[] {
					"register", "login", "profile", "order_list", "order_detail", "address_list", "collection_list",
					"message_list", "tag_list", "get_password", "reset_password", "booking_list", "add_booking", "account_raply",
					"account_deposit", "account_log", "account_detail", "act_account", "pay", "default", "bonus", "group_buy", 
					"group_buy_detail", "affiliate", "comment_list","validate_email","track_packages", "transform_points",
					"act_edit_profile"
			};
			
			List<String> uiArr = Arrays.asList(s);
			

			String backAct = "";
			/* 未登录处理 */
			if(StringUtils.isEmpty(userId)) {
				if(!notLoginArr.contains(action)) {
					if(uiArr.contains(action)) {
						 /* 如果需要登录,并是显示页面的操作，记录当前操作，用于登录后跳转到相应操作*/
						if(!StringUtils.isEmpty(request.getQueryString())) {
							backAct = (String)getSession().getAttribute("lastQueryString");
							debug("in execute: backAct="+backAct);
						}
						
						action = "login";
					} 
					else {
						
					}
				}
				
			}
			
			// TODO handle variable action in freemarker: 
			// see http://struts.apache.org/2.x/docs/freemarker.html
			// solution: see this.toString()
//			request.setAttribute("action", action);
			
			request.setAttribute("shopRegClosed", getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_SHOP_REG_CLOSED));
			request.setAttribute("backAct", backAct);
			
			if("default".equals(action)) {
				
				request.setAttribute("rankName", "");
				request.setAttribute("nextRankName", "");
				UserWrapper uw = getUserDefault(userId);
				//查找userRank和nextRank
				UserRank userRank = getUserRank(uw,request);
				if(userRank != null && userRank.getMaxPoints() > 0) {
					getNextRank(uw,userRank,request);
				}
				
//				String shopName = null;				
//				Condition codition = new Condition(IShopConfig.CODE,Condition.EQUALS,"shop_name");
//		        Criteria criteria = new Criteria();
//		        criteria.addCondition(codition);
//		        List<ShopConfig> shopConfigs = getDefaultManager().getList(ModelNames.SHOPCONFIG, criteria);
//		        for(Iterator iterator = shopConfigs.iterator();iterator.hasNext();) {
//		        	ShopConfig shopConfig = (ShopConfig)iterator.next();
//		        	shopName = shopConfig.getValue();
//		        }
				String shopName = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_NAME);
				uw.put("shopName", shopName);
				
				//获得上次登录时间
				Date lastTime = uw.getUser().getLastTime() == null ? new Date() : uw.getUser().getLastTime();//如果是第一次登录，上次登录时间为本次登录时间 
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String lastTimeStr = formatter.format(lastTime);

			    //记录本次登录时间
				Date date = new Date();
				uw.getUser().setLastTime(new Timestamp(date.getTime()));
				userManager.saveUser(uw.getUser());
				uw.put("lastTime", lastTimeStr);
				
				//获得所有订单
				Condition condition = new Condition(IOrderInfo.USER_ID,Condition.EQUALS,uw.getUser().getId());
				Criteria criteria = new Criteria();
				criteria.addCondition(condition);
				List<Order> orderInfos = SpringUtil.getOrderManager().getOrderList(criteria);
				uw.setOrder(orderInfos);
				
				uw.put("isValidate", 1);
				uw.put("shippedOrder", null);
				
				request.setAttribute("info", uw);
				request.setAttribute("userNotice", getCachedShopConfig().get("userNotice"));
				request.setAttribute("prompt", new String[0]);
				includeUserMenu();
				return RES_USER_CLIPS;
			}
			else if("register".equals(action)) {
				request.setAttribute("enabledCaptcha", 0);
				request.setAttribute("rand", new Double(1000000*Math.random()).longValue());

				request.setAttribute("backAct", backAct);
				return RES_USER_PASSPORT;
			}
			else if("check_email".equals(action)){
				if(checkEmail(getEmail())) {
					isUsed = new StringBufferInputStream("false");
				}else {
					isUsed = new StringBufferInputStream("ok");
				}
				return RES_CHECK_EMAIL;
			}
			else if("login".equals(action)) {
				request.setAttribute("backAct", backAct);
				return RES_USER_PASSPORT;
			}
			else if("act_login".equals(action) || "actLogin".equals(action)) {
				if(backAct!=null) {
					backAct = backAct.trim();
				}
				String url = StringUtils.isEmpty(backAct) ? "user.action" : backAct;
				if(login(getUsername(), getPassword())) {
					if(url.endsWith("user.action")) {
						return LibMain.showMessage(Lang.getInstance().getString("loginSuccess"), Lang.getInstance().getString("profileLnk"), 
								"user.action", "info", false, request);
					}
					else {
						// TODO redirect to a link
						getResponse().sendRedirect(url);
						// will it be executed??
						System.out.println("!!!!!!!!!!!!!!!!!!!!!");
						return "error";
					}
				}
				else {
					backAct = "login";
					return LibMain.showMessage(Lang.getInstance().getString("loginFailure"), Lang.getInstance().getString("reloginLnk"), 
							url, "error", false, request);
				}

			}
			else if("act_register".equals(action) || "actRegister".equals(action)) {
				if(getCaptcha() == null){
					String error = register(getUsername(), getPassword(), getEmail(), other );
					if(error == null) {
						return LibMain.showMessage( new PrintfFormat(Lang.getInstance().getString("registerSuccess")).sprintf(getUsername()), Lang.getInstance().getString("profileLnk"), 
								"user.action", "info", true, request);
					} else {
						return LibMain.showMessage(error, Lang.getInstance().getString("signUp"), 
								"user.action", "info", true, request);
					}
				}
//				Captcha _captcha =  (Captcha)getSession().getAttribute("simpleCaptcha");
//				System.out.println("_captcha:"+_captcha);
//				if(_captcha.isCorrect(getCaptcha())){
//					String error = register(getUsername(), getPassword(), getEmail(), other );
//					if(error == null) {
//						return LibMain.showMessage( new PrintfFormat(Lang.getInstance().getString("registerSuccess")).sprintf(getUsername()), Lang.getInstance().getString("profileLnk"), 
//								"user.action", "info", true, request);
//					} else {
//						return LibMain.showMessage(error, Lang.getInstance().getString("signUp"), 
//								"user.action?act=register", "info", true, request);
//					}
//				}
//				else{
//					return LibMain.showMessage(Lang.getInstance().getString("invalidCaptcha"), Lang.getInstance().getString("signUp"), 
//							"user.action?act=register", "info", true, request);
//				}
				return "Captcha rude library be used, current remove";
			}
			else if("is_registered".equals(action) || "isRegistered".equals(action)) {
				if(checkUser(getUsername(), null)) {
					isRegistered = new StringBufferInputStream("false");
				}else {
					isRegistered = new StringBufferInputStream("true");
				}
				return RES_IS_REGISTERED;
			}
			else if("profile".equals(action)) {
				UserWrapper uw = LibTransaction.getProfile(userId);
				request.setAttribute("profile", uw);
				includeUserMenu();
				return RES_USER_TRANSACTION;
				
			} 
			else if("act_edit_profile".equals(action)) {
				
				if( !email.equals(getSession().getAttribute(KEY_USER_EMAIL)) ){
					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(IUser.EMAIL,Condition.EQUALS,email));
					if(userManager.getUserList(criteria).size() > 0 ){
						return LibMain.showMessage(Lang.getInstance().getString("emailExists"), null, 
								"user.action?act=profile", "info", true, request);
					}
				}
				
				
				User user = userManager.getUser((String)getSession().getAttribute(KEY_USER_ID));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				user.setBirthday(new Timestamp(dateFormat.parse(birthday).getTime()));
				user.setSex(sex);
				user.setMSN(other.get("msn"));
				user.setQQ(other.get("qq"));
				user.setOfficePhone(other.get("office_phone"));
				user.setHomePhone(other.get("home_phone"));
				user.setMobilePhone(other.get("mobile_phone"));
				SpringUtil.getUserManager().saveUser(user);
				return LibMain.showMessage(Lang.getInstance().getString("editProfileSuccess"), null, 
						"user.action?act=profile", "info", true, request);
				
			} 
			else if("act_edit_password".equals(action)) {
			
				Criteria criteria = new Criteria();
				criteria.addCondition(new Condition(IUser.ID,Condition.EQUALS,(String)getSession().getAttribute(KEY_USER_ID)));
				criteria.addCondition(new Condition(IUser.PASSWORD,Condition.EQUALS,old_password));
				List userList = userManager.getUserList(criteria);
				if( userList.size() > 0 ){
					User user = (User)userList.get(0);
					user.setPassword(new_password);
					userManager.saveUser(user);
					return LibMain.showMessage(Lang.getInstance().getString("editPasswordSuccess"), null, 
							"user.action?act=default", "info", true, request);
				}
				else{
					return LibMain.showMessage(Lang.getInstance().getString("editPasswordFailure"), null, 
							"user.action?act=default", "info", true, request);
				}
			}
			else if("order_list".equals(action) || "orderList".equals(action)){
				getOrderList(request, userId);
				includeUserMenu();
				return RES_USER_TRANSACTION;
			}
			/* 收货地址列表界面*/
			else if("address_list".equals(action) || "addressList".equals(action)) {
				includeUserMenu();
				String shopCountry = (String)getCachedShopConfig().get(IShopConfigMeta.CFG_KEY_SHOP_COUNTRY);
				
				/* 取得国家列表、商店所在国家、商店所在国家的省列表 */
				request.setAttribute("countryList", LibCommon.getRegion(IRegion.TYPE_COUNTRY, null, SpringUtil.getRegionManager()));
				request.setAttribute("shopProvinceList", LibCommon.getRegion(
						IRegion.TYPE_PROVINCE, 
						shopCountry, 
						SpringUtil.getRegionManager()));
				
				/* 获得用户所有的收货人信息 */
				List<UserAddressWrapper> consigneeList = LibTransaction.getConsigneeList(userId);
				

				if(consigneeList.size()<1 && getSession().getAttribute(KEY_USER_ID)!=null) {
					// we allow only one address
					/* 如果用户收货人信息的总数小于1 则增加一个新的收货人信息 */
					UserAddressWrapper holder = new UserAddressWrapper(new UserAddress());
//					holder.getUserAddress().setCountry(newCountry);
					String email = (String)getSession().getAttribute(KEY_USER_EMAIL);
					if(email == null) {
						email = "";
					}
					holder.getUserAddress().setEmail(email);
//					holder.getUserAddress().setCountry(shopCountry);
					consigneeList.add(holder);
				}
				
				
				request.setAttribute("consigneeList", consigneeList);
				List<List<RegionWrapper>> provinceList = new ArrayList<List<RegionWrapper>>();
				List<List<RegionWrapper>> cityList = new ArrayList<List<RegionWrapper>>();
				List<List<RegionWrapper>> districtList = new ArrayList<List<RegionWrapper>>();
				 //取得国家列表，如果有收货人列表，取得省市区列表
				for (UserAddressWrapper userAddressWrapper : consigneeList) {
//			    	provinceList.add(LibCommon.getRegion(IRegion.TYPE_PROVINCE,userAddressWrapper.getUserAddress().getCountry(), SpringUtil.getRegionManager()));
//			    	request.setAttribute("provinceList", provinceList.toArray());
//			    	cityList.add(LibCommon.getRegion(IRegion.TYPE_CITY, userAddressWrapper.getUserAddress().getProvince(), SpringUtil.getRegionManager()));
//			    	request.setAttribute("cityList", cityList.toArray());
//			    	districtList.add(LibCommon.getRegion(IRegion.TYPE_DISTRICT,userAddressWrapper.getUserAddress().getCity(), SpringUtil.getRegionManager()));
//			    	request.setAttribute("districtList", districtList.toArray());
//				
			        provinceList.add(LibCommon.getRegion(IRegion.TYPE_PROVINCE, null, SpringUtil.getRegionManager()));
			        request.setAttribute("provinceList", provinceList);			        
			        cityList.add(LibCommon.getRegion(IRegion.TYPE_CITY, null, SpringUtil.getRegionManager()));
			        request.setAttribute("cityList", cityList);
			        districtList.add(LibCommon.getRegion(IRegion.TYPE_DISTRICT, null, SpringUtil.getRegionManager()));
			        request.setAttribute("districtList", districtList);
				}
		    	
		    	
		    	//赋值于模板
		    	request.setAttribute("realGoodsCount", 1);
		    	request.setAttribute("shopCountry", shopCountry);
		    	request.setAttribute("shopProvince", LibCommon.getRegion(IRegion.TYPE_PROVINCE, shopCountry, SpringUtil.getRegionManager()));
		    	request.setAttribute("address", "TODO address");
		    	request.setAttribute("currencyFormat", "TODO currency_format");
		    	request.setAttribute("integralScale", "TODO integral_scale");
		    	request.setAttribute("nameOfRegion", new String[]{"国家", "省", "市", "区"});
				return RES_USER_TRANSACTION;
			}
			else if("act_edit_address".equals(action)) {
                User user = userManager.getUser(userId);
                
				UserAddress ua = new UserAddress();
				String addressId = request.getParameter("address_id");
				boolean isNew = true;
				if(StringUtils.isNotEmpty(addressId)) {
					ua.setId(addressId);
					isNew = false;
				}
//				ua.setCountry(request.getParameter("country"));
//				ua.setProvince(request.getParameter("province"));
//				ua.setCity(request.getParameter("city"));
//				ua.setDistrict(request.getParameter("district"));
				ua.setAddress(request.getParameter("address"));
				ua.setConsignee(request.getParameter("consignee"));
				ua.setEmail(request.getParameter("email"));
				ua.setPhone(request.getParameter("tel"));
				ua.setMobile(request.getParameter("mobile"));
				ua.setBestTime(request.getParameter("best_time"));
				ua.setSignBuilding(request.getParameter("sign_building"));
				ua.setZip(request.getParameter("zipcode"));
				
				try {
					SpringUtil.getUserManager().saveUser(user);
					return LibMain.showMessage(
									Lang.getInstance().getString("editAddressSuccess"), 
									Lang.getInstance().getString("addressListLnk"),
									"user.action?act=address_list", null, null,
									request);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				
			}
			else if("order_detail".equals(action) || "orderDetail".equals(action)) {
				
				includeUserMenu();
				String orderId = request.getParameter("order_id");
			    /* 订单详情 */
				OrderWrapper ow = getOrderDetail(orderId, userId);
				if(ow==null) {
					
				}
				/* 是否显示添加到购物车 */
				if(!"group_buy".equals(ow.getOrder().getExtensionCode())){ 
					request.setAttribute("allowToCart", 1);
				}
				
				/* 订单商品 */
				List<OrderGoodsWrapper> goodsList = LibOrder.orderGoods(orderId);
				for(OrderGoodsWrapper goods : goodsList) {
					goods.put("marketPrice", WebFormatUtils.priceFormat(goods.getOrderGoods().getMarketPrice()));
					goods.put("goodsPrice", WebFormatUtils.priceFormat(goods.getOrderGoods().getGoodsPrice()));
					goods.put("subtotal", WebFormatUtils.priceFormat(
							goods.getOrderGoods().getGoodsPrice() * goods.getOrderGoods().getGoodsNumber()));
				}
				
				/* 订单 支付 配送 状态语言项 */
				String str = ConstantsMappingUtils.getOrderStatus(ow.getOrder().getStatus());
				ow.put("orderStatus", ((Map)Lang.getInstance().get("os")).get(str));
				str = ConstantsMappingUtils.getPayStatus(ow.getOrder().getPayStatus());
				ow.put("payStatus", ((Map)Lang.getInstance().get("ps")).get(str));
				str = ConstantsMappingUtils.getShippingStatus(ow.getOrder().getShippingStatus());
				ow.put("shippingStatus", ((Map)Lang.getInstance().get("ss")).get(str));
				
				request.setAttribute("order", ow);
				request.setAttribute("goodsList", goodsList);
				return RES_USER_TRANSACTION;
				
			}
			else if("logout".equals(action)) {
				logout();
				return LibMain.showMessage(Lang.getInstance().getString("logout"), Lang.getInstance().getString("backHomeLnk"), 
						"home.action", "info", true, request);
			}
			//加入收藏夹
			else if("collect".equals(action)) {
				String goodsId = request.getParameter("id");
				Goods goods = SpringUtil.getGoodsManager().getGoods(goodsId);
				goodsId = goods.getId();
				
				JSONObject res = new JSONObject();
				//判断是否登录
				if(userId == null) {					
					res.put("message", Lang.getInstance().get("loginPlease"));
				}
				
				//检查是否已经存在于用户的收藏夹 
				else {
					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(ICollectGood.USER_ID, Condition.EQUALS, userId));
					criteria.addCondition(new Condition(ICollectGood.GOODS_ID, Condition.EQUALS, goodsId));
					int count = SpringUtil.getCollectGoodsManager().getCollectGoodsCount(criteria);
					if(count > 0) {
						res.put("message", Lang.getInstance().get("collectExisted"));
					}
					else {
						long addTime = new Date().getTime();
						
						CollectGoods collectGood = new CollectGoods();
						collectGood.setUser(userManager.getUser(userId));
						collectGood.setAddTime(new Timestamp(addTime));
						collectGood.setGoods(SpringUtil.getGoodsManager().getGoods(goodsId));
						SpringUtil.getCollectGoodsManager().saveCollectGoods(collectGood);
						res.put("message", Lang.getInstance().get("collectSuccess"));
					}
				}
				String out = res.toString();
				collectGoods = new ByteArrayInputStream(out.getBytes(IWebConstants.ENC));
				return RES_COLLECT;
			}
			
			//收藏夹列表
			else if("collection_list".equals(action)) {
				includeUserMenu();				
				int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				return setCollectionList(page, userId, request);				
			}
			
			//关注
			else if("add_to_attention".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("rec_id");
				setAttention(id, true);
				action = "collection_list";
				return setCollectionList(1, userId, request);				
			}
			
			//取消关注
			else if("del_attention".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("rec_id");
				setAttention(id, false);
				action = "collection_list";
				return setCollectionList(1, userId, request);				
			}
			
			//从收藏夹中删除
			else if("delete_collection".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("collection_id");
				SpringUtil.getCollectGoodsManager().removeCollectGoods(id);
				action = "collection_list";
				return setCollectionList(1, userId, request);
			}
			
			//评论列表
			else if("comment_list".equals(action)) {
				includeUserMenu();	
				int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				return setCommentList(page, userId, request);	
			}
			
			//删除评论
			else if("del_cmt".equals(action)) {
				includeUserMenu();
				String id = request.getParameter("id");
				SpringUtil.getCommentManager().removeComment(id);
				action = "comment_list";
				return setCommentList(1, userId, request);
			}
			
			//合并订单
			else if("merge_order".equals(action)) {
				includeUserMenu();
				
				String fromOrderSn = request.getParameter("from_order");
				Criteria criteria = new Criteria();
				criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, fromOrderSn));
				Order fromOrder = SpringUtil.getOrderManager().getOrderList(criteria).get(0);
				String fromId = fromOrder.getId();
				
				String toOrderSn = request.getParameter("to_order");
				criteria.removeAll();
				criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, toOrderSn));
				Order toOrder = SpringUtil.getOrderManager().getOrderList(criteria).get(0);
				String toId = toOrder.getId();
				
				//合并订单
				Order newOrder = (Order)toOrder.clone();
				long addTime = new Date().getTime();
				newOrder.setAddTime(new Timestamp(addTime));
				newOrder.setId("");
				newOrder.setGoodsAmount(newOrder.getGoodsAmount() + fromOrder.getGoodsAmount());
				
				String shippingCodFee = null;
				if(newOrder.getShipping() != null) {
					// 重新计算配送费用
					Map<String,Object> weightPrice = LibOrder.getWeightPrice(fromOrder.getId(), toOrder.getId());
					UserAddressWrapper consignee = (UserAddressWrapper)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
			    	if(consignee == null) {
			    		 /* 如果不存在，则取得用户的默认收货人信息 */
			    		Criteria c = new Criteria();
			    		c.addCondition(new Condition(IUserAddress.USER, Condition.EQUALS, userId));
			    		List<UserAddress> list = SpringUtil.getUserAddressManager().getUserAddressList(c);
			    		if(list.size()>0) {
			    			consignee = new UserAddressWrapper(list.get(0));
			    		}
			    		else {
		    				consignee = new UserAddressWrapper(new UserAddress());
		    			}
			    	}
			    	List<String> regionIdList = new ArrayList<String>();
//		        	regionIdList.add( (String)consignee.getCountry() );
//		        	regionIdList.add( (String)consignee.getProvince() );
//		        	regionIdList.add( (String)consignee.getCity() );
//		        	regionIdList.add( (String)consignee.getDistrict());
		        	Map<String,Object> shippingInfo = LibOrder.shippingAreaInfo(newOrder.getShipping(), regionIdList);
		        	if(!shippingInfo.isEmpty()){
			        	newOrder.setShippingFee(LibOrder.shippingFee((String)shippingInfo.get("shippingCode"),(String)shippingInfo.get("configure"),(Double)weightPrice.get("weight"),(Double)weightPrice.get("amount")));
			        	if( ( newOrder.getInsureFee() != 0 ) && (Integer.parseInt((String)shippingInfo.get("insure")) > 0 )){
			        		newOrder.setInsureFee(LibOrder.shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingInfo.get("insure")));
		        		}
			        	else{
			        		newOrder.setInsureFee(0.0);
		        		}
			        	if((Boolean)shippingInfo.get("supportCod")){
		        			shippingCodFee = ((Double)shippingInfo.get("payFee")).toString();
		        		}
		        	}
				}
				// 合并余额、已付款金额
				newOrder.setSurplus(newOrder.getSurplus() + fromOrder.getSurplus());
				newOrder.setMoneyPaid(newOrder.getMoneyPaid() + newOrder.getMoneyPaid());
				if(newOrder.getPayment() != null){
					newOrder.setPayFee(LibOrder.payFee(newOrder.getPayment().getId(),newOrder.getGoodsAmount(), shippingCodFee));
		        }
				newOrder.setOrderAmount(newOrder.getGoodsAmount() + newOrder.getShippingFee() + newOrder.getInsureFee() + newOrder.getPayFee()
						- newOrder.getSurplus() - newOrder.getMoneyPaid());
				
				//删除原订单
				SpringUtil.getOrderManager().removeOrder(fromId);
				SpringUtil.getOrderManager().removeOrder(toId);
				
				SpringUtil.getOrderManager().saveOrder(newOrder);
				
				//更新订单商品
				criteria.removeAll();
				criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, fromId));
				List<OrderGoods> orderGoods = SpringUtil.getOrderGoodsManager().getOrderGoodsList(criteria);
				criteria.removeAll();
				criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, toId));
				orderGoods.addAll(SpringUtil.getOrderGoodsManager().getOrderGoodsList(criteria));
				
//				for(OrderGoods goods : orderGoods) {
//					goods.setOrderId(newOrder.getPkId());
//					getDefaultManager().txattach(goods);
//					System.out.println(goods.getOrderId());
//				}
				
				for(OrderGoods goods : orderGoods) {
					OrderGoods g = (OrderGoods) goods.clone();
					g.setOrder(SpringUtil.getOrderManager().getOrder(newOrder.getId()));
					SpringUtil.getOrderGoodsManager().removeOrderGoods(goods.getId());
					SpringUtil.getOrderGoodsManager().saveOrderGoods(g);
					System.out.println(g.getOrder().getId());
				}
				return LibMain.showMessage(Lang.getInstance().getString("mergeOrderSuccess"), Lang.getInstance().getString("orderListLnk"), 
						"user.action?act=order_list", "info", true, request);
			}
			
			//取消订单
			else if("cancel_order".equals(action)) {
				String orderId = request.getParameter("orderId");
				OrderManager manager = SpringUtil.getOrderManager();
				
				/* 查询订单信息，检查状态 */
				Order orderInfo = manager.getOrder(orderId);
				if(orderInfo == null) {
					return LibMain.showMessage(Lang.getInstance().getString("orderExist"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				// 订单状态只能是“未确认”或“已确认”
				if(orderInfo.getStatus() != IOrderInfo.OS_CONFIRMED && orderInfo.getStatus() != IOrderInfo.OS_UNCONFIRMED) {
					return LibMain.showMessage(Lang.getInstance().getString("currentOsNotUnconfirmed"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				//订单一旦确认，不允许用户取消
				if(orderInfo.getStatus() == IOrderInfo.OS_CONFIRMED) {
					return LibMain.showMessage(Lang.getInstance().getString("currentOsAlreadyConfirmed"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				// 发货状态只能是“未发货”
				if(orderInfo.getShippingStatus() != IOrderInfo.SS_UNSHIPPED) {
					return LibMain.showMessage(Lang.getInstance().getString("currentSsNotCancel"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				// 如果付款状态是“已付款”、“付款中”，不允许取消，要取消和商家联系
				if(orderInfo.getPayStatus() != IOrderInfo.PS_UNPAYED) {
					return LibMain.showMessage(Lang.getInstance().getString("currentPsNotCancel"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				//将用户订单设置为取消
				orderInfo.setStatus(IOrderInfo.OS_CANCELED);
				manager.saveOrder(orderInfo);
					/*TODO 退货用户余额 */
					/*TODO 如果使用库存，且下订单时减库存，则增加库存 */
				
				includeUserMenu();
				getOrderList(request, userId);
				action = "orderList";
				return RES_USER_TRANSACTION;
			}
			
			//确认收货
			else if("affirm_received".equals(action)) {
				String orderId = request.getParameter("orderId");
				OrderManager manager = SpringUtil.getOrderManager();
				
				 /* 查询订单信息，检查状态 */
				Order orderInfo = manager.getOrder(orderId);
				if(orderInfo.getStatus() == IOrderInfo.SS_RECEIVED) {
					return LibMain.showMessage(Lang.getInstance().getString("orderAlreadyReceived"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				if(orderInfo.getStatus() != IOrderInfo.SS_SHIPPED) {
					return LibMain.showMessage(Lang.getInstance().getString("orderInvalid"), Lang.getInstance().getString("orderListLnk"), 
							"user.action?act=order_list", "error", true, request);
				}
				
				/* 修改订单发货状态为“确认收货” */
				orderInfo.setShippingStatus(IOrderInfo.SS_RECEIVED);
				manager.saveOrder(orderInfo);
				includeUserMenu();
				getOrderList(request, userId);
				action = "orderList";
				return RES_USER_TRANSACTION;
			}
			
			//将订单中的商品放入购入车
			else if("return_to_cart".equals(action)) {
				JSONObject res = new JSONObject();
				
				String orderId = request.getParameter("order_id");
				Criteria criteria = new Criteria();
				criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, orderId));
				List<OrderGoods> orderGoods = SpringUtil.getOrderGoodsManager().getOrderGoodsList(criteria);
				
				for(OrderGoods orderGood : orderGoods) {
					Goods goods = SpringUtil.getGoodsManager().getGoods(orderGood.getGoods().getId());
					
					// 如果该商品不存在，已删除或下架，处理下一个商品
					if(goods == null || goods.isDeleted() || !goods.isOnSale()) {
						continue;
					}
					//TODO 库存
					
					//获取订单商品的规格
					String orderGoodsAttr = orderGood.getGoodsAttribute();
					String[] attrs = orderGoodsAttr.split("<br>");
					List<String> spec = new ArrayList<String>();
					Set<GoodsAttribute> goodsAttr = goods.getAttributes();
					for(Iterator iterator = goodsAttr.iterator(); iterator.hasNext();) {
						GoodsAttribute attr = (GoodsAttribute)iterator.next();
						for(int i = 0;i < attrs.length;i++) {
							Attribute attribute = SpringUtil.getAttributeManager().getAttribute(attr.getId());
							String attrName = attribute.getName();
							String attrKeyValue = attrName + ":" + attr.getValue();
							if(attrKeyValue.equals(attrs[i]))
								spec.add(attr.getId());
						}
					}
					SpringUtil.getWebManager().addToCart(goods.getId(), 
							orderGood.getGoodsNumber(), spec, request.getSession().getId(), userId, null);
				}
				
				res.put("message", Lang.getInstance().getString("returnToCartSuccess"));
				String out = res.toString();
				setReturnToCart(new ByteArrayInputStream(out.getBytes(IWebConstants.ENC)));
				return RES_RETURN_TO_CART;
			}
			
			else {
				includeUserMenu();
				return RES_USER_CLIPS;
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


	//获得订单列表，取消订单、确认收货后调用此方法进入订单列表页面
	private void getOrderList(HttpServletRequest request, String userId) {
		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		Criteria criteria = new Criteria();
		Condition cond = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
		criteria.addCondition(cond);
		int recordCount = SpringUtil.getOrderManager().getOrderCount(criteria);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("act", action);
		Pager pager = LibMain.getPager("user.action", param, recordCount, page, -1, getCachedShopConfig());
		
		request.setAttribute("pager", pager);
		request.setAttribute("orders", 
				LibTransaction.getUserOrders(userId, pager.getRecordPerPage(), pager.getStart()));
		
		//获得可合并订单
		Map<String,String> mergeOrder = new HashMap<String,String>();
    	
    	//可合并订单的条件，未付款&&未配送&&(未确认||已确认)
    	criteria.removeAll();
    	Condition user = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
    	Condition unPayed = new Condition(IOrderInfo.PAY_STATUS, Condition.EQUALS, "0");
    	Condition unShipped = new Condition(IOrderInfo.SHIPPING_STATUS, Condition.EQUALS, "0");
    	Condition unConfirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "0");
    	Condition confirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "1");
    	criteria.addCondition(user);
    	criteria.addCondition(unPayed);
    	criteria.addCondition(unShipped);
    	criteria.addCondition(confirmed);		    	
    	List<Order> orders = SpringUtil.getOrderManager().getOrderList(criteria);
    	
    	criteria.removeCondition(confirmed);
    	criteria.addCondition(unConfirmed);
    	orders.addAll(SpringUtil.getOrderManager().getOrderList(criteria));
    	
    	for(Iterator iterator = orders.iterator(); iterator.hasNext();) {
    		Order order = (Order) iterator.next();
    		mergeOrder.put(order.getSN(),order.getSN());
    	}
		request.setAttribute("merge", mergeOrder);		
	}

	private String setCollectionList(int page, String userId, HttpServletRequest request) {
		Map<String, Object> cmt = LibMain.assignCollectionList(userId, page, SpringUtil.getCollectGoodsManager(), SpringUtil.getGoodsManager(), getCachedShopConfig());		
		List<CollectGoodsWrapper> goodsList = (List<CollectGoodsWrapper>) cmt.get("goodsList");
		Pager pager = (Pager) cmt.get("pager");
		
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("pager", pager);
		request.setAttribute("userId", userId);
		request.setAttribute("url", "");
		
		return RES_USER_CLIPS;		
	}
	
	private String setCommentList(int page, String userId, HttpServletRequest request) {
		Map<String, Object> cmt = LibMain.assignCommentList(userId, page, SpringUtil.getCommentManager(), SpringUtil.getGoodsManager(), SpringUtil.getArticleManager(), getCachedShopConfig());		
		List<CommentWrapper> commentList = (List<CommentWrapper>) cmt.get("commentList");
		Pager pager = (Pager) cmt.get("pager");
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("pager", pager);
		
		return RES_USER_CLIPS;		
	}

	//设置关注状态
	private void setAttention(String id, boolean state) {
		CollectGoods collectGood = SpringUtil.getCollectGoodsManager().getCollectGoods(id);
		collectGood.setAttention(state);
		SpringUtil.getCollectGoodsManager().saveCollectGoods(collectGood);		
	}

	/**
	 *  获取指订单的详情
	 *
	 * @access  public
	 * @param   int         $order_id       订单ID
	 * @param   int         $user_id        用户ID
	 *
	 * @return   arr        $order          订单所有信息的数组
	 */
	public OrderWrapper getOrderDetail(String orderId, String userId) {
		OrderWrapper ow = LibOrder.orderInfo(orderId, null);
		if(ow==null) {
			
		}
		
		//检查订单是否属于该用户
		if(userId!=null && !userId.equals(ow.getOrder().getUser().getId())) {
			
		}
		
		// TODO /* 对发货号处理 */
		 
		/* 只有未确认才允许用户修改订单地址 */
		if(ow.getOrder().getStatus() == IOrderInfo.OS_UNCONFIRMED) {
			ow.put("allowUpdateAddress", 1);
		}else {
			ow.put("allowUpdateAddress", 0);
		}
		
		// TODO /* 获取订单中实体商品数量 */
		ow.put("existRealGoods", true);
		
		/* 如果是未付款状态，生成支付按钮 */
		if(ow.getOrder().getPayStatus() == IOrderInfo.PS_UNPAYED && 
				( ow.getOrder().getStatus() == IOrderInfo.OS_UNCONFIRMED || 
					ow.getOrder().getStatus() == IOrderInfo.OS_CONFIRMED) ) {
			
	        /*
	         * 在线支付按钮
	         */
			Payment paymentInfo = LibOrder.paymentInfo( ow.getOrder().getPayment().getId());

	        //无效支付方式
	        if (paymentInfo == null)
	        {
	        	ow.put("payOnline", "");
	        }
	        else
	        {
	            //取得支付信息，生成支付代码
	            if(ow.getOrder().getOrderAmount()>0) {
            		String payOnline = getPaymentMetaManager().getCode(ow.getOrder().getId(), paymentInfo.getId());
            		debug("payOnline: "+payOnline);
            		ow.put("payDesc", paymentInfo.getDescription());
            		ow.put("payOnline", payOnline);
	            	
	            }
	        }
			
			
		}
		else {
			ow.put("payOnline", "");
		}
		String payId = ow.getOrder().getPayment().getId();
    	Payment payment = SpringUtil.getPaymentManager().getPayment(payId);
    	ow.put("payDesc", payment.getDescription());
		return ow;
	}
	
	public void includeUserMenu() {
		Affiliate a = new Affiliate();
		a.setOn(0);
		getRequest().setAttribute("affiliate", a);
	}
	
	public User getUser(String username) {
		Criteria c = new Criteria();
		Condition cond = new Condition();
		cond.setField(IUser.NAME);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(username);
		c.addCondition(cond);
		
		List<User> res = userManager.getUserList(c);
		User user = res.size()>0 ? res.get(0) : null;
		
		return user;
	}
	
	public UserWrapper getUserDefault(String userId) {
		User user = userManager.getUser(userId);
		return new UserWrapper(user);
	}
	
    /**
     *  检查指定用户是否存在及密码是否正确
     *
     * @access  public
     * @param   string  $username   用户名
     *
     * @return  int
     */
	public boolean checkUser(String username, String password) {
		
		User user = getUser(username);
		if(user == null) {
			return false;
		}
		
		if(password!=null) {
			return password.equals(user.getPassword());
		}
		else {
			return true;
		}
		
	}
	public boolean checkEmail(String email) {
		
		if(email != null) {
			Criteria c = new Criteria();
			Condition cond = new Condition();
			cond.setField(IUser.EMAIL);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(email);
			c.addCondition(cond);
			
			List<User> res = userManager.getUserList(c);
			return res.size() > 0 ? true : false;
		}
		
		
		return false;

		
	}
	private boolean login(String username, String password) {
		if(checkUser(username, password)) {
			setSession(username);
			return true;
		}
		else {
			return false;
		}
	}
	private void logout() {
		//清空该用户的购物车
		String sessionId = getSession().getId();
		Condition condition = new Condition(ICart.SESSION,Condition.EQUALS,sessionId);
		Criteria criteria = new Criteria();
		criteria.addCondition(condition);
		List<Cart> carts = SpringUtil.getCartManager().getCartList(criteria);
		SpringUtil.getCartManager().removeCarts(carts);
		getSession().invalidate();
		
	}
	private void setSession(String username) {
		if(StringUtils.isEmpty(username)) {
			// destroy session
			getSession().setAttribute(KEY_USER_ID, null);
		}
		else {
			User user = getUser(username);
			if(user!=null) {
				//修改，放入wrapper,member_info.ftl 调用getUsername()方法
				getSession().setAttribute("userInfo", new UserWrapper(user));//modifyed
				getSession().setAttribute(KEY_USER_ID, user.getId());
				getSession().setAttribute(KEY_USER_NAME, username);
				getSession().setAttribute(KEY_USER_EMAIL, user.getEmail());
			}
		}
		
	}
	
	public String register(String username, String password, String email, Map<String, String> other) {
		User user = new User();
		//记录上次登录时间为注册时间
		user.setLastTime(new Timestamp(new Date().getTime()));
		user.setName(username);
		user.setPassword(password);
		user.setEmail(email);
		int length = 0 ;
		if(other != null){
			length = other.size();
		}
		 
		// TODO wrap it and return error code
		String error = null;
		try {
			SpringUtil.getUserManager().saveUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
			if(StringUtils.isEmpty(error)) {
				error = "UNknown reason";
			}
		}
		
		if(error==null) {
			user = getUser(username);//modified
			getSession().setAttribute(KEY_USER_ID, user.getId());
			getSession().setAttribute(KEY_USER_NAME, user.getName());
			getSession().setAttribute(KEY_USER_EMAIL, user.getEmail());
			// TODO update user's "other" properties 
			
		}
		return error;
		
		
		
	}

	public String getUsername() {
		return username == null? "" : username.trim();
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password == null? "" : password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email == null? "":email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Map<String, String> getOther() {
		return other;
	}


	public void setOther(Map<String, String> other) {
		this.other = other;
	}

	public InputStream getIsRegistered() {
		return isRegistered;
	}
	public InputStream getIsUsed() {
		return isUsed;
	}
	
	public String toString() {
		// this is a tricky hacking
		// in freemarker the action variable points to the Action class
		if (action == null) {
		    return WebFormatUtils.phpVarFromat("default");
		}
		return WebFormatUtils.phpVarFromat(action);
	}

	//查找userRank
	public UserRank getUserRank(UserWrapper uw,HttpServletRequest request) {
		//查找用户rank
		String userRankId = uw.getUserRank()+"";
		long rankPoints = uw.getRankPoints();
		Criteria criteria = new Criteria();
		
		//当用户的rankId为0时，根据rankPoints查找，否则根据rankId查找 
		if(!userRankId.equals("0")) {
			Condition condition = new Condition(IUserRank.ID,Condition.EQUALS,userRankId);
			criteria.addCondition(condition);
		}
		List<UserRank> userRanks = SpringUtil.getUserRankManager().getUserRankList(criteria);
		UserRank rank = null;
		for(Iterator iterator = userRanks.iterator();iterator.hasNext();) {
			UserRank userRank = (UserRank)iterator.next();
			//rankId为0时,根据rankPoints判断rank
			if(userRankId.equals("0")) {
				//当rank的maxPoints为0时，说明是代销用户，代销用户只能根据rankId判断
				if(userRank.getMaxPoints() > 0 && rankPoints >= userRank.getMinPoints() && rankPoints <= userRank.getMaxPoints()) {
					rank = userRank;
					request.setAttribute("rankName", "您的等级是" + userRank.getName());
					break;
				}
			}
			//否则，根据rankId判断
			else {
				rank = userRank;
				request.setAttribute("rankName", "您的等级是" + userRank.getName());
			}
		}
		
		return rank;
	}
	
	//根据userRank查找nextRank
	public void getNextRank(UserWrapper uw, UserRank userRank, HttpServletRequest request) {
		
		UserRank nextRank = null;
		//首先查询有没有最低积分等于该用户所属rank的最高积分的rank，如果有则是nextRank
		Condition condition = new Condition(IUserRank.MINPOINTS,Condition.EQUALS,userRank.getMaxPoints()+"");
		Criteria criteria = new Criteria();
		criteria.addCondition(condition);
		List<UserRank> nextUserRanks = SpringUtil.getUserRankManager().getUserRankList(criteria);
		
		//存在相等
		if(nextUserRanks.size() > 0) {
			nextRank = nextUserRanks.get(0);
		}
		
		//不存在，查找出所有最低积分高于该用户所属rank的最高积分的rank，从中选出最低积分最低的那个作为nextRank
		else {
			condition.setOperator(Condition.GREATERTHAN);
			criteria.removeAll();
			criteria.addCondition(condition);
			nextUserRanks = SpringUtil.getUserRankManager().getUserRankList(criteria);

			long minPoints = -1;
			for(Iterator iterator = nextUserRanks.iterator();iterator.hasNext();) {
				UserRank rank = (UserRank)iterator.next();
				if(minPoints == -1) {
					minPoints = rank.getMinPoints();
				}
				if(rank.getMinPoints() <= minPoints) {
					nextRank = rank;
					minPoints = rank.getMinPoints();
				}
			}
		}		
		
		if(nextRank != null) {
			long points = nextRank.getMinPoints() - uw.getRankPoints(); //距离升级差的积分
			request.setAttribute("nextRankName", "您还差" +points + "积分达到" + nextRank.getName());
		}
	}

	public void setCollectGoods(InputStream collectGoods) {
		this.collectGoods = collectGoods;
	}

	public InputStream getCollectGoods() {
		return collectGoods;
	}

	public void setReturnToCart(InputStream returnToCart) {
		this.returnToCart = returnToCart;
	}

	public InputStream getReturnToCart() {
		return returnToCart;
	}
	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getComfirm_password() {
		return comfirm_password;
	}

	public void setComfirm_password(String comfirm_password) {
		this.comfirm_password = comfirm_password;
	}
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
