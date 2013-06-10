package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
//import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IWholesale;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class NewWholesalePanel extends ContentWidget {
	
	private ColumnPanel contentPanel = new ColumnPanel();
	
	public static class State extends PageState {
		public String getPageClassName() {
			return NewWholesalePanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "添加批发方案 ";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "添加批发方案 ";
    }

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		ColumnPanel topPanel = new ColumnPanel();
		
		HorizontalPanel searchPanel = new HorizontalPanel();
		final TextBox searchTextBox = new TextBox();
		Button searchButton = new Button("搜索");
		searchPanel.add(searchTextBox);
		searchPanel.add(searchButton);
		topPanel.createPanel("", "根据商品编号、名称或货号搜索商品", searchPanel);
		
		HorizontalPanel selectPanel = new HorizontalPanel();
		final ListBox listGoods = new ListBox();
		listGoods.addItem("请先搜索商品");
		selectPanel.add(listGoods);
		topPanel.createPanel("", "批发商品名称：", selectPanel);
		
		searchButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				listGoods.clear();
				new ListService().listBeans(ModelNames.GOODS, new ListService.Listener() {

					public void onSuccess(List<BeanObject> beans) {
						List<String> goods = new ArrayList<String>();
						for(BeanObject bean : beans) {
							goods.add(bean.getString(IGoods.NAME));
							if(bean.getString(IGoods.NAME).equals(searchTextBox.getText()) || bean.getString(IGoods.NUMBER).equals(searchTextBox.getText())) {
								listGoods.addItem(bean.getString(IGoods.NAME));
							}
						}
						if(searchTextBox.getText().equals("")) {
							for(String str : goods) {
								listGoods.addItem(str);
							}
						}
					}
				});
			}});
		
		HorizontalPanel levelPanel = new HorizontalPanel();
		final CheckBox regC = new CheckBox("注册用户");
		final CheckBox priceC = new CheckBox("代销价格");
		levelPanel.add(regC);
		levelPanel.add(priceC);
		topPanel.createPanel("", "适用会员等级：", levelPanel);
		
		HorizontalPanel startPanel = new HorizontalPanel();
		final RadioButton yesR = new RadioButton("","是");
		final RadioButton noR = new RadioButton("","否");
		startPanel.add(yesR);
		startPanel.add(noR);
		topPanel.createPanel("", "是否启用：", startPanel);
		
		ColumnPanel bottomPanel = new ColumnPanel();
		
		final FlexTable flexTable = new FlexTable();
	    FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
	    flexTable.setWidth("32em");
	    flexTable.setCellSpacing(5);
	    flexTable.setCellPadding(3);
	    VerticalPanel buttonPanel = new VerticalPanel();
	    buttonPanel.setSpacing(3);
	    Button addRowButton = new Button("增加一行");
	    Button removeRowButton = new Button("移除一行");
//	    HorizontalPanel addPanel = new HorizontalPanel();
	    buttonPanel.add(addRowButton);
	    buttonPanel.add(removeRowButton);
	    addRowButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				addRow(flexTable);
			}});
	    removeRowButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				removeRow(flexTable);
			}});
	    flexTable.setWidget(0,5,buttonPanel);
	    cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
	    addRow(flexTable);
	    
   	    bottomPanel.add(flexTable);
   	    
   	    Button okButton = new Button("确定");
		Button resetButton = new Button("重置");
		
		HorizontalPanel contentpanel = new HorizontalPanel();
		okButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				String goodName = listGoods.getItemText(listGoods.getSelectedIndex());
				String rankId = "";
				if(regC.getValue() && priceC.getValue()) {
					rankId ="注册用户,代销价格";
				} else if(regC.getValue() && !priceC.getValue()){
					rankId ="注册用户";
				} else if(!regC.getValue() && priceC.getValue()) {
					rankId ="代销价格";
				}
				boolean enable = false;
				if(yesR.getValue()) {
					enable = true;
				}
				String prices = "";
				for(int i = 0 ; i < flexTable.getRowCount(); i ++) {
					TextBox tbk = (TextBox) flexTable.getWidget(i, 1);
					TextBox tbv = (TextBox) flexTable.getWidget(i, 3);
					if(tbk != null && tbv != null) {
						prices += tbk.getText() + "," + tbv.getText() + "&=%";
					}
				}
				System.out.println(goodName + " " + rankId + " " + enable + " " + prices);
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put(IWholesale.GOODS_NAME, goodName);
				map.put(IWholesale.RANK_IDS, rankId);
				map.put(IWholesale.ENABLED, enable);
				map.put(IWholesale.PRICES, prices);
				BeanObject wholesale = new BeanObject(ModelNames.WHOLESALE, map);
				new CreateService().createBean(wholesale,
						new CreateService.Listener() {
							public void onSuccess(String id) {
								WholesaleListPanel.State state = new WholesaleListPanel.State();
								state.execute();
							}
				});
			}});
		resetButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				searchTextBox.setText("");
				listGoods.clear();
				regC.setValue(false);
				priceC.setValue(false);
				yesR.setValue(false);
				noR.setValue(false);
			}});
		
		contentpanel.setSpacing(30);
		contentpanel.add(okButton);
		contentpanel.add(resetButton);
		
		contentPanel.add(topPanel);
		contentPanel.add(bottomPanel);
		contentPanel.add(contentpanel);
		add(contentPanel);
	}

	private void addRow(FlexTable flexTable) {
		int numRows = flexTable.getRowCount();
	    flexTable.setWidget(numRows, 0, new Label("数量"));
	    flexTable.setWidget(numRows, 1, new TextBox());
	    flexTable.setWidget(numRows, 2, new Label("价格"));
	    flexTable.setWidget(numRows, 3, new TextBox());
	    flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
	}
	
	private void removeRow(FlexTable flexTable) {
	    int numRows = flexTable.getRowCount();
	    if (numRows > 1) {
	      flexTable.removeRow(numRows - 1);
	      flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
	    }
	  }

	

}
