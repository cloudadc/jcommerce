package com.jcommerce.web.to;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.User;
import com.jcommerce.web.util.WebFormatUtils;

public class UserWrapper extends BaseModelWrapper {
	User user;
	List<Order> orderList;
	
	@Override
	protected Object getWrapped() {
		return getUser();
	}
	public UserWrapper(ModelObject user) {
		super();
		this.user = (User)user;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getFormatedCreditLine() {
		return WebFormatUtils.priceFormat(getUser().getCreditLine());
	}
	public String getUsername() {
		return getUser().getName();
	}
	
//	/*修改，获得商店名称*/
//	public String getShopName() {
//		String shopName = null;
//		
//		Condition codition = new Condition(IShopConfig.CODE,Condition.EQUALS,"shop_name");
//        Criteria criteria = new Criteria();
//        criteria.addCondition(codition);
//        List<ShopConfig> shopConfigs = manager.getList(ModelNames.SHOPCONFIG, criteria);
//        for(Iterator iterator = shopConfigs.iterator();iterator.hasNext();) {
//        	ShopConfig shopConfig = (ShopConfig)iterator.next();
//        	shopName = shopConfig.getValue();
//        }
//		return shopName;
//	}
//	/*修改完*/
	
	/*修改，获得上次登录时间*/
//	public String getLastTime() {
//		//获得上次登录时间
//		Date lastTime = getUser().getLastTime() == null ? new Date() : getUser().getLastTime();//如果是第一次登录，上次登录时间为本次登录时间 
//		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//		String lastTimeStr = formatter.format(lastTime);
//
//	    //记录本次登录时间
//		Date date = new Date();
//		System.out.println(date.getTimezoneOffset());
//		Calendar c = Calendar.getInstance();
//		System.out.println(c.getTimeZone());
//		System.out.println(c.getTime());
//		String text = formatter.format(date);
//		System.out.println(TimeZone.getDefault().getDisplayName());
//		getUser().setLastTime(date);
//		manager.txattach(getUser());
//		return lastTimeStr;
//	}
//	/*修改完*/
	
	/*修改，获得最近30天的订单数*/
	public int getOrderCount() {
		int orderNumber = 0;	
		List<Order> orderInfos = getOrder();
		Date date = new Date();
		long nowTime = date.getTime();
		
		for(Iterator iterator = orderInfos.iterator();iterator.hasNext();) {
			Order orderInfo = (Order)iterator.next();
			long orderAddTime = orderInfo.getAddTime().getTime();
			//判断订单是否在30天以内
			if((nowTime - orderAddTime) < 2592000000L) {
				orderNumber++;
			}
		}
		return orderNumber;
	}
	/*修改完*/
	
	public List<OrderWrapper> getShippedOrder() {
		List<Order> orderInfos = getOrder();
		List<OrderWrapper> shippedOrder = null;
		for(Iterator iterator = orderInfos.iterator();iterator.hasNext();) {
			Order orderInfo = (Order)iterator.next();
			if(orderInfo.getShippingStatus() == 1) {
				if(shippedOrder == null) {
					shippedOrder = new ArrayList<OrderWrapper>();
				}
				OrderWrapper orderInfoWrapper = new OrderWrapper(orderInfo);
				shippedOrder.add(orderInfoWrapper);
			}
		}
		return shippedOrder;
	}
	//获得所有订单
	public List<Order> getOrder() {
		return orderList;
	}
	public void setOrder(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	public String getSurplus() {
		return WebFormatUtils.priceFormat(getUser().getMoney());
	}
	
	public String getMsn() {
		return getUser().getMSN() == null ? "" : getUser().getMSN();
	}
	public String getQq() {
		return getUser().getQQ() == null ? "" : getUser().getQQ();
	}
	public String getOfficePhone() {
		return getUser().getOfficePhone() == null ? "" : getUser().getOfficePhone();
	}
	public String getHomePhone() {
		return getUser().getHomePhone() == null ? "" : getUser().getHomePhone();
	}
	public String getMobilePhone() {
		return getUser().getMobilePhone() == null ? "" : getUser().getMobilePhone();
	}
	public String getUserRank() {
		return getUser().getRank();
	}
	public int getRankPoints() {
		return getUser().getRankPoints();
	}
//	public Long getIntegral() {
//		return getUser().getPayPoints();
//	}
	public String getBirthday(){
		if(getUser().getBirthday() == null){
			return "";
		}
		else{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(getUser().getBirthday());
		}
	}
}
