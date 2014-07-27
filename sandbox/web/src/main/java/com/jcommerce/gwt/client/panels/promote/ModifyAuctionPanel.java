package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IAuction;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.DateWidget;

public class ModifyAuctionPanel extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	
	public static class State extends PageState {
		
		private BeanObject Auction = null;
		
		public BeanObject getAuction() {
			return Auction;
		}
		public void setAuction(BeanObject Auction) {
			this.Auction = Auction;
			setEditting(Auction != null);
		}
		public String getPageClassName() {
			return ModifyAuctionPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "编辑拍卖活动 ";
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
        return "编辑拍卖活动 ";
    }
    
    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		ColumnPanel topPanel = new ColumnPanel();
		
		final TextBox snTextBox = new TextBox();
		snTextBox.setText(this.getCurState().getAuction().getString(IAuction.AUCTION_NAME));
		topPanel.createPanel(IAuction.AUCTION_NAME, "拍卖活动名称：", snTextBox);
		
		final TextArea descArea = new TextArea();
		descArea.setSize(300, 50);
		descArea.setValue(getCurState().getAuction().getString(IAuction.DESC));
		topPanel.createPanel("", "拍卖活动描述：", descArea);
		
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
		topPanel.createPanel("", "拍卖商品名称：", selectPanel);
		
		final DateWidget startTime = new DateWidget();
		final DateWidget endTime = new DateWidget();
		topPanel.createPanel("", "拍卖开始时间：", startTime);
		topPanel.createPanel("", "拍卖结束时间：", endTime);
		
		final TextBox startBox = new TextBox();
		startBox.setText(getCurState().getAuction().getString(IAuction.START_PRICE));
		final TextBox dirBox = new TextBox();
		dirBox.setText(getCurState().getAuction().getString(IAuction.DIR_PRICE));
		final TextBox addRangeBox = new TextBox();
		addRangeBox.setText(getCurState().getAuction().getString(IAuction.ADD_RANGE));
		final TextBox secBox = new TextBox();
		secBox.setText(getCurState().getAuction().getString(IAuction.SEC_PRICE));
		
		
		topPanel.createPanel("", "起拍价：", startBox);
		topPanel.createPanel("", "一口价：", dirBox);
		topPanel.createPanel("", "加价幅度：", addRangeBox);
		topPanel.createPanel("", "保证金：", secBox);
		
		
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
		
		HorizontalPanel contentpanel = new HorizontalPanel();
		Button okButton = new Button("确定");
		Button resetButton = new Button("重置");
		contentpanel.setSpacing(30);
		contentpanel.add(okButton);
		contentpanel.add(resetButton);
		contentPanel.add(topPanel);
		contentPanel.add(contentpanel);
		add(contentPanel);
		
		okButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put(IAuction.AUCTION_NAME, snTextBox.getText());
				map.put(IAuction.GOOD_NAME, listGoods.getItemText(listGoods.getSelectedIndex()));
				map.put(IAuction.START_TIME, startTime.getValue().getTime());
				map.put(IAuction.END_TIME, endTime.getValue().getTime());
				map.put(IAuction.START_PRICE, startBox.getText());
				map.put(IAuction.DIR_PRICE, dirBox.getText());
				map.put(IAuction.ADD_RANGE, addRangeBox.getText());
				map.put(IAuction.SEC_PRICE, secBox.getText());
				map.put(IAuction.DESC, descArea.getValue());
				BeanObject bean = new BeanObject(ModelNames.AUCTION,map);
				String id = getCurState().getAuction().getString(IAuction.ID);
				if(getCurState().isEditting()) {
					new UpdateService().updateBean(id, bean, null);
					AuctionListPanel.State state = new AuctionListPanel.State();
					state.execute();
				}
			}
		});
		resetButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				snTextBox.setText("");
				startTime.setValue(new Date());
				endTime.setValue(new Date());
				startBox.setText("0.0");
				dirBox.setText("0.0");
				addRangeBox.setText("0.0");
				secBox.setText("");
				descArea.clear();
			}
		});
    }


}
