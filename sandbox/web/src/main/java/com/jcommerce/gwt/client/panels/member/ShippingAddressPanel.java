package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.UserAddressCellRenderer;

public class ShippingAddressPanel extends ContentWidget {

	public static interface Constants {
		String ShippingAddress_address();
		String ShippingAddress_bestTime();
		String ShippingAddress_build();
		String ShippingAddress_consignee();
		String ShippingAddress_contactManner();
		String ShippingAddress_email();
		String ShippingAddress_mobilePhone();
		String ShippingAddress_other();
		String ShippingAddress_phone();
		String ShippingAddress_title();
    }
	
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return ShippingAddressPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.ShippingAddress_title();
		}
	}
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	int pageSize = 5;
	
	PagingToolBar toolBar; 
	
	public static ShippingAddressPanel getInstance(){
		if(instance==null) {
    		instance = new ShippingAddressPanel();
    	}
    	return instance;
	}

	private static ShippingAddressPanel instance;
    private ShippingAddressPanel() {
    	super();
    }

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.ShippingAddress_title();
	}
	
	Grid<BeanObject> grid = null;
	BasePagingLoader loader = null;
	
	UserAddressCellRenderer addressRender = null; 
 	UserAddressCellRenderer contactRender = null;
 	UserAddressCellRenderer otherRender = null;
 	
 	ColumnConfig addressCol = null;
	ColumnConfig contactCol = null;
	ColumnConfig otherCol = null;
	ColumnModel cm = null;
	
	protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
    	String pkId = getCurState().getId();
    	Criteria c = new Criteria();
    	c.addCondition(new Condition(IUserAddress.USER, Condition.EQUALS, pkId));
    	loader = new PagingListService().getLoader(ModelNames.USERADDRESS, c);

//      loader.load(0, 50);
  	
    	final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

    	store.addStoreListener(new StoreListener<BeanObject>() {
    		public void storeUpdate(StoreEvent<BeanObject> se) {
    			List<Record> changed = store.getModifiedRecords();
    		}
			@Override
			public void storeDataChanged(StoreEvent<BeanObject> se) {
				// TODO Auto-generated method stub
				super.storeDataChanged(se);
				List storeData = se.getStore().getModels();
				for (Object object : storeData) {
					BeanObject bean = (BeanObject) object;
					contactRender = new UserAddressCellRenderer(grid, "contact");
					contactRender.setMobile(bean.getString("mobile"));
					contactRender.setTel(bean.getString("tel"));
					contactRender.setEmail(bean.getString("email"));
					contactCol.setRenderer(contactRender);
					
					otherRender = new UserAddressCellRenderer(grid, "other");
					otherRender.setTime(bean.getString("bestTime"));
					otherRender.setBuild(bean.getString("signBuilding"));
					otherCol.setRenderer(otherRender);

					addressRender = new UserAddressCellRenderer(grid, "address");
					addressRender.setZipcode(bean.getString("zipcode"));
					final String countryId = bean.getString("country");
					final String provinceId = bean.getString("province");
					final String cityId = bean.getString("city");
					final String districtId = bean.getString("district");
					
					new ListService().listBeans(ModelNames.REGION,null,new ListService.Listener(){
						@Override
						public void onSuccess(List<BeanObject> beans) {
							for(BeanObject bean : beans) {
								String id = bean.getString("pkId");
								if(id.equals(countryId)) {
									addressRender.setCountry(bean.getString("regionName"));
								}
								else if(id.equals(provinceId)) {
									addressRender.setProvince(bean.getString("regionName"));
								}
								else if(id.equals(cityId)) {
									addressRender.setCity(bean.getString("regionName"));
								}
								else if(id.equals(districtId)) {
									addressRender.setDistrict(bean.getString("regionName"));
								}
							}
							addressCol.setRenderer(addressRender);
							grid.reconfigure(store, cm);
						}
			    	});
				}				
			}    		
    	});
    	
    	toolBar = new PagingToolBar(50);
    	toolBar.bind(loader);

    	List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
      
    	columns.add(new ColumnConfig(IUserAddress.CONSIGNEE, Resources.constants.ShippingAddress_consignee(), 140));
    	addressCol = new ColumnConfig(Resources.constants.ShippingAddress_address(), Resources.constants.ShippingAddress_address(), 150);
    	contactCol = new ColumnConfig(Resources.constants.ShippingAddress_contactManner(), Resources.constants.ShippingAddress_contactManner(), 170);
    	otherCol = new ColumnConfig(Resources.constants.ShippingAddress_other(), Resources.constants.ShippingAddress_other(), 170);
    	columns.add(addressCol);
    	columns.add(contactCol);
    	columns.add(otherCol);
    	
    	cm = new ColumnModel(columns);

    	grid = new Grid<BeanObject>(store, cm);
    	grid.setLoadMask(true);
     	grid.setBorders(true);


//     	addressRender = new TextCellRender(grid, "a");
//     	addressCol.setRenderer(addressRender);   
//     	contactRender = new TextCellRender(grid, "b");
//     	contactCol.setRenderer(contactRender);
//     	otherRender = new TextCellRender(grid, "c");
//     	otherCol.setRenderer(otherRender);
      
     	ContentPanel panel = new ContentPanel();
     	panel.setFrame(true);
     	panel.setCollapsible(true);
     	panel.setAnimCollapse(false);
     	panel.setButtonAlign(HorizontalAlignment.CENTER);
     	panel.setIconStyle("icon-table");
     	panel.setHeading("Paging Grid");
     	panel.setLayout(new FitLayout());
     	panel.add(grid);
     	panel.setSize(800, 350);
//      panel.setSize("100%", "350px");
     	panel.setBottomComponent(toolBar);     
      
     	panel.setButtonAlign(HorizontalAlignment.CENTER);
     
     	add(panel);       
    }
	
    public void refresh() {
    	refreshAddressList();
    	toolBar.refresh();
    }
    
    private void refreshAddressList() {
		String pkId = getCurState().getId();
		Criteria criteria = new Criteria(); 
		criteria.addCondition(new Condition(IUserAddress.USER, Condition.EQUALS, pkId));
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);	
	}

    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.UserList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonAddClicked();
          }
      });
      return buttonAddClone;
    }
    
    public void onButtonAddClicked() {
		UserListPanel.State newState = new UserListPanel.State();
		newState.execute();
    }



}
