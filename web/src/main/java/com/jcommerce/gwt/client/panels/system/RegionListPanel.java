package com.jcommerce.gwt.client.panels.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.RegionForm;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

public class RegionListPanel extends ContentWidget {

	public static interface Constants {
		String RegionList_menuName();
		String RegionList_title();
		String RegionList_previousLevel();
		String RegionList_manage();
	}
	
	public static class State extends PageState {
		public static final String PARENT_PARENT_ID = "parentparentId";
		public static final String PARENT_ID = "parentId";
		public static final String TYPE = "type";
		public static final String PARENT_NAME = "parentName";
		public State() {
			setParentId(null); 
//			setRegionType(IRegion.TYPE_COUNTRY.toString());
		}

		public String getPageClassName() {
			return RegionListPanel.class.getName();
		}

		public String getMenuDisplayName() {
			return Resources.constants.RegionList_menuName();
		}
		
		public void setParentId(String parentId){
			setValue(PARENT_ID, parentId);
		}
		public String getParentId(){
		    String p = (String)getValue(PARENT_ID);
			return "null".equalsIgnoreCase(p) ? null : p;
		}
//		public String getParentName(){
//			return (String)getValue(PARENT_NAME);
//		}
//		public void setParentName(String parentName){
//			setValue(PARENT_NAME, parentName);
//		}

//		public void setRegionType(String type){
//			setValue(TYPE, type);
//		}
//		public String getRegionType(){
//			return (String)getValue(TYPE);
//		}
		
//		public String getParentParentId() {
//			return (String)getValue(PARENT_PARENT_ID);
//		}
//		public void setParentParentId(String parentParentId) {
//			setValue(PARENT_PARENT_ID, parentParentId);
//		}

	}

	protected State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Button getShortCutButton() {
	      return btnBack;
	}
	
	public String getName() {
		return Resources.constants.RegionList_title();
	}

	private static RegionListPanel instance;

	public static RegionListPanel getInstance() {
		if (instance == null) {
			instance = new RegionListPanel();
		}
		return instance;
	}

	private RegionListPanel() {
		System.out.println("----------RegionListPanel");
		initJS(this);
	}

	Button btnNew = new Button();
	Button btnBack = new Button();
	FormPanel addPanel = new FormPanel();
	ContentPanel listPanel = new ContentPanel();
	Map<String, HorizontalPanel> cells = new HashMap<String, HorizontalPanel>();
	
	TextField<String> fName;
//	HiddenField<String> fType;
//	HiddenField<String> fParentId;
	MultiField mf;
	
	String parentName;
	String parentParentId;
	int currentRegionType;

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
	      btnBack.setText(Resources.constants.RegionList_previousLevel());
			btnBack.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent sender) {
					onBackClicked();
				}
			});
		
		addPanel.setHeaderVisible(false);
		
		mf = new MultiField();
		mf.setSpacing(20);
		
		fName = RegionForm.getNameField("region");
//		fName.setFieldLabel("新增地区");
//		addPanel.add(fName);
		mf.add(fName);
		

//		fType = RegionForm.getTypeField();
//		addPanel.add(fType);
//
//		fParentId = RegionForm.getParentIdField();
//		addPanel.add(fParentId);

		btnNew.setText(Resources.constants.ok());
		btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				onAddClicked();
			}
		});

//		addPanel.setButtonAlign(HorizontalAlignment.CENTER);
//		addPanel.addButton(btnNew);
		mf.add(new AdapterField(btnNew));
		addPanel.add(mf);
		//addPanel.setWidth(800);
		
		TableLayout tl1 = new TableLayout(3);
//		tl1.setCellPadding(20);
//		tl1.setCellSpacing(20);
		listPanel.setLayout(tl1);
		
		//listPanel.setWidth(800);
		
//		this.setLayout(new TableLayout(1));
//		TableData td = new TableData();
//		td.setWidth("100%");
//		td.setVerticalAlign(Style.VerticalAlignment.TOP);
//		td.setHorizontalAlign(Style.HorizontalAlignment.LEFT);
//		TableLayout tl = (TableLayout)getLayout();
		
		this.add(addPanel);
		this.add(listPanel);
	}

	public void refreshListPanel(List<BeanObject> result) {
		log("[refreshListPanel] result size: "+result.size());
		listPanel.removeAll();
		for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
			final BeanObject region = it.next();
			appendRegion(region);
		}
		listPanel.layout();
	}

	private void appendRegion(final BeanObject region) {
		String id = region.getString(IRegion.ID);
		String name = region.getString(IRegion.NAME);
		StringBuffer html = new StringBuffer();
		html.append("<a href=\"javascript: onManageClicked('").append(id).append("', '").append(name).append("');\">管理</a>")
			.append(" | <a href=\"javascript: onDeleteClicked('").append(id).append("');\">删除</a>");
		TableData td = new TableData();
		td.setWidth("30%");
		HorizontalPanel cellContent = new HorizontalPanel();
		cellContent.setSpacing(10);
//			cellContent.add(new LabelField(region.getString(IRegion.REGION_NAME)+ " | "));
//			cellContent.add(new Editor(RegionForm.getNameField("")));
//			cellContent.add(new CellEditor(new LabelField(region.getString(IRegion.REGION_NAME))));
		
		final TextField<String> child_name = RegionForm.getNameField("");
		cellContent.add(child_name);
		child_name.setOriginalValue(name);
		child_name.setValue(name);
		child_name.addListener(Events.OnBlur, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				String oldValue = child_name.getOriginalValue(); 
				String value = child_name.getValue();
				if( value !=null && value.length() > 0 ){
					if(!value.equals(oldValue)){
						region.set(IRegion.NAME, value);
						onUpdateFinished(region);
					}else{
						//do nothing
					}
				}else{
					child_name.setValue(oldValue);
				}
			}
		});
		cellContent.add(new Html(html.toString()));
		listPanel.add(cellContent,td);
		
		cells.put(id, cellContent);
		
	}

	public void refresh() {
		System.out.println("refresh..");
		String parentId = getCurState().getParentId();
		log("parentId="+parentId);
		
		cells.clear();
		fName.setRawValue("");
//		fType.setValue(getCurState().getRegionType());
//		fParentId.setValue(parentId ==null? "" : parentId);


		if (parentId != null) {
			// temporarily disable back button until necessary data (parentParentId) is refreshed
			btnBack.disable();
			// temporarily disable new button, until necessary data is refreshed
			btnNew.disable();
			Criteria criteria = new Criteria();
			Condition cond = new Condition(IRegion.PARENT, Condition.EQUALS,
					parentId);
			criteria.addCondition(cond);
			new ReadService().getBean(ModelNames.REGION, parentId,
					new ReadService.Listener() {
						public void onSuccess(BeanObject bean) {
							parentParentId = bean.getString(IRegion.PARENT);
							parentName = bean.getString(IRegion.NAME);
							int parentRegionType = bean.getInt(IRegion.TYPE);
							currentRegionType = parentRegionType + 1;
							log("parentParentId="+parentParentId+", parentName="+parentName+", currentRegionType="+currentRegionType);
							btnBack.enable();
							btnNew.enable();
							mf.setFieldLabel(getAddLabelTextByType(currentRegionType));
							listPanel.setHeading(getCurrentLabelTextByType(currentRegionType, parentName));
						}

					});
		} else {
			// always disable back button, if no parent level
			btnBack.disable();
			parentParentId = null;
			parentName = null;
			currentRegionType = IRegion.TYPE_COUNTRY;
			
			mf.setFieldLabel(getAddLabelTextByType(currentRegionType));
			listPanel.setHeading(getCurrentLabelTextByType(currentRegionType, parentName));			
		}
		
		Criteria criteria = new Criteria();
		Condition cond;
		if (parentId == null) {
		    cond = new Condition(IRegion.PARENT, Condition.EQUALS, parentId);
		} else {
		    cond = new Condition(IRegion.PARENT, Condition.ISNULL, parentId);
		}
		criteria.addCondition(cond);

		new ListService().listBeans(ModelNames.REGION, criteria,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> beans) {
						refreshListPanel(beans);
					}
				});
	}
	
	private void onAddClicked() {
		try {
			if (!addPanel.isValid()) {
				Window.alert("Please check input before submit!!!");
				return;
			}
			Map<String, String> props = FormUtils.getPropsFromForm(addPanel);
			props.put(IRegion.PARENT, getCurState().getParentId());
			props.put(IRegion.TYPE, ""+currentRegionType);
			final BeanObject form = new BeanObject(ModelNames.REGION, (Map<String, Object>)(Map)props);
			new CreateService().createBean(form, new CreateService.Listener() {
				public synchronized void onSuccess(String id) {
					log("new onSuccess( " + id);
					form.set(IRegion.ID, id);
					appendRegion(form);
					listPanel.layout();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void onUpdateFinished(BeanObject region){

		String id = region.getString(IRegion.ID);
        new UpdateService().updateBean(id, region, new UpdateService.Listener() {
	            public synchronized void onSuccess(Boolean success) {
	            	Info.display("Congratulation", "update successful");
	            }
        	}
        );
		
	}
	private void onManageClicked(String id, String name) {
		Info.display("here", "onEditRegion: id="+id+", name="+name);
		
		State newState = new State();
		newState.setParentId(id);	
//		newState.setParentName(name);
		// depth++
//		newState.setRegionType(String.valueOf(Long.valueOf(getCurState().getRegionType())+1));
		newState.execute();
		
	}
	private void onDeleteClicked(final String id) {
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField(IRegion.PARENT);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(id);
		criteria.addCondition(cond);
		
		new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){
				public void onSuccess(List<BeanObject> beans) {
					if(beans.size() > 0){
						MessageBox.alert("Resources.constants.Region_info()", Resources.messages.Region_canNotDelete(), null);
					}else{
						new DeleteService().deleteBean(ModelNames.REGION, id, 
								new DeleteService.Listener(){
									public void onSuccess(Boolean success) {
										Info.display("Congratulation", "deleted: id="+id);
//										refresh();
										Widget cell = cells.get(id);
										if(cell !=null) {
											listPanel.remove(cell);
											cells.remove(id);
											listPanel.layout();
										}
									}
								}
						);
					}
				}
			}
		);

	}
	public void onBackClicked() {
		State newState = new State();
		newState.setParentId(parentParentId);
		newState.execute();
	}
	
	private String getAddLabelTextByType(int type){
		String rtn = null;
		if(type == IRegion.TYPE_COUNTRY){
			rtn = "Resources.constants.Region_addFirstRegion()";
		} else if(type == IRegion.TYPE_PROVINCE){
			rtn = "Resources.constants.Region_addSecondRegion()";
		} else if(type == IRegion.TYPE_CITY){
			rtn = "Resources.constants.Region_addThirdRegion()";
		} else if(type == IRegion.TYPE_DISTRICT){
			rtn = "Resources.constants.Region_addFourthRegion()";
		}
		return rtn;
	}
	
	private String getCurrentLabelTextByType(int type, String parentName){
		String rtn = null;
		if(type == IRegion.TYPE_COUNTRY){
			rtn = "Resources.constants.Region_firstRegion()";
		} else if(type == IRegion.TYPE_PROVINCE){
			rtn = "[ " + parentName + " ]" + "Resources.constants.Region_secondRegion()";
		} else if(type == IRegion.TYPE_CITY){
			rtn = "[ " + parentName + " ]" + "Resources.constants.Region_thirdRegion()";
		} else if(type == IRegion.TYPE_DISTRICT){
			rtn = "[ " + parentName + " ]" + "Resources.constants.Region_fourthRegion()";
		}
		return rtn;
	}
	
    private native void initJS(RegionListPanel me) /*-{
    $wnd.onManageClicked = function (id, name) {
        me.@com.jcommerce.gwt.client.panels.system.RegionListPanel::onManageClicked(Ljava/lang/String;Ljava/lang/String;)(id, name);
    };
    $wnd.onDeleteClicked = function (id) {
	    me.@com.jcommerce.gwt.client.panels.system.RegionListPanel::onDeleteClicked(Ljava/lang/String;)(id);
	};
    }-*/;


}
