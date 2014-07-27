package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.service.shipping.impl.BaseShippingMetaPlugin;
import com.jcommerce.core.service.shipping.impl.EMS;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.core.wrapper.ShopConfigWrapper;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.front.action.FlowAction;
import com.jcommerce.web.to.CartWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.OrderGoodsWrapper;
import com.jcommerce.web.to.OrderWrapper;
import com.jcommerce.web.to.Total;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibOrder {
	
	/**
	 * 取得订单信息
	 * @param   int     $order_id   订单id（如果order_id > 0 就按id查，否则按sn查）
	 * @param   string  $order_sn   订单号
	 * @return  array   订单信息（金额都有相应格式化的字段，前缀是formated_）
	 */
	public static OrderWrapper orderInfo(String orderId, String orderSn) {
		Order order = SpringUtil.getOrderManager().getOrder(orderId);
		return new OrderWrapper(order);
	}

	/**
	 * 取得订单商品
	 * @param   int     $order_id   订单id
	 * @return  array   订单商品数组
	 */
	public static List<OrderGoodsWrapper> orderGoods(String orderId) {
		Criteria c = new Criteria();
		Condition cond = new Condition(IOrderGoods.ORDER, Condition.EQUALS, orderId);
		c.addCondition(cond);
		List<OrderGoods> list = SpringUtil.getOrderGoodsManager().getOrderGoodsList(c);
		List<OrderGoodsWrapper> res = WrapperUtil.wrap(list, OrderGoodsWrapper.class);
		
		return res;
		
		
		
	}
	
    public static Total orderFee(Order order, List<Cart> carts, UserAddressWrapper consignee, HttpSession session) {
    	// refer to lib_order.php order_fee()
    	/* 初始化订单的扩展code */
    	if( null == order.getExtensionCode()){
    		order.setExtensionCode("");
    	}
    	
    	Total total = new Total();
    	
    	
    	
    	for(Cart cart:carts) {
    		//TODO goods有了isreal属性之后去掉本段代码
    		total.setRealGoodsCount(1);
//    		if(cart.getIsReal()){
//    			total.setRealGoodsCount(total.getRealGoodsCount()+1);
//    		}
    		total.setGoodsPrice(total.getGoodsPrice() + cart.getGoodsPrice() * cart.getGoodsNumber() );
    		total.setMarketPrice(total.getMarketPrice() + cart.getMarketPrice() * cart.getGoodsNumber() );
    	}
    	total.setSaving(total.getMarketPrice() - total.getGoodsPrice());
    	total.setSaveRate(( total.getMarketPrice() != 0.0 ) ? Math.round( total.getSaving() * 100 / total.getMarketPrice()) / 100.0 : 0.0);
    	
    	
    	// TODO 折扣  税额 包装费用  贺卡费用 红包 线下红包 
    	String shippingCodFee = null;
        if( order.getShipping() != null && (total.getRealGoodsCount() > 0 )){
        	List<String> regionIdList = new ArrayList<String>();
//        	regionIdList.add( (String)consignee.getCountry() );
//        	regionIdList.add( (String)consignee.getProvince() );
//        	regionIdList.add( (String)consignee.getCity() );
//        	regionIdList.add( (String)consignee.getDistrict());
        	Map<String,Object> shippingInfo = shippingAreaInfo(order.getShipping(),regionIdList);
        	
        	if(!shippingInfo.isEmpty()){
        		Map<String,Object> weightPrice = cartWeightPrice(Constants.CART_GENERAL_GOODS , session.getId());
        		total.setShippingFee(shippingFee((String)shippingInfo.get("shippingCode"),(String)shippingInfo.get("configure"),(Double)weightPrice.get("weight"),(Double)weightPrice.get("amount")));
        		if( ( order.getInsureFee() != 0 ) && (Integer.parseInt((String)shippingInfo.get("insure")) > 0 )){
        			total.setShippingInsure(shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingInfo.get("insure")));
        		}
        		else{
        			total.setShippingInsure(0.0);
        		}
        		
        		if((Boolean)shippingInfo.get("supportCod")){
        			shippingCodFee = ((Double)shippingInfo.get("payFee")).toString();
        		}
        	}
        }
                
        // TODO 红包
        /* 计算订单总额 */
        total.setAmount( total.getGoodsPrice() + total.getShippingFee() + total.getShippingInsure() );
        
        /* 余额 */
        order.setSurplus( ( order.getSurplus() > 0 ) ? order.getSurplus() : 0 );
        if( total.getAmount() > 0 ){
        	if( order.getSurplus() > total.getAmount() ){
        		order.setSurplus( total.getAmount() );
        		total.setAmount(0.0);
        	}
        	else{
        		total.setAmount( total.getAmount() - order.getSurplus() );
        		
        	}
        }
        else{
        	order.setSurplus(0.0);
        	total.setAmount(0.0);
        }
        
        total.setSurplus( order.getSurplus() );
        total.setIntegral(order.getIntegral());
        
        /* 保存订单信息 */
        session.setAttribute("flowOrder", order);
        
        if( order.getPayment() != null && total.getRealGoodsCount() > 0 ){
        	total.setPayFee(payFee(order.getPayment().getId(),total.getAmount(),shippingCodFee));
        }
                
        total.setAmount(total.getAmount()+total.getPayFee());
        
               
    	return total;
    }
    
    public static Map<String, Object> getCartGoods(String sessionId, ShopConfigWrapper scw) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<CartWrapper> goodsList = new ArrayList<CartWrapper>();
    	Total total = new Total();
    	
    	Criteria c = new Criteria();
    	Condition cond = new Condition(ICart.SESSION, Condition.EQUALS, sessionId);
    	c.addCondition(cond);
    	List<Cart> carts = SpringUtil.getCartManager().getCartList(c);
    	int virtualGoodsCount = 0;
    	int realGoodsCount = 0;
    	
    	
    	
    	for(Cart cart: carts) {
    		CartWrapper cw = new CartWrapper(cart);
    		
    		String id = cw.getCart().getGoods().getId();
    		Goods good = SpringUtil.getGoodsManager().getGoods(id);
    		
    		total.setGoodsPrice(total.getGoodsPrice() + cart.getGoodsPrice() * cart.getGoodsNumber());
    		total.setMarketPrice(total.getMarketPrice() + cart.getMarketPrice() * cart.getGoodsNumber());
    		//设置节省的钱及比例
    		total.setSaving(total.getSaving() + cart.getMarketPrice() * cart.getGoodsNumber() - cart.getGoodsPrice() * cart.getGoodsNumber());
    		total.setSaveRate(( total.getMarketPrice() != 0.0 ) ? Math.round( total.getSaving() * 100 / total.getMarketPrice()) / 100.0 : 0.0);
    		
    		if(cart.isRealGoods()) {
    			realGoodsCount++;
    		}else {
    			virtualGoodsCount++;
    		}
    		int showGoodsInCart = scw.getInt(IShopConfigMeta.CFG_KEY_SHOW_GOODS_IN_CART);
    		if(showGoodsInCart == 2 || showGoodsInCart ==3) {
    			String goodsId = cart.getGoods().getId();
    			Goods goods = SpringUtil.getGoodsManager().getGoods(goodsId);
    			cw.put("goodsThumb", new GoodsWrapper(goods).getThumb()); 
    		}
    		goodsList.add(cw);
    		cw.put("subtotal", WebFormatUtils.priceFormat(cart.getGoodsPrice() * cart.getGoodsNumber()));
    		cw.put("goodsPrice", WebFormatUtils.priceFormat(cart.getGoodsPrice()));
    		cw.put("marketPrice", WebFormatUtils.priceFormat(cart.getMarketPrice()));
    	}

    	map.put("goodsList", goodsList);
    	map.put("total", total);
    	
    	return map;
    }
    
    /**
     * 检查收货人信息是否完整
     * @param   array   consignee  收货人信息
     * @param   int     flow_type  购物流程类型
     * @return  boolean    true 完整 false 不完整
     */    
    public static boolean checkConsigneeInfo(UserAddressWrapper consignee, int flowType, String sessionId) {
    	if (existRealGoods( 0 , flowType , sessionId ))
        {
            /* 如果存在实体商品 */
//            boolean res = (consignee.getConsignee() != "") &&
//               				(consignee.getCountry() != "") &&
//               				(consignee.getEmail() != "") &&
//               				(consignee.getPhone() != "");
//            if (res){
//                if ( null == (consignee.getProvince())){
//                    /* 没有设置省份，检查当前国家下面有没有设置省份 */
//                    List<RegionWrapper> pro = LibCommon.getRegion(IRegion.TYPE_PROVINCE, consignee.getCountry(), SpringUtil.getRegionManager());
//                    res = ( 0 == pro.size() );
//                }
//                else if (null == consignee.getCity()){
//                    /* 没有设置城市，检查当前省下面有没有城市 */
//                	List<RegionWrapper> city = LibCommon.getRegion(IRegion.TYPE_CITY, consignee.getProvince(), SpringUtil.getRegionManager());
//                    res = ( 0 == city.size() );
//                }
//                else if (null == consignee.getDistrict())
//                {
//                	List<RegionWrapper> dist = LibCommon.getRegion(IRegion.TYPE_DISTRICT, consignee.getCity(), SpringUtil.getRegionManager());
//                    res = ( 0 == dist.size() );
//                }
//            }

           // return res;
    	    return true;
        }
        else
        {
            /* 如果不存在实体商品 */
            return (consignee.getConsignee() != "") &&
   				(consignee.getEmail() != "") &&
   				(consignee.getPhone() != "");
        }
    	
    }
    
    
    /**
     * 查询购物车（订单id为0）或订单中是否有实体商品
     * @param   int     order_id   订单id
     * @param   int     flow_type  购物流程类型
     * @return  boolean
     */    
    public static boolean existRealGoods(int orderId , int flowType , String sessionId){
    	int num = 0;
    	if( orderId <= 0 ){
    		Criteria criteria = new Criteria();
    		Condition condition = new Condition();
    		
    		condition.setField(ICart.SESSION);
    		condition.setOperator(Condition.EQUALS);
    		condition.setValue(sessionId);
    		
    		Condition condition2 = new Condition();
    		condition2.setField(ICart.IS_REAL);
    		condition2.setOperator(Condition.EQUALS);
    		condition2.setValue("true");
    		
    		Condition condition3 = new Condition();
    		condition3.setField(ICart.REC_TYPE);
    		condition3.setOperator(Condition.EQUALS);
    		condition3.setValue(flowType+"");
    		
    		
    		criteria.addCondition(condition);
//    		criteria.addCondition(condition2);
    		criteria.addCondition(condition3);
    		num = SpringUtil.getCartManager().getCartCount(criteria);
    	}
    	else{
    		Criteria criteria = new Criteria();
    		Condition condition = new Condition();
    		
    		condition.setField(IOrderGoods.ORDER);
    		condition.setOperator(Condition.EQUALS);
    		condition.setValue(orderId+"");
    		
    		Condition condition2 = new Condition();
    		condition2.setField(ICart.IS_REAL);
    		condition2.setOperator(Condition.EQUALS);
    		condition2.setValue("true");
    		
    		criteria.addCondition(condition);
//    		criteria.addCondition(condition2);
    		num = SpringUtil.getOrderGoodsManager().getOrderGoodsCount(criteria);
    	}
    	return num > 0;
    }
    
    /**
     * 获得订单信息
     *
     * @access  private
     * @return  array
     */
    public static Order flowOrderInfo(HttpSession session){
    	Order order = (Order)session.getAttribute(FlowAction.KEY_FLOW_ORDER);
    	if(order == null) {
    		order = new Order();
    		
//    		getSession().setAttribute(KEY_FLOW_ORDER, order);
    	}
    	/* 初始化配送和支付方式 */
    	if( (order.getShipping() == null) || (order.getPayment() ==  null ) ) {
    		/* 如果还没有设置配送和支付 */
    		if(session.getAttribute(FlowAction.KEY_USER_ID)!=null) {
    			/* 用户已经登录了，则获得上次使用的配送和支付 */
    			Map<String,String> map = lastShippingAndPayment(session);
    			if( null == order.getShipping().getId()){
    				order.setShipping(SpringUtil.getShippingManager().getShipping(map.get("shippingId")));
    			}
    			if( null == order.getPayment()){
    				order.setPayment(SpringUtil.getPaymentManager().getPayment(map.get("payId")));
    			}
    		}
    		else{
    			if( null == order.getShipping()){
    				order.setShipping(null);
    			}
    			if( null == order.getPayment()){
    				order.setPayment(null);
    			}
    		}
    		
//    		if (null == order.getPack())
//    	    {
//    			order.setPack(null);  // 初始化包装
//    	    }
//    	    if (null == order.getCardId())
//    	    {
//    	    	order.setCardId("");  // 初始化贺卡
//    	    }
//    	    if (null == order.getBonus())
//    	    {
//    	    	order.setBonus(0.0);    // 初始化红包
//    	    }
//    	    if (null == order.getIntegral())
//    	    {
//    	    	order.setIntegral(0L); // 初始化积分
//    	    }
//    	    if (null == order.getSurplus())
//    	    {
//    	    	order.setSurplus(0.0);  // 初始化余额
//    	    }

    	    /* 扩展信息 */
    	    if ((null != session.getAttribute("flowType")) && (((Number)session.getAttribute("flowType")).intValue() != Constants.CART_GENERAL_GOODS))
    	    {
    	        order.setExtensionCode((String)session.getAttribute("extensionCode"));
    	        order.setExtensionId(new Integer((String)session.getAttribute("extensionId")));
    	    }
    		
    	}

    	return order;
    }
    
    /**
     * 获得上一次用户采用的支付和配送方式
     *
     * @access  public
     * @return  shippingId and payId
     */
    public static Map<String,String> lastShippingAndPayment(HttpSession session){
    	Criteria criteria = new Criteria();
		Condition condition = new Condition();
		
		condition.setField(IOrderInfo.USER_ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue((String)session.getAttribute("userId"));
		
//		Order order = new Order();
//		order.setField(IOrderInfo.ORDER_ID);
//		order.setAscend(Order.DESCEND);
		
		
		criteria.addCondition(condition);
//		criteria.addOrder(order);
		List<Order> list = SpringUtil.getOrderManager().getOrderList(0, 1, criteria);
    	
		Map<String,String> map = new HashMap<String,String>();
		if(0 == list.size()){
			/* 如果获得是一个空数组，则返回默认值 */
			map.put("shippingId", "");
			map.put("payId", "");			
		}
		else
		{
			map.put("shippingId", ((Order)list.get(0)).getShipping().getId());
			map.put("payId", ((Order)list.get(0)).getPayment().getId());	
		}
		
    	return map;
    }
    
    /**
     * 取得某配送方式对应于某收货地址的区域信息
     * @param   int     shippingId        配送方式id
     * @param   array   regionIdList     收货人地区id数组
     * @return  array   配送区域信息（config 对应着反序列化的 configure）
     */
    public static Map<String,Object> shippingAreaInfo(Shipping shipping, List<String> regionIdList){
    	Map<String,Object> shippingAreaInfo = new HashMap<String,Object>();
//		Criteria criteria = new Criteria();
//		Condition condition = new Condition();		
//
//		condition.setField(IShipping.ID);
//		condition.setOperator(Condition.EQUALS);
//		condition.setValue(shipping.getId());
//		
//		Condition condition2 = new Condition();		
//
//		condition2.setField(IShipping.ENABLED);
//		condition2.setOperator(Condition.EQUALS);
//		condition2.setValue("true");
		
//		criteria.addCondition(condition);
//		criteria.addCondition(condition2);
//		List<Shipping> list = SpringUtil.getShippingManager().getShippingList(criteria);
		
		if( shipping != null ){
			//Shipping shipping = (Shipping)list.get(0);
			for (ShippingArea shippingArea : shipping.getShippingAreas()) {
				for (Region region : shippingArea.getRegions()) {
					if(regionIdList.contains(region.getId())){
						Map<String,String> shippingConfig = BaseShippingMetaPlugin.deserialize(shippingArea.getConfig());
						if(shippingConfig.containsKey("payFee")){
							shippingAreaInfo.put("payFee", shippingConfig.get("payFee"));
//							if(shippingConfig.get("payFee").indexOf("%") != -1){
//								shippingAreaInfo.put("payFee", Double.parseDouble(shippingConfig.get("payFee"))+"%");
//							}
//							else{
//								shippingAreaInfo.put("payFee", Double.parseDouble(shippingConfig.get("payFee")));
//							}
						}
						else{
							shippingAreaInfo.put("payFee", new Double(0));
						}
						shippingAreaInfo.put("shippingCode", shipping.getCode());
						shippingAreaInfo.put("shippingName", shipping.getName());
						shippingAreaInfo.put("shippingDesc", shipping.getDescription());
						shippingAreaInfo.put("insure", shipping.getInsure());
						shippingAreaInfo.put("supportCod", shipping.isSupportCod());
						shippingAreaInfo.put("configure", shippingArea.getConfig());
					}
				};
			}
					
		}
		return shippingAreaInfo;
		
    }
    
    /**
     * 获得购物车中商品的总重量、总价格、总数量
     *
     * @access  public
     * @param   int     type   类型：默认普通商品
     * @return  array
     */
    public static Map<String,Object> cartWeightPrice(int type , String sessionId){
    	/* 获得购物车中商品的总重量 */
    	Map<String,Object> cartWeightPrice = new HashMap<String,Object>();
		Criteria criteria = new Criteria();
		Condition condition = new Condition();		
		
		condition.setField(ICart.SESSION);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(sessionId);
		
		Condition condition2 = new Condition();		

		condition2.setField(ICart.REC_TYPE);
		condition2.setOperator(Condition.EQUALS);
		condition2.setValue(type+"");
		
		criteria.addCondition(condition);
		criteria.addCondition(condition2);
		List<Cart> cartList = SpringUtil.getCartManager().getCartList(criteria);
		double weight = 0;
		double number = 0;
		double amount = 0;
		
    	
		for (Cart cart : cartList) {
			weight += cart.getGoodsWeight() * cart.getGoodsNumber();
			number += cart.getGoodsNumber();
			amount += cart.getGoodsPrice() * cart.getGoodsNumber();
			
		}
		String formatedWeight  = LibCommon.formatedWeight(weight);
    	cartWeightPrice.put("weight", new Double(weight));
    	cartWeightPrice.put("number", new Double(number) );
    	cartWeightPrice.put("amount", new Double(amount) );
    	cartWeightPrice.put("formatedWeight", formatedWeight );
    	
    	return cartWeightPrice;
    	
    }
    
    /**
     * 计算运费
     * @param   string  $shipping_code      配送方式代码
     * @param   mix     $shipping_config    配送方式配置信息
     * @param   float   $goods_weight       商品重量
     * @param   float   $goods_amount       商品金额
     * @param   float   $goods_number       商品数量
     * @return  float   运费
     */
    public static double shippingFee( String shippingCode , String configure , double goodsWeight , double goodsAmount ){
    	Map<String,String> shippingConfig = EMS.deserialize(configure);
    	// TODO 根据商品数量算运费
    	IShippingMetaManager shippingMetaManager = SpringUtil.getShippingMetaManager();
    	return shippingMetaManager.calculate(shippingCode , goodsWeight , goodsAmount , shippingConfig );
    }
    
    /**
     * 获取指定配送的保价费用
     *
     * @access  public
     * @param   string      $shipping_code  配送方式的code
     * @param   float       $goods_amount   保价金额
     * @param   mix         $insure         保价比例
     * @return  float
     */
    public static double shippingInsureFee( double goodsAmount , String insure){
    	if(insure.indexOf("%") == -1){
    		return Double.parseDouble(insure);
    	}
    	else{
    		return Math.ceil( Double.parseDouble(insure.substring(0,insure.indexOf("%"))) * goodsAmount / 100 );
    	}
    	
    }
    
    /**
     * 获得订单需要支付的支付费用
     *
     * @access  public
     * @param   integer $payment_id
     * @param   float   $order_amount
     * @param   mix     $cod_fee
     * @return  float
     */
    public static double payFee(String paymentId , double orderAmount , String codFee){
    	double payFee = 0 ;
    	Payment payment = paymentInfo( paymentId);
    	String rate = (payment.isCod() && (codFee != null )) ? codFee : payment.getFee();
    	
    	if(rate.indexOf("%") != -1){
    		/* 支付费用是一个比例 */
    		double val = Double.parseDouble(rate.substring(0,rate.indexOf("%"))) / 100 ;
    		payFee = val > 0 ? orderAmount * val / ( 1 - val ) : 0 ;
    	}
    	else{
    		payFee = Double.parseDouble(rate);
    	}
    	return Math.round(payFee);
    }
    
    /**
     * 取得支付方式信息
     * @param   int     $pay_id     支付方式id
     * @return  array   支付方式信息
     */
    public static Payment paymentInfo(String payId)
    {
    	Criteria criteria = new Criteria();
		Condition condition = new Condition();		
		
		condition.setField(IPayment.ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(payId);
		
		// TODO temporarily hide it, as the import of payment cannot recognize a true value for enabled
//		Condition condition2 = new Condition();		
//
//		condition2.setField(IPayment.ENABLED);
//		condition2.setOperator(Condition.EQUALS);
//		condition2.setValue("true");
		
		criteria.addCondition(condition);
//		criteria.addCondition(condition2);
		List<Payment> paymentList = SpringUtil.getPaymentManager().getPaymentList(criteria);
        if( paymentList.size() > 0 ){
        	return (Payment)paymentList.get(0);
        }
        else{
        	return null;
        }
    }
    
    /**
     * 取得配送方式信息
     * @param   int     $shipping_id    配送方式id
     * @return  array   配送方式信息
     * @throws Exception 
     */
    public static Shipping shippingInfo(String shippingId) throws Exception{
    	Criteria criteria = new Criteria();
		Condition condition = new Condition();		

		condition.setField(IShipping.ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(shippingId);
		
		Condition condition2 = new Condition();		

		condition2.setField(IShipping.ENABLED);
		condition2.setOperator(Condition.EQUALS);
		condition2.setValue("true");
		
		criteria.addCondition(condition);
		criteria.addCondition(condition2);
		List<Shipping> list = SpringUtil.getShippingManager().getShippingList(criteria);
		if(list.size() > 0 ){
			return list.get(0);
		}
		else{
			throw new Exception();
		}
			
		
    }
    
    /**
     * 通过orderId获得合并订单后商品的总重量、总价格、总数量
     * @param fromId
     * @param toId
     * @param manager
     * @return
     */
    public static Map<String,Object> getWeightPrice(String fromId, String toId){
    	/* 获得购物车中商品的总重量 */
    	Map<String,Object> weightPrice = new HashMap<String,Object>();
    	double weight = 0;
		double number = 0;
		double amount = 0;
		
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, fromId));
		List<OrderGoods> fromGoods = SpringUtil.getOrderGoodsManager().getOrderGoodsList(criteria);
		
		criteria.removeAll();
		criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, toId));
		List<OrderGoods> toGoods = SpringUtil.getOrderGoodsManager().getOrderGoodsList(criteria);
		toGoods.addAll(fromGoods);
		
		for(Iterator iterator = toGoods.iterator(); iterator.hasNext();) {
			OrderGoods orderGoods = (OrderGoods)iterator.next();
			String goodsId = orderGoods.getGoods().getId();
			Goods goods = SpringUtil.getGoodsManager().getGoods(goodsId);
			
			weight += goods.getWeight();
			number += orderGoods.getGoodsNumber();
			amount += orderGoods.getGoodsPrice() * orderGoods.getGoodsNumber();
		}

		String formatedWeight  = LibCommon.formatedWeight(weight);
		weightPrice.put("weight", new Double(weight));
		weightPrice.put("number", new Double(number) );
		weightPrice.put("amount", new Double(amount) );
		weightPrice.put("formatedWeight", formatedWeight );
    	return weightPrice;
    }

    /**
     * 改变订单中商品库存
     * @param   int     $order_id   订单号
     * @param   bool    $is_dec     是否减少库存
     * @param   bool    $storage     减库存的时机，1，下订单时；0，发货时；
     */
    public static void changeOrderGoodsStorage(OrderGoods orderGoods){
    	changeGoodsStorage(orderGoods.getGoods().getId(),-orderGoods.getGoodsNumber());
    }
    /**
     * 商品库存增与减
     * @param   int    $good_id    商品ID
     * @param   int    $number     增减数量，默认0；
     * @return  bool               true，成功；false，失败；
     */
    public static boolean changeGoodsStorage(String goodsId , int number){
    	if(goodsId == null || number == 0 ){
    		return false;
    	}
    	Goods goods = SpringUtil.getGoodsManager().getGoods(goodsId);
    	if(goods != null){
	    	goods.setNumber((int)(goods.getNumber()+number));
	    	SpringUtil.getGoodsManager().saveGoods(goods);
	    	return true;
    	}
    	else{
    		return false;
    	}
    }
}
