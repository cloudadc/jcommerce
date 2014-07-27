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
import com.jcommerce.gwt.client.model.ISnatch;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.DateWidget;

public class NewSnatchPanel extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	
	public static class State extends PageState {
		public String getPageClassName() {
			return NewSnatchPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "添加夺宝奇兵 ";
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
        return "添加夺宝奇兵 ";
    }
    
    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		ColumnPanel topPanel = new ColumnPanel();
		
		final TextBox snTextBox = new TextBox();
		topPanel.createPanel(ISnatch.SNATCH_NAME, "活动名称", snTextBox);
		HorizontalPanel searchPanel = new HorizontalPanel();
		final TextBox searchTextBox = new TextBox();
		Button searchButton = new Button("搜索");
		searchPanel.add(searchTextBox);
		searchPanel.add(searchButton);
		topPanel.createPanel("", "商品关键字", searchPanel);
		
		HorizontalPanel selectPanel = new HorizontalPanel();
		final ListBox listGoods = new ListBox();
		listGoods.addItem("请先搜索商品");
		selectPanel.add(listGoods);
		topPanel.createPanel("", "活动商品", selectPanel);
		
		final DateWidget startTime = new DateWidget();
		final DateWidget endTime = new DateWidget();
		topPanel.createPanel("", "活动开始时间", startTime);
		topPanel.createPanel("", "活动结束时间", endTime);
		
		final TextBox maxBox = new TextBox();
		final TextBox minBox = new TextBox();
		final TextBox maxPaidBox = new TextBox();
		final TextBox scoreConsumBox = new TextBox();
		final TextArea descArea = new TextArea();
		descArea.setSize(300, 50);
		topPanel.createPanel("", "价格上限", maxBox);
		topPanel.createPanel("", "价格下限", minBox);
		topPanel.createPanel("", "最多需支付的价格", maxPaidBox);
		topPanel.createPanel("", "消耗积分", scoreConsumBox);
		topPanel.createPanel("", "活动描述", descArea);
		
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
				map.put(ISnatch.SNATCH_NAME, snTextBox.getText());
				map.put(ISnatch.GOOD_NAME, listGoods.getItemText(listGoods.getSelectedIndex()));
				map.put(ISnatch.START_TIME, startTime.getValue().getTime());
				map.put(ISnatch.END_TIME, endTime.getValue().getTime());
				map.put(ISnatch.MAX_PAID_PRICE, maxPaidBox.getText());
				map.put(ISnatch.SCORE_CONSUM, scoreConsumBox.getText());
				map.put(ISnatch.MAX_PRICE, maxBox.getText());
				map.put(ISnatch.MIN_PRICE, minBox.getText());
//				System.out.println(descArea.getValue());
				map.put(ISnatch.DESC, descArea.getValue());
				BeanObject bean = new BeanObject(ModelNames.SNATCH,map);
				new CreateService().createBean(bean, new CreateService.Listener() {
					public void onSuccess(String id) {
						SnatchListPanel.State state = new SnatchListPanel.State();
						state.execute();
					}
				});
			}
		});
		resetButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				snTextBox.setText("");
				startTime.setValue(new Date());
				endTime.setValue(new Date());
				maxBox.setText("0.0");
				minBox.setText("0.0");
				maxPaidBox.setText("0.0");
				scoreConsumBox.setText("");
				descArea.clear();
			}
		});
    }

}
