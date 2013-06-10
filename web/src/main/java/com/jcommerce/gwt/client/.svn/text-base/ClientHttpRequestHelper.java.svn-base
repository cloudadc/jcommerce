package com.jcommerce.gwt.client;

import java.io.IOException;

import com.google.gwt.user.client.Random;

public class ClientHttpRequestHelper{
	
	public String getRequestData() {
		return os.toString();		
	}
	
	private StringBuffer os = new StringBuffer();

	protected void write(String s)  {
		os.append(s);
	}

	protected void write(char c) {
		os.append(c);
	}

	protected void newline() {
		write("\r\n");
	}

	protected void writeln(String s) {

		write(s);
		newline();
	}

	protected static String randomString() {
		return Long.toString(Random.nextInt(), 36);
	}

	String boundary = "---------------------------" + randomString()
			+ randomString() + randomString();

	private void boundary() {
		write("--");
		write(boundary);
	}

	private void writeName(String name) {
		newline();
		write("Content-Disposition: form-data; name=\"");
		write(name);
		write('"');
	}

	/**
	 * adds a string parameter to the request
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws IOException
	 */
	public void setParameter(String name, String value)  {
			boundary();
			writeName(name);
			newline();
			newline();
			writeln(value);
	}
}
