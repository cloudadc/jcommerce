package com.jcommerce.gwt.client.util;

import com.google.gwt.i18n.client.DateTimeFormat;

public class GWTFormatUtils {
	

	
	public static final String SHORT_DATE_FORMAT = "MM/dd/yyyy";

	// used by GWT
	public static DateTimeFormat shortDateFormat() {
		return DateTimeFormat.getFormat(SHORT_DATE_FORMAT);
	}
}	
