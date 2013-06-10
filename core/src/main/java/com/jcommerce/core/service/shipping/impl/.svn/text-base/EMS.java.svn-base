package com.jcommerce.core.service.shipping.impl;

import java.util.HashMap;
import java.util.Map;

import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.ShippingAreaFieldMeta;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;
import com.jcommerce.core.service.shipping.ShippingConfigMeta;
import com.jcommerce.core.util.IConstants;

public class EMS extends BaseShippingMetaPlugin implements IShippingMetaPlugin {

	private static final ShippingAreaMeta defaultShippingAreaMeta;
	private static final ShippingConfigMeta defaultShippingConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, ShippingAreaFieldMeta> fieldMetas;
    

    @Override
    protected Map<String, ShippingAreaFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
	static {
		defaultShippingConfigMeta = new ShippingConfigMeta();
		defaultShippingConfigMeta.setCode("ems");
		defaultShippingConfigMeta.setName("EMS");
		defaultShippingConfigMeta.setVersion("1.0.0");
		defaultShippingConfigMeta.setDescription("EMS 国内邮政特快专递描述内容");
		defaultShippingConfigMeta.setSupportCod(false);
		defaultShippingConfigMeta.setAuthor("GSHOP TEAM");
		defaultShippingConfigMeta.setInsure("0");
		defaultShippingConfigMeta.setWebsite(IConstants.PROJECT_URL);
		// FIXME
//		defaultShippingConfigMeta.setShippingPrint("<B>TODO</B>: <br>fill shipping print template in plugin");

		fieldMetas = new HashMap<String, ShippingAreaFieldMeta>();
		defaultConfigValues = new HashMap<String, String>();
		
		// TODO exteriorialized the string to a client-side resource file

		ShippingAreaFieldMeta areaMeta = new ShippingAreaFieldMeta("500克以内费用");
		fieldMetas.put(KEY_BASE_FEE, areaMeta);
		defaultConfigValues.put(KEY_BASE_FEE, "20");
		
		areaMeta = new ShippingAreaFieldMeta("续重每500克或其零数");
		fieldMetas.put(KEY_STEP_FEE, areaMeta);
		defaultConfigValues.put(KEY_STEP_FEE, "15");
		
//		areaMeta = new ShippingAreaFieldMeta("配送区域名称");
//		fieldMetas.put("name", areaMeta);
//		defaultConfigValues.put("name", "");
		
		areaMeta = new ShippingAreaFieldMeta("免费额度");
		fieldMetas.put(KEY_FREE_MONEY, areaMeta);
		defaultConfigValues.put(KEY_FREE_MONEY, "0");
		
		defaultShippingAreaMeta = new ShippingAreaMeta();
		defaultShippingAreaMeta.setFieldMetas(fieldMetas);
		defaultShippingAreaMeta.setFieldValues(defaultConfigValues);
		
	}
	

	public ShippingAreaMeta getDefaultAreaMeta() {
		return defaultShippingAreaMeta;
	}
	public ShippingConfigMeta getDefaultConfigMeta() {
		return defaultShippingConfigMeta;
	}
	
	
    /**
     * 计算订单的配送费用的函数
     *
     * @param   float   $goods_weight   商品重量
     * @param   float   $goods_amount   商品金额
     * @return  decimal
     */
	public double calculate(double goodsWeight, double goodsAmount, Map<String, String> configValues) {
		try {
			if(configValues == null) {
				return 0;
			}
			double freeMoney = getDouble(configValues.get(KEY_FREE_MONEY));
			double baseFee = getDouble(configValues.get(KEY_BASE_FEE));
			double stepFee = getDoube(configValues.get(KEY_STEP_FEE));
			if(freeMoney>0 && goodsAmount>=freeMoney) {
				return 0;
			}
			else {
				double fee = baseFee;
				if(goodsWeight>500) {
					fee += Math.ceil((goodsWeight-500)/500)*stepFee;
				}
				return fee;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	private double getDoube(String s) {
	    if (s == null) {
	        return 0;
	    }
	    
	    return new Double(s).doubleValue();
	}
}
