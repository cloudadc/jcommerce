package com.jcommerce.gwt.client.panels.system;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.RegionForm;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;

public class RegionPanel extends ContentWidget {

	private static RegionPanel instance = null;
	
	//add panel
	private HorizontalPanel addRegionPanel = null;
	private LabelField add_Label = null;
	private HiddenField<String> add_id = null;
	private TextField<String> add_name = null;
	private HiddenField<String> add_type = null;
	private HiddenField<String> add_parentId = null;
	private HiddenField<String> add_agencyId = null;
	private Button btnAdd = null;
	//current panel
	private ContentPanel currentRegionPanel = null;
	private LabelField current_Label = null;
	private HiddenField<String> current_name = null;
	private HiddenField<String> current_type = null;
	//children panel
	private ContentPanel childrenRegionPanel = null;
	
	private String addParentId = null;
	private int addType = 0;
	private String addParentName = null;
	
	@Override
	protected State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public static RegionPanel getInstance() {
		if (instance == null) {
			instance = new RegionPanel();
		}
		return instance;
	}

	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.Region_setup();
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		buildAddPanel();
		buildCurrentRegionLabel();
		buildChildrenPanel();
		
//		getDatafromDb(getCurState().getParentId(), getCurState().getType(), getCurState().getParentName());

	}
	
	private void buildAddPanel(){
		addRegionPanel = new HorizontalPanel();
		addRegionPanel.setSpacing(10);
		
		add_Label = new LabelField();
		add_Label.setWidth(100);
		
		add_id = RegionForm.getIdField();
		add_name = RegionForm.getNameField(Resources.constants.Region_name());
		add_name.setWidth(150);
		add_type = RegionForm.getTypeField();
		add_parentId = RegionForm.getParentIdField();
		add_agencyId = RegionForm.getAgencyIdField();
		
		btnAdd = new Button();
		btnAdd.setText(Resources.constants.Region_ok());
		
		btnAdd.addSelectionListener(new SelectionListener<ButtonEvent>(){
				public void componentSelected(ButtonEvent ce) {
					String newRegion = add_name.getValue();
					if(newRegion != null && newRegion.length()>0 ){
						new CreateService().createBean(generateAddBean(), new CreateService.Listener(){
								public void onSuccess(String id) {
									//BeanObject bean = generateAddBean();
									//bean.set(add_id.getName(), id);
									
									add_name.setValue(null);
//									getDatafromDb(getCurState().getParentId(), getCurState().getType(), getCurState().getParentName());
									refresh();
									//childrenRegionPanel.add(new RegionChildPanel(bean));
								}
							}
						);
					}
				}
			}
		);
		
		addRegionPanel.add(add_Label);
		addRegionPanel.add(add_id);
		addRegionPanel.add(add_name);
		addRegionPanel.add(add_type);
		addRegionPanel.add(add_parentId);
		addRegionPanel.add(add_agencyId);
		LabelField blankLabel = new LabelField();
		blankLabel.setWidth(20);
		addRegionPanel.add(blankLabel);
		addRegionPanel.add(btnAdd);
		
		super.add(addRegionPanel);		
	}
	
	private BeanObject generateAddBean(){
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(add_id.getName(), add_id.getValue());
		props.put(add_name.getName(), add_name.getValue());
		props.put(add_type.getName(), add_type.getValue());
		props.put(add_parentId.getName(), add_parentId.getValue());
		props.put(add_agencyId.getName(), add_agencyId.getValue());
		BeanObject form = new BeanObject(ModelNames.REGION, props);
		return form;
	}
	
	private void buildCurrentRegionLabel(){
		currentRegionPanel = new ContentPanel();
		currentRegionPanel.setHeaderVisible(false);
		TableRowLayout tableLayout = new TableRowLayout();
		tableLayout.setWidth("100%");
		tableLayout.setBorder(0);
		tableLayout.setCellHorizontalAlign(HorizontalAlignment.CENTER);
		tableLayout.setCellVerticalAlign(VerticalAlignment.BOTTOM);
		
		currentRegionPanel.setLayout(tableLayout);
		currentRegionPanel.setHeight(40);
		
		current_Label = new LabelField();
		
		current_name = RegionForm.getHiddenNameField();
		current_type = RegionForm.getTypeField();
		
		currentRegionPanel.add(current_Label);
		currentRegionPanel.add(current_name);
		currentRegionPanel.add(current_type);

		super.add(currentRegionPanel);
	}

	private void buildChildrenPanel(){
		childrenRegionPanel = new ContentPanel();
		TableLayout tableLayout = new TableLayout();
		tableLayout.setColumns(3);
		tableLayout.setBorder(1);
		tableLayout.setWidth("100%");
		childrenRegionPanel.setLayout(tableLayout);
		childrenRegionPanel.setHeaderVisible(false);
		
		super.add(childrenRegionPanel);
	}
	
	private void getDatafromDb(String parentId, int type, String parentName){
//		getCurState().setParentId(parentId);
//		getCurState().setType(type);
//		if(parentName != null){
//			getCurState().setParentName(parentName);
//		}
		
		addParentId = parentId;
		addType = type;
		addParentName = parentName;
		
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField(IRegion.PARENT);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(parentId);
		criteria.addCondition(cond);
		cond = new Condition();
		cond.setField(IRegion.TYPE);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(""+type);
		criteria.addCondition(cond);
		
		new ListService().listBeans(ModelNames.REGION, criteria, 
				new ListService.Listener(){
					public void onSuccess(List<BeanObject> beans) {
						add_type.setValue(""+addType);
						add_parentId.setValue(addParentId);
						add_agencyId.setValue(IRegion.AGENCY);
						add_Label.setText(getAddLabelTextByType(addType));
						
						current_Label.setText(getCurrentLabelTextByType(addType, addParentName));
						current_name.setValue(addParentName);
//						current_type.setValue(IRegion.TYPE_WORLD.toString());
						
						refreshChildrenPanel(beans);
					}
				}
		);
	}
	
	private void refreshChildrenPanel(List<BeanObject> beans){
		childrenRegionPanel.removeAll();
		
		for(BeanObject bean:beans){
			childrenRegionPanel.add(new RegionChildPanel(bean));
		}
		
		childrenRegionPanel.layout();
	}
	
	private String getAddLabelTextByType(int type){
		String rtn = null;
		if(type == IRegion.TYPE_COUNTRY){
			rtn = Resources.constants.Region_addFirstRegion();
		} else if(type == IRegion.TYPE_PROVINCE){
			rtn = Resources.constants.Region_addSecondRegion();
		} else if(type == IRegion.TYPE_CITY){
			rtn = Resources.constants.Region_addThirdRegion();
		} else if(type == IRegion.TYPE_DISTRICT){
			rtn = Resources.constants.Region_addFourthRegion();
		}
		return rtn;
	}
	
	private String getCurrentLabelTextByType(int type, String parentName){
		String rtn = null;
		if(type == IRegion.TYPE_COUNTRY){
			rtn = Resources.constants.Region_firstRegion();
		} else if(type == IRegion.TYPE_PROVINCE){
			rtn = "[ " + parentName + " ]" + Resources.constants.Region_secondRegion();
		} else if(type == IRegion.TYPE_CITY){
			rtn = "[ " + parentName + " ]" + Resources.constants.Region_thirdRegion();
		} else if(type == IRegion.TYPE_DISTRICT){
			rtn = "[ " + parentName + " ]" + Resources.constants.Region_fourthRegion();
		}
		return rtn;
	}
	
	private int getChildrenType(int currentType){
		int childrenType = 0;
		if (currentType == IRegion.TYPE_COUNTRY){
			childrenType = IRegion.TYPE_PROVINCE;
		} else if (currentType == IRegion.TYPE_PROVINCE) {
			childrenType = IRegion.TYPE_CITY;
		} else if (currentType == IRegion.TYPE_CITY) {
			childrenType = IRegion.TYPE_DISTRICT;
		}
		
		return childrenType;
	}
	
	public void refresh() {
		getDatafromDb(getCurState().getParentId(), getCurState().getType(), getCurState().getParentName());
	}
	
	public static class State extends PageState {
		public static final String PARENT_ID = "parentId";
		public static final String TYPE = "type";
		public static final String PARENT_NAME = "parentName";
		
		public void setParentId(String parentId){
			setValue(PARENT_ID, parentId);
		}
		public void setType(int type){
			setValue(TYPE, ""+type);
		}
		public void setParentName(String parentName){
			setValue(PARENT_NAME, parentName);
		}
		
		public String getParentId(){
			return (String)getValue(PARENT_ID);
		}
		public int getType(){
			return new Integer((String)getValue(TYPE)).intValue();
		}
		public String getParentName(){
			return (String)getValue(PARENT_NAME);
		}
		
		public String getPageClassName() {
			return RegionPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Region_setup();
		}
		
		public State(){
			super();
//			setParentId(IRegion.COUNTRY_PARENT_ID);
			setParentId(null);
			setType(IRegion.TYPE_COUNTRY);
		}
	}
	
	class RegionChildPanel extends HorizontalPanel{
		private HiddenField<String> child_id = null;
		private TextField<String> child_name = null;
		private HiddenField<String> child_oldName = null;
		private HiddenField<String> child_type = null;
		private HiddenField<String> child_parentId = null;
		private HiddenField<String> child_agencyId = null;
		private Hyperlink manage = null;
		private Hyperlink delete = null;
		
		public RegionChildPanel (BeanObject bean){
			super();
			init(bean);
		}
		
		private void init(BeanObject bean){
			this.setSpacing(10);
			
			child_id = RegionForm.getIdField();
			child_id.setValue(bean.getString(IRegion.ID));
			
			child_name = RegionForm.getEditNameField();
			child_name.setValue(bean.getString(IRegion.NAME));
			
//			child_oldName = RegionForm.getOldNameField();
			
			child_type = RegionForm.getTypeField();
			child_type.setValue(bean.getString(IRegion.TYPE));
			
			child_parentId = RegionForm.getParentIdField();
			child_parentId.setValue(bean.getString(IRegion.PARENT));
			
			child_agencyId = RegionForm.getAgencyIdField();
			child_agencyId.setValue(bean.getString(IRegion.AGENCY));

			child_name.addListener(Events.OnFocus, new Listener<ComponentEvent>(){
					public void handleEvent(ComponentEvent be) {
						child_oldName.setValue(child_name.getValue());
					}
				}
			);

			child_name.addListener(Events.OnBlur, new Listener<ComponentEvent>() {
					public void handleEvent(ComponentEvent be) {
						String oldValue = child_oldName.getValue();
						String value = child_name.getValue();
						if( value !=null && value.length() > 0 ){
							if(!value.equals(oldValue)){
								updateChild();
							}else{
								//do nothing
							}
						}else{
							child_name.setValue(oldValue);
						}
					}
				}
			);
			
			this.add(child_id);
			this.add(child_name);
			this.add(child_oldName);
			this.add(child_type);
			this.add(child_parentId);
			this.add(child_agencyId);
			this.add(new LabelField(" | "));
			
			if(bean.getInt(IRegion.TYPE) != IRegion.TYPE_DISTRICT){
				State newState = new State();
				newState.setParentId(child_id.getValue());
				newState.setType(getChildrenType(new Integer(child_type.getValue())));
				newState.setParentName(child_name.getValue());
				
				manage = new Hyperlink(Resources.constants.Region_manage(), true, newState.getFullHistoryToken());
				manage.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                    }
				});
				this.add(manage);
			}
			
			
			delete = new Hyperlink(Resources.constants.Region_delete(), true, getCurState().getFullHistoryToken());
			delete.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
						onDelete();
					}
				}
			);
			this.add(delete);
		}
		
		private void updateChild(){
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(child_id.getName(), child_id.getValue());
			props.put(child_name.getName(), child_name.getValue());
			props.put(child_type.getName(), child_type.getValue());
			props.put(child_parentId.getName(), child_parentId.getValue());
			props.put(child_agencyId.getName(), child_agencyId.getValue());
			BeanObject form = new BeanObject(ModelNames.REGION, props);
			
	        new UpdateService().updateBean(child_id.getValue(), form, new UpdateService.Listener() {
		            public synchronized void onSuccess(Boolean success) {
		            	//do nothing
		            }
	        	}
	        );
			
		}
		
//		private void onManage(){
//			getDatafromDb(child_id.getValue(), getChildrenType(child_type.getValue()), child_name.getValue());
//		}
		
		private void onDelete(){
			Criteria criteria = new Criteria();
			Condition cond = new Condition();
			cond.setField(IRegion.PARENT);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(child_id.getValue());
			criteria.addCondition(cond);
//			cond = new Condition();
//			cond.setField(IRegion.TYPE);
//			cond.setOperator(Condition.EQUALS);
//			cond.setValue(type);
//			criteria.addCondition(cond);
			
			new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){
					public void onSuccess(List<BeanObject> beans) {
						if(beans.size() > 0){
							MessageBox.alert(Resources.constants.Region_info(), Resources.messages.Region_canNotDelete(), null);
						}else{
							new DeleteService().deleteBean(ModelNames.REGION, child_id.getValue(), 
									new DeleteService.Listener(){
										public void onSuccess(Boolean success) {
											getDatafromDb(child_parentId.getValue(), new Integer(child_type.getValue()), current_name.getValue());
										}
									}
							);
						}
					}
				}
			);
			
		}
		
	}
	
	public static interface Constants{
		String Region_setup();
		String Region_name();
		String Region_ok();
		String Region_addFirstRegion();
		String Region_addSecondRegion();
		String Region_addThirdRegion();
		String Region_addFourthRegion();
		String Region_firstRegion();
		String Region_secondRegion();
		String Region_thirdRegion();
		String Region_fourthRegion();
		String Region_manage();
		String Region_delete();
		String Region_info();
	}
	
	public static interface Message{
		String Region_canNotDelete();
	}
}
