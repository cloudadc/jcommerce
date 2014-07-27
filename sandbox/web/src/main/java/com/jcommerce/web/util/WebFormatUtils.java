package com.jcommerce.web.util;

import java.text.NumberFormat;
import java.util.Locale;

import com.jcommerce.core.util.dwt.DWTConverter;
import com.jcommerce.web.to.Lang;

public class WebFormatUtils {
	
	public static String priceFormat(Double price) {
		Locale currentLocale = Lang.getCurrentLocale();
		NumberFormat nf = NumberFormat.getCurrencyInstance(currentLocale);
		String res = nf.format(price);
		return res;
	}
	public static String priceFormat(String price) {
		return priceFormat(Double.valueOf(price));
	}
	public static String phpVarFromat(String var) {
		return new DWTConverter().replacePhpVars(var);
	}
}
