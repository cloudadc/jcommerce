package com.jcommerce.gwt.client.panels.orders.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.gwt.client.form.BeanObject;

/**
 * @author monkey
 */
public class OrderData {
	/**
	 * 该数据结构用于存放选择商品结果。
	 * 使用静态变量使之只初始化一次，供不同的类操作。
	 * 再完成添加订单的时候将此中数据清空。
	 */
	private static List<BeanObject> orderGoods = new ArrayList<BeanObject>();

	/**
	 * 存放订单的所有信息。
	 * 使用静态变量使之只初始化一次，供不同的类操作。
	 * 再完成添加订单的时候将此中数据清空。
	 * 
	 * key: order id
	 * value: order
	 */
	private static Map<String, Object> orderInfos = new HashMap<String, Object>();
	
	/**
	 * @return the orderGoods
	 */
	public List<BeanObject> getOrderGoods() {
		return orderGoods;
	}

	/**
	 * @param orderGoods the orderGoods to set
	 */
	public void setOrderGoods(BeanObject orderGood) {
		orderGoods.add(orderGood);
	}

	/**
	 * @return the orderInfos
	 */
	public Map<String, Object> getOrderInfos() {
		return orderInfos;
	}

	/**
	 * @param orderInfos the orderInfos to set
	 */
	public void setOrderInfos(String key, Object value) {
		orderInfos.put(key, value);
	}
	
}
