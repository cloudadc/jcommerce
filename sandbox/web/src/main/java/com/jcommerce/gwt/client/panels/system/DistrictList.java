package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;


public class DistrictList extends ContentWidget {

	public DistrictList() {
		initJS(this);
	}

	public static class State extends PageState {
	    String parentId;
		public String getPageClassName() {
			return DistrictList.class.getName();
		}

		public String getMenuDisplayName() {
			return Resources.constants.District_list();
		}

		public void setParentId(String parentId) {
			setValue("parentId", parentId);
		    this.parentId = parentId;
		}

		public String getParentId() {
//			return (String) getValue("parentId");
		    return parentId;
		}		
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	private ColumnPanel contentOutPanel = new ColumnPanel();
	private ColumnPanel addPanel = new ColumnPanel();
	private ContentPanel tablePanel = new ContentPanel();
	private Grid<BeanObject> grid;
	private Button btnAdd = new Button(Resources.constants.Add_button());
	private Button btnBack = new Button();
	private PagingToolBar toolBar = new PagingToolBar(10);
	ListStore<BeanObject> store;
	String parentName;
	String grandparentId;
	int currentDistrictType = IRegion.TYPE_COUNTRY;
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		btnBack.setText(Resources.constants.RegionList_previousLevel());
		btnBack.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				onBackClicked();
			}
		});

		add(contentOutPanel);

		addPanel.createPanel(IRegion.NAME, Resources.constants.Add_country(),
				new TextBox());

		HorizontalPanel header = new HorizontalPanel();
		header.add(addPanel);
		header.add(btnAdd);
        header.add(btnBack);

		contentOutPanel.add(header);

		btnAdd.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				addRegion();
			}
		});

		displayTable();

		tablePanel.add(this.grid);
		tablePanel.setFrame(true);
		tablePanel.setCollapsible(true);
		tablePanel.setAnimCollapse(false);
		tablePanel.setButtonAlign(HorizontalAlignment.CENTER);
		tablePanel.setIconStyle("icon-table");

		tablePanel.setLayout(new FitLayout());
		tablePanel.setHeading(Resources.constants.Country_district());
		tablePanel.setHeight(500);
		tablePanel.setWidth("100%");
		tablePanel.setBottomComponent(toolBar);
		tablePanel.setButtonAlign(HorizontalAlignment.CENTER);

		add(tablePanel);

        String parentId = getCurState().getParentId();
//		btnBack.setVisible(parentId != null);		
	}

	private void displayTable() {

//		Criteria criteria = new Criteria();
		String parentId = getCurState().getParentId();
//		
//		List<String> wantedFields = new ArrayList<String>();
//		Condition cond = new Condition(IRegion.PARENT, Condition.EQUALS,
//				parentId);
//		criteria.addCondition(cond);
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
        cond.setOperator(Condition.EQUALS);
		if (parentId == null) {
            cond.setField(IRegion.TYPE);
            cond.setValue(""+IRegion.TYPE_COUNTRY);		    
		} else {
    		cond.setField(IRegion.PARENT);
    		cond.setValue(parentId);
		}
		criteria.addCondition(cond);	

		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.REGION, criteria);
		loader.load(0, 10);
		toolBar.bind(loader);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateDistrict(bean, null);
				}
			}
		});
        this.store = store;
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		ColumnConfig district = new ColumnConfig(IRegion.NAME,
				Resources.constants.Region_name(), 300);
		district.setEditor(new CellEditor(new TextField()));
		columns.add(district);
		ColumnConfig actcol1 = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 400);
		columns.add(actcol1);
		ColumnModel cm = new ColumnModel(columns);

		grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		ActionCellRenderer render = new ActionCellRenderer();
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		if(currentDistrictType != IRegion.TYPE_DISTRICT){
			act.setText("管理 ");
			act.setAction("onManageClick($id)");
			act.setTooltip(Resources.constants.Region_action_manage());
			render.addAction(act);
		}
		
	
		act = new ActionCellRenderer.ActionInfo();
		act.setText(" 删除");
		act.setAction("onDeleteClick($id)");
		act.setTooltip(Resources.constants.Region_action_delete());
		render.addAction(act);
		actcol1.setRenderer(render);

	}

	private void updateDistrict(BeanObject district,
			UpdateService.Listener listener) {
		new UpdateService().updateBean(district.getID(),
				district, listener);
	}

	private native void initJS(DistrictList me) /*-{
		$wnd.onManageClick = function (id) {      
			me.@com.jcommerce.gwt.client.panels.system.DistrictList::manageRegion(Ljava/lang/String;)(id);
		};
		$wnd.onDeleteClick = function (id) {
			me.@com.jcommerce.gwt.client.panels.system.DistrictList::deleteRegion(Ljava/lang/String;)(id);
		};
	}-*/;
    private void addRegion(){
    	// save information
		final Map<String, Object> district_name = addPanel.getValues();
		int flag = 0;
		if((String)district_name.get(IRegion.NAME)!=null&&
				(String)district_name.get(IRegion.NAME)!=""){
			for(int i = 0;i<store.getCount();i++){
				
				if(store.getAt(i).get(IRegion.NAME).equals((String)district_name.get(IRegion.NAME))){
					MessageBox.alert("error", "error", null);
					flag=1;
					break;
				}
			}
			if(flag==0){
				
				district_name.put(IRegion.PARENT, getCurState().getParentId());
				district_name.put(IRegion.TYPE, currentDistrictType);

				addPanel.clearValues();

				new CreateService().createBean(new BeanObject(
					ModelNames.REGION, district_name),
					new CreateService.Listener() {
						public synchronized void onSuccess(String id) {
							toolBar.refresh();
						}
					});				
			}
											
		}else{
			MessageBox.alert("error", "error", null);
		}
    }
	private void deleteRegion(final String id) {
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField(IRegion.PARENT);
		cond.setOperator(Condition.EQUALS);	
		cond.setValue(id); 
		criteria.addCondition(cond);

		new ListService().listBeans(ModelNames.REGION, criteria,
			new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					
					if (beans.size() > 0) {
						MessageBox.alert("error", "error", null);
						
					} else {
						new DeleteService().deleteBean(ModelNames.REGION,
								id, new DeleteService.Listener() {
										public void onSuccess(Boolean success) {
											Info.display("Congratulation",
													"deleted: id=" + id);
											toolBar.refresh();

										}
									});
					}
				}
			});

	}

	private void manageRegion(String id) {	    
		State newState = new State();
		newState.setParentId(id);
		newState.execute();		
	}

	public void refresh() {
		String parentId = getCurState().getParentId();
        
		if (parentId != null) {			
			btnBack.setEnabled(true);			
			System.out.println("parentId");
			new ReadService().getBean(ModelNames.REGION, parentId,
				new ReadService.Listener() {
					@Override
					public void onSuccess(BeanObject bean) {
						grandparentId = bean.getString(IRegion.PARENT);
						parentName = bean.getString(IRegion.NAME);
						currentDistrictType = bean.getInt(IRegion.TYPE) + 1;
						//btnBack.setEnabled(true);
						addPanel.getTable().setText(0, 0,
								getCurrentAddLabelText(currentDistrictType));
						
						tablePanel.setHeading(getCurrentTableTitle(
								currentDistrictType, parentName));
						tablePanel.remove(grid);
						displayTable();
						tablePanel.add(grid);

						tablePanel.layout();
					}

				});
		
		} else {
			btnBack.setEnabled(false);
			grandparentId = null;
			parentName = Resources.constants.Country_district();
			currentDistrictType = IRegion.TYPE_COUNTRY;
			addPanel.getTable().setText(0, 0,
					getCurrentAddLabelText(currentDistrictType));
			tablePanel.setHeading(getCurrentTableTitle(currentDistrictType,
					parentName));
			tablePanel.remove(this.grid);
			
			displayTable();
			tablePanel.add(this.grid);

			tablePanel.layout();
		}
		
	}

	private String getCurrentAddLabelText(int districtType) {
		String currentAddLabelText = null;
		if (districtType == IRegion.TYPE_COUNTRY) {
			currentAddLabelText = Resources.constants.Add_country();
		} else if (districtType == IRegion.TYPE_PROVINCE) {
			currentAddLabelText = Resources.constants.Add_province();
		} else if (districtType == IRegion.TYPE_CITY) {
			currentAddLabelText = Resources.constants.Add_city();
		} else if (districtType == IRegion.TYPE_DISTRICT) {
			currentAddLabelText = Resources.constants.Add_town();
		}
		return currentAddLabelText;
	}

	private String getCurrentTableTitle(int districtType, String parentName) {
		String CurrentTableTitle = null;
		if (districtType == IRegion.TYPE_COUNTRY) {
			CurrentTableTitle = Resources.constants.Country_district();
		} else if (districtType == IRegion.TYPE_PROVINCE) {
			CurrentTableTitle = "[ " + parentName + " ] 以下"
					+ Resources.constants.Province_district();
		} else if (districtType == IRegion.TYPE_CITY) {
			CurrentTableTitle = "[ " + parentName + " ] 以下"
					+ Resources.constants.City_district();
		} else if (districtType == IRegion.TYPE_DISTRICT) {
			CurrentTableTitle = "[ " + parentName + " ] 以下"
					+ Resources.constants.Town_district();
		}
		return CurrentTableTitle;
	}

	public void onBackClicked() {
		State newState = new State();
		newState.setParentId(grandparentId);
		newState.execute();
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return Resources.constants.District_list();
	}

}
