package com.jcommerce.gwt.client.panels.system;

import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.RadioPanel;
import com.jcommerce.gwt.client.widgets.WidgetInfo;

public class ShopOrderFlowPanel extends ColumnPanel {
    /* 减库存时机 */
    public final static int SDT_SHIP = 0; // 发货时
    public final static int SDT_PLACE = 1; // 下订单时
    
	private String CART_CONFIRM = "cart_confirm";//购物车确定提示
	private String CAN_INVOICE = "can_invoice";//能否开发票
	private String USE_INTEGRAL = "use_integral";//是否使用积分
	private String USE_BONUS = "use_bonus";//是否使用红包
	private String USE_SURPLUS = "use_surplus";//是否使用余额
	private String USE_HOW_OOS = "use_how_oos";//是否使用缺货处理
	private String SEND_CONFIRM_EMAIL = "send_confirm_email";//确认订单时
	private String SEND_SHIP_EMAIL = "send_ship_email";//发货时
	private String ORDER_CANCEL_NOTE = "order_cancel_note";//取消订单时
    private String SEND_CANCEL_EMAIL = "send_cancel_email";//取消订单时
	private String SEND_INVALID_EMAIL = "send_invalid_email";//把订单设为无效时
	private String ORDER_RETURN_NOTE = "order_return_note";//退货时
	private String ORDER_PAY_NOTE = "order_pay_note";//设置订单为“已付款”时
	private String ORDER_UNPAY_NOTE = "order_unpay_note";//设置订单为“未付款”时
	private String ORDER_SHIP_NOTE = "order_ship_note";//设置订单为“已发货”时
	private String ORDER_UNSHIP_NOTE = "order_unship_note";//设置订单为“未发货”时
	private String ORDER_RECEIVE_NOTE = "order_receive_note";//设置订单为“收货确认”时
	private String ORDER_INVALID_NOTE = "order_invalid_note";//把订单设为无效时
	private String ANONYMOUS_BUY = "anonymous_buy";//是否允许未登录用户购物
	private String INVOICE_CONTENT = "invoice_content";//发票内容
	private String MIN_GOODS_AMOUNT = "min_goods_amount";//最小购物金额
	private String ONE_STEP_BUY = "one_step_buy";//是否一步购物
	private String INVOICE_TYPE = "invoice_type";//发票类型及税率
	private String STOCK_DEC_TIME = "stock_dec_time";//减库存的时机
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

        ListBox list = new ListBox();
        list.addItem("提示用户，点击“确定”进购物车", "1");
        list.addItem("提示用户，点击“取消”进购物车", "2");
        list.addItem("直接进入购物车", "3");
        list.addItem("不提示并停留在当前页面", "4");
        list.setSelectedIndex(2);     
		WidgetInfo info = new WidgetInfo(CART_CONFIRM, "购物车确定提示", list);
        info.setNote("允许您设置用户点击“加入购物车”后是否提示以及随后的动作。");
        createPanel(info);

		RadioPanel radioPanel = new RadioPanel(CAN_INVOICE);
        radioPanel.addButton(1, "能");
        radioPanel.addButton(0, "不能");
        createPanel(CAN_INVOICE, "能否开发票", radioPanel);
        radioPanel = new RadioPanel(USE_INTEGRAL);
        radioPanel.addButton(1, "使用");
        radioPanel.addButton(0, "不使用");
        createPanel(USE_INTEGRAL, "是否使用积分", radioPanel);
        radioPanel = new RadioPanel(USE_SURPLUS);
        radioPanel.addButton(1, "使用");
        radioPanel.addButton(0, "不使用");
        createPanel(USE_SURPLUS, "是否使用余额", radioPanel);
        radioPanel = new RadioPanel(USE_BONUS);
        radioPanel.addButton(1, "使用");
        radioPanel.addButton(0, "不使用");
        createPanel(USE_BONUS, "是否使用红包", radioPanel);
        
        radioPanel = new RadioPanel(USE_HOW_OOS);
        radioPanel.addButton(1, "使用");
        radioPanel.addButton(0, "不使用");
        info = new WidgetInfo(USE_HOW_OOS, "是否使用缺货处理", radioPanel);
        info.setNote("使用缺货处理时前台订单确认页面允许用户选择缺货时处理方法。");
        createPanel(info);
        
        radioPanel = new RadioPanel(SEND_CONFIRM_EMAIL);
        radioPanel.addButton(1, "发送邮件");
        radioPanel.addButton(0, "不发送邮件");
        createPanel(SEND_CONFIRM_EMAIL, "确认订单时", radioPanel);
        radioPanel = new RadioPanel(SEND_SHIP_EMAIL);
        radioPanel.addButton(1, "发送邮件");
        radioPanel.addButton(0, "不发送邮件");
        createPanel(SEND_SHIP_EMAIL, "发货时", radioPanel);
        radioPanel = new RadioPanel(SEND_CANCEL_EMAIL);
        radioPanel.addButton(1, "发送邮件");
        radioPanel.addButton(0, "不发送邮件");
        createPanel(SEND_CANCEL_EMAIL, "取消订单时", radioPanel);
        radioPanel = new RadioPanel(SEND_INVALID_EMAIL);
        radioPanel.addButton(1, "发送邮件");
        radioPanel.addButton(0, "不发送邮件");
        createPanel(SEND_INVALID_EMAIL, "把订单设为无效时", radioPanel);

        radioPanel = new RadioPanel(ORDER_PAY_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_PAY_NOTE, "设置订单为“已付款”时", radioPanel);
        radioPanel = new RadioPanel(ORDER_UNPAY_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_UNPAY_NOTE, "设置订单为“未付款”时", radioPanel);
        radioPanel = new RadioPanel(ORDER_SHIP_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_SHIP_NOTE, "设置订单为“已发货”时", radioPanel);
        radioPanel = new RadioPanel(ORDER_RECEIVE_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_RECEIVE_NOTE, "设置订单为“收货确认”时", radioPanel);
        radioPanel = new RadioPanel(ORDER_UNSHIP_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_UNSHIP_NOTE, "设置订单为“未发货”时", radioPanel);
        radioPanel = new RadioPanel(ORDER_RETURN_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_RETURN_NOTE, "退货时", radioPanel);
        radioPanel = new RadioPanel(ORDER_INVALID_NOTE);
        radioPanel.addButton(1, "必须填写备注");
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_INVALID_NOTE, "把订单设为无效时", radioPanel);
        radioPanel = new RadioPanel(ORDER_CANCEL_NOTE);
        radioPanel.addButton(1, "必须填写备注"); 
        radioPanel.addButton(0, "无需填写备注");
        createPanel(ORDER_CANCEL_NOTE, "取消订单时", radioPanel);
        
        TextArea area = new TextArea();
        area.setSize("500", "100");
		info = new WidgetInfo(INVOICE_CONTENT, "发票内容", area);
        info.setNote("客户要求开发票时可以选择的内容。例如：办公用品。每一行代表一个选项。");
        createPanel(info);
        
		radioPanel = new RadioPanel(ANONYMOUS_BUY);
        radioPanel.addButton(1, "允许"); 
        radioPanel.addButton(0, "不允许");
        createPanel(ANONYMOUS_BUY, "是否允许未登录用户购物", radioPanel);
        
        info = new WidgetInfo(MIN_GOODS_AMOUNT, "最小购物金额", new TextBox());
        info.setNote("达到此购物金额，才能提交订单。");
        info.setAppendNote(true);
		createPanel(info);
        radioPanel = new RadioPanel(ONE_STEP_BUY);
        radioPanel.addButton(1, "是"); 
        radioPanel.addButton(0, "否");
        createPanel(ONE_STEP_BUY, "是否一步购物", radioPanel);

        radioPanel = new RadioPanel(STOCK_DEC_TIME);
        radioPanel.addButton(SDT_PLACE, "下订单时"); 
        radioPanel.addButton(SDT_SHIP, "发货时");
        createPanel(STOCK_DEC_TIME, "减库存的时机", radioPanel);
	}
}
