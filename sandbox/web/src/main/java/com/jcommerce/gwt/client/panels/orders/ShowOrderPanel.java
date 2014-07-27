package com.jcommerce.gwt.client.panels.orders;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IOrderAction;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * 
 * @author Rainbow
 *	在添加订单时，最后显示订单的功能
 */
public class ShowOrderPanel extends ContentPanel {
	private List<BeanObject> orderGoods;
	private BeanObject theOrderInfo = new BeanObject(ModelNames.ORDER);
	private BeanObject userAddress = new BeanObject(ModelNames.USERADDRESS);
	private BeanObject orderAction = new BeanObject(ModelNames.ORDERACTION);
	
	private Label orderIDLabel = new Label();
	private Label userNameLabel = new Label();
	private Label orderSNLabel = new Label();
	private Label paymentsLabel = new Label();
	private Label shippingLabel = new Label();
	
	private Label orderStatusLabel = new Label();
	private Label addTimeLabel = new Label();
	private Label payForTimeLabel = new Label();
	private Label shippingTimeLabel = new Label();
	private Label orderGeneratorLabel = new Label();
	
	private Label invoceTypeLabel = new Label();
	private Label packLabel = new Label();
	private Label howSurplusLabel = new Label();
	private Label invoceContentLabel = new Label();
	private Label invoceNoLabel = new Label();
	
	private Label toBuyerLabel = new Label();
	private Label cardLabel = new Label();
	private Label cardMessagesLabel = new Label();
	
	private Label consigneeLabel = new Label();
	private Label emailLabel = new Label();
	private Label phoneLabel = new Label();
	private Label mobielLabel = new Label();
	private Label signBuildingLabel = new Label();
	private Label bestTimeLabel = new Label();
	private Label addressLabel = new Label();
	private Label zipLabel = new Label();
	
	private TextArea remark = new TextArea();
	
	private Button commitBt = new Button("提交");
	private Button payBt = new Button("付款");
	private Button shipBt = new Button("发货");
	private Button goodsRejectBt = new Button("退货");
	private Button cancleBt = new Button("无效");
	private Button doneBt = new Button("确认");

	private String orderID = "";
	private String orderActionID = "";
	private Criteria orderActionCri = new Criteria();
	private PagingToolBar toolBar;
	
	private DeleteService deleteService = new DeleteService();
	private ReadService readService = new ReadService();
	private ListService listService = new ListService();
	private UpdateService updateService = new UpdateService();
	private CreateService createService = new CreateService();
	
	public ShowOrderPanel(final ListStore<BeanObject> orderGoodsStore ,
			final BeanObject orderInfo) {
		this.theOrderInfo = orderInfo;
		
		
		/**
		 * 显示订单基本信息
		 */
		final HorizontalPanel baseMessagePanel = new HorizontalPanel();
		final ColumnPanel baseColumnPanel1 = new ColumnPanel();	
		
		baseColumnPanel1.createPanel(IOrder.ID, "订单号：", orderIDLabel);
		baseColumnPanel1.createPanel(IOrder.USER, "购货人：", userNameLabel);
		baseColumnPanel1.createPanel(IOrder.HOWOSS, "支付方式：", paymentsLabel);
		baseColumnPanel1.createPanel(IOrder.HOWSURPLUS, "配送方式： ", shippingLabel);
		baseColumnPanel1.createPanel(IOrder.SN, "发货单号： ", orderSNLabel);
		baseColumnPanel1.setWidth(400);
		
		final ColumnPanel baseColumnPanel2 = new ColumnPanel();
		baseColumnPanel2.createPanel(IOrder.STATUS, "订单状态：", orderStatusLabel);
		baseColumnPanel2.createPanel(IOrder.ADDTIME, "下单时间：", addTimeLabel);
		baseColumnPanel2.createPanel(IOrder.PAYTIME, "付款时间：", payForTimeLabel);
		baseColumnPanel2.createPanel(IOrder.SHIPPINGTIME, "发货时间：", shippingTimeLabel);
		baseColumnPanel2.createPanel(IOrder.FORMAD, "订单来源：", orderGeneratorLabel);
		
		baseMessagePanel.setPixelSize(800, 10);
		baseMessagePanel.setBorderWidth(1);
		baseMessagePanel.add(baseColumnPanel1);
		baseMessagePanel.add(baseColumnPanel2);
//		this.setAutoHeight(stateful);
		this.setAutoHeight(true);
		this.setCollapsible(true);
		this.add(baseMessagePanel);
		this.setHeading("订单基本信息");
		
		/**
		 * 显示订单其他信息
		 */
		final ContentPanel otherPanel = new ContentPanel();
		final HorizontalPanel otherMessagePanel = new HorizontalPanel();
		final ColumnPanel otherColumnPanel1 = new ColumnPanel();
		otherColumnPanel1.createPanel(IOrder.INVOICETYPE, "发票类型：",invoceTypeLabel);
		otherColumnPanel1.createPanel(IOrder.INVOICENO, "发票抬头：", invoceNoLabel);
		otherColumnPanel1.createPanel(IOrder.INVOICECONTENT, "发票内容： ", invoceContentLabel);
		otherColumnPanel1.createPanel(IOrder.HOWSURPLUS, "缺货处理： ", howSurplusLabel);
		otherColumnPanel1.createPanel(IOrder.PACK, "包装：", packLabel);
		otherColumnPanel1.setWidth(400);
		
		
		final ColumnPanel otherColumnPanel2 = new ColumnPanel();
		otherColumnPanel2.createPanel(IOrder.CARDMESSAGE, "贺卡祝福语:", cardMessagesLabel);
		otherColumnPanel2.createPanel(IOrder.TOBUYER, "商家给客户留言:", toBuyerLabel);
		otherColumnPanel2.createPanel(IOrder.CARD, "贺卡:", cardLabel);
		otherColumnPanel2.createPanel(null, null, null);
		
		otherMessagePanel.setPixelSize(800, 10);
		otherMessagePanel.setBorderWidth(1);
		otherMessagePanel.add(otherColumnPanel1);
		otherMessagePanel.add(otherColumnPanel2);
		otherPanel.setCollapsible(true);
		otherPanel.setHeading("其他信息");
		otherPanel.add(otherMessagePanel);
		this.add(otherPanel);
		
		
		/**
		 * 收货人信息
		 */
		final ContentPanel receiverPanel = new ContentPanel();
		final HorizontalPanel receiverMessagePanel = new HorizontalPanel();
		final ColumnPanel receiverColumnPanel1 = new ColumnPanel();
		receiverColumnPanel1.createPanel(IOrder.CONSIGNEE, "收货人：", consigneeLabel);
		receiverColumnPanel1.createPanel(IOrder.EMAIL, "电子邮件：", emailLabel);
		receiverColumnPanel1.createPanel(IOrder.ADDRESS, "地址：", addressLabel);
		receiverColumnPanel1.createPanel(IOrder.ZIP, "邮编：", zipLabel);
		receiverColumnPanel1.setWidth(400);
		
		final ColumnPanel receiverColumnPanel2 = new ColumnPanel();
		receiverColumnPanel2.createPanel(IOrder.PHONE, "电话:", phoneLabel);
		receiverColumnPanel2.createPanel(IOrder.MOBILE, "手机:", mobielLabel);
		receiverColumnPanel2.createPanel(IOrder.SIGNBUILDING, "标志性建筑:", signBuildingLabel);
		receiverColumnPanel2.createPanel(IOrder.BESTTIME, "最佳送货时间:", bestTimeLabel);
		
		receiverMessagePanel.setPixelSize(800, 10);
		receiverMessagePanel.setBorderWidth(1);
		receiverMessagePanel.add(receiverColumnPanel1);
		receiverMessagePanel.add(receiverColumnPanel2);
		receiverPanel.setCollapsible(true);
		receiverPanel.setHeading("收货信息");
		receiverPanel.add(receiverMessagePanel);
		this.add(receiverPanel);
		
		/**
		 * 订单商品信息
		 */
		final ContentPanel goodsPanel = initailGoodsListPanel(orderGoodsStore);
		goodsPanel.setHeading("商品信息");
		this.add(goodsPanel);
		/**
		 * 费用信息
		 */
		final ContentPanel feePanel = initialFeePanel();
		feePanel.setCollapsible(true);
		this.add(feePanel);
		
		final ContentPanel operationPanel = new ContentPanel();
		ColumnPanel operationColumnPanel = new ColumnPanel();
		
		operationColumnPanel.createPanel(IOrderAction.NOTE, "操作备注：", remark);
		HorizontalPanel operationButtons = new HorizontalPanel();
		
		
		operationButtons.add(commitBt);
		operationButtons.add(payBt);
		operationButtons.add(shipBt);
		operationButtons.add(goodsRejectBt);
		operationButtons.add(cancleBt);
		operationButtons.add(doneBt);
		addButtonListner(orderGoodsStore);
		
		operationColumnPanel.createPanel(IOrder.ADDRESS, "当前可执行操作", operationButtons);
		operationPanel.add(operationColumnPanel);
		operationPanel.setCollapsible(true);
		this.add(operationPanel);
		
		/**
		 * 订单商品信息
		 */
		final ContentPanel orderActionPanel = initailOrderActionPanel();
		goodsPanel.setHeading("订单操作信息");
		this.add(orderActionPanel);

	}
	
	private void addButtonListner(final ListStore<BeanObject> orderGoodsStore)
	{
		commitBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
				if(null != theOrderInfo.getString(IOrder.USER))
				{
					userAddress.set(IUserAddress.CONSIGNEE, theOrderInfo.getString(IOrder.CONSIGNEE));
					userAddress.set(IUserAddress.EMAIL, theOrderInfo.getString(IOrder.EMAIL));
					userAddress.set(IUserAddress.ADDRESS, theOrderInfo.getString(IOrder.ADDRESS));
					userAddress.set(IUserAddress.ZIP, theOrderInfo.getString(IOrder.ZIP));
					userAddress.set(IUserAddress.PHONE, theOrderInfo.getString(IOrder.PHONE));
					userAddress.set(IUserAddress.MOBILE, theOrderInfo.getString(IOrder.MOBILE));
					userAddress.set(IUserAddress.SIGNBUILDING, theOrderInfo.getString(IOrder.SIGNBUILDING));
					userAddress.set(IUserAddress.BESTTIME,theOrderInfo.getString(IOrder.SIGNBUILDING));
					userAddress.set(IUserAddress.REGION, theOrderInfo.getString(IOrder.REGION));
					userAddress.set(IUserAddress.USER, theOrderInfo.getString(IOrder.USER));
					
//					userAddress.setModelName(ModelNames.USERADDRESS);
					
					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(IUserAddress.USER, Condition.EQUALS, 
							theOrderInfo.getString(IOrder.USER)));
					listService.listBeans(ModelNames.USERADDRESS, criteria, new ListService.Listener()
					{
						public void onSuccess(List<BeanObject> beans) 
						{
							if(beans.size() == 0)
							{
								new CreateService().createBean(userAddress, null);
							}
							else
							{
								BeanObject bean = beans.get(0);
								updateService.updateBean(bean.getString(IUserAddress.ID), userAddress, null);
							}
						}
					});
				}
				
				theOrderInfo.set(IOrder.STATUS, 100);
				theOrderInfo.set(IOrder.SHIPPINGSTATUS, 0);
				theOrderInfo.set(IOrder.PAYSTATUS, 0);
//				theOrderInfo.setModelName(ModelNames.ORDER);
				createService.createBean(theOrderInfo, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderID = id;
						
						for(int i=0; i<orderGoodsStore.getCount(); i++)
						{
							BeanObject anOrderGoods = orderGoodsStore.getAt(i);
							anOrderGoods.setModelName(ModelNames.ORDERGOODS);
							anOrderGoods.set(IOrderGoods.ORDER, id);
							createService.createBean(anOrderGoods, null);
						}
						
						String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//						orderAction.setModelName(ModelNames.ORDERACTION);
						
						orderAction.set(IOrderAction.ACTIONUSER, "admin");
						orderAction.set(IOrderAction.ORDERSTATUS, 100);
						orderAction.set(IOrderAction.PAYSTATUS, 0);
						orderAction.set(IOrderAction.SHIPPINGSTATUS, 0);
						orderAction.set(IOrderAction.ORDER, id);
						orderAction.set(IOrderAction.NOTE, "Commit: " + remark.getValue());
						orderAction.set(IOrderAction.LOGTIME, date);
						
						createService.createBean(orderAction, new CreateService.Listener(){

							public void onSuccess(String id)
							{
								orderActionID = id;
								orderActionCri.removeAll();
								orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
								toolBar.refresh();
							}
							
						});
					}});
				
//				commitBt.setVisible(false);
				setOrderStatus();
				remark.setValue("");
				setBtVisiable();
			}
			
		});
		
		payBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
				if(theOrderInfo.getString(IOrder.SHIPPINGSTATUS).equals("0"))
				{
					theOrderInfo.set(IOrder.STATUS, 110);
					orderAction.set(IOrderAction.ORDERSTATUS, 110);
				}
				else if(theOrderInfo.getString(IOrder.SHIPPINGSTATUS).equals("1"))
				{
					theOrderInfo.set(IOrder.STATUS, 111);
					orderAction.set(IOrderAction.ORDERSTATUS, 111);
				}
				else
				{
					Window.alert("Wrong!");
					return;
				}
				
				theOrderInfo.set(IOrder.PAYSTATUS, 1);
				String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				theOrderInfo.set(IOrder.PAYTIME, date);
				payForTimeLabel.setText(date);
				
				updateService.updateBean(orderID, theOrderInfo, null);
				orderAction.set(IOrderAction.PAYSTATUS, 1);
				orderAction.set(IOrderAction.LOGTIME, date);
				orderAction.set(IOrderAction.NOTE, "Pay : " + remark.getValue());
//				updateService.updateBean(orderActionID, orderAction, new UpdateService.Listener(){
//
//					public void onSuccess(Boolean success)
//					{
//						toolBar.refresh();
//					}
//					
//				});
				
				createService.createBean(orderAction, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderActionCri.removeAll();
						orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
						toolBar.refresh();
					}
					
				});
				
				setOrderStatus();
				
				remark.setValue("");
				setBtVisiable();
			}
		});
		
		shipBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
					
				if(theOrderInfo.getString(IOrder.PAYSTATUS).equals("0"))
				{
					theOrderInfo.set(IOrder.STATUS, 101);
					orderAction.set(IOrderAction.ORDERSTATUS, 101);
				}
				else if(theOrderInfo.getString(IOrder.PAYSTATUS).equals("1"))
				{
					theOrderInfo.set(IOrder.STATUS, 111);
					orderAction.set(IOrderAction.ORDERSTATUS, 111);
				}
				else
				{
					Window.alert("Wrong!");
					return;
				}
				
				theOrderInfo.set(IOrder.SHIPPINGSTATUS, 1);
				String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				String orderSN = convertDateStrToString(date);
				
				theOrderInfo.set(IOrder.SN, orderSN);
				theOrderInfo.set(IOrder.SHIPPINGTIME, date);
				
				orderSNLabel.setText(orderSN);
				shippingTimeLabel.setText(date);
				updateService.updateBean(orderID, theOrderInfo, null);
				
				
				orderAction.set(IOrderAction.SHIPPINGSTATUS, 1);
				orderAction.set(IOrderAction.LOGTIME, date);
				orderAction.set(IOrderAction.NOTE, "Shipping: " + remark.getValue());
//				updateService.updateBean(orderActionID, orderAction, null);
				createService.createBean(orderAction, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderActionCri.removeAll();
						orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
						toolBar.refresh();
					}
					
				});
				setOrderStatus();
				
				remark.setValue("");
				setBtVisiable();
			}
		});
		
		goodsRejectBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
				
				theOrderInfo.set(IOrder.STATUS, 99);
				theOrderInfo.set(IOrder.SHIPPINGSTATUS, -1);
				String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				theOrderInfo.set(IOrder.SHIPPINGTIME, "0000-00-00 00:00:00");
				shippingTimeLabel.setText("");
				
				updateService.updateBean(orderID, theOrderInfo, null);
				
				orderAction.set(IOrderAction.ORDERSTATUS, 99);
				orderAction.set(IOrderAction.SHIPPINGSTATUS, -1);
				orderAction.set(IOrderAction.LOGTIME, date);
				orderAction.set(IOrderAction.NOTE, "GoodsReject: " + remark.getValue());
//				updateService.updateBean(orderActionID, orderAction, null);
				createService.createBean(orderAction, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderActionCri.removeAll();
						orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
						toolBar.refresh();
					}
					
				});
				setOrderStatus();
				
				remark.setValue("");
				setBtVisiable();
			}
		});
		
		cancleBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
				
				theOrderInfo.set(IOrder.STATUS, 98);
				theOrderInfo.set(IOrder.SHIPPINGSTATUS, -2);
				theOrderInfo.set(IOrder.PAYSTATUS, -1);
				theOrderInfo.set(IOrder.SHIPPINGTIME, "");
				theOrderInfo.set(IOrder.PAYTIME, "");
				
				String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				shippingTimeLabel.setText("");
				payForTimeLabel.setText("");
				
				updateService.updateBean(orderID, theOrderInfo, null);
				
				orderAction.set(IOrderAction.ORDERSTATUS, 98);
				orderAction.set(IOrderAction.PAYSTATUS, -1);
				orderAction.set(IOrderAction.SHIPPINGSTATUS, -2);
				orderAction.set(IOrderAction.LOGTIME, date);
				orderAction.set(IOrderAction.NOTE, "Cancel orader: " + remark.getValue());
//				updateService.updateBean(orderActionID, orderAction, null);
				createService.createBean(orderAction, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderActionCri.removeAll();
						orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
						toolBar.refresh();
					}
					
				});
				setOrderStatus();
				remark.setValue("");
				setBtVisiable();
			}
		});
		
		doneBt.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) 
			{
				theOrderInfo.set(IOrder.STATUS, 1111);
				theOrderInfo.set(IOrder.SHIPPINGSTATUS, 1);
				theOrderInfo.set(IOrder.PAYSTATUS, 1);
				String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				
				updateService.updateBean(orderID, theOrderInfo, null);
				
				orderAction.set(IOrderAction.ORDERSTATUS, 1111);
				orderAction.set(IOrderAction.PAYSTATUS, 1);
				orderAction.set(IOrderAction.SHIPPINGSTATUS, 1);
				orderAction.set(IOrderAction.LOGTIME, date);
				orderAction.set(IOrderAction.NOTE, "Done orader: " + remark.getValue());
//				updateService.updateBean(orderActionID, orderAction, null);
				createService.createBean(orderAction, new CreateService.Listener(){

					public void onSuccess(String id)
					{
						orderActionCri.removeAll();
						orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
						toolBar.refresh();
					}
					
				});
				if(null != theOrderInfo.getString(IOrder.USER))
				{
					new ReadService().getBean(ModelNames.USER, theOrderInfo.getString(IOrder.USER), 
							new ReadService.Listener()
					{
						 public void onSuccess(final BeanObject user) 
						 {
							 double fee = Double.parseDouble(convertToString(theOrderInfo.getString(IOrder.PAYNOTE)));
							 int integral = (int)fee/200;
							 final int sumIntegral = Integer.parseInt(user.getString(IUser.PAYPOINTS)) + integral;

							 readService.getBean(ModelNames.USERRANK, user.getString(IUser.RANK), 
										new ReadService.Listener(){
								 public void onSuccess(BeanObject userRank) 
								 {
									 if(!(Double.parseDouble(userRank.getString(IUserRank.MAXPOINTS)) < sumIntegral))
									 {
										 user.set(IUser.PAYPOINTS, sumIntegral);
//										 user.set(IUser.ADDRESS, userAddress.getString(IUserAddress.ID));
										 updateService.updateBean(user.getString(IUser.ID), user, null); 
										 
									 }
								 }
							 });
						 }
					});
				}
				
				 for(int i=0; i<orderGoodsStore.getCount(); i++)
				 {
					 final BeanObject orderGoods = orderGoodsStore.getAt(i);
					 readService.getBean(ModelNames.GOODS, orderGoods.getString(IOrderGoods.GOODS),
							 new ReadService.Listener(){
						 public void onSuccess(BeanObject goods) 
						 {
							 
							 int number = Integer.parseInt(goods.getString(IGoods.NUMBER))- 
							 					Integer.parseInt(orderGoods.getString(IOrderGoods.GOODSNUMBER));
							
							 if(!(number>0))
							 {
								 Info.display("Warn", "The nubmer of " + goods.getString(IGoods.NAME) + " has less than 1," +
								 		" Please remerber to complementary goods");
							 }
							 goods.set(IGoods.NUMBER, number);
							 updateService.updateBean(goods.getString(IGoods.ID), goods, null);
						 }
					 });
					 
				 }
				setOrderStatus();
				remark.setValue("");
				setBtVisiable();
			}
		});
	}
	
	public ContentPanel initailGoodsListPanel(ListStore<BeanObject> orderGodosStore)
	{
		int pageSize = 1;
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		Grid<BeanObject> grid;
		PagingToolBar toolBar;
    	
    	toolBar = new PagingToolBar(pageSize);
		//The beanobject in table is IOrderGoods
    	columns.add(new ColumnConfig(IOrderGoods.ID, "物件", 80));
    	columns.add(new ColumnConfig(IOrderGoods.GOODS, "商品编号", 100));
		columns.add(new ColumnConfig(IOrderGoods.GOODSNAME, "商品名称", 100));
		columns.add(new ColumnConfig(IOrderGoods.GOODSSN, "货号", 80));
        columns.add(new ColumnConfig(IOrderGoods.GOODSPRICE, "价格", 80));
        ColumnConfig noColumn = new ColumnConfig(IOrderGoods.GOODSNUMBER, "数量", 80);
        columns.add(noColumn);
        columns.add(new ColumnConfig(IOrderGoods.MARKETPRICE, "小计", 80));
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new EditorGrid<BeanObject>(orderGodosStore, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
        
    	
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.add(grid);
        panel.setSize(780, 200);
        panel.setBottomComponent(toolBar);
        return panel;
	}
	
	public ContentPanel initailOrderActionPanel()
	{
		
		if( null != theOrderInfo.getString(IOrder.ID) && !(theOrderInfo.getString(IOrder.ID).equals("")))
		{
			orderID = theOrderInfo.getString(IOrder.ID);
		}
		orderActionCri.addCondition(new Condition(IOrderAction.ORDER, Condition.EQUALS, orderID));
		int pageSize = 10;
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ORDERACTION, orderActionCri);
		loader.load(0, 10);
		ListStore<BeanObject> orderActionStore = new ListStore<BeanObject>(loader);
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		Grid<BeanObject> grid;
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		//The beanobject in table is IOrderGoods
    	columns.add(new ColumnConfig(IOrderAction.ACTIONUSER, "操作者", 80));
    	columns.add(new ColumnConfig(IOrderAction.LOGTIME, "操作时间", 200));
		columns.add(new ColumnConfig(IOrderAction.ORDERSTATUS, "订单状态", 100));
		columns.add(new ColumnConfig(IOrderAction.SHIPPINGSTATUS, "发货状态", 80));
        columns.add(new ColumnConfig(IOrderAction.PAYSTATUS, "付款状态", 80));
        columns.add(new ColumnConfig(IOrderAction.NOTE, "备注", 200));
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new EditorGrid<BeanObject>(orderActionStore, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
        
    	
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.add(grid);
        panel.setSize(780, 200);
        panel.setBottomComponent(toolBar);
        return panel;
	}
	
	//convert ￥ 565.0 元  to 565.0
	private String convertToString(String entry)
	{
		System.out.println((entry.substring(2)).substring(0, entry.length()-4));
		return (entry.substring(2)).substring(0, entry.length()-4);
	}
	
	public ContentPanel initialFeePanel()
	{
		final ContentPanel feePanel = new ContentPanel();
		double goodsFee = Double.parseDouble(convertToString(theOrderInfo.getString(IOrder.PAYNOTE)));
		double discountFee = 0;
		if(!theOrderInfo.getString(IOrder.DISCOUNT).equals(""))
		{
			discountFee = (Double.parseDouble(theOrderInfo.getString(IOrder.DISCOUNT)))/100*goodsFee;
		}
		double taxFee = Double.parseDouble(theOrderInfo.getString(IOrder.TAX));
		double shippingFee = Double.parseDouble(theOrderInfo.getString(IOrder.SHIPPINGFEE));
		double insureFee = Double.parseDouble(theOrderInfo.getString(IOrder.INSUREFEE));
		double payFee = Double.parseDouble(theOrderInfo.getString(IOrder.PAYFEE));
		double packFee = Double.parseDouble(theOrderInfo.getString(IOrder.PACKFEE));
		double cardFee = Double.parseDouble(theOrderInfo.getString(IOrder.CARDFEE));
		double orderamount = goodsFee - discountFee + taxFee + shippingFee + insureFee + payFee
							+packFee +cardFee;
		double sum = goodsFee + taxFee + shippingFee + insureFee + payFee
		+packFee +cardFee;
		String feeStr = "商品总金额：￥"+ goodsFee +"元";
		feeStr += " - 折扣：￥"+ discountFee + "元";
		feeStr += " + 发票税额：￥"+ taxFee + "元";
		feeStr += " + 配送费用：￥"+ shippingFee + "元";
		feeStr += " + 保价费用：￥"+ insureFee + "元";
		feeStr += " + 支付费用：￥"+ payFee +"元";
		feeStr += " + 包装费用：￥"+ packFee +"元";
		feeStr += " + 贺卡费用：￥"+ cardFee +"元";
		String claFeeStr = "=应付款金额：￥"+ orderamount +"元";
		Label feeLabel = new Label(feeStr);
		Label claFeeLabel = new Label(claFeeStr);
		claFeeLabel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		feePanel.add(feeLabel);
		feePanel.add(claFeeLabel);
		feePanel.setCollapsible(true);
		
		theOrderInfo.set(IOrder.ORDERAMOUNT, orderamount);
		theOrderInfo.set(IOrder.MONEYPAID, sum);
		
        return feePanel;
	}
	
	//convert 2010.4月.05 公元 04:25 to 20104050425
	private  String convertDateStrToString(String date)
	{
		String once = date.replaceAll("\\-", "");
		String second = once.replaceAll("\\:", "");
		String third = second.replaceAll("\\ ", "");
		return third;
	}
	
	public void  initialOrderInfo()
	{
		String date = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
//		theOrderInfo.set(IOrder.USER, user.getString(IUser.NAME));
//		theOrderInfo.set(IOrder.PAYMENT, payments.getString(IPayment.ID));
//		theOrderInfo.set(IOrder.PAYNAME, payments.getString(IPayment.NAME));
//		theOrderInfo.set(IOrder.SHIPPING, shipping.getString(IShipping.ID));
//		theOrderInfo.set(IOrder.SHIPPINGNAME, shipping.getString(IShipping.NAME));
//		theOrderInfo.set(IOrder.CONSIGNEE, userAddress.getString(IUserAddress.CONSIGNEE));
		theOrderInfo.set(IOrder.ADDTIME, date);
//		theOrderInfo.set(IOrder.PACK, pack.getString(IPack.ID));
//		theOrderInfo.set(IOrder.PACKNAME, pack.getString(IPack.NAME));
//		theOrderInfo.set(IOrder.CARD, card.getString(ICard.ID));
//		theOrderInfo.set(IOrder.CARDNAME, card.getString(ICard.NAME));
		
		
		
//		theOrderInfo.set(IOrder.EMAIL, userAddress.getString(IUserAddress.EMAIL));
//		theOrderInfo.set(IOrder.PHONE, userAddress.getString(IUserAddress.PHONE));
//		theOrderInfo.set(IOrder.MOBILE, userAddress.getString(IUserAddress.MOBILE));
//		theOrderInfo.set(IOrder.SIGNBUILDING, userAddress.getString(IUserAddress.SIGNBUILDING));
//		theOrderInfo.set(IOrder.ADDRESS, userAddress.getString(IUserAddress.ADDRESS));
//		theOrderInfo.set(IOrder.ZIP, userAddress.getString(IUserAddress.ZIP));
//		theOrderInfo.set(IOrder.BESTTIME, userAddress.getString(IUserAddress.BESTTIME));
		
		int goodsNo = 0;
		for(int i=0; i<orderGoods.size(); i++)
		{
			BeanObject anOrderGoods = orderGoods.get(i);
			goodsNo += Integer.parseInt(anOrderGoods.getString(IOrderGoods.GOODSNUMBER));
		}
		theOrderInfo.set(IOrder.GOODSAMOUNT, goodsNo +"");
		
	}
	
	public void displayOrderInfo()
	{
		setOrderStatus();
		
		if(null == theOrderInfo.getString(IOrder.USER) ||theOrderInfo.getString(IOrder.USER).equals(""))
		{	
			userNameLabel.setText("匿名用户");
		}
		else
		{
			userNameLabel.setText(theOrderInfo.getString(IOrder.USER));
		}
		
		orderIDLabel.setText(theOrderInfo.getString(IOrder.ID));
		paymentsLabel.setText(theOrderInfo.getString(IOrder.PAYNAME));
		shippingLabel.setText(theOrderInfo.getString(IOrder.SHIPPINGNAME));
		
		addTimeLabel.setText(theOrderInfo.getString(IOrder.ADDTIME));
		shippingTimeLabel.setText(theOrderInfo.getString(IOrder.SHIPPINGTIME));
		payForTimeLabel.setText(theOrderInfo.getString(IOrder.PAYTIME));
		orderSNLabel.setText(theOrderInfo.getString(IOrder.SN));
		orderGeneratorLabel.setText("管理员添加");
		
		invoceTypeLabel.setText(theOrderInfo.getString(IOrder.INVOICETYPE));
		packLabel.setText(theOrderInfo.getString(IOrder.PACKNAME));
		howSurplusLabel.setText(theOrderInfo.getString(IOrder.HOWSURPLUS));
		invoceContentLabel.setText(theOrderInfo.getString(IOrder.INVOICECONTENT));
		invoceNoLabel.setText(theOrderInfo.getString(IOrder.INVOICENO));
		toBuyerLabel.setText(theOrderInfo.getString(IOrder.TOBUYER));
		cardLabel.setText(theOrderInfo.getString(IOrder.CARDNAME));
		cardMessagesLabel.setText(theOrderInfo.getString(IOrder.CARDMESSAGE));
		
		consigneeLabel.setText(theOrderInfo.getString(IOrder.CONSIGNEE));
		emailLabel.setText(theOrderInfo.getString(IOrder.EMAIL));
		phoneLabel.setText(theOrderInfo.getString(IOrder.PHONE));
		mobielLabel.setText(theOrderInfo.getString(IOrder.MOBILE));
		signBuildingLabel.setText(theOrderInfo.getString(IOrder.SIGNBUILDING));
		addressLabel.setText(theOrderInfo.getString(IOrder.ADDRESS));
		zipLabel.setText(theOrderInfo.getString(IOrder.ZIP));
		bestTimeLabel.setText(theOrderInfo.getString(IOrder.BESTTIME));
	}
	
	//OrderStatus should be changed as we operate the order.
	public void setOrderStatus()
	{
		String confirmstatus= "未确认";
		String paystatus= "未付款";
		String shippingstatus= "未发货";
		
		if(null != theOrderInfo.getString(IOrder.STATUS) &&
				Integer.parseInt(theOrderInfo.getString(IOrder.STATUS)) >= 99
				&& Integer.parseInt(theOrderInfo.getString(IOrder.STATUS))<112)
		{
			confirmstatus = "已确认";
		}
		else if(null != theOrderInfo.getString(IOrder.STATUS) &&
				Integer.parseInt(theOrderInfo.getString(IOrder.STATUS)) < 99)
		{
			confirmstatus = "订单已无效";
			orderStatusLabel.setText(confirmstatus);
			return;
		}
		else if(null != theOrderInfo.getString(IOrder.STATUS) &&
				Integer.parseInt(theOrderInfo.getString(IOrder.STATUS)) == 1111)
		{
			confirmstatus = "已成功交易";
			orderStatusLabel.setText(confirmstatus);
			
			return;
		}
		
		if(null != theOrderInfo.getString(IOrder.PAYSTATUS) &&
				theOrderInfo.getString(IOrder.PAYSTATUS).equals("1"))
		{
			paystatus = "已付款";
		}
		
		if(null != theOrderInfo.getString(IOrder.SHIPPINGSTATUS) && 
				theOrderInfo.getString(IOrder.SHIPPINGSTATUS).equals("1"))
		{
			shippingstatus = "已发货";
		}
		else if(null != theOrderInfo.getString(IOrder.SHIPPINGSTATUS) && 
				theOrderInfo.getString(IOrder.SHIPPINGSTATUS).equals("-1"))
		{
			shippingstatus = "已退货";
		}
		
		orderStatusLabel.setText(confirmstatus + ", " + paystatus + ", "+ shippingstatus);
		
	}

	public void setOrderID(String orderID)
	{
		this.orderID = orderID;
	}
	
	public void setOrderActionID(String actionID)
	{
		this.orderActionID = actionID;
	}
	
	public void setBtVisiable()
	{
		
		if(null != theOrderInfo.getString(IOrder.STATUS))
		{
			if (theOrderInfo.getString(IOrder.STATUS).equals("98"))
			{
				commitBt.setVisible(false);
				payBt.setVisible(false);
				shipBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(false);
				doneBt.setVisible(false);
			}
			else if (theOrderInfo.getString(IOrder.STATUS).equals("99"))
			{
				commitBt.setVisible(false);
				shipBt.setVisible(true);
				payBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(true);
				doneBt.setVisible(false);
			}
			else if (theOrderInfo.getString(IOrder.STATUS).equals("100"))
			{
				commitBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(false);
				shipBt.setVisible(true);
				payBt.setVisible(true);
				doneBt.setVisible(false);
			}
			else if (theOrderInfo.getString(IOrder.STATUS).equals("101"))
			{
				commitBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(false);
				shipBt.setVisible(false);
				payBt.setVisible(true);
				doneBt.setVisible(false);
			}
			else if (theOrderInfo.getString(IOrder.STATUS).equals("110"))
			{
				commitBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(false);
				shipBt.setVisible(true);
				payBt.setVisible(false);
				doneBt.setVisible(false);
			}
			else if (theOrderInfo.getString(IOrder.STATUS).equals("111"))
			{
				commitBt.setVisible(false);
				goodsRejectBt.setVisible(true);
				cancleBt.setVisible(false);
				shipBt.setVisible(false);
				payBt.setVisible(false);
				doneBt.setVisible(true);
			}
			else if(theOrderInfo.getString(IOrder.STATUS).equals("1111"))
			{
				commitBt.setVisible(false);
				payBt.setVisible(false);
				shipBt.setVisible(false);
				goodsRejectBt.setVisible(false);
				cancleBt.setVisible(false);
				doneBt.setVisible(false);
			}
		}
		else
		{
			commitBt.setVisible(true);
			shipBt.setVisible(false);
			goodsRejectBt.setVisible(false);
			cancleBt.setVisible(false);
			doneBt.setVisible(false);
		}
	}
	
	public BeanObject getOrderInfo()
	{
		return theOrderInfo;
	}

	public void setOrderGoods(List<BeanObject> orderGoods) 
	{
		this.orderGoods = orderGoods;
	}

	public Button getCommitBt()
	{
		return commitBt;
	}
	public Button getPayBt() {
		return payBt;
	}

	public Button getShipBt() {
		return shipBt;
	}


	public Button getGoodsRejectBt() {
		return goodsRejectBt;
	}

	public Button getCancleBt() {
		return cancleBt;

	}
	
	public Button getDoneBt() {
		return doneBt;
	}
}

