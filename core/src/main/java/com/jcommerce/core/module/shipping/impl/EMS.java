/**
 * Author: Bob Chen
 */

package com.jcommerce.core.module.shipping.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.module.ConfigMetaData;
import com.jcommerce.core.module.FieldMetaData;
import com.jcommerce.core.module.IConfigMetaData;
import com.jcommerce.core.module.IFieldMetaData;
import com.jcommerce.core.module.shipping.IShippingModule;

public class EMS implements IShippingModule {

	PropertyReader pReader = new PropertyReader("ems.properties", this.getClass());

	public PropertyReader getpReader() {
		return pReader;
	}

	public String getAuthor() {
		return pReader.getValue(IShippingConstants.NAME);
	}

	public String getCode() {
		return "ems";
	}

	public String getDescription() {
		return pReader.getValue(IShippingConstants.DESCRIPTION);
	}

	public String getVersion() {
		return pReader.getValue(IShippingConstants.VERSION);
	}

	public URL getWebSite() {
		try {
			return new URL("http://www.ecshop.com");
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public Map<String, Object> getConfig() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("base_fee", 20);
		cfg.put("step_fee", 15);
		return cfg;
	}

	private float getBaseFee() {
		if (getConfig().get("base_fee") == null) {
			return 0;
		}

		return ((Number) getConfig().get("base_fee")).floatValue();
	}

	private float getStepFee() {
		if (getConfig().get("step_fee") == null) {
			return 0;
		}

		return ((Number) getConfig().get("step_fee")).floatValue();
	}

	private float getFreeMoney() {
		if (getConfig().get("free_money") == null) {
			return 0;
		}

		return ((Number) getConfig().get("free_money")).floatValue();
	}

	/**
	 * 邮政快递包裹费用计算方式
	 * ==============================================================
	 * ====================== 500g及500g以内 20元
	 * ------------------------------------
	 * ------------------------------------------------- 续重每500克或其零数
	 * 6元/9元/15元(按分区不同收费不同，具体分区方式，请寄件人拨打电话或到当地邮局营业窗口咨询，客服电话11185。)
	 * --------------
	 * -----------------------------------------------------------------------
	 * 
	 */
	public float getShippingFee(float goodsWeight, float goodsTotalMoney) {
		float freeMoney = getFreeMoney();
		if (freeMoney > 0 && goodsTotalMoney >= freeMoney) {
			return 0;
		}

		float fee = getBaseFee();
		if (goodsWeight >= 0.5) {
			fee += (Math.ceil((goodsWeight - 0.5) / 0.5)) * getStepFee();
		}

		return fee;
	}

	public String getQueryForm(String invoice_sn) {
		String str = "<form style=\"margin:0px\" method=\"post\" "
				+ "action=\"http://www.ems.com.cn/qcgzOutQueryAction.do\" name=\"queryForm_"
				+ invoice_sn
				+ " target=\"_blank\">"
				+ "<input type=\"hidden\" name=\"mailNum\" value=\""
				+ invoice_sn
				+ "\" />"
				+ "<a href=\"javascript:document.forms['queryForm_"
				+ invoice_sn
				+ "'].submit();\">"
				+ invoice_sn
				+ "</a>"
				+ "<input type=\"hidden\" name=\"reqCode\" value=\"browseBASE\" />"
				+ "<input type=\"hidden\" name=\"checknum\" value=\"0568792906411\" />"
				+ "</form>";

		return str;
	}

	public IConfigMetaData getConfigMetaData(String serializedConfig) {
		IConfigMetaData emfMetadata = new ConfigMetaData();
		Map<String, String> fieldValues = new HashMap<String,String>();
		emfMetadata.setFieldValues(fieldValues);
		StringBuffer sb = new StringBuffer();
		List<String> lists = new ArrayList<String>();
		for(int i = 0 ;i < serializedConfig.length(); i ++) {
			char c = serializedConfig.charAt(i);
			sb.append(c);
			if(c == '\n'){
				lists.add(sb.toString());
				sb.delete(0, sb.length());
			}
		}
		for(String str : lists) {
			if(str.startsWith("key: ")){
				str = str.substring("key: ".length(), str.length());
				String key = str.substring(0, str.indexOf(", "));
				str = str.substring(key.length() + 2, str.length());
				String value = str.substring("value:".length(),str.length());
				fieldValues.put(key, value);
			}
		}
		return emfMetadata;
	}

	public String getDefaultConfig() {
		IFieldMetaData field = new FieldMetaData();
		Map<String, String> options = new HashMap<String,String>();
		options.put("less1000", "15");
		options.put("more1000", "7");
		options.put("free", "0");
		field.setLable("EMS");
		field.setOptions(options);
		return field.toString();
	}

	public String getSerializedConfig(Map<String, Object> props) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		EMS e = new EMS();
		System.out.println(e.getDefaultConfig());
		IConfigMetaData data = e.getConfigMetaData(e.getDefaultConfig());
		System.out.println(data.getFieldValues());
	}

}
