/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

public class Tag extends ModelObject {

	private User user;
	private Goods goods;
	private String tagWords;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getTagWords() {
		return tagWords;
	}

	public void setTagWords(String tagWords) {
		this.tagWords = tagWords;
	}

}
