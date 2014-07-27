package com.jcommerce.web.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.client.Random;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.service.CartManager;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.SessionManager;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.core.wrapper.ShopConfigWrapper;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.to.Lang;

public class LibInsert {
	/**
	 * 调用评论信息
	 *
	 * @access  public
	 * @return  string
	 */
	public static void insertComments(int type, String id, HttpServletRequest request, ShopConfigWrapper scw) {
		//TODO
		/* 验证码相关设置 */
//		ShopConfigWrapper.getDefaultConfig().get("captcha")
		
		if(false) {
			
			request.setAttribute("enabledCaptcha", true);
			request.setAttribute("rand", Random.nextInt());
		}
		else {
			request.setAttribute("enabledCaptcha", false);
		}
		
		request.setAttribute("username", request.getSession().getAttribute(IWebConstants.KEY_USER_NAME));
		request.setAttribute("email", request.getSession().getAttribute(IWebConstants.KEY_USER_EMAIL));
		request.setAttribute("commentType", type);
		request.setAttribute("id", id);
		Map<String, Object> cmt = LibMain.assignComment(id, type, 1, SpringUtil.getCommentManager(), scw);
		request.setAttribute("comments", cmt.get("comments"));
		request.setAttribute("pager", cmt.get("pager"));
		
		return;
	}
	
	/**
	 * 调用购物车信息
	 *
	 * @access  public
	 * @return  string
	 */
	public static String insertCartInfo(CartManager manager, HttpServletRequest request) {
		String res = "";
		long number = 0;
		double amount = 0;
		Criteria c = new Criteria();
		c.addCondition(new Condition(ICart.SESSION, Condition.EQUALS, request.getSession().getId()));
		c.addCondition(new Condition(ICart.REC_TYPE, Condition.EQUALS, ""+Constants.CART_GENERAL_GOODS));
		List<Cart> carts = manager.getCartList(c);
		for(Cart cart : carts) {
			number += cart.getGoodsNumber();
			amount += cart.getGoodsNumber()*cart.getGoodsPrice();
		}
		
		res = new PrintfFormat(Lang.getInstance().getString("cartInfo")).sprintf(new Object[]{number, WebFormatUtils.priceFormat(amount)});
		res = new StringBuffer("<a href=\"flow.action\" title=\"")
								.append(Lang.getInstance().getString("viewCart"))
								.append("\">").append(res).append("</a>").toString();
		return res;
		
		
	}
	/**
	 * 获得查询时间
	 *
	 * @access  public
	 * @return  string
	 */
	public void insertQueryTime(SessionManager manager,Long queryStartTime,HttpServletRequest request){
		Long queryEndTime = System.currentTimeMillis();
		Lang lang = Lang.getInstance();
		String queryInfo = new PrintfFormat(lang.getString("queryInfo")).sprintf((queryEndTime-queryStartTime)/1000.0);
		
		int onlineCount = manager.getSessionCount(null);
		String onlineInfo = new PrintfFormat(lang.getString("onlineInfo")).sprintf(onlineCount+"");
		request.setAttribute("queryInfo",queryInfo+onlineInfo);
	}
}
