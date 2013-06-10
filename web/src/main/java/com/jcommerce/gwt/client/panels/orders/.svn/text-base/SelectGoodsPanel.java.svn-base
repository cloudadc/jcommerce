package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.model.RegularExConstants;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author monkey
 */
public class SelectGoodsPanel extends ContentPanel{
	
//	private String searchField = null;
	private ListStore<BeanObject> store;
	private BeanObject order = new BeanObject(ModelNames.ORDER);
	private BeanObject user = new BeanObject(ModelNames.USER);
	private List<BeanObject> selectedGoods = new ArrayList<BeanObject>();
	private TextBox goodsNumberBox = new TextBox();
	private TextBox nameBox = new TextBox();
	private TextBox brandBox = new TextBox();
	private TextBox priceBox = new TextBox();
	private TextBox attributeBox = new TextBox();
	private TextField<String> amountBox = new TextField<String>();
	private List<BeanObject> orderGoods;
	private int marking = 0;
	private double discount = 0;
	List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	Grid<BeanObject> grid;
	private static SelectGoodsPanel instance;
	PagingToolBar toolBar;
	
	public static SelectGoodsPanel getInstance() {
		if (instance == null) {
			instance = new SelectGoodsPanel();
		}
		return instance;
	}
	
	private native void initJS(SelectGoodsPanel me) /*-{
	   $wnd.deleteOrderGoods = function (id) {
	       me.@com.jcommerce.gwt.client.panels.orders.SelectGoodsPanel::deleteOrderGoods(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	public boolean isNoData() {
		boolean isNoData = true;
		if(grid.getStore().getCount() > 0) {	
			isNoData = false;
		}
		return isNoData;
	}
	
	public ContentPanel initailGoodsListPanel()
	{
		int pageSize = 1;
    	
    	store = new ListStore<BeanObject>();
    	
    	toolBar = new PagingToolBar(pageSize);
		//The beanobject in table is IOrderGoods
    	columns.add(new ColumnConfig(IOrderGoods.ID, "物件", 80));
    	columns.add(new ColumnConfig(IOrderGoods.GOODS, "商品编号", 100));
		columns.add(new ColumnConfig(IOrderGoods.GOODSNAME, "商品名称", 100));
		columns.add(new ColumnConfig(IOrderGoods.GOODSSN, "货号", 80));
        columns.add(new ColumnConfig(IOrderGoods.GOODSPRICE, "价格", 80));
        
        ColumnConfig noColumn = new ColumnConfig(IOrderGoods.GOODSNUMBER, "数量", 80);
        
        columns.add(noColumn);
        
        
        columns.add(new ColumnConfig(IOrderGoods.MARKETPRICE, "小计", 80));
        
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.action(), 80);
        columns.add(actcol);
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
        
        //add a deleteOrdergoodsAction to table
        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = null;
        act = new ActionCellRenderer.ActionInfo();
//        act.setText(" 删除");
        act.setImage("icon_trash.gif");
		act.setAction("deleteOrderGoods($id)");
		render.addAction(act);
              
        actcol.setRenderer(render);
    	
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.add(grid);
        panel.setSize(780, 200);
        panel.setBottomComponent(toolBar);
        return panel;
	}
	
	public List<BeanObject> getOderGoodsLlist() 
	{
		List<BeanObject> orderGoodsList = new ArrayList<BeanObject>();
		
		for(int i=0; i<store.getCount(); i++)
		{
			orderGoodsList.add(store.getAt(i));
		}
		
		return orderGoodsList;
	}

	public SelectGoodsPanel() {
		
		initJS(this);
		
		ContentPanel goodsPanel = initailGoodsListPanel();
        
        /**
         * search panel
         */
        ContentPanel contentSearch = new ContentPanel();
        HorizontalPanel search = new HorizontalPanel();
		final TextField nameField = new TextField();
		
        nameField.setFieldLabel("按商品编号或商品名称或商品货号搜索"); 
        final ListBox searchResultBox = new ListBox();
        searchResultBox.addItem("--请选择--");
        
        searchResultBox.addClickListener(new ClickListener() {
        	//Read the selected Goods info to textfields.
			public void onClick(Widget sender) {
				if(searchResultBox.getItemCount() > 1 && searchResultBox.getSelectedIndex()!=0) {
					int index = searchResultBox.getSelectedIndex();
					// get object by index
					BeanObject selectedBean = selectedGoods.get(index);
					nameBox.setText(selectedBean.getString(IGoods.NAME));
					goodsNumberBox.setText(selectedBean.getString(IGoods.SN));
					String brandID = selectedBean.getString(IGoods.BRAND);
					new ReadService().getBean(ModelNames.BRAND, brandID, new ReadService.Listener() {
						public synchronized void onSuccess(BeanObject result) {
							brandBox.setText(result.getString(IBrand.NAME));
			            }
					});
					priceBox.setText(selectedBean.getString(IGoods.SHOPPRICE));
					attributeBox.setText(selectedBean.getString(IGoods.ATTRIBUTES));
					marking = index;
				}
			}
        	
        });
        
        Button searchButton = new Button("搜索"); 
        searchButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				
				if(nameField.getValue() == null)
				{
					return ;
				}
				searchResultBox.clear();
				selectedGoods.clear();
				searchResultBox.addItem("--请选择--");
				selectedGoods.add(null);
				// use goods_name or goods_sn to search info.
				Criteria criteria = new Criteria(Criteria.OR);
				criteria.addCondition(new Condition(IGoods.NAME,
						Condition.EQUALS, nameField.getValue().toString()));
				criteria.addCondition(new Condition(IGoods.SN,
						Condition.EQUALS, nameField.getValue().toString()));
				
				new ListService().listBeans(ModelNames.GOODS, criteria,
						new ListService.Listener() 
				{
					public void onSuccess(List<BeanObject> beans) 
					{
						if(beans.size() < 1)
						{
							Window.alert("未找到与"+ nameField.getValue().toString()  +"匹配的商品!");
						}
						for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();)
						{
							BeanObject goods = it.next();
							searchResultBox.addItem(goods.getString(IGoods.NAME), 
													goods.getString(IGoods.ID));
							selectedGoods.add(goods);
						}
					}
					
				});
			}
		});
        searchButton.setHeight("22");
        search.add(new Label("按商品名称或商品货号搜索:"));
        search.add(nameField);
        search.add(new Label("    "));
        search.add(searchButton);
        search.add(new Label("    "));
        search.add(searchResultBox);      
        contentSearch.add(search);
        contentSearch.setBodyBorder(true);
        contentSearch.setHeight("70px");
        
        /**
         * search goods panel
         */
        ContentPanel contentSearchGoods = new ContentPanel();
        ColumnPanel contentPanel = new ColumnPanel();
        
        nameBox.setWidth("600px");
        nameBox.setEnabled(false);
        contentPanel.createPanel(IGoods.NAME, "商品名称:", nameBox);
        
        goodsNumberBox.setWidth("600px");
        goodsNumberBox.setEnabled(false);
        contentPanel.createPanel(IGoods.SN, "货号", goodsNumberBox);
        
        brandBox.setWidth("600px");
        brandBox.setEnabled(false);
        contentPanel.createPanel(IBrand.NAME, "品牌名称:", brandBox);
        
        priceBox.setWidth("600px");
        priceBox.setEnabled(false);
        contentPanel.createPanel(IGoods.SHOPPRICE, "价格", priceBox);
        
        attributeBox.setWidth("600px");
        attributeBox.setEnabled(false);
        contentPanel.createPanel(IGoods.BRIEF, "属性", attributeBox);
        
        amountBox.setWidth("100px");
        amountBox.setEnabled(true);
        amountBox.setRegex(RegularExConstants.POSITIVEINT);
        amountBox.getMessages().setRegexText(RegularExConstants.POSITIVEINTREGMSG);
        amountBox.setValue("1");
        contentPanel.createPanel(IOrder.GOODSAMOUNT, "数量", amountBox);
        com.extjs.gxt.ui.client.widget.button.Button orderButton = new com.extjs.gxt.ui.client.widget.button.Button();
        orderButton.setText("加入订单");
        //convert goods to ordergoods. and write it into table
        orderButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(nameBox.getText() != null && nameBox.getText().trim().length() != 0) {
					orderGoods = new ArrayList<BeanObject>();
					BeanObject goods = selectedGoods.get(marking);
					if(!(amountBox.isValid()))
					{
						Info.display("Error", "Please type a correct number" +
								" for the goods you want to buy");
						return;
					}
					orderGoods.add(convertGoodsToOrderGoods(goods));
					store.add(orderGoods);
				}
				nameBox.setText("");
				goodsNumberBox.setText("");
				brandBox.setText("");
				priceBox.setText("");
				attributeBox.setText("");
				nameField.setValue(new StringBuffer(""));
				searchResultBox.clear();
				searchResultBox.addItem("--请选择--");
			}
        });
        
        contentSearchGoods.addButton(orderButton);
        contentSearchGoods.setFrame(true);
        contentSearchGoods.setCollapsible(true);
        contentSearchGoods.setButtonAlign(HorizontalAlignment.CENTER);
        contentSearchGoods.setIconStyle("icon-table");
        contentSearchGoods.setButtonAlign(HorizontalAlignment.CENTER);      
        
        contentSearchGoods.add(contentPanel);
    
        this.add(goodsPanel);
        this.add(contentSearch);
        this.add(contentSearchGoods);
        this.setHeading("选择商品");
        this.setSize(780, 555);
	}
	
	//We get Goods info from database first. then We need convert them to OrderGoods. After this
	//we can insert OrderGoods BeanObject into database.
	public BeanObject convertGoodsToOrderGoods(BeanObject goods)
	{
		BeanObject bean = new BeanObject();
		bean.set(IOrderGoods.ID, store.getCount()+1);
		bean.set(IOrderGoods.GOODS, goods.getString(IGoods.ID));
		bean.set(IOrderGoods.GOODSNAME, goods.getString(IGoods.NAME));
		bean.set(IOrderGoods.GOODSSN, goods.getString(IGoods.SN));
		bean.set(IOrderGoods.GOODSPRICE, goods.getString(IGoods.SHOPPRICE));
		
		int number = Integer.parseInt(amountBox.getValue().toString());
		bean.set(IOrderGoods.GOODSNUMBER, number);
		float unitPrice = Float.parseFloat(priceBox.getText());
		
		
		bean.set(IOrderGoods.MARKETPRICE, number*unitPrice*(100-discount)/100);
		
		if(null !=order.getString(IOrder.PAYNOTE))
		{
			order.set(IOrder.PAYNOTE, Double.parseDouble(order.getString(IOrder.PAYNOTE)) + number*unitPrice);
		}
		else
		{
			order.set(IOrder.PAYNOTE, number*unitPrice);
		}
		return bean;
	}
	
	//get the line id. and delete the beanobject from table.
	private void deleteOrderGoods(String id)
	{
		int idInt = Integer.parseInt(id);
		BeanObject orderGoods = store.getAt(idInt-1);
		System.out.println(orderGoods.getString(IOrderGoods.GOODSNAME));
		store.remove(store.getAt(idInt-1));
		
		toolBar.refresh();
	}
	
	public ListStore<BeanObject> getOrderGoodsStore() 
	{
		return store;
	}
	
	public BeanObject getOrder() 
	{
		
		return order;
	}

	public void setOrder(BeanObject order) 
	{
		this.order = order;
	}
	
	public void setUser(BeanObject user) 
	{
		this.user = user;
	}
	
	public double caculateDiscount()
	{
		if(user != null)
		{
			final ReadListener listener = new ReadListener();
			new ReadService().getBean(ModelNames.USERRANK, user.getString(IUser.RANK),
										listener);
			new WaitService(new WaitService.Job() {			
				public boolean isReady()
				{
					return listener.isFinished();
				}
				public void run() 
				{
					BeanObject rank = listener.getUserRank();
					
					double theDiscount = Double.parseDouble(rank.getString(IUserRank.DISCOUNT));
					double integral = Double.parseDouble(user.getString(IUser.PAYPOINTS));
					discount = theDiscount + integral;
					
					Info.display("Info", "The member has a " + discount + " discount");
					order.set(IOrder.DISCOUNT, discount);
				}
	          });
			
		}
		return 0;
	}
	
	class ReadListener extends ReadService.Listener {
		private boolean finished = false;
		private BeanObject userRank = new BeanObject(ModelNames.USERRANK);

		public BeanObject getUserRank() {
			return userRank;
		}


		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}

		public void onSuccess(BeanObject bean)
		{
			userRank = bean;
			finished = true;
		}
	}
}
