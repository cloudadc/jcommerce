package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.model.RegularExConstants;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ConsigneeSettingPanel extends ContentPanel {
	String userId = "";
	ListBox country = new ListBox();
	ListBox province = new ListBox();
	ListBox city = new ListBox();
	ListBox district = new ListBox();
	List<BeanObject> countryList = new ArrayList<BeanObject>();
	List<BeanObject> provinceList = new ArrayList<BeanObject>();
	List<BeanObject> cityList = new ArrayList<BeanObject>();
	List<BeanObject> districtList = new ArrayList<BeanObject>();
	String userAddrStr = "";
	
	TextField<String> consignee = new TextField<String>();
	TextField<String> email = new TextField<String>();
	TextField<String> address = new TextField<String>();
	TextField<String> zip = new TextField<String>();
	TextField<String> tel = new TextField<String>();
	TextField<String> mobile = new TextField<String>();
	TextField<String> sign_building = new TextField<String>();
	TextField<String> best_time = new TextField<String>();
	ColumnPanel contentPanel = new ColumnPanel();
	
	BeanObject userAddInfo = null;
	BeanObject order = new BeanObject(ModelNames.ORDER);
	
	
	public BeanObject getOrder() {
		
		order.setValues(contentPanel.getValues());
		
		
		order.set(IOrder.REGION, district.getValue(district.getSelectedIndex()));
		order.set(IOrder.ADDRESS, userAddrStr + " " + address.getValue());
	
		return order;
	}

	public void setOrder(BeanObject order) {
		this.order = order;
	}

	public BeanObject getUserAddInfo() {
		return userAddInfo;
	}

	public ConsigneeSettingPanel() {
		ContentPanel panel = new ContentPanel();

		
		
		consignee.setWidth("120px");
		consignee.setEnabled(true);
		contentPanel.createPanel(IOrder.CONSIGNEE, "收货人:", consignee);
		
		initialDistrictList();
		
		HorizontalPanel districtPanel = new HorizontalPanel();
		districtPanel.add(new Label("  联系人地址:       "));
		districtPanel.add(country);
		districtPanel.add(province);
		districtPanel.add(city);
		districtPanel.add(district);
		contentPanel.add(districtPanel);
		
		
		email.setWidth("120px");
		email.setEnabled(true);
		contentPanel.createPanel(IOrder.EMAIL, "电子邮件:", email);
		
		address.setWidth("120px");
		address.setEnabled(true);
		contentPanel.createPanel(IOrder.ADDRESS, "地址:", address);
		
		zip.setWidth("120px");
		zip.setEnabled(true);
		contentPanel.createPanel(IOrder.ZIP, "邮编:", zip);
		
		tel.setWidth("120px");
		tel.setEnabled(true);
		contentPanel.createPanel(IOrder.PHONE, "电话:", tel);
		
		mobile.setWidth("120px");
		mobile.setEnabled(true);
		contentPanel.createPanel(IOrder.MOBILE, "手机:", mobile);
		
		sign_building.setWidth("120px");
		sign_building.setEnabled(true);
		contentPanel.createPanel(IOrder.SIGNBUILDING, "标志性建筑:", sign_building);
		
		best_time.setWidth("120px");
		best_time.setEnabled(true);
		contentPanel.createPanel(IOrder.BESTTIME, "最佳送货时间:", best_time);

		setValidatorToAddressField();
		
		
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.add(contentPanel);
		
        this.add(panel);
        this.setHeading("设置收货人信息");
        this.setSize(780, 555);
	}

	public void initialDistrictList()
	{
		country.addItem("请选择");
		province.addItem("请选择");
		city.addItem("请选择");
		district.addItem("请选择");
		
		addListBoxOnChangeListener(country, countryList ,province, provinceList);
		addListBoxOnChangeListener(province, provinceList, city, cityList);
		addListBoxOnChangeListener(city, cityList, district, districtList);
		initalCounrtyListBox();
	}
	
	//add a boxchangelistener to district List so that this method can be a model called by three
	//listboxs
	private void addListBoxOnChangeListener(final ListBox listBox, final List<BeanObject> arrayList
			     , final ListBox nextLevelListBox, final List<BeanObject> nextLevelArrayList)
	{
		listBox.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender)
			{
				if(listBox.getSelectedIndex() != 0)
				{
					String countryInfo = listBox.getValue(listBox.getSelectedIndex());
					BeanObject region = arrayList.get(listBox.getSelectedIndex());
					
					nextLevelListBox.clear();
					nextLevelListBox.addItem("请选择");
					nextLevelArrayList.clear();
					nextLevelArrayList.add(null);
					
					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(IRegion.PARENT, Condition.EQUALS,
														region.getString(IRegion.ID)));
					
					ListService listService = new ListService();
					listService.listBeans(ModelNames.REGION, criteria,
						       new ListService.Listener()
					{
						public void onSuccess(List<BeanObject> beans) 
						{
							for(int i=0; i< beans.size(); i++)
							{
								BeanObject regionInfo = beans.get(i);
								nextLevelArrayList.add(regionInfo);
								
								nextLevelListBox.addItem(regionInfo.getString(IRegion.NAME),
												regionInfo.getString(IRegion.ID));
							}
						}
						
					} );
				}
				else
				{
					return ;
				}
			}
		});
	}
	
	
	//I need set the basic country info to countryListBox, when loading this panel.
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
			       new ListService.Listener()
		{
			public void onSuccess(List<BeanObject> beans) 
			{
				for(int i=0; i< beans.size(); i++)
				{
					BeanObject countryInfo = beans.get(i);
					countryList.add(countryInfo);
					
					country.addItem(countryInfo.getString(IRegion.NAME),
									countryInfo.getString(IRegion.ID));
				}
			}
			
		} );
	}
	
	//intial textfields' regular express validator.
	public void setValidatorToAddressField()
	{
		consignee.setRegex(RegularExConstants.CONSIGNEE);
		consignee.getMessages().setRegexText(RegularExConstants.CONSIGNEEREGMSG);
		
		email.setRegex(RegularExConstants.EMAIL);
		email.getMessages().setRegexText(RegularExConstants.EMAILREGMSG);
		
		zip.setRegex(RegularExConstants.ZIP);
		zip.getMessages().setRegexText(RegularExConstants.ZIPREGMSG);
		
		tel.setRegex(RegularExConstants.TEL);
		tel.getMessages().setRegexText(RegularExConstants.TELREGMSG);
		
		mobile.setRegex(RegularExConstants.MOBILE);
		mobile.getMessages().setRegexText(RegularExConstants.MOBILEREGMSG);
		
	}
	
	public boolean validateUserAddressInfo()
	{
		userAddrStr = country.getItemText(country.getSelectedIndex()) + " " +
					  province.getItemText(province.getSelectedIndex()) + " " +
					  city.getItemText(city.getSelectedIndex()) + " " +
					  district.getItemText(district.getSelectedIndex());
		
		if((consignee.isValid() && email.isValid() && zip.isValid())
		  && (!consignee.getValue().equals("") && !email.getValue().equals("") 
		  		&& !zip.getValue().equals("") && !userAddrStr.contains("请选择")))
		{
			//tel or mobile must be correct.
			if((tel.isValid() || mobile.isValid()) && (!tel.getValue().equals("") 
			  		|| !mobile.getValue().equals("")))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		 
	}
	
	public Map<String, Object> getOrderUserAddressMap()
	{
		return contentPanel.getValues();
	}
	
	public BeanObject getUserAddressInfo()
	{
		
		if(validateUserAddressInfo())
		{
			userAddInfo = new BeanObject();
			userAddInfo.set(IUserAddress.CONSIGNEE, consignee.getValue() + "");
			userAddInfo.set(IUserAddress.EMAIL, email.getValue() + "");
			userAddInfo.set(IUserAddress.ADDRESS, userAddrStr + " " + address.getValue() + " ");
			userAddInfo.set(IUserAddress.ZIP, zip.getValue() + "");
			userAddInfo.set(IUserAddress.PHONE, tel.getValue() + "");
			userAddInfo.set(IUserAddress.MOBILE, mobile.getValue());
			userAddInfo.set(IUserAddress.SIGNBUILDING, sign_building.getValue() + " ");
			userAddInfo.set(IUserAddress.BESTTIME, best_time.getValue());
			
			//insert a region bean to userAddressInof
			userAddInfo.set(IUserAddress.REGION, district.getValue(district.getSelectedIndex()));
			
			userAddInfo.set(IUserAddress.USER, userId);
			
			order.setValues(contentPanel.getValues());
			
			//insert a region bean to userAddressInof
			order.set(IOrder.REGION, district.getValue(district.getSelectedIndex()));
		}
		else
		{
			Info.display("Error", "You should to write the correct Info");
		}
		
		return userAddInfo;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getUserAddrStr()
	{
		return userAddrStr;
	}

}
