package com.jcommerce.core.service.shipping.impl;

import java.util.HashMap;
import java.util.Map;

import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.ShippingAreaFieldMeta;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;
import com.jcommerce.core.service.shipping.ShippingConfigMeta;
import com.jcommerce.core.util.IConstants;

public class YuanTong extends BaseShippingMetaPlugin implements
		IShippingMetaPlugin {
	private static final ShippingAreaMeta defaultShippingAreaMeta;
	private static final ShippingConfigMeta defaultShippingConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, ShippingAreaFieldMeta> fieldMetas;
    

	static {
		defaultShippingConfigMeta = new ShippingConfigMeta();
		defaultShippingConfigMeta.setCode("yuantong");
		defaultShippingConfigMeta.setName("圆通快递");
		defaultShippingConfigMeta.setVersion("1.0.0");
		defaultShippingConfigMeta.setDescription("上海圆通物流（速递）有限公司经过多年的网络快速发展，在中国速递行业中一直处于领先地位。为了能更好的发展国际快件市场，加快与国际市场的接轨，强化圆通的整体实力，圆通已在东南亚、欧美、中东、北美洲、非洲等许多城市运作国际快件业务 ");
		defaultShippingConfigMeta.setSupportCod(true);
		defaultShippingConfigMeta.setAuthor("GSHOP TEAM");
		defaultShippingConfigMeta.setInsure("0");
		defaultShippingConfigMeta.setWebsite(IConstants.PROJECT_URL);
//		defaultShippingConfigMeta.setShippingPrint("<B>TODO</B>: <br>fill shipping print template in plugin");

		fieldMetas = new HashMap<String, ShippingAreaFieldMeta>();
		defaultConfigValues = new HashMap<String, String>();
		
		// TODO exteriorialized the string to a client-side resource file

		ShippingAreaFieldMeta areaMeta = new ShippingAreaFieldMeta("首重费用");
		fieldMetas.put(KEY_BASE_FEE, areaMeta);
		defaultConfigValues.put(KEY_BASE_FEE, "5");
		
		areaMeta = new ShippingAreaFieldMeta("续重费用");
		fieldMetas.put(KEY_STEP_FEE, areaMeta);
		defaultConfigValues.put(KEY_STEP_FEE, "5");
		
//		areaMeta = new ShippingAreaFieldMeta("配送区域名称");
//		fieldMetas.put("name", areaMeta);
//		defaultConfigValues.put("name", "");
		
		areaMeta = new ShippingAreaFieldMeta("免费额度");
		fieldMetas.put(KEY_FREE_MONEY, areaMeta);
		defaultConfigValues.put(KEY_FREE_MONEY, "120");
		
		defaultShippingAreaMeta = new ShippingAreaMeta();
		defaultShippingAreaMeta.setFieldMetas(fieldMetas);
		defaultShippingAreaMeta.setFieldValues(defaultConfigValues);
		
	}
	@Override
	protected Map<String, ShippingAreaFieldMeta> getFieldMetas() {
		return fieldMetas;
	}

	public ShippingAreaMeta getDefaultAreaMeta() {
		return defaultShippingAreaMeta;
	}

	public ShippingConfigMeta getDefaultConfigMeta() {
		return defaultShippingConfigMeta;
	}
	
	public double calculate(double goodsWeight, double goodsAmount, Map<String, String> configValues) {
		try {
			if(configValues == null) {
				return 0;
			}
			double freeMoney = getDouble(configValues.get(KEY_FREE_MONEY));
			double baseFee = getDouble(configValues.get(KEY_BASE_FEE));
			double stepFee = getDouble(configValues.get(KEY_STEP_FEE));
			if(freeMoney>0 && goodsAmount>=freeMoney) {
				return 0;
			}
			else {
				double fee = baseFee;
				if(goodsWeight>1) {
					fee += Math.ceil((goodsWeight-1))*stepFee;
				}
				return fee;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	

}
