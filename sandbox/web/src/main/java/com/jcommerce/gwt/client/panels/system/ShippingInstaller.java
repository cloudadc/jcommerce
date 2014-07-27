package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.RegionService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ShippingInstaller extends ContentWidget {
	//pick up 
	private String name = "配送区域名称:";
	private String free_money = "免费额度:";
	private String pay_fee = "货到付款支付费用:";
	private String base_fee = "500克以内费用:";
	private String step_fee = "续重每500克或其零数:";
	private String step_fee1 = "5000克以内续重每1000克费用:";
	private String step_fee2 = "5001克以上续重1000克费用:";
	private String basic_fee = "1000克以内费用:";
	private String pack_fee = "包装费用:";
	//
	private String shipping_id = null;
	private String area_id = null;
	private Boolean exist = false;
	//
	private ListBox list_nation = new ListBox(true);
	private ListBox list_province = new ListBox(true);
	private ListBox list_city = new ListBox(true);
	private ListBox list_county = new ListBox(true);
	//
	private  VerticalPanel vpanel = new VerticalPanel();
	private  HorizontalPanel localpanel = new HorizontalPanel();
	private  ColumnPanel contentPanelGeneral;
	//
	private Set<String> regionIdList = new HashSet<String>();
	private List<BeanObject> beans = new ArrayList<BeanObject>();
	private Map<String,String> configurePanelMap = new HashMap<String,String>();
	
	public ShippingInstaller(){
	}
	
	public static class State extends PageState{
	    
		public String getPageClassName() {
			return ShippingInstaller.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "配送方式";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	public String getDescription() {
		return "--新建配送区域";
	}

	public String getName() {
		return "it will change";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		add(vpanel);
	}
	
	public void setArea_id(String str){
		area_id = str;
	}
	
	public void setShipping_id(String str){
		shipping_id = str;
	}
	
	public void initMethod(String sign){
		vpanel.clear();
		configurePanelMap.clear();
		
		if(area_id == null){
			if(sign.equals("cac")){
				configurePanelMap.put(free_money,"0");
				configurePanelMap.put(pay_fee,"0");
			}else if(sign.equals("ems")){
				configurePanelMap.put(free_money,"0");
				configurePanelMap.put(base_fee,"0");
				configurePanelMap.put(step_fee,"0");
			}else if(sign.equals("express")){
				configurePanelMap.put(basic_fee,"0");
				configurePanelMap.put(free_money,"0");
				configurePanelMap.put(pay_fee,"0");
			}else if(sign.equals("flat")){
				configurePanelMap.put(base_fee,"0");
				configurePanelMap.put(free_money,"0");
				configurePanelMap.put(pay_fee,"0");
			}else if(sign.equals("fpd")){
				configurePanelMap.put(free_money,"0");
			}else if(sign.equals("post_express")){
				configurePanelMap.put(base_fee,"0");
				configurePanelMap.put(step_fee1,"0");
				configurePanelMap.put(step_fee2,"0");
				configurePanelMap.put(free_money,"0");
			}else if(sign.equals("post_mail")){
				configurePanelMap.put(basic_fee,"0");
				configurePanelMap.put(step_fee1,"0");
				configurePanelMap.put(step_fee2,"0");
				configurePanelMap.put(pack_fee,"0");
				configurePanelMap.put(free_money,"0");
			}else if(sign.equals("sf_express")){
				configurePanelMap.put(basic_fee,"0");
				configurePanelMap.put(step_fee,"0");
				configurePanelMap.put(free_money,"0");
			}else if(sign.equals("sto_express")){
				configurePanelMap.put(basic_fee,"0");
				configurePanelMap.put(step_fee,"0");
				configurePanelMap.put(free_money,"0");
			}else if(sign.equals("yto")){
				configurePanelMap.put(basic_fee,"0");
				configurePanelMap.put(step_fee,"0");
				configurePanelMap.put(free_money,"0");
				configurePanelMap.put(pay_fee,"0");
			}else{
				System.out.println("Error in init Distribution page.");
			}
			
			initConfigure(configurePanelMap);
		}else{
			initArea();
		}
	}
	
	private void initArea(){
//		new ReadService().getBean(ModelNames.SHIPPINGAREA, area_id, new ReadService.Listener() {
//			public void onSuccess(BeanObject bean) {
////				String regions = (String) bean.getProperties().get(IShippingArea.REGIONS);
////				initLocalpanel(regions);
//				String config = (String)bean.getProperties().get(IShippingArea.CONFIG);
//				initConfigure(Deserialize(config));
//			}
//		});
	}
	
	private void initConfigure(Map<String,String> map){
		contentPanelGeneral = new ColumnPanel();
		contentPanelGeneral.createPanel(name , name , new TextBox());
		
		configurePanelMap = map;
		for(Iterator it = (Iterator) configurePanelMap.entrySet().iterator();it.hasNext();){
			Map.Entry<String, String> m = (Map.Entry<String, String>)it.next();
			TextBox txtbox = new TextBox();
			txtbox.setText(m.getValue());
			contentPanelGeneral.createPanel(m.getKey() , m.getKey() , txtbox);
		}
		
		vpanel.add(contentPanelGeneral);
		vpanel.add(localpanel);
		vpanel.add(getLocatePanel());
		vpanel.add(getOSPanel());
	}
	
	public void initBeans(String id , final String listname){
		new RegionService().getRegionChildList(id , new RegionService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					initLocatePanel(beans , listname);
				}
		});
	}
	
	private void initLocatePanel(List<BeanObject> beans , String listname){
		if(listname.equals("init")){
			list_nation.clear();
			list_nation.addItem("请选择...","0");
			list_nation.setSelectedIndex(0);
			list_province.clear();
			list_province.addItem("请选择...","0");
			list_province.setSelectedIndex(0);
			list_city.clear();
			list_city.addItem("请选择...","0");
			list_city.setSelectedIndex(0);
			list_county.clear();
			list_county.addItem("请选择...","0");
			list_county.setSelectedIndex(0);
		}else if(listname.equals("nation")){
			list_province.clear();
			list_province.addItem("请选择...","0");
			list_province.setSelectedIndex(0);
			list_city.clear();
			list_city.addItem("请选择...","0");
			list_city.setSelectedIndex(0);
			list_county.clear();
			list_county.addItem("请选择...","0");
			list_county.setSelectedIndex(0);
		}else if(listname.equals("province")){
			list_city.clear();
			list_city.addItem("请选择...","0");
			list_city.setSelectedIndex(0);
			list_county.clear();
			list_county.addItem("请选择...","0");
			list_county.setSelectedIndex(0);
		}else if(listname.equals("city")){
			list_county.clear();
			list_county.addItem("请选择...","0");
			list_county.setSelectedIndex(0);
		}else{
			System.out.println("Error click");
		}
		
		for(int i=0;i<beans.size();i++){
			String id = (String) beans.get(i).getProperties().get("id");
			String name = (String) beans.get(i).getProperties().get("name");
			int type = (Integer) beans.get(i).getProperties().get("type");
			
			if(type == 0){
				list_nation.addItem(name, id);
			}else if(type == 1){
				list_province.addItem(name,id);
			}else if(type == 2){
				list_city.addItem(name, id);
			}else{
				list_county.addItem(name, id);
			}
		}
	}
	
	private void initLocalpanel(String regions){
		System.out.println(regions);
	}
	
	private HorizontalPanel getLocatePanel(){
		HorizontalPanel tableLocate = new HorizontalPanel();
		Label lbl_nation = new Label("国家:");
		Label lbl_province = new Label("省份:");
		Label lbl_city = new Label("城市:");
		Label lbl_county = new Label("区/县:");
		Button btn_Add = new Button("+");
		
		list_nation.clear();
		list_nation.addItem("请选择...","0");
		list_nation.setHeight("200");
		list_nation.setWidth("80");
		list_nation.setSelectedIndex(0);
		
		list_province.clear();
		list_province.addItem("请选择...","0");
		list_province.setHeight("200");
		list_province.setWidth("80");
		list_province.setSelectedIndex(0);
		
		list_city.clear();
		list_city.addItem("请选择...","0");
		list_city.setHeight("200");
		list_city.setWidth("80");
		list_city.setSelectedIndex(0);
		
		list_county.clear();
		list_county.addItem("请选择...","0");
		list_county.setHeight("200");
		list_county.setWidth("80");
		list_county.setSelectedIndex(0);
		
		tableLocate.add(lbl_nation);
		tableLocate.add(list_nation);
		tableLocate.add(lbl_province);
		tableLocate.add(list_province);
		tableLocate.add(lbl_city);
		tableLocate.add(list_city);
		tableLocate.add(lbl_county);
		tableLocate.add(list_county);
		tableLocate.add(btn_Add);
		
		list_nation.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				int index = list_nation.getSelectedIndex();
				if(index!=0){
					initBeans(list_nation.getValue(index) , "nation");
				}
			}
		});
		
		list_province.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				int index = list_province.getSelectedIndex();
				if(index!=0){
					initBeans(list_province.getValue(index) , "province");
				}
			}
		});
		
		list_city.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				int index = list_city.getSelectedIndex();
				if(index!=0){
					initBeans(list_city.getValue(index) , "city");
				}
			}
		});
		
		btn_Add.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				String name = null;
				String id = null;
				if(list_county.getSelectedIndex() != 0){
					name = list_county.getItemText(list_county.getSelectedIndex());
					id = list_county.getValue(list_county.getSelectedIndex());
				}else{
					if(list_city.getSelectedIndex() != 0){
						name = list_city.getItemText(list_city.getSelectedIndex());
						id = list_city.getValue(list_city.getSelectedIndex());
					}else{
						if(list_province.getSelectedIndex() != 0){
							name = list_province.getItemText(list_province.getSelectedIndex());
							id = list_province.getValue(list_province.getSelectedIndex());
						}else{
							if(list_nation.getSelectedIndex() != 0){
								name = list_nation.getItemText(list_nation.getSelectedIndex());
								id = list_nation.getValue(list_nation.getSelectedIndex());
							}
						}
					}
				}
				if(!regionIdList.contains(id) && name != null){
					addLocalpanel(id,name,true);
				}else{
					Window.alert("You have select it or something error!");
				}
			}
		});
		
		initBeans(null , "init");
		return tableLocate;
	}
	
	private void addLocalpanel(String id, String name , Boolean checked){
		CheckBox checkBox = new CheckBox();
		checkBox.setTitle(id);
		checkBox.setText(name);
		checkBox.setChecked(checked);
		localpanel.add(checkBox);
		regionIdList.add(id);
	}
	
	private HorizontalPanel getOSPanel(){
		Button btn_OK = new Button("确定");
		Button btn_Reset = new Button("重置");
		
		btn_OK.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				int length = regionIdList.size();
				String config_name = (String) contentPanelGeneral.getValue(name);
				if(config_name == null || config_name.equals("")){
					Window.alert("配置区域不能为空！");
				}else{
					updateConfigurePanelMap();
					Map<String,Object> areaMaps = new HashMap<String,Object>();
					areaMaps.put(IShippingArea.NAME, config_name);
					areaMaps.put(IShippingArea.CONFIG, Serialize(configurePanelMap));
					areaMaps.put(IShippingArea.SHIPPING, shipping_id);
					areaMaps.put(IShippingArea.REGIONS, regionIdList);
//					BeanObject shippingAreaObject = new BeanObject(ModelNames.SHIPPINGAREA, areaMaps);
//					new RegionAreaService().createBean(shippingAreaObject, new RegionAreaService.Listener() {
//						
//						public void onSuccess(String id) {
//							Window.alert("Complete");
//						}
//					});
				}
			}
		});
		
		btn_Reset.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				
				reset();
			}
		});
		
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.add(btn_OK);
		hpanel.add(btn_Reset);
		return hpanel;
	}
	
	private void reset(){
		vpanel.clear();
		localpanel.clear();
		regionIdList.clear();
		initConfigure(configurePanelMap);
	}
	
	private void updateConfigurePanelMap(){
		for(Iterator it = (Iterator) configurePanelMap.entrySet().iterator();it.hasNext();){
			Map.Entry<String, String> m = (Map.Entry<String, String>)it.next();
			String key = m.getKey();
			String value = (String) contentPanelGeneral.getValue(key);
			m.setValue(value);
		}
	}
	
	private String Serialize(Map<String,String> map){
		String result = "";
		for(Iterator it = (Iterator)map.entrySet().iterator(); it.hasNext(); ){
			Map.Entry<String, String> m = (Map.Entry<String, String>)it.next();
			result +=m.getKey()+"="+m.getValue()+";";
		}
		return result;
	}
	
	private Map<String,String> Deserialize(String str){
		String[] strList = str.split(";");
		Map<String,String> map = new HashMap<String,String>();
		for(int i = 0; i < strList.length; i ++ ){
			String key = strList[i].split("=")[0];
			String value = strList[i].split("=")[1];
			map.put(key, value);
		}
		return map;
	}
}
