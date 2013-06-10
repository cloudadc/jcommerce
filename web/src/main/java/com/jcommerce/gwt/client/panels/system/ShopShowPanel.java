package com.jcommerce.gwt.client.panels.system;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ShopShowPanel extends ColumnPanel {
	private String SEARCHKEYWORDS = "search_keywords";//首页搜索的关键字
	private String DATAFORMAT = "date_format";//日期格式
	private String TIMEFORMAT = "time_format";//时间格式
	private String CURRENCYFORMAT = "currency_format";//货币格式
	private String THUMBWIDTH = "thumb_width";//缩略图宽度
	private String THUMBHEIGHT = "thumb_height";//缩略图高度
	private String IMAGEWIDTH = "image_width";//商品图片宽度
	private String IMAGEHEIGHT = "image_height";//商品图片高度
	private String TOPNUMBER = "top_number";//销量排行数量
	private String HISTORYNUMBER = "history_number";//浏览历史数量
	private String COMMENTSNUMBER = "comments_number";//评论数量
	private String BOUGHTGOODS = "bought_goods";//相关商品数量
	private String ARTICLENUMBER = "article_number";//最新文章显示数量
	private String GOODSNAMELENGTH = "goods_name_length";//商品名称的长度
	private String PRICEFORMAT = "price_format";//商品价格显示规则
	private String PAGESIZE = "page_size";//商品分类页列表的数量
	private String SORTORDERTYPE = "sort_order_type";//商品分类页默认排序类型
	private String SORTORDERMETHOD = "sort_order_method";//商品分类页默认排序方式
	private String SHOWORDERTYPE = "show_order_type";//商品分类页默认显示方式
	private String ATTRRELATEDNUMBER = "attr_related_number";//属性关联的商品数量
	private String GOODSGALLERYNUMBER = "goods_gallery_number";//商品详情页相册图片数量
	private String ARTICLETITLELENGTH = "article_title_length";//文章标题长度
	private String NAMEOFREGION1 = "name_of_region_1";//一级配送区域名称
	private String NAMEOFREGION2 = "name_of_region_2";//二级配送区域名称
	private String NAMEOFREGION3 = "name_of_region_3";//三级配送区域名称
	private String NAMEOFREGION4 = "name_of_region_4";//四级配送区域名称
	private String RELATEDGOODSNUMBER = "related_goods_number";//关联商品显示数量
	private String HELPOPEN = "help_open";//用户帮助是否打开
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		createPanel(SEARCHKEYWORDS, Resources.constants.search_keywords(), new TextBox());
		createPanel(DATAFORMAT, Resources.constants.date_format(),new TextBox());
		createPanel(TIMEFORMAT, Resources.constants.time_format(),new TextBox());
		createPanel(CURRENCYFORMAT, Resources.constants.currency_format(),new TextBox());
		createPanel(THUMBWIDTH, Resources.constants.thumb_width(),new TextBox());
		createPanel(THUMBHEIGHT, Resources.constants.thumb_height(),new TextBox());
		createPanel(IMAGEWIDTH, Resources.constants.image_width(),new TextBox());
		createPanel(IMAGEHEIGHT, Resources.constants.image_height(),new TextBox());
		createPanel(TOPNUMBER, Resources.constants.top_number(),new TextBox());
		createPanel(HISTORYNUMBER, Resources.constants.history_number(),new TextBox());
		createPanel(COMMENTSNUMBER, Resources.constants.comments_number(),new TextBox());
		createPanel(BOUGHTGOODS, Resources.constants.bought_goods(),new TextBox());
		createPanel(ARTICLENUMBER, Resources.constants.article_number(),new TextBox());
		createPanel(GOODSNAMELENGTH, Resources.constants.goods_name_length(),new TextBox());
		ListBox priceFormat = new ListBox();
		priceFormat.addItem(Resources.constants.do_nothing(), "0");
		priceFormat.addItem(Resources.constants.reservations_effective_mantissa(), "1");
		priceFormat.addItem(Resources.constants.not_rounding_keep_1(), "2");
		priceFormat.addItem(Resources.constants.not_rounding_not_keep(), "3");
		priceFormat.addItem(Resources.constants.rounding_keep_1(), "4");
		priceFormat.addItem(Resources.constants.rounding_not_keep(), "5");
		priceFormat.setSelectedIndex(0);
		createPanel(PRICEFORMAT, Resources.constants.price_format(),priceFormat);
		createPanel(PAGESIZE, Resources.constants.page_size(),new TextBox());
		ListBox sortOrderType = new ListBox();
		sortOrderType.addItem(Resources.constants.sort_shelf_time(), "0");
		sortOrderType.addItem(Resources.constants.sort_goods_price(), "1");
		sortOrderType.addItem(Resources.constants.sort_last_update(), "2");
		sortOrderType.setSelectedIndex(0);
		createPanel(SORTORDERTYPE, Resources.constants.sort_order_type(),sortOrderType);
		ListBox sortOrderMethod = new ListBox();
		sortOrderMethod.addItem(Resources.constants.sort_DESC(), "0");
		sortOrderMethod.addItem(Resources.constants.sort_ASC(), "1");
		sortOrderType.setSelectedIndex(0);
		createPanel(SORTORDERMETHOD, Resources.constants.sort_order_method(),sortOrderMethod);
		ListBox showOrderType = new ListBox();
		showOrderType.addItem(Resources.constants.show_list(), "0");
		showOrderType.addItem(Resources.constants.show_grid(), "1");
		showOrderType.addItem(Resources.constants.show_text(), "2");
		showOrderType.setSelectedIndex(0);
		createPanel(SHOWORDERTYPE, Resources.constants.show_order_type(),showOrderType);
		createPanel(ATTRRELATEDNUMBER, Resources.constants.attr_related_number(),new TextBox());
		createPanel(GOODSGALLERYNUMBER, Resources.constants.goods_gallery_number(),new TextBox());
		createPanel(ARTICLETITLELENGTH, Resources.constants.article_title_length(),new TextBox());
		createPanel(NAMEOFREGION1, Resources.constants.name_of_region_1(),new TextBox());
		createPanel(NAMEOFREGION2, Resources.constants.name_of_region_2(),new TextBox());
		createPanel(NAMEOFREGION3, Resources.constants.name_of_region_3(),new TextBox());
		createPanel(NAMEOFREGION4, Resources.constants.name_of_region_4(),new TextBox());
		createPanel(RELATEDGOODSNUMBER, Resources.constants.related_goods_number(),new TextBox());
		ListBox helpOpen = new ListBox();
		helpOpen.addItem(Resources.constants.no(), "0");
		helpOpen.addItem(Resources.constants.yes(), "1");
		helpOpen.setSelectedIndex(0);
		createPanel(HELPOPEN, Resources.constants.help_open(),helpOpen);
	}
}
