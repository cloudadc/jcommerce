package com.jcommerce.core.wrapper;

public class ShopConfigWrapper extends BaseWrapper {
	
	public ShopConfigWrapper() {
		init();
	}
	
	@Override
	public void clear() {
		super.clear();
		init();
	}
	
	public void init() {
		// default values
		this.put("showGoodssn", "true");
		this.put("showGoodsnumber", "true");
		this.put("showBrand", "true");

		this.put("showAddtime", "true");
		this.put("showGoodsnumber", "true");
		this.put("showGoodsnumber", "true");
		this.put("showGoodsnumber", "true");
		this.put("goodsattrStyle", 1);
		this.put("pageSize", "5");
		this.put("pointsName", "积分");
		
		this.put("shopRegClosed", 0);
		this.put("pageStyle", 0);
		
		this.put("oneStepBuy", 2);
		
		//1只显示文字 2只显示图片 3显示文字与图片
		this.put("showGoodsInCart", 3);
		
		this.put("showGoodsAttribute", 1);
		
		// TODO make it configurable thru GUI
//		this.put(CFG_KEY_SHOW_GOODSWEIGHT, "true");
//		this.put(CFG_KEY_SHOW_MARKETPRICE, "true");
//		this.put(CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd");
		
		this.put("shop_country", "agpnY2xvdWRzaG9wciULEgZSZWdpb24iGV8yM2ZiMGJiYjAxMjNmYjBiYmI3NDAwMDAM");

		this.put("comment_check", 0);		
		this.put("comments_number", 5);
		this.put("collection_number", 5);
		this.put("comment_number", 5);
	}


}
