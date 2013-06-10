package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.ChoicePanel;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ShopGoodsPanel extends ColumnPanel {
	private String SHOWGOODSSN = "show_goodssn";//是否显示货号
	private String SHOWBRAND = "show_brand";//是否显示品牌
	private String SHOWGOODSWEIGHT = "show_goodsweight";//是否显示重量
	private String SHOWGOODSNUMBER = "show_goodsnumber";//是否显示库存
	private String SHOWADDTIME = "show_addtime";//是否显示上架时间
	private String GOODSATTRSTYLE = "goodsattr_style";//商品属性显示样式
	private String SHOWMARKETPRICE = "show_marketprice";//是否显示市场价格
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		//Add a Goods show Set tab
		ListBox showGoodsSN = new ListBox();
		showGoodsSN.addItem(Resources.constants.yes(), "1");
		showGoodsSN.addItem(Resources.constants.no(), "0");
		showGoodsSN.setSelectedIndex(0);
		createPanel(SHOWGOODSSN, Resources.constants.show_goodssn(), showGoodsSN);
		
		List<ChoicePanel.Item> brandItems = new ArrayList<ChoicePanel.Item>();
		brandItems.add(new ChoicePanel.Item(Resources.constants.yes(), "1"));
		brandItems.add(new ChoicePanel.Item(Resources.constants.no(), "0"));		
		ChoicePanel showBrand = new ChoicePanel("0",brandItems); 
		createPanel(SHOWBRAND, Resources.constants.show_brand(), showBrand);
		
		List<ChoicePanel.Item> goodsWeightItems = new ArrayList<ChoicePanel.Item>();
		goodsWeightItems.add(new ChoicePanel.Item(Resources.constants.yes(), "1"));
		goodsWeightItems.add(new ChoicePanel.Item(Resources.constants.no(), "0"));		
		ChoicePanel showGoodsWeight = new ChoicePanel("0",goodsWeightItems); 
		createPanel(SHOWGOODSWEIGHT, Resources.constants.show_goodsweight(), showGoodsWeight);
		
		List<ChoicePanel.Item> goodsNumberItems = new ArrayList<ChoicePanel.Item>();
		goodsNumberItems.add(new ChoicePanel.Item(Resources.constants.yes(), "1"));
		goodsNumberItems.add(new ChoicePanel.Item(Resources.constants.no(), "0"));		
		ChoicePanel showGoodsNumber = new ChoicePanel("0",goodsNumberItems); 
		createPanel(SHOWGOODSNUMBER, Resources.constants.show_goodsnumber(), showGoodsNumber);
		
		List<ChoicePanel.Item> addTimeItems = new ArrayList<ChoicePanel.Item>();
		addTimeItems.add(new ChoicePanel.Item(Resources.constants.yes(), "1"));
		addTimeItems.add(new ChoicePanel.Item(Resources.constants.no(), "0"));		
		ChoicePanel showAddTime = new ChoicePanel("0",addTimeItems); 
		createPanel(SHOWADDTIME, Resources.constants.show_addtime(), showAddTime);
		
		List<ChoicePanel.Item> attrStyleItems = new ArrayList<ChoicePanel.Item>();
		attrStyleItems.add(new ChoicePanel.Item(Resources.constants.attr_Style_1(), "1"));
		attrStyleItems.add(new ChoicePanel.Item(Resources.constants.attr_Style_2(), "0"));		
		ChoicePanel goodsAttrStyle = new ChoicePanel("0",attrStyleItems); 
		createPanel(GOODSATTRSTYLE, Resources.constants.goodsattr_style(), goodsAttrStyle);
		
		List<ChoicePanel.Item> marketPriceItems = new ArrayList<ChoicePanel.Item>();
		marketPriceItems.add(new ChoicePanel.Item(Resources.constants.yes(), "1"));
		marketPriceItems.add(new ChoicePanel.Item(Resources.constants.no(), "0"));		
		ChoicePanel showMarketPrice = new ChoicePanel("0",marketPriceItems); 
		createPanel(SHOWMARKETPRICE, Resources.constants.show_marketprice(), showMarketPrice);		
	}
}
