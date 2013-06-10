package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.OrderWrapper;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibTransaction {
	
	public static UserWrapper getProfile(String userId) {
		User user = SpringUtil.getUserManager().getUser(userId);
		UserWrapper uw = new UserWrapper(user);
		
		return uw;
	}
	
	public static List<OrderWrapper> getUserOrders(String userId, int size, int start) {
		
		Criteria c = new Criteria();
		Condition cond = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
		c.addCondition(cond);
		
		List<Order> list = SpringUtil.getOrderManager().getOrderList(start, size, c);
		
		List<OrderWrapper> wrapperList = new ArrayList<OrderWrapper>();
		for(Order orderInfo : list) {
			OrderWrapper ow = new OrderWrapper(orderInfo);
			/* 订单 支付 配送 状态语言项 */
			String str = ConstantsMappingUtils.getOrderStatus(ow.getOrder().getStatus());
			String orderStatus = (String) ((Map)Lang.getInstance().get("os")).get(str);
			str = ConstantsMappingUtils.getPayStatus(ow.getOrder().getPayStatus());
			String payStatus = (String) ((Map)Lang.getInstance().get("ps")).get(str);
			str = ConstantsMappingUtils.getShippingStatus(ow.getOrder().getShippingStatus());
			String shippingStatus = (String) ((Map)Lang.getInstance().get("ss")).get(str);
			
			ow.put("orderStatus", orderStatus + "," + payStatus + "," + shippingStatus);
			wrapperList.add(ow);
		}
		
		return wrapperList;
	}
	
	
	public static List<UserAddressWrapper> getConsigneeList(String userId) {
	    
		Criteria c = new Criteria();
		Condition cond = new Condition(IUserAddress.USER, Condition.EQUALS, userId);
		c.addCondition(cond);
		
		List<UserAddress> list = SpringUtil.getUserAddressManager().getUserAddressList(0 ,5, c);
		
		return WrapperUtil.wrap(list, UserAddressWrapper.class);
	}
	
	/**
	 * 保存用户的收货人信息
	 * 如果收货人信息中的 id 为 0 则新增一个收货人信息
	 *
	 * @access  public
	 * @param   array   $consignee
	 * @param   boolean $default        是否将该收货人信息设置为默认收货人信息
	 * @return  boolean
	 */
	public static boolean saveConsignee(UserAddressWrapper consignee , User user, boolean isDefault){
		/* 修改地址 */
		UserAddress ua = consignee.getUserAddress();
		user.addAddress(ua);
		
        Set<UserAddress> addresses = user.getAddresses();        
        if (isDefault) {
            for (UserAddress a : addresses) {
                ua.setDefaultAddress(a == ua);
            }
        }
//        String uaId = ua.getId();
//		if(StringUtils.isEmpty(uaId)) {
//			SpringUtil.getUserAddressManager().saveUserAddress(ua);
//			uaId = ua.getId();
//		} else {
//		    SpringUtil.getUserAddressManager().saveUserAddress(ua);
//		}
//		
//		
		SpringUtil.getUserManager().saveUser(user);
		return true;
	}
}
