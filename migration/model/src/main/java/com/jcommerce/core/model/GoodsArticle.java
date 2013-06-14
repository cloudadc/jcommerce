/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

public class GoodsArticle extends ModelObject {
	
	private Article article;
	private Goods goods;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}
