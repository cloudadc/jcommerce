package com.jcommerce.gwt.client.panels.order;

import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IAreaRegion;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;

public class MergeOrderPanel extends ContentWidget {
	public static interface Constants {
		String MergeOrder_confirmMerge();
        String MergeOrder_mainOrder();
        String MergeOrder_merge();
        String MergeOrder_select();
        String MergeOrder_selectOrder();
        String MergeOrder_subOrder();
        String MergeOrder_title();
        String MergeOrder_differentUsers();
        String MergeOrder_sameOrder();
        String MergeOrder_success();
        String MergeOrder_fail();
    }
	public interface MergeOrderMessage extends Messages {
		String MergeOrder_wrongOrder(String orderSn);
	}
	
	public static class State extends PageState {
		public String getPageClassName() {
			return MergeOrderPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.MergeOrder_title();
		}
	}
	private static final boolean instanceOf = false;
	
	public State getCurState() {
		return (State)curState;
	}
	
	private static MergeOrderPanel instance;
	private MergeOrderPanel() {
		super();
		curState = new State();
	}
	public static MergeOrderPanel getInstance(){
		if(instance == null) {
			instance = new MergeOrderPanel();
		}
		return instance;
	}	

    ListBox mainOrderList = new ListBox();
    ListBox subOrderList = new ListBox();    

    TextField<String> mainOrderField = new TextField<String>();
    TextField<String> subOrderField = new TextField<String>();      
    
    BeanObject mainOrder = null;
    BeanObject subOrder = null;
	
	protected void onRender(Element parent, int index) {
	    super.onRender(parent, index);
	    
	    HorizontalPanel mainOrderPanel = new HorizontalPanel();
	    mainOrderPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
	    Label mainOrderLabel = new Label(Resources.constants.MergeOrder_mainOrder() + ":");
	    mainOrderLabel.setWidth("70");
	    mainOrderPanel.add(mainOrderLabel);
	    mainOrderPanel.add(mainOrderField);
	    mainOrderPanel.add(mainOrderList);
	    mainOrderList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(mainOrderList.getSelectedIndex() > 0)
					mainOrderField.setValue(mainOrderList.getValue(mainOrderList.getSelectedIndex()));
			}
	    });
	    add(mainOrderPanel);
        
	    HorizontalPanel subOrderPanel = new HorizontalPanel();
	    subOrderPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
	    Label subOrderLabel = new Label(Resources.constants.MergeOrder_subOrder() + ":");
	    subOrderLabel.setWidth("70");
	    subOrderPanel.add(subOrderLabel);
	    subOrderPanel.add(subOrderField);
	    subOrderPanel.add(subOrderList);
	    subOrderList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(subOrderList.getSelectedIndex() > 0)
					subOrderField.setValue(subOrderList.getValue(subOrderList.getSelectedIndex()));
			}
	    });
	    add(subOrderPanel);
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
        Button btnSearch = new Button(Resources.constants.MergeOrder_merge());
        buttonPanel.add(btnSearch);
        btnSearch.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				MessageBox.confirm(Resources.constants.MergeOrder_confirmMerge(), Resources.constants.MergeOrder_confirmMerge(), new com.extjs.gxt.ui.client.event.Listener<MessageBoxEvent>() {  
		        	public void handleEvent(MessageBoxEvent ce) {  
		                Button btn = ce.getButtonClicked(); 
		                if ( btn.getItemId().equals("yes")){
		    				merge();		                	
		                }
		            }  
		        });
			}
		});
        add(buttonPanel);   
	}	
	
	protected void merge() {
		if(getOrder()) {
			new WaitService(new WaitService.Job() {
				public boolean isReady() {
					if(isReady < 2) {
						return false;
					}
					else {
						isReady = 0;
						return true;
					}
				}
	
				public void run() {
					if(mainOrder == null) {
						Window.alert(Resources.messages.MergeOrder_wrongOrder(mainOrderField.getValue()));
					}
					else if(subOrder == null) {
						Window.alert(Resources.messages.MergeOrder_wrongOrder(subOrderField.getValue()));
					}
					else {
						String mainOrderUser = mainOrder.get(IOrderInfo.USER_ID);
						String subOrderUser = subOrder.get(IOrderInfo.USER_ID);
						if(!mainOrderUser.equals(subOrderUser))
							Window.alert(Resources.constants.MergeOrder_differentUsers());
						else
							mergeOrder();
					}
				}
			});
		}
	}
	
	String shippingCodFee = null;
	List<String> regionIdList;
	//合并订单
	protected void mergeOrder() {
		new RemoteService().getSpecialService().mergeOrder((String)subOrder.get(IOrderInfo.ID), (String)mainOrder.get(IOrderInfo.ID), new AsyncCallback<Boolean>(){
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(Boolean result) {
				if(result) {
					Window.alert(Resources.constants.MergeOrder_success());
					refresh();
				}
				else {
					Window.alert(Resources.constants.MergeOrder_fail());
					refresh();
				}
			}
		});
	}
	
	int isReady = 0;
	private boolean getOrder() {	
		mainOrder = null;
		subOrder = null;
		
		String mainOrderSN = mainOrderField.getValue();
		String subOrderSN = subOrderField.getValue();
		
		if(mainOrderSN == null || subOrderSN == null) {
			Window.alert(Resources.constants.MergeOrder_selectOrder());
			return false;
		}
		else if(mainOrderSN.equals(subOrderSN)) {
			Window.alert(Resources.constants.MergeOrder_sameOrder());
			return false;
		}
		else {
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, mainOrderSN));		
			new ListService().listBeans(ModelNames.ORDER, criteria, new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
						BeanObject order = it.next();
						mainOrder = order;
					}
					isReady++;
				}
			});
			
			criteria.removeAll();
			criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, subOrderSN));
			new ListService().listBeans(ModelNames.ORDER, criteria, new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
						BeanObject order = it.next();
						subOrder = order;
					}
					isReady++;
				}
			});	
			return true;
		}
	}
	
	public void refresh() {
		//获得可合并订单,可合并订单的条件，未付款&&未配送&&(未确认||已确认)
		Criteria criteria = new Criteria();
    	Condition unPayed = new Condition(IOrderInfo.PAY_STATUS, Condition.EQUALS, "0");
    	Condition unShipped = new Condition(IOrderInfo.SHIPPING_STATUS, Condition.EQUALS, "0");
    	Condition unConfirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "0");
    	Condition confirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "1");
    	criteria.addCondition(unPayed);
    	criteria.addCondition(unShipped);
    	criteria.addCondition(confirmed);
    	
    	mainOrderList.clear();
    	subOrderList.clear();
    	mainOrderList.addItem(Resources.constants.MergeOrder_select(), "0");
    	subOrderList.addItem(Resources.constants.MergeOrder_select(), "0");
    	mainOrderField.clear();
    	subOrderField.clear();
    	populateOrderList(criteria);  
    	
    	criteria.removeCondition(confirmed);
    	criteria.addCondition(unConfirmed);
    	populateOrderList(criteria);
    	
	}	
	
	private void populateOrderList(Criteria criteria) {
		new ListService().listBeans(ModelNames.ORDER, criteria,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							final BeanObject order = it.next();
							String userId = order.getString(IOrderInfo.USER_ID);
							new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener() {
								public void onSuccess(BeanObject bean) {
									mainOrderList.addItem(order.getString(IOrderInfo.ORDER_SN) + "[" + bean.get(IUser.NAME) + "]",
											order.getString(IOrderInfo.ORDER_SN));
									subOrderList.addItem(order.getString(IOrderInfo.ORDER_SN) + "[" + bean.get(IUser.NAME) + "]",
											order.getString(IOrderInfo.ORDER_SN));
								}
							});
						}
					}
				});	
	}
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	
    public Button getShortCutButton() {
      Button buttonOrderList = new Button(Resources.constants.OrderList_title());
      buttonOrderList.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonOrderList;
    }
    public void onButtonListClicked() {
		OrderListPanel.State newState = new OrderListPanel.State();
		newState.execute();
    }

	@Override
	public String getName() {
		return Resources.constants.MergeOrder_title();	
	}

}
