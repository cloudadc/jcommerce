package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IFavourableActivity;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.DateWidget;

public class NewFavourableActivityPanel extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	private Button btnNew = new Button();
	private Button btnCancel = new Button();

	private List<CheckBox> rangeContentList = new ArrayList<CheckBox>();
	private Map<CheckBox, TextBox> methodContentMap = new HashMap<CheckBox, TextBox>();

	public static class State extends PageState {

		public String getPageClassName() {
			return NewFavourableActivityPanel.class.getName();
		}

		public String getMenuDisplayName() {
			return "添加优惠活动 ";
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
		return "添加优惠活动 ";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		add(contentPanel);

		final TextBox nameTextBox = new TextBox();
		final DateWidget startTime = new DateWidget();
		final DateWidget endTime = new DateWidget();

		contentPanel.createPanel(IFavourableActivity.ACT_NAME, "优惠活动名称：",
				nameTextBox);
		contentPanel.createPanel(IFavourableActivity.START_TIME, "优惠开始时间：",
				startTime);
		contentPanel.createPanel(IFavourableActivity.END_TIME, "优惠结束时间：",
				endTime);
		HorizontalPanel levelPanel = new HorizontalPanel();
		final CheckBox noneCb = new CheckBox("非会员");
		final CheckBox regCb = new CheckBox("注册用户");
		final CheckBox priceCb = new CheckBox("代销价格");
		levelPanel.add(noneCb);
		levelPanel.add(regCb);
		levelPanel.add(priceCb);
		contentPanel.createPanel(IFavourableActivity.ACT_RANGE, "享受优惠的会员等级：",
				levelPanel);

		final ListBox listBoxRange = new ListBox();
		String[] ranges = { "全部商品", "以下分类", "以下品牌", "以下商品" };
		for (String str : ranges) {
			listBoxRange.addItem(str);
		}
		contentPanel.createPanel("", "优惠范围：", listBoxRange);

		final FlexTable rangeContentTable = new FlexTable();
		FlexCellFormatter cellFormatterContent = rangeContentTable
				.getFlexCellFormatter();
		rangeContentTable.setWidth("32em");
		rangeContentTable.setCellSpacing(5);
		rangeContentTable.setCellPadding(3);
		cellFormatterContent.setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		contentPanel.createPanel("", "", rangeContentTable);

		final FlexTable rangeTable = new FlexTable();
		FlexCellFormatter cellFormatter = rangeTable.getFlexCellFormatter();
		rangeTable.setWidth("32em");
		rangeTable.setCellSpacing(5);
		rangeTable.setCellPadding(3);
		cellFormatter
				.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		contentPanel.createPanel("", "", rangeTable);

		final Label ragLabel = new Label("搜索并加入优惠范围");
		final TextBox ragTextBox = new TextBox();
		final Button ragButton = new Button("搜索");
		final ListBox ragListBox = new ListBox();
		final Button ragAdd = new Button("添加");
		listBoxRange.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent arg0) {
				rangeContentList.clear();
				rangeTable.clear();
				rangeContentTable.clear();
				String selectedRange = listBoxRange.getItemText(listBoxRange
						.getSelectedIndex());
				if (!selectedRange.equals("全部商品")) {
					Widget[] widgets = { ragLabel, ragTextBox, ragButton,
							ragListBox, ragAdd };
					addRow(rangeTable, widgets);
				}
			}
		});
		ragButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				ragListBox.clear();
				String selectedRange = listBoxRange.getItemText(listBoxRange
						.getSelectedIndex());
				if (selectedRange.equals("以下分类")) {
					new ListService().listBeans(ModelNames.CATEGORY,
							new ListService.Listener() {
								public void onSuccess(List<BeanObject> beans) {
									for (BeanObject bean : beans) {
										ragListBox.addItem(bean
												.getString(ICategory.NAME));
									}
								}
							});

				} else if (selectedRange.equals("以下品牌")) {
					new ListService().listBeans(ModelNames.BRAND,
							new ListService.Listener() {
								public void onSuccess(List<BeanObject> beans) {
									for (BeanObject bean : beans) {
										ragListBox.addItem(bean
												.getString(IBrand.NAME));
									}
								}
							});
				} else if (selectedRange.equals("以下商品")) {
					new ListService().listBeans(ModelNames.GOODS,
							new ListService.Listener() {
								public void onSuccess(List<BeanObject> beans) {
									for (BeanObject bean : beans) {
										ragListBox.addItem(bean
												.getString(IGoods.NAME));
									}
								}
							});
				}
			}
		});
		ragAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				String selectedContent = ragListBox.getItemText(ragListBox
						.getSelectedIndex());
				CheckBox entity = new CheckBox(selectedContent);
				entity.setValue(true);
				for (CheckBox c : rangeContentList) {
					if (c.getText().equals(selectedContent)) {
						MessageBox.alert("该项已经存在", "该项已经存在", null);
						return;
					}
				}
				rangeContentList.add(entity);
				Widget[] widgets = { entity };
				addRow(rangeContentTable, widgets);
			}
		});

		final TextBox minBox = new TextBox();
		minBox.setText("0.0");
		contentPanel.createPanel(IFavourableActivity.MIN_AMOUNT, "金额下限：",
				minBox);
		HorizontalPanel maxPanel = new HorizontalPanel();
		final TextBox maxBox = new TextBox();
		maxBox.setText("0.0");
		Label maxbel = new Label("0表示没有上限");
		maxPanel.add(maxBox);
		maxPanel.add(maxbel);
		contentPanel.createPanel("", "金额上限：", maxPanel);

		HorizontalPanel methodPanel = new HorizontalPanel();
		final ListBox listBoxMethod = new ListBox();
		String[] methods = { "享受赠品(特惠品)", "享受现金减免", "享受现金折扣" };
		for (String str : methods) {
			listBoxMethod.addItem(str);
		}
		methodPanel.add(listBoxMethod);
		TextBox methodBox = new TextBox();
		methodBox.setText("0");
		methodPanel.add(methodBox);
		contentPanel.createPanel("", "优惠方式：", methodPanel);

		final FlexTable methodContentTable = new FlexTable();
		FlexCellFormatter cellFormatterRangeContent = methodContentTable
				.getFlexCellFormatter();
		methodContentTable.setWidth("32em");
		methodContentTable.setCellSpacing(5);
		methodContentTable.setCellPadding(3);
		cellFormatterRangeContent.setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		contentPanel.createPanel("", "", methodContentTable);

		final FlexTable methodTable = new FlexTable();
		FlexCellFormatter cellFormatterRange = methodTable
				.getFlexCellFormatter();
		methodTable.setWidth("32em");
		methodTable.setCellSpacing(5);
		methodTable.setCellPadding(3);
		cellFormatterRange.setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		contentPanel.createPanel("", "", methodTable);

		String selectedMethod = listBoxMethod.getItemText(listBoxMethod
				.getSelectedIndex());
		Label speLabel = new Label("搜索并加入优惠范围");
		TextBox speTextBox = new TextBox();
		Button speButton = new Button("搜索");
		final ListBox speListBox = new ListBox();
		Button speAdd = new Button("添加");
		final Widget[] widgets = { speLabel, speTextBox, speButton, speListBox,
				speAdd };
		if (selectedMethod.equals("享受赠品(特惠品)")) {
			addRow(methodTable, widgets);
		}

		listBoxMethod.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent arg0) {
				methodContentMap.clear();
				methodTable.clear();
				methodContentTable.clear();
				String selectedMethod = listBoxMethod.getItemText(listBoxMethod
						.getSelectedIndex());
				if (selectedMethod.equals("享受赠品(特惠品)")) {
					addRow(methodTable, widgets);
				}
			}
		});

		speButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				speListBox.clear();
				new ListService().listBeans(ModelNames.GOODS,
						new ListService.Listener() {
							public void onSuccess(List<BeanObject> beans) {
								for (BeanObject bean : beans) {
									speListBox.addItem(bean
											.getString(IGoods.NAME));
								}
							}
						});
			}
		});

		speAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				String selectedContent = speListBox.getItemText(speListBox
						.getSelectedIndex());
				CheckBox key = new CheckBox(selectedContent);
				key.setValue(true);
				TextBox value = new TextBox();
				value.setText("0");
				for (CheckBox checkBox : methodContentMap.keySet()) {
					if (checkBox.getText().equals(selectedContent)) {
						MessageBox.alert("该项已经存在", "该项已经存在", null);
						return;
					}
				}
				if (methodContentMap.size() == 0) {
					Widget[] widegs = { new Label("赠品（特惠品）"), new Label("价格") };
					addRow(methodContentTable, widegs);
				}
				Widget[] adds = { key, value };
				methodContentMap.put(key, value);
				addRow(methodContentTable, adds);
			}
		});

		btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(IFavourableActivity.ACT_NAME, nameTextBox.getText());
				map.put(IFavourableActivity.START_TIME, startTime.getValue().getTime());
				map.put(IFavourableActivity.END_TIME, endTime.getValue().getTime());
				map.put(IFavourableActivity.MAX_AMOUNT, maxBox.getText());
				map.put(IFavourableActivity.MIN_AMOUNT, minBox.getText());
				map.put(IFavourableActivity.SORT_ORDER, 0);

				String userRank = "";
				if (noneCb.getValue() && regCb.getValue() && priceCb.getValue()) {
					userRank = "非会员" + "," + "注册用户" + "," + "代销价格";
				} else if (!noneCb.getValue() && regCb.getValue()
						&& priceCb.getValue()) {
					userRank = "注册用户" + "," + "代销价格";
				} else if (noneCb.getValue() && !regCb.getValue()
						&& priceCb.getValue()) {
					userRank = "非会员" + "," + "代销价格";
				} else if (noneCb.getValue() && regCb.getValue()
						&& !priceCb.getValue()) {
					userRank = "非会员" + "," + "注册用户";
				} else if (!noneCb.getValue() && !regCb.getValue()
						&& priceCb.getValue()) {
					userRank = "代销价格";
				} else if (!noneCb.getValue() && regCb.getValue()
						&& !priceCb.getValue()) {
					userRank = "注册用户";
				} else if (noneCb.getValue() && !regCb.getValue()
						&& !priceCb.getValue()) {
					userRank = "非会员";
				}
				map.put(IFavourableActivity.USER_RANK, userRank);

				String actRangeExt = "";
				for (CheckBox c : rangeContentList) {
					if (c.getValue()) {
						actRangeExt += c.getText() + "&=%";
					}
				}
				map.put(IFavourableActivity.ACT_RANGE_EXT, actRangeExt);

				String actTypeExt = "";
				for (CheckBox c : methodContentMap.keySet()) {
					if (c.getValue()) {
						actTypeExt += c.getText() + ","
								+ methodContentMap.get(c).getText() + "%=&";
					}
				}
				map.put(IFavourableActivity.ACT_TYPE_EXT, actTypeExt);

				BeanObject bean = new BeanObject(ModelNames.FAVOURABLEACTIVITY,
						map);
				new CreateService().createBean(bean,
						new CreateService.Listener() {
							public void onSuccess(String id) {
								FavourableActivityListPanel.State state = new FavourableActivityListPanel.State();
								state.execute();
							}
						});
			}
		});
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				nameTextBox.setText("");
				noneCb.setValue(false);
				regCb.setValue(false);
				priceCb.setValue(false);
				rangeContentTable.clear();
				methodContentTable.clear();
				methodContentMap.clear();
				rangeContentList.clear();
				minBox.setText("0.0");
				maxBox.setText("0.0");
			}
		});

		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		btnNew.setText("确定");
		btnCancel.setText("重置");
		panel.add(btnNew);
		panel.add(btnCancel);
		contentPanel.createPanel(null, null, panel);
	}

	private void addRow(FlexTable flexTable, Widget[] wigdets) {
		int numRows = flexTable.getRowCount();
		for (int i = 0; i < wigdets.length; i++) {
			flexTable.setWidget(numRows, i, wigdets[i]);
		}
		flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
	}

	public void refresh() {
		
	}

}
