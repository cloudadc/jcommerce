package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IAgency;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.panels.privilege.NewAdminUser.State;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.validator.SpaceChecker;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.UserSelector;

public class NewAgency extends ContentWidget{
	
	ColumnPanel contentPanel = new ColumnPanel();	
	TextBox tb_name = new TextBox();
	TextArea tb_desc = new TextArea();
	UserSelector adminus = null;
	ListBox country = new ListBox();
	ListBox province = new ListBox();
	ListBox city = new ListBox();
	ListBox district = new ListBox();
	List<BeanObject> countryList = new ArrayList<BeanObject>();
	List<BeanObject> provinceList = new ArrayList<BeanObject>();
	List<BeanObject> cityList = new ArrayList<BeanObject>();
	List<BeanObject> districtList = new ArrayList<BeanObject>();
	List<BeanObject> adminList = new ArrayList<BeanObject>();
	ListBox lb_agency = new ListBox();
	Button btn_addAgency = null;
	Button btn_ok = new Button();
	Button btn_reset = new Button();
	CheckBox admin = new CheckBox();
	Set<String> regionSet = new HashSet<String>();
	Set<String> regionIDSet = new HashSet<String>();
	List<BeanObject> regionList = new ArrayList<BeanObject>();
	
//	private BeanObject agency = null;
//	private String agencyId = null;
//	private boolean editting = false;
//	
    public static class State extends PageState {
        private BeanObject agency = null;
        
        public BeanObject getAgency() {
            return agency;
        }
        public void setAgency(BeanObject agency) {
            this.agency = agency;
            setEditting(agency != null);
        }

        public String getPageClassName() {
            return NewAgency.class.getName();
        }
        public String getMenuDisplayName() {
            return "添加办事处";
        }
    }

    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		if (!getCurState().isEditting())
			return "添加办事处";
		else
			return "编辑办事处";
	}
	
//	public void setAgency(BeanObject data) {
//		this.agency = data;
//		this.agencyId = data != null ? data.getString(IAgency.ID) : null;
//		editting = agency != null;
//	}
//	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		if(getCurState().isEditting()){
		    BeanObject agency = getCurState().getAgency();
			tb_name.setText(agency.getString(IAgency.NAME));
			tb_desc.setText(agency.getString(IAgency.DESCRIPTION));
			final String agencyId = agency.getString(IAgency.ID);
			new ListService().listBeans(ModelNames.REGION, new ListService.Listener() {
				@Override
				public void onSuccess(List<BeanObject> beans) {
					for( BeanObject bean : beans ) {
						if(bean.getString(IRegion.AGENCY).equals(agencyId)){
							lb_agency.addItem(bean.getString(IRegion.NAME));
						}
					}
				}
			});
			new ListService().listBeans(ModelNames.ADMINUSER, new ListService.Listener() {
				@Override
				public void onSuccess(List<BeanObject> beans) {
					for(BeanObject bean : beans){
						if(bean.getString(IAdminUser.AGENCY).equals(agencyId)){
							adminus.setText(bean.getString(IAdminUser.NAME));
						}
					}
					
				}
			});
		}
		contentPanel.createPanel(IAgency.NAME, "办事处名称:", tb_name, new SpaceChecker(IAgency.NAME));
		tb_desc.setWidth("300px");
		contentPanel.createPanel(IAgency.DESCRIPTION, "办事处描述:", tb_desc);
		
		adminus = new UserSelector();
		adminus.setBean(ModelNames.ADMINUSER);
		adminus.setCaption("请选择办事处管理员：");
		contentPanel.createPanel(IAdminUser.NAME, "负责该办事处的管理员:", adminus);
		
		final HorizontalPanel adminRegionPanel = new HorizontalPanel();
		adminRegionPanel.setSize(300, 15);
		adminRegionPanel.addText("该办事处管辖的地区：");

		adminRegionPanel.add(lb_agency);
		contentPanel.add(adminRegionPanel);
		
		add(contentPanel);
		
		VerticalPanel regionPanel = new VerticalPanel();
		HorizontalPanel regionConPanel = new HorizontalPanel();
		
		regionPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		regionPanel.addText("从下面的列表中选择地区，点加号按钮添加到该办事处管辖的地区");
		
		VerticalPanel countryPanel = new VerticalPanel();
		countryPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		countryPanel.addText("一级地区：");
		country.setWidth("200px");
		country.setVisibleItemCount(10);
		countryPanel.add(country);
		
		VerticalPanel provincePanel = new VerticalPanel();
		provincePanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		provincePanel.addText("二级地区：");
		province.setWidth("200px");
		province.setVisibleItemCount(10);
		provincePanel.add(province);
		
		VerticalPanel cityPanel = new VerticalPanel();
		cityPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		cityPanel.addText("三级地区：");
		city.setWidth("200px");
		city.setVisibleItemCount(10);
		cityPanel.add(city);
		
		VerticalPanel districtPanel = new VerticalPanel();
		districtPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		districtPanel.addText("四级地区：");
		district.setWidth("200px");
		district.setVisibleItemCount(10);
		districtPanel.add(district);
		
		
		regionConPanel.add(countryPanel);
		regionConPanel.add(provincePanel);
		regionConPanel.add(cityPanel);
		regionConPanel.add(districtPanel);
		initialDistrictList();
		
		btn_addAgency = new Button();
		btn_addAgency.setText("+");
		btn_addAgency.setHeight("28");
		regionConPanel.add(btn_addAgency);
		regionPanel.add(regionConPanel);
		
		HorizontalPanel confirmPanel = new HorizontalPanel();
		confirmPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
		btn_ok.setText("确定");
		btn_reset.setText("重置");
		confirmPanel.add(btn_ok);
		confirmPanel.add(btn_reset);
		add(regionPanel);
		add(confirmPanel);
		
		btn_addAgency.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				String agencyStr = "";
				String agencyID = "";
				if(district.getSelectedIndex() > 0){
					agencyStr = district.getItemText(district.getSelectedIndex());
					agencyID = district.getValue(district.getSelectedIndex());
				}else if(city.getSelectedIndex() > 0){
					agencyStr = city.getItemText(city.getSelectedIndex());
					agencyID = city.getValue(city.getSelectedIndex());
				}else if(province.getSelectedIndex() > 0){
					agencyStr = province.getItemText(province.getSelectedIndex());
					agencyID = province.getValue(province.getSelectedIndex());
				}else if(country.getSelectedIndex() > 0){
					agencyStr = country.getItemText(country.getSelectedIndex());
					agencyID = country.getValue(country.getSelectedIndex());
				}
				
				regionSet.add(agencyStr);	
				regionIDSet.add(agencyID);
				lb_agency.clear();
				for(String region : regionSet){
					lb_agency.addItem(region);
				} 

			}
		});
		
		btn_ok.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				if ( tb_name.getText().trim() != null && adminus.getValue() != null && lb_agency.getItemCount() > 0) {
					Map<String, Object> values = new HashMap<String, Object>();
					values.put(IAgency.DESCRIPTION, tb_desc.getText());
					values.put(IAgency.NAME, tb_name.getText());
					new CreateService().createBean(new BeanObject(ModelNames.AGENCY, values), new CreateService.Listener() {
						@Override
						public void onSuccess(String id) {
							final String agencyId = id;
							for( final String regionId : regionIDSet ) {
								new ReadService().getBean(ModelNames.REGION, regionId, new ReadService.Listener() {
									 public synchronized void onSuccess(BeanObject result) {
										 Map<String, Object> region = result.getProperties();
										 region.put(IRegion.AGENCY, agencyId);
										 new UpdateService().updateBean(regionId, new BeanObject(ModelNames.REGION, region), new UpdateService.Listener() {
											@Override
											public void onSuccess(Boolean success) {
												
											}
										});
									 }
								});
							}
							new ReadService().getBean(ModelNames.ADMINUSER, adminus.getValue(), new ReadService.Listener() {
								public synchronized void onSuccess(BeanObject result){
									Map<String, Object> adminuser = result.getProperties();
									adminuser.put(IAdminUser.AGENCY, agencyId);
									new UpdateService().updateBean(adminus.getValue(), new BeanObject(ModelNames.ADMINUSER, adminuser), new UpdateService.Listener() {
										@Override
										public void onSuccess(Boolean success) {
											
										}
									});
								}
							});
						}
					});
					Info info = new Info();
					info.show(new InfoConfig("恭喜", "添加办事处成功！"));
					AgencyList.State state = new AgencyList.State();
					state.execute();
				} else {
					Window.alert("请填写办事处名称，为办事处选择管理员，并选择办事处的管辖地区！");
				}
			}
		});
	}

	public void initialDistrictList() {
		country.addItem("请选择");
		province.addItem("请选择");
		city.addItem("请选择");
		district.addItem("请选择");

		addListBoxOnChangeListener(country, countryList, province, provinceList);
		addListBoxOnChangeListener(province, provinceList, city, cityList);
		addListBoxOnChangeListener(city, cityList, district, districtList);
		initalCounrtyListBox();
	}

	private void addListBoxOnChangeListener(final ListBox listBox, final List<BeanObject> arrayList, final ListBox nextLevelListBox,
			final List<BeanObject> nextLevelArrayList) {
		listBox.addChangeHandler(new ChangeHandler() {
            
            public void onChange(ChangeEvent arg0) {
				if (listBox.getSelectedIndex() != 0) {
					BeanObject region = arrayList.get(listBox.getSelectedIndex());
					nextLevelListBox.clear();
					nextLevelListBox.addItem("请选择");
					nextLevelArrayList.clear();
					nextLevelArrayList.add(null);

					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(IRegion.PARENT,Condition.EQUALS, region.getString(IRegion.ID)));

					ListService listService = new ListService();
					listService.listBeans(ModelNames.REGION, criteria,
							new ListService.Listener() {
								public void onSuccess(List<BeanObject> beans) {
									for (int i = 0; i < beans.size(); i++) {
										BeanObject regionInfo = beans.get(i);
										nextLevelArrayList.add(regionInfo);
										nextLevelListBox.addItem(regionInfo.getString(IRegion.NAME),regionInfo.getString(IRegion.ID));
									}
								}
							});
				} else {
					return;
				}
			}
		});
	}
	
	public void initalCounrtyListBox() 
	{
		countryList.add(null);
		provinceList.add(null);
		cityList.add(null);
		districtList.add(null);
		
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IRegion.PARENT, Condition.ISNULL , null));
		
		ListService listService = new ListService();
		listService.listBeans(ModelNames.REGION, criteria,
			       new ListService.Listener(){
			public void onSuccess(List<BeanObject> beans) 
			{
				for(int i=0; i< beans.size(); i++)
				{
					BeanObject countryInfo = beans.get(i);
					countryList.add(countryInfo);
					country.addItem(countryInfo.getString(IRegion.NAME),countryInfo.getString(IRegion.ID));
				}
			}
		} );
	}

}
