package com.jcommerce.gwt.client;

import com.google.gwt.core.client.GWT;

public abstract class Logger {
	public static Logger getClientLogger() {
		return new GWTLogger();
	}
	public abstract void log(String s);
	
	public static class GWTLogger extends Logger {
		public static final boolean DEBUG = false;
		public void log(String s) {
			if(DEBUG) {
				GWT.log(s, null);
			}
		}
	}
}

