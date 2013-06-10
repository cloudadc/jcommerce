package com.jcommerce.core.service.shipping;

import java.util.Map;

public interface IShippingMetaPlugin {
	
	public static final String KEY_BASE_FEE = "base_fee";
	public static final String KEY_STEP_FEE = "step_fee";
	public static final String KEY_FREE_MONEY = "freeMoney";
	
    /**
     * 计算订单的配送费用的函数
     *
     * @param   float   $goods_weight   商品重量
     * @param   float   $goods_amount   商品金额
     * @return  decimal
     */
	public double calculate(double goodsWeight, double goodsAmount, Map<String, String> configValues);
	
	public ShippingConfigMeta getDefaultConfigMeta();
	
	public ShippingAreaMeta getDefaultAreaMeta();
//    public String getCode(OrderInfo order, Payment payment);
//    public String getDefaultConfig();
    public String serializeConfig(Map<String, Object> props);
    // key -> value
    // lable -> key -> type -> (options)
    
    public ShippingAreaMeta deserializeConfig(String serializedConfig);
}
