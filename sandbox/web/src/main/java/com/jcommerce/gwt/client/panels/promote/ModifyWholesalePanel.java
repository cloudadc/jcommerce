package com.jcommerce.gwt.client.panels.promote;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IWholesale;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ModifyWholesalePanel extends ContentWidget{

	private ColumnPanel contentPanel = new ColumnPanel();
	
	public static class State extends PageState {
		
		private BeanObject wholesale = null;
		
		public BeanObject getWholesale() {
			return wholesale;
		}
		public void setWholesale(BeanObject wholesale) {
			this.wholesale = wholesale;
			setEditting(wholesale != null);
		}
		public String getPageClassName() {
			return ModifyWholesalePanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "修改批发方案 ";
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
        return "修改批发方案 ";
    }

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		ColumnPanel topPanel = new ColumnPanel();
		final TextBox textBox = new TextBox();
		textBox.setText(getCurState().getWholesale().getString(IWholesale.GOODS_NAME));
		topPanel.createPanel(IWholesale.GOODS_NAME, "批发商品名称：", textBox);
		
		HorizontalPanel levelPanel = new HorizontalPanel();
		final CheckBox regC = new CheckBox("注册用户");
		final CheckBox priceC = new CheckBox("代销价格");
		levelPanel.add(regC);
		levelPanel.add(priceC);
		topPanel.createPanel("", "适用会员等级：", levelPanel);
		
		String rankId = getCurState().getWholesale().getString(IWholesale.RANK_IDS);
		if(rankId.equals("注册用户,代销价格")) {
			regC.setValue(true);
			priceC.setValue(true);
		} else if(rankId.contains("注册用户")) {
			regC.setValue(true);
			priceC.setValue(false);
		} else if(rankId.contains("代销价格")) {
			regC.setValue(false);
			priceC.setValue(true);
		}else {
			regC.setValue(false);
			priceC.setValue(false);
		}
		
		HorizontalPanel startPanel = new HorizontalPanel();
		final RadioButton yesR = new RadioButton("","是");
		final RadioButton noR = new RadioButton("","否");
		startPanel.add(yesR);
		startPanel.add(noR);
		topPanel.createPanel("", "是否启用：", startPanel);
		
		String enable = getCurState().getWholesale().getString(IWholesale.ENABLED);
		if(enable.equals("true")){
			yesR.setValue(true);
		} else {
			noR.setValue(true);
		}
		
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
	    
   	    bottomPanel.add(flexTable);
   	    
   	    String prices = getCurState().getWholesale().getString(IWholesale.PRICES);
   	    System.out.println(prices);
   	    Map<String,String>map = new HashMap<String,String>();
   	    while(prices.contains("&=%")) {
   	    	String tmp = prices.substring(0, prices.indexOf("&=%"));
   	    	map.put(tmp.substring(0, tmp.indexOf(",")), tmp.substring(tmp.indexOf(",") + 1, tmp.length()));
   	    	prices = prices.substring(prices.indexOf("&=%") + 3, prices.length());
   	    }
   	    System.out.println(map);
   	    for(String key : map.keySet()) {
   	    	addRowWithValue(flexTable, key,map.get(key));
   	    }
   	    
   	    Button okButton = new Button("确定");
		Button resetButton = new Button("重置");
		
		HorizontalPanel contentpanel = new HorizontalPanel();
		okButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				String goodName = textBox.getText();
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
				String id = getCurState().getWholesale().getString(IWholesale.ID);
				if(getCurState().isEditting()) {
					new UpdateService().updateBean(id, wholesale, null);
					WholesaleListPanel.State state = new WholesaleListPanel.State();
					state.execute();
				}
			}});
		resetButton.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				textBox.setText("");
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
	
	private void addRowWithValue(FlexTable flexTable, String key, String value) {
		int numRows = flexTable.getRowCount();
	    flexTable.setWidget(numRows, 0, new Label("数量"));
	    TextBox keyT = new TextBox();
	    keyT.setText(key);
	    flexTable.setWidget(numRows, 1, keyT);
	    flexTable.setWidget(numRows, 2, new Label("价格"));
	    TextBox vT = new TextBox();
	    vT.setText(value);
	    flexTable.setWidget(numRows, 3, vT);
	    flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
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
