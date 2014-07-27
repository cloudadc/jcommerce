/**
 * Author: Kylin Soong
 */

package com.jcommerce.gwt.client.model;

public class ChangeBeanObjectAttributeNotCompleteException extends
		RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String content;
	
	public ChangeBeanObjectAttributeNotCompleteException(String content) {
		this.content = content;
	}

	public String getMessage() {
		return content + super.getMessage();
	}

}
