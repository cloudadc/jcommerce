package com.jcommerce.core.module.shipping.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.jcommerce.core.module.ConfigMetaData;
import com.jcommerce.core.module.FieldMetaData;
import com.jcommerce.core.module.IConfigMetaData;
import com.jcommerce.core.module.IFieldMetaData;
import com.jcommerce.core.module.shipping.IShippingModule;

public class FreightAtPaid implements IShippingModule {
	
	private static final String name = "运费到付";

	PropertyReader pReader = new PropertyReader("freightAtPaid.properties", this.getClass());

	public String getQueryForm(String invoiceSn) {
		return null;
	}

	public float getShippingFee(float goodsWeight, float goodsTotalMoney) {
		return 0;
	}

	public String getAuthor() {
		return pReader.getValue(IShippingConstants.NAME);
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
		options.put("less1000", "12");
		options.put("more1000", "5");
		options.put("free", "0");
		field.setLable("FreightAtPaid");
		field.setOptions(options);
		return field.toString();
	}

	public String getCode() {
		return "FreightAtPaid";
	}

	public String getDescription() {
		return pReader.getValue(IShippingConstants.DESCRIPTION);
	}

	public String getVersion() {
		return pReader.getValue(IShippingConstants.VERSION);
	}

	public String getSerializedConfig(Map<String, Object> props) {
		return null;
	}

	public URL getWebSite() {
		try {
			return new URL("http://www.ecshop.com");
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
