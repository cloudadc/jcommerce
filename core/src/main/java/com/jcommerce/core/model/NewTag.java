/**
 * @author Kylin Soong     
 */
package com.jcommerce.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "new_tag", catalog = "ishop")
public class NewTag extends ModelObject{
	
	private static final long serialVersionUID = -122915739410073449L;
	private String tagName;
	private String searchContent;
	private String goodsName;
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
