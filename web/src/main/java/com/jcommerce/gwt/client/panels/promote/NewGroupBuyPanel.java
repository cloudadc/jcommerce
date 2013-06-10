package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class NewGroupBuyPanel extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	ListBox categoryList = new ListBox();
	ListBox brandList = new ListBox();
	Criteria criteria = new Criteria();
	Button addButton = new Button("[+]");
//	List<> removeButtons = new Stack();
	

	public static class State extends PageState {
		public String getPageClassName() {
			return NewGroupBuyPanel.class.getName();
		}

		public String getMenuDisplayName() {
			return "添加团购活动 ";
		}
	}

	public State getCurState() {
		if (curState == null) {
			curState = new State();
		}
		return (State) curState;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "添加团购活动 ";
	}

	public String getButtonText() {
		return "团购活动列表 ";
	}

	protected void buttonClicked() {
		GroupBuyListPanel.State state = new GroupBuyListPanel.State();
		state.execute();
	}
	
	public NewGroupBuyPanel() {
		new ListService().listBeans(ModelNames.CATEGORY, criteria, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					categoryList.addItem(bean.getString(ICategory.NAME));
				}
			}
		});
		new ListService().listBeans(ModelNames.BRAND, criteria, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					brandList.addItem(bean.getString(IBrand.NAME));
				}
			}
		});
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		HorizontalPanel header = new HorizontalPanel();
		final TextBox searchText = new TextBox();
		Button searchButton = new Button("搜索");
		header.add(Resources.images.icon_search().createImage());
		header.add(categoryList);
		header.add(brandList);
		header.add(new Label("活动名称"));
		header.add(searchText);
		header.add(searchButton);
		
		final ListBox goodList = new ListBox();
		goodList.addItem("请先搜索商品");
		DateField startDateField = new DateField();
	    DateField endDateField = new DateField();
	    TextBox secPriceBox = new TextBox();
	    TextBox limitBox = new TextBox();
	    TextBox scoreBox = new TextBox();
	    final FlexTable flexTable = new FlexTable();
	    FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
	    flexTable.setWidth("32em");
	    flexTable.setCellSpacing(5);
	    flexTable.setCellPadding(3);
	    cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
	    TextArea decArea = new TextArea();
	    decArea.setSize(500, 50);
		
		searchButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				goodList.clear();
				new ListService().listBeans(ModelNames.GOODS, new ListService.Listener() {

					public void onSuccess(List<BeanObject> beans) {
						List<String> goods = new ArrayList<String>();
						for(BeanObject bean : beans) {
							goods.add(bean.getString(IGoods.NAME));
							if(bean.getString(IGoods.NAME).equals(searchText.getText()) || bean.getString(IGoods.NUMBER).equals(searchText.getText())) {
								goodList.addItem(bean.getString(IGoods.NAME));
							}
						}
						if(searchText.getText().equals("")) {
							for(String str : goods) {
								goodList.addItem(str);
							}
						}
					}
				});
			}
		});
		
		ColumnPanel mainPanel = new ColumnPanel();
		mainPanel.createPanel("", "团购商品：", goodList);
		mainPanel.createPanel("", "活动开始时间：", startDateField);
		mainPanel.createPanel("", "活动结束时间：", endDateField);
		mainPanel.createPanel("", "保证金：", secPriceBox);
		mainPanel.createPanel("", "限购数量：", limitBox);
		mainPanel.createPanel("", "赠送积分数：", scoreBox);
		mainPanel.createPanel("", "价格阶梯：", flexTable);	
		mainPanel.createPanel("", "活动说明：", decArea);	
		addRow(flexTable, addButton);
		
		addButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				Button removeButton = new Button("[-]");
				addRow(flexTable, removeButton);
				removeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						removeRow(flexTable);
					}
				});
				
			}
		});
		
		
		contentPanel.add(header);
		contentPanel.add(mainPanel);
		add(contentPanel);
	}
	
	private void addRow(FlexTable flexTable, Button button) {
		int numRows = flexTable.getRowCount();
	    flexTable.setWidget(numRows, 0, new Label("数量达到"));
	    flexTable.setWidget(numRows, 1, new TextBox());
	    flexTable.setWidget(numRows, 2, new Label("享受价格"));
	    flexTable.setWidget(numRows, 3, new TextBox());
	    flexTable.setWidget(numRows, 4, button);
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
