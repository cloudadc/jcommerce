package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.RegionService;

public class SearchOrderPanel extends ContentWidget {
	public static interface Constants {
        String SearchOrder_address();
        String SearchOrder_addTime();
        String SearchOrder_buyer();
        String SearchOrder_email();
        String SearchOrder_orderState();
        String SearchOrder_payManner();
        String SearchOrder_payState();
        String SearchOrder_phone();
        String SearchOrder_region();
        String SearchOrder_reset();
        String SearchOrder_search();
        String SearchOrder_select();
        String SearchOrder_shipManner();
        String SearchOrder_shipState();
        String SearchOrder_tel();
        String SearchOrder_title();
        String SearchOrder_zipcode();
    }
	
	public static class State extends PageState {
		public String getPageClassName() {
			return SearchOrderPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.SearchOrder_title();
		}
	}
	private static final boolean instanceOf = false;
	
	@Override
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	private static SearchOrderPanel instance;
	private SearchOrderPanel() {
		super();
	}
	public static SearchOrderPanel getInstance(){
		if(instance == null) {
			instance = new SearchOrderPanel();
		}
		return instance;
	}
	

    ListBox countryList = new ListBox();
    ListBox provinceList = new ListBox();
    ListBox cityList = new ListBox();
    ListBox districtList = new ListBox();
    ListBox shipMannerList = new ListBox();
    ListBox payMannerList = new ListBox();
    ListBox orderStateList = new ListBox();
    ListBox payStateList = new ListBox();
    ListBox shipStateList = new ListBox();
    

    TextField<String> orderSnField = new TextField<String>();
    TextField<String> emailField = new TextField<String>();       
    TextField<String> buyerField = new TextField<String>();  
    TextField<String> consigneeField = new TextField<String>(); 
    TextField<String> addressField = new TextField<String>(); 
    TextField<String> zipcodeField = new TextField<String>();
    TextField<String> phoneField = new TextField<String>();
    TextField<String> telField = new TextField<String>();
    DateField fromDateField = new DateField();
    DateField toDateField = new DateField();
	
	protected void onRender(Element parent, int index) {
	    super.onRender(parent, index);
	    
	    HorizontalPanel snPanel = new HorizontalPanel();
	    Label snLabel = new Label(Resources.constants.OrderList_orderSN() + ":");
	    snLabel.setWidth("70");
	    snPanel.add(snLabel);
	    snPanel.add(orderSnField);
	    add(snPanel);
        
        HorizontalPanel emailPanel = new HorizontalPanel();
        Label emailLabel = new Label(Resources.constants.SearchOrder_email() + ":");
        emailLabel.setWidth("70");
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        add(emailPanel);
        
        HorizontalPanel bsPanel = new HorizontalPanel();
        HorizontalPanel buyerPanel = new HorizontalPanel();
        Label buyerLabel = new Label(Resources.constants.SearchOrder_buyer() + ":");
        buyerLabel.setWidth("70");
        buyerPanel.add(buyerLabel);
        buyerPanel.add(buyerField);
        buyerPanel.setWidth("500");
        bsPanel.add(buyerPanel);
        
        HorizontalPanel consigneePanel = new HorizontalPanel(); 
        Label consigneeLabel = new Label(Resources.constants.OrderList_consignee() + ":");
        consigneeLabel.setWidth("70");
        consigneePanel.add(consigneeLabel);
        consigneePanel.add(consigneeField);
        bsPanel.add(consigneePanel);
        add(bsPanel);
        
        HorizontalPanel azPanel = new HorizontalPanel();
        HorizontalPanel addressPanel = new HorizontalPanel();
        Label addressLabel = new Label(Resources.constants.SearchOrder_address() + ":");
        addressLabel.setWidth("70");
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);
        addressPanel.setWidth("500");
        azPanel.add(addressPanel);
        
        HorizontalPanel zipcodePanel = new HorizontalPanel();
        Label zipcodeLabel = new Label(Resources.constants.SearchOrder_zipcode() + ":");
        zipcodeLabel.setWidth("70");
        zipcodePanel.add(zipcodeLabel);
        zipcodePanel.add(zipcodeField);
        azPanel.add(zipcodePanel);
        add(azPanel);
        
        HorizontalPanel ptPanel = new HorizontalPanel();
        HorizontalPanel phonePanel = new HorizontalPanel();
        Label phoneLabel = new Label(Resources.constants.SearchOrder_phone() + ":");
        phoneLabel.setWidth("70");
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        phonePanel.setWidth("500");
        ptPanel.add(phonePanel);
        
        HorizontalPanel telPanel = new HorizontalPanel();
        Label telLabel = new Label(Resources.constants.SearchOrder_tel() + ":");
        telLabel.setWidth("70");
        telPanel.add(telLabel);
        telPanel.add(telField);
        ptPanel.add(telPanel);
        add(ptPanel);
        
        HorizontalPanel regionPanel = new HorizontalPanel();
        Label regionLabel = new Label(Resources.constants.SearchOrder_region() + ":");
        regionLabel.setWidth("70");
        regionPanel.add(regionLabel);
        regionPanel.add(countryList);
        countryList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String countryId = countryList.getValue(countryList.getSelectedIndex());
				if(countryId.equals("0")) {
					provinceList.setSelectedIndex(0);
					cityList.setSelectedIndex(0);
					districtList.setSelectedIndex(0);
				}
				else
					populateRegionList(provinceList,"1",countryId);
				
			}
        });
        
        regionPanel.add(provinceList);
        provinceList.addItem(Resources.constants.SearchOrder_select(), "0");
        provinceList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String provinceId = provinceList.getValue(provinceList.getSelectedIndex());
				if(provinceId.equals("0")) {
					cityList.setSelectedIndex(0);
					districtList.setSelectedIndex(0);
				}
				else
					populateRegionList(cityList,"2",provinceId);
				
			}
        });
        
        regionPanel.add(cityList);
        cityList.addItem(Resources.constants.SearchOrder_select(), "0");
        cityList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String cityId = cityList.getValue(cityList.getSelectedIndex());
				if(cityId.equals("0"))
					districtList.setSelectedIndex(0);
				else
					populateRegionList(districtList,"3",cityId);
				
			}
        });
        
        regionPanel.add(districtList);
        districtList.addItem(Resources.constants.SearchOrder_select(), "0");
        add(regionPanel);
        
        HorizontalPanel spPanel = new HorizontalPanel();
        HorizontalPanel shipMannerPanel = new HorizontalPanel();
        Label shipMannerLabel = new Label(Resources.constants.SearchOrder_shipManner() + ":");
        shipMannerLabel.setWidth("70");
        shipMannerPanel.add(shipMannerLabel);
        shipMannerPanel.add(shipMannerList);
        shipMannerPanel.setWidth("500");
        spPanel.add(shipMannerPanel);
        
        HorizontalPanel payMannerPanel = new HorizontalPanel();
        Label payMannerLabel = new Label(Resources.constants.SearchOrder_payManner() + ":");
        payMannerLabel.setWidth("70");
        payMannerPanel.add(payMannerLabel);
        payMannerPanel.add(payMannerList);
        spPanel.add(payMannerPanel);
        add(spPanel);
        
        HorizontalPanel addTimePanel = new HorizontalPanel();
        Label addTimeLabel = new Label(Resources.constants.SearchOrder_addTime() + ":");
        addTimeLabel.setWidth("70");
        addTimePanel.add(addTimeLabel);
        addTimePanel.add(fromDateField);
        addTimePanel.add(new Label("~"));
        addTimePanel.add(toDateField);
        add(addTimePanel);

        HorizontalPanel statePanel = new HorizontalPanel();
        Label orderStateLabel = new Label(Resources.constants.SearchOrder_orderState() + ":");
        orderStateLabel.setWidth("70");
        Label payStateLabel = new Label(Resources.constants.SearchOrder_payState() + ":");
        payStateLabel.setWidth("70");
        Label shipStateLabel = new Label(Resources.constants.SearchOrder_shipState() + ":");
        shipStateLabel.setWidth("70");
        
        statePanel.add(orderStateLabel);
        statePanel.add(orderStateList);
        orderStateList.addItem(Resources.constants.SearchOrder_select());
        orderStateList.addItem(Resources.constants.OrderStatus_OS_UNCONFIRMED(), "0");
        orderStateList.addItem(Resources.constants.OrderStatus_OS_CONFIRMED(), "1");
        orderStateList.addItem(Resources.constants.OrderStatus_OS_CANCELED(), "2");
        orderStateList.addItem(Resources.constants.OrderStatus_OS_INVALID(), "3");
        orderStateList.addItem(Resources.constants.OrderStatus_OS_RETURNED(), "4");
        
        statePanel.add(payStateLabel);
        statePanel.add(payStateList);
        payStateList.addItem(Resources.constants.SearchOrder_select());
        payStateList.addItem(Resources.constants.OrderStatus_PS_UNPAYED(), "0");
        payStateList.addItem(Resources.constants.OrderStatus_PS_PAYING(), "1");
        payStateList.addItem(Resources.constants.OrderStatus_PS_PAYED(), "2");
        
        statePanel.add(shipStateLabel);
        statePanel.add(shipStateList);
        shipStateList.addItem(Resources.constants.SearchOrder_select());
        shipStateList.addItem(Resources.constants.OrderStatus_SS_UNSHIPPED(), "0");
        shipStateList.addItem(Resources.constants.OrderStatus_SS_SHIPPED(), "1");
        shipStateList.addItem(Resources.constants.OrderStatus_SS_RECEIVED(), "2");
        shipStateList.addItem(Resources.constants.OrderStatus_SS_PREPARING(), "3");
        add(statePanel);
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        Button btnSearch = new Button(Resources.constants.SearchOrder_search());
        Button btnReset = new Button(Resources.constants.SearchOrder_reset());
        buttonPanel.add(btnSearch);
        btnSearch.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search();
			}
		});
        
        buttonPanel.add(btnReset);        
        btnReset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				reset();
			}
		});
        add(buttonPanel);   
	}
	
	protected void search() {				
		String buyer = buyerField.getValue();
		if(buyer != null) {
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IUser.NAME, Condition.EQUALS, buyer));
			new ListService().listBeans(ModelNames.USER, criteria,
					new ListService.Listener() {
						public synchronized void onSuccess(List<BeanObject> result) {
							String userId = null;
							for (Iterator<BeanObject> it = result.iterator(); it
									.hasNext();) {
								BeanObject user = it.next();
								userId = user.getString(IUser.ID);
							}							

							List<String> condition = getValue();
							if(userId != null)
								condition.add(IOrderInfo.USER_ID + ":" + userId);
							OrderListPanel.State newState = new OrderListPanel.State();
							newState.setCondition(condition);
							newState.execute();
						}
					});	
		}
		else {
			List<String> condition = getValue();
			OrderListPanel.State newState = new OrderListPanel.State();
			newState.setCondition(condition);
			newState.execute();
		}
		
	}
	
	private List<String> getValue() {
		
		List<String> condition = new ArrayList<String>();
		
		if(orderSnField.getValue() != null)
			condition.add(IOrderInfo.ORDER_SN + ":" + orderSnField.getValue());
		if(emailField.getValue() != null)
			condition.add(IOrderInfo.EMAIL + ":" + emailField.getValue());
		if(consigneeField.getValue() != null)
			condition.add(IOrderInfo.CONSIGNEE + ":" + consigneeField.getValue());
		if(addressField.getValue() != null)
			condition.add(IOrderInfo.ADDRESS + ":" + addressField.getValue());
		if(zipcodeField.getValue() != null)
			condition.add(IOrderInfo.ZIPCODE + ":" + zipcodeField.getValue());
		if(telField.getValue() != null)
			condition.add(IOrderInfo.MOBILE + ":" + telField.getValue());
		if(phoneField.getValue() != null)
			condition.add(IOrderInfo.TEL + ":" + phoneField.getValue());		
		
		String regionID = districtList.getValue(districtList.getSelectedIndex());
		if (regionID == null && cityList.getSelectedIndex() > 0) {
		    regionID = cityList.getValue(districtList.getSelectedIndex());
		}
        if (regionID == null && provinceList.getSelectedIndex() > 0) {
            regionID = provinceList.getValue(districtList.getSelectedIndex());
        }
        if (regionID == null && countryList.getSelectedIndex() > 0) {
            regionID = countryList.getValue(districtList.getSelectedIndex());
        }
		
		if(regionID != null)
			condition.add(IOrderInfo.REGION+ ":" + regionID);

		if(payMannerList.getSelectedIndex() > 0)
			condition.add(IOrderInfo.PAY_ID + ":" + payMannerList.getValue(payMannerList.getSelectedIndex()));
		if(shipMannerList.getSelectedIndex() > 0)
			condition.add(IOrderInfo.SHIPPING_ID + ":" + shipMannerList.getValue(shipMannerList.getSelectedIndex()));
		if(orderStateList.getSelectedIndex() > 0)
			condition.add(IOrderInfo.ORDER_STATUS + ":" + orderStateList.getValue(orderStateList.getSelectedIndex()));
		if(payStateList.getSelectedIndex() > 0)
			condition.add(IOrderInfo.PAY_STATUS + ":" + payStateList.getValue(payStateList.getSelectedIndex()));
		if(shipStateList.getSelectedIndex() > 0)
			condition.add(IOrderInfo.SHIPPING_STATUS + ":" + shipStateList.getValue(shipStateList.getSelectedIndex()));
		
		//为日期加上时间
		if(fromDateField.isDirty()) {
			Date now = new Date();
			int hours = now.getHours();
			int minute = now.getMinutes();
			int second = now.getSeconds();
			
			Date fromDate = fromDateField.getValue();
			fromDate.setHours(hours);
			fromDate.setMinutes(minute);
			fromDate.setSeconds(second);
			
			condition.add("fromDate" + ":" + String.valueOf(fromDate.getTime()));
		}
		if(toDateField.isDirty()) {
			Date now = new Date();
			int hours = now.getHours();
			int minute = now.getMinutes();
			int second = now.getSeconds();
			
			Date toDate = toDateField.getValue();
			toDate.setHours(hours);
			toDate.setMinutes(minute);
			toDate.setSeconds(second);
			
			condition.add("toDate" + ":" + String.valueOf(toDate.getTime()));
		}
		return condition;
	}
	public void refresh() {
		payMannerList.clear();
		payMannerList.addItem(Resources.constants.SearchOrder_select(), "0");
		new ListService().listBeans(ModelNames.PAYMENT,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject payment = it.next();
							payMannerList.addItem(payment.getString(IPayment.NAME),
									payment.getString(IPayment.ID));
						}
					}
				});
		
		shipMannerList.clear();
		shipMannerList.addItem(Resources.constants.SearchOrder_select(), "0");
		new ListService().listBeans(ModelNames.SHIPPING,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject shipping = it.next();
							shipMannerList.addItem(shipping.getString(IShipping.NAME),
									shipping.getString(IShipping.ID));
						}
					}
				});
		
		populateRegionList(countryList,"0",null);
		
		reset();
	}
	
	private void reset() {
		countryList.setSelectedIndex(0);
		provinceList.clear();
		provinceList.addItem(Resources.constants.SearchOrder_select(), "0");
		cityList.clear();
		cityList.addItem(Resources.constants.SearchOrder_select(), "0");
	    districtList.clear();
	    districtList.addItem(Resources.constants.SearchOrder_select(), "0");
	    shipMannerList.setSelectedIndex(0);
	    payMannerList.setSelectedIndex(0);
	    orderStateList.setSelectedIndex(0);
	    payStateList.setSelectedIndex(0);
	    shipStateList.setSelectedIndex(0);	    

	    orderSnField.setValue("");
	    emailField.setValue("");      
	    buyerField.setValue("");
	    consigneeField.setValue(""); 
	    addressField.setValue("");
	    zipcodeField.setValue("");
	    phoneField.setValue("");
	    telField.setValue("");
	    fromDateField.clear();
	    toDateField.clear();
	}
	
	private void populateRegionList(final ListBox regionList, String regionType, String parentId) {
		regionList.clear();
		regionList.addItem(Resources.constants.SearchOrder_select(), "0");
		new RegionService().getRegionChildList(parentId, new RegionService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject province = it.next();
							regionList.addItem(province.getString(IRegion.NAME), province.getString(IRegion.ID));
						}
					}
				});
	}
	
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
		return Resources.constants.SearchOrder_title();	
	}

}
