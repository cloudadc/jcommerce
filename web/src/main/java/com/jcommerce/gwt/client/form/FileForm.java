package com.jcommerce.gwt.client.form;

import java.util.Map;

public class FileForm extends BeanObject {
	
	public FileForm() {
		super();
	}
	public FileForm(String modelName) {
		super(modelName);
	}
	public FileForm(String modelName, Map<String, Object> props) {
		super(modelName, props);
	}
	
	private static final String CONTENT = "content";
	private static final String FILENAME = "fileName";
	private static final String MIMETYPE = "mimeType";

	public byte[] getContent() {
		return (byte[])get(CONTENT);
	}
	public void setContent(byte[] content) {
		set(CONTENT, content);
	}
	public String getFileName() {
		return getString(FILENAME);
	}
	public void setFileName(String fileName) {
		set(FILENAME, fileName);
	}
	public String getMimeType() {
		return getString(MIMETYPE);
	}
	public void setMimeType(String mimeType) {
		set(MIMETYPE, mimeType);
	}
	
	
	
	
}
