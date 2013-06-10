package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.TotalPriceCellRenderer;

public class OrderGoodsPanel extends ContentWidget{
	
	public static interface Constants {
		public String OrderGoods_title();
		public String OrderGoods_selectGoods();
		public String OrderGoods_ok();
		public String OrderGoods_cancel();
		public String OrderGoods_next();
		public String OrderGoods_delete();
		public String OrderGoods_goodsName();
		public String OrderGoods_goodsSN();
		public String OrderGoods_goodsPrice();
		public String OrderGoods_goodsNum();
		public String OrderGoods_goodsAttr();
		public String OrderGoods_subTotal();
		public String OrderGoods_action();
		public String OrderGoods_goodsCategory();
		public String OrderGoods_goodsBrand();
		public String OrderGoods_customPrice();
		public String OrderGoods_select();
		public String OrderGoods_addToOrder();
		public String OrderGoods_modifyOrderGoods();
		
	}

	public interface Message extends Messages {
		String OrderGoods_marketPrice(double price);
		String OrderGoods_shopPrice(double price);
	}
	
	private static OrderGoodsPanel instance = null;

	protected State getCurState() {
		return (State)curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		if(getCurState().getIsEdit())
			return Resources.constants.OrderGoods_title();
		else
			return Resources.constants.OrderUser_title();
	}
	
	public static OrderGoodsPanel getInstance(){
		if (instance == null) {
			instance = new OrderGoodsPanel();
		}
		return instance;
	}
	
	private OrderGoodsPanel() {
	    curState = new State();
		initJS(this);
	}

	public static class State extends PageState {

		public static final String PKID = "pkId";
		public static final String USERID = "userId";
		public static final String ISEDIT = "idEdit";
		
		public void setId(String id){
			setValue(PKID, id);
		}
		
		public String getPkId(){
			return (String)getValue(PKID);
		}
		
		public void setUserId(String id){
			setValue(USERID, id);
		}
		
		public String getUserId(){
			return (String)getValue(USERID);
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		
		public boolean getIsEdit(){
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		
		@Override
		public String getPageClassName() {
			return OrderGoodsPanel.class.getName();
		}
	}
	
	private PagingToolBar goodsToolBar;
	private ContentPanel goodsPanel;
	private ListBox lbGoods;
	private ContentPanel goodsInfoPanel;
	private BeanObject orderGoods;
	private Button btnNext;
	private Button btnOk;
	private Button btnCancel;
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		goodsPanel = new ContentPanel();
		renderGoodsPanel();
		add(goodsPanel);
		
		HorizontalPanel selectGoodsPanel = new HorizontalPanel();
		selectGoodsPanel.add(new Label(Resources.constants.OrderGoods_selectGoods() + ":"));
		lbGoods = new ListBox();
		lbGoods.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				selectGoods();
			}
	    });
		selectGoodsPanel.add(lbGoods);
		add(selectGoodsPanel);
		
		goodsInfoPanel = new ContentPanel();
		renderGoodsInfoPanel();
		add(goodsInfoPanel);
		
//		ContentPanel buttonPanel = new ContentPanel();
//		buttonPanel.setButtonAlign(Style.HorizontalAlignment.CENTER);

		
//		buttonPanel.addButton(btnOk);
//		buttonPanel.addButton(btnNext);
//		buttonPanel.addButton(btnCancel);
//		add(buttonPanel);
	}
	
	BasePagingLoader loader;
	private void renderGoodsPanel() {
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, getCurState().getPkId()));
		
		loader = new PagingListService().getLoader(ModelNames.ORDERGOODS, criteria);	  	
	    final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);	
	    store.addStoreListener(new StoreListener<BeanObject>() {
	    	public void storeDataChanged(StoreEvent<BeanObject> se) {
	    		List<Component> items = goodsPanel.getItems();
	    		for (Component item : items) {
	    			item.repaint();
	    		}
	    	}
	    });
	    
		goodsToolBar = new PagingToolBar(10);
		goodsToolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		ColumnConfig colGoodsName = new ColumnConfig(IOrderGoods.GOODSNAME, Resources.constants.Goods_name(), 100);
		columns.add(colGoodsName);
		ColumnConfig colGoodsSN = new ColumnConfig(IOrderGoods.GOODSSN, Resources.constants.Goods_SN(), 100);
		columns.add(colGoodsSN);
		ColumnConfig colGoodsPrice = new ColumnConfig(IOrderGoods.GOODSPRICE, Resources.constants.OrderDetail_price(), 100);
		colGoodsPrice.setNumberFormat(NumberFormat.getCurrencyFormat());
		//colGoodsPrice.setEditor(new CellEditor(new NumberField()));
		columns.add(colGoodsPrice);
		ColumnConfig colGoodsNum = new ColumnConfig(IOrderGoods.GOODSNUMBER, Resources.constants.OrderDetail_goodsNum(), 100);
		//colGoodsNum.setEditor(new CellEditor(new TextField<Number>()));
		columns.add(colGoodsNum);		
		ColumnConfig colGoodsAttr = new ColumnConfig(IOrderGoods.GOODSATTRIBUTE, Resources.constants.OrderDetail_goodsAttr(), 100);
		columns.add(colGoodsAttr);		
		ColumnConfig colTotalPrice = new ColumnConfig("subtotal", Resources.constants.OrderDetail_subTotalPrice(), 100);
		colTotalPrice.setRenderer(new TotalPriceCellRenderer());
		columns.add(colTotalPrice);
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.action(), 150);
        columns.add(actcol);
		
		ColumnModel cm = new ColumnModel(columns);		
		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setAutoHeight(true);
		grid.setAutoExpandColumn("Action");
		
		ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.OrderGoods_delete());
        act.setAction("deleteOrderGoods($id)");
        render.addAction(act);
        actcol.setRenderer(render);      
		goodsPanel.add(grid);
//		goodsPanel.setButtonAlign(Style.HorizontalAlignment.RIGHT);
//		goodsPanel.addButton(new Button(Resources.constants.OrderGoods_modifyOrderGoods(), new SelectionListener<ButtonEvent>() {
//			public void componentSelected(ButtonEvent ce) {
//				store.commitChanges();
//				updatePrice();
//			}
//		}));
		goodsPanel.setBottomComponent(goodsToolBar);
	}
	
	Label goodsNameLabel;
	Label goodsSnLabel;
	Label categoryLabel;
	Label brandLabel;
	HorizontalPanel radioPanel;
	RadioGroup rgPrice;
	Radio rdMarketPrice;
	Radio rdShopPrice;
	Radio rdCustomPrice;
	TextField<String> number;
	TextField<String> price;
	VerticalPanel attributePanel;
	LayoutContainer infoContainer;
	private void renderGoodsInfoPanel() {
		infoContainer = new LayoutContainer();
		TableLayout tl = new TableLayout(2);
		tl.setWidth("100%");
		tl.setBorder(1);
		infoContainer.setLayout(tl);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsName()));
		goodsNameLabel = new Label("");
		infoContainer.add(goodsNameLabel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsSN()));
		goodsSnLabel = new Label("");
		infoContainer.add(goodsSnLabel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsCategory()));
		categoryLabel = new Label("");
		infoContainer.add(categoryLabel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsBrand()));
		brandLabel = new Label("");
		infoContainer.add(brandLabel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsPrice()));
		radioPanel = new HorizontalPanel();
		radioPanel.setVisible(false);
		rgPrice = new RadioGroup();
		rdMarketPrice = new Radio();
		rdMarketPrice.setBoxLabel("");
		rdShopPrice = new Radio();
		rdShopPrice.setBoxLabel("");
		rdCustomPrice = new Radio();
		rdCustomPrice.setBoxLabel(Resources.constants.OrderGoods_customPrice());
		rgPrice.add(rdMarketPrice);
		rgPrice.add(rdShopPrice);
		rgPrice.add(rdCustomPrice);
		rgPrice.setValue(rdShopPrice);
		price = new TextField<String>();
		radioPanel.add(rgPrice);
		radioPanel.add(price);	
		infoContainer.add(radioPanel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsAttr()));
		attributePanel = new VerticalPanel();
		//attributePanel.setAutoHeight(true);
		infoContainer.add(attributePanel);
		
		infoContainer.add(new Label(Resources.constants.OrderGoods_goodsNum()));
		number = new TextField<String>();
		number.setValue("1");
		infoContainer.add(number);
		
		goodsInfoPanel.add(infoContainer);
		goodsInfoPanel.setButtonAlign(HorizontalAlignment.CENTER);
		goodsInfoPanel.addButton(new Button(Resources.constants.OrderGoods_addToOrder(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				addGoodsToOrder();
			}
		}));
		
	    btnNext = new Button(Resources.constants.OrderGoods_next());
	    goodsInfoPanel.addButton(btnNext);
        btnNext.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                // 判断是否添加商品
                Criteria c = new Criteria();
                c.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, getCurState().getPkId()));
                new ListService().listBeans(ModelNames.ORDERGOODS, c, new ListService.Listener() {
                    public void onSuccess(List<BeanObject> beans) {
                        if (beans.size() == 0) {
                            Window.alert(Resources.constants.OrderGoods_selectGoods());
                        } else {
                            ConsigneePanel.State newState = new ConsigneePanel.State();
                            newState.setOrderId(getCurState().getPkId());
                            newState.setUserId(getCurState().getUserId());
                            newState.setIsEdit(false);
                            newState.execute();
                        }
                    }
                });
            }
        });
	        
        btnOk = new Button(Resources.constants.OrderGoods_ok());
        goodsInfoPanel.addButton(btnOk);
        btnOk.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
	                Criteria c = new Criteria();
	                c.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, getCurState().getPkId()));
	                new ListService().listBeans(ModelNames.ORDERGOODS, c, new ListService.Listener() {
	                    public void onSuccess(List<BeanObject> beans) {
	                        if(beans.size() == 0) {
	                            Window.alert(Resources.constants.OrderGoods_selectGoods());
	                        }
	                        else {
	                            if(!isChange) {
	                                OrderDetailPanel.State newState = new OrderDetailPanel.State();
	                                newState.setId(getCurState().getPkId());
	                                newState.execute();
	                            }
	                            else if(isIncrease) {
	                                Success.State newState = new Success.State();
	                                newState.setMessage(Resources.constants.OrderDetail_orderAmountIncrease());
	                                OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
	                                choice1.setId(getCurState().getPkId());
	                                newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1);
	                                newState.execute();
	                            }
	                            else {
	                                Success.State newState = new Success.State();
	                                newState.setMessage(Resources.constants.OrderDetail_orderAmountDecrease());
	                                OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
	                                choice1.setId(getCurState().getPkId());
	                                newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1);
	                                newState.execute();
	                            }
	                        }
	                    }
	                });
	            }
	        });
	        
	        btnCancel = new Button(Resources.constants.OrderGoods_cancel());
	        goodsInfoPanel.addButton(btnCancel);
	        btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
	            public void componentSelected(ButtonEvent ce) {
	                cancel();
	            }
	        });
	}

	BeanObject goods = null;
	private void selectGoods() {
		final String goodsId = lbGoods.getValue(lbGoods.getSelectedIndex());
		new ReadService().getBean(ModelNames.GOODS, goodsId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				goods = bean;
				goodsNameLabel.setText((String) bean.get(IGoods.NAME));
				goodsSnLabel.setText(String.valueOf(bean.get(IGoods.SN)));
				String categoryId = bean.get(IGoods.MAINCATEGORY);
				String branId = bean.get(IGoods.BRAND);
				
				new ReadService().getBean(ModelNames.BRAND, branId, new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						brandLabel.setText((String) bean.get(IBrand.NAME));
					}
				});
				
				new ReadService().getBean(ModelNames.CATEGORY, categoryId, new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						categoryLabel.setText((String) bean.get(ICategory.NAME));
					}
				});
				
				Double marketPrice = bean.get(IGoods.MARKETPRICE);
				Double shopPrice = bean.get(IGoods.SHOPPRICE);
				Double promotePrice = bean.get(IGoods.PROMOTEPRICE);
				Boolean isPromote = bean.get(IGoods.PROMOTED);
				Long start = bean.get(IGoods.PROMOTESTART);
				Long end = bean.get(IGoods.PROMOTEEND);
				rdMarketPrice.setBoxLabel(Resources.messages.OrderGoods_marketPrice(marketPrice)); 
				rdMarketPrice.setValueAttribute(String.valueOf(marketPrice));
				//是否促销
				if(isPromote) {
					long now = new Date().getTime();
					if(now > start && now < end) {
						shopPrice = promotePrice;
					}
				}
				rdShopPrice.setBoxLabel(Resources.messages.OrderGoods_shopPrice(shopPrice));
				rdShopPrice.setValueAttribute(String.valueOf(shopPrice));
				radioPanel.setVisible(true);
				
				RemoteService.getSpecialService().getAttributeMap(goodsId, new AsyncCallback<Map<String, Map<String, String>>>(){
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window.alert("ERROR: "+caught.getMessage());
					}
					public void onSuccess(Map<String, Map<String, String>> result) {
						for(Object attributeName : result.keySet()) {
//							HorizontalPanel panel = new HorizontalPanel();
//							panel.setHeight("50");
//							panel.add(new Label(attributeName + ":"));
//							RadioGroup rgAttr = new RadioGroup();
//							panel.add(rgAttr);
//							Map<String, String> value = result.get(attributeName);
//							for(Object attributeValue : value.keySet()) {
//								String price = value.get(attributeValue) == null ? "0" : value.get(attributeValue);
//								Radio radio = new Radio();
//								radio.setBoxLabel(attributeValue + "[" + price + "]");
//								rgAttr.add(radio);
//							}

						}

						attributePanel.setHeight(24);
						
						Html panel = new Html("2223333333333333333333333333333");
						panel.setBorders(true);
						attributePanel.add(panel);
						attributePanel.repaint();
						infoContainer.repaint();
						repaint();
					}
				});
			}
		});
	}

	@Override
	public void refresh() {
		isChange = false;
		lbGoods.clear();
		lbGoods.addItem(Resources.constants.OrderGoods_select(), "0");
		new ListService().listBeans(ModelNames.GOODS, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					String goodsName = bean.get(IGoods.NAME);
					String goodsSN = bean.get(IGoods.SN);
					lbGoods.addItem((String)bean.get(IGoods.NAME) + " " + bean.get(IGoods.SN), (String)bean.get(IGoods.ID));
				}
			}
		});
		
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER, Condition.EQUALS, getCurState().getPkId()));
		proxy.setCriteria(criteria);
		goodsToolBar.refresh();
		
		btnOk.setVisible(false);
		btnNext.setVisible(false);
		if(getCurState().getIsEdit()) {
			btnOk.setVisible(true);
		}
		else
			btnNext.setVisible(true);
	}
	
	double goodsPrice;
	int goodsNum;
	private void addGoodsToOrder() {
		if(lbGoods.getSelectedIndex() == 0) {
			Window.alert(Resources.constants.OrderGoods_selectGoods());
		}
		else {
			try{
				if(rgPrice.getValue().equals(rdCustomPrice))
					goodsPrice = Double.parseDouble(price.getValue());
				else
					goodsPrice = Double.parseDouble(rgPrice.getValue().getValueAttribute());
			} catch (NumberFormatException e) {
				goodsPrice = 10;
			}
			
			try{
				goodsNum = Integer.parseInt(number.getValue());
			} catch(NumberFormatException e) {
				goodsNum = 0;
			}
			
			//添加OrderGoods
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(IOrderGoods.GOODS, lbGoods.getValue(lbGoods.getSelectedIndex()));
			props.put(IOrderGoods.ORDER, getCurState().getPkId());
			props.put(IOrderGoods.GOODSNAME, goods.get(IGoods.NAME));
			props.put(IOrderGoods.GOODSNUMBER, goodsNum);
			props.put(IOrderGoods.GOODSPRICE, goodsPrice);
			props.put(IOrderGoods.GOODSSN, goods.get(IGoods.SN));
			props.put(IOrderGoods.MARKETPRICE, goods.get(IGoods.MARKETPRICE));
			BeanObject orderGoods = new BeanObject(ModelNames.ORDERGOODS, props);
			new CreateService().createBean(orderGoods, new CreateService.Listener() {
				public void onSuccess(String id) {
					goodsToolBar.refresh();
					//修改OrderInfo中的价格
					updatePrice();
				}
			});
		}
	}
	
	boolean isChange = false;//付款后订单金额是否改变
	boolean isIncrease = false;//订单金额增加还是减少
	protected void updatePrice() {
		new ReadService().getBean(ModelNames.ORDER, getCurState().getPkId(), new ReadService.Listener() {
			public void onSuccess(final BeanObject bean) {
				new RemoteService().getSpecialService().getOrderFee(getCurState().getPkId(), null, null, new AsyncCallback<Map<String, String>>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window.alert("ERROR: "+caught.getMessage());
					}

					public void onSuccess(Map<String, String> result) {
						if(getCurState().getIsEdit()) {
							bean.set(IOrderInfo.SHIPPING_FEE, result.get("shippingFee"));
							bean.set(IOrderInfo.PAY_FEE, result.get("payFee"));
							if(((Number)bean.get(IOrderInfo.PAY_STATUS)).intValue() == IOrderInfo.PS_PAYED && (Double)bean.get(IOrderInfo.ORDER_AMOUNT) != new Double(result.get("amount"))) {
								isChange = true;
								if((Double)bean.get(IOrderInfo.ORDER_AMOUNT) > new Double(result.get("amount")))
									isIncrease = false;
								else {
									isIncrease = true;
									bean.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
								}
							}
						}
						
						bean.set(IOrderInfo.GOODS_AMOUNT, result.get("goodsPrice"));
						bean.set(IOrderInfo.ORDER_AMOUNT, result.get("amount"));
						
						
						new UpdateService().updateBean(getCurState().getPkId(), bean, null);
					}						
				});
			}
		});
	}

	private void cancel() {
		if(getCurState().getIsEdit()) {
			OrderDetailPanel.State newState = new OrderDetailPanel.State();
			newState.setId(getCurState().getPkId());
			newState.execute();
		}
		else {
			//删除订单及订单商品
			new RemoteService().getSpecialService().deleteOrder(getCurState().getPkId(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
				}

				public void onSuccess(Boolean result) {
				}
			});
			OrderListPanel.State newState = new OrderListPanel.State();
			newState.execute();			
		}
	}
	
	private native void initJS(OrderGoodsPanel me) /*-{
    $wnd.deleteOrderGoods = function (id) {
        me.@com.jcommerce.gwt.client.panels.order.OrderGoodsPanel::deleteOrderGoodsAndRefresh(Ljava/lang/String;)(id);
    };
    }-*/;
	
	private void deleteOrderGoodsAndRefresh(final String id) {
		new DeleteService().deleteBean(ModelNames.ORDERGOODS, id, new DeleteService.Listener() {
			public void onSuccess(Boolean success) {
				goodsToolBar.refresh();
				new ReadService().getBean(ModelNames.ORDER, getCurState().getPkId(), new ReadService.Listener() {
					public void onSuccess(final BeanObject bean) {
						new RemoteService().getSpecialService().getOrderFee(getCurState().getPkId(), null, null, new AsyncCallback<Map<String, String>>() {

							public void onFailure(Throwable caught) {
								caught.printStackTrace();
								Window.alert("ERROR: "+caught.getMessage());
							}

							public void onSuccess(Map<String, String> result) {
								bean.set(IOrderInfo.GOODS_AMOUNT, result.get("goodsPrice"));
								bean.set(IOrderInfo.ORDER_AMOUNT, result.get("amount"));
								
								if(getCurState().getIsEdit()) {
									bean.set(IOrderInfo.SHIPPING_FEE, result.get("shippingFee"));
									bean.set(IOrderInfo.PAY_FEE, result.get("payFee"));
								}
								
								new UpdateService().updateBean(getCurState().getPkId(), bean, null);
							}						
						});
					}
				});
			}
		});
	}
}
