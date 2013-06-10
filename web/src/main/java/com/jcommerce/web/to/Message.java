package com.jcommerce.web.to;

import com.jcommerce.core.wrapper.BaseWrapper;

public class Message extends BaseWrapper{
	
	String content;
	String link;
	String href;
	String type;
	
	
	public String getBackUrl() {
		return href;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Object getUrlInfo(){
		return null;
	}
	
	

}
