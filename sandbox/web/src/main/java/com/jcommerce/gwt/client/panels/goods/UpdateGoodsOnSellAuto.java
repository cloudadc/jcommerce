package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.BooleanPropertyActionCellRendere;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * 商品自动上下架
 * @author Daniel
 *
 */
public class UpdateGoodsOnSellAuto extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	private TextBox goodsNameTxt = new TextBox();
	private PagingToolBar toolBar = new PagingToolBar(10);;
	private IShopServiceAsync service = getService();
	private Button onSellInBatchBt = new Button("批量上架");
	private Button downSellInBatchBt = new Button("批量下架");
	private Button searchBt = null;
	private DateField dateField = new DateField();
	private ActionCellRenderer.ActionInfo act = null;
	private BeanObject category = null;
	private Grid<BeanObject> grid = null;
	private Criteria criteria = new Criteria();
	private static UpdateGoodsOnSellAuto instance;
	
	
	public static UpdateGoodsOnSellAuto getInstance() {
		if (instance == null) {
			instance = new UpdateGoodsOnSellAuto();
		}
		return instance;
	}

	public UpdateGoodsOnSellAuto() {
		initJS(this);
	}

	private native void initJS(UpdateGoodsOnSellAuto me) /*-{
	   $wnd.undoAndRefresh = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.UpdateGoodsOnSellAuto::undoAndRefresh(Ljava/lang/String;)(id);
	   };
	   }-*/;

	public static class State extends PageState {
		public String getPageClassName() {
			return UpdateGoodsOnSellAuto.class.getName();
		}

		public String getMenuDisplayName() {
			return "商品自动上下架";
		}
	}

	private State curState = new State();

	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}

	@Override
	public String getDescription() {
		return "UpdateGoodsOnSellAuto";
	}

	@Override
	public String getName() {

		return "商品自动上下架";
	}

	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);
		add(contentPanel);
        Button searchBt = new Button("搜索", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                criteria.removeAll();
                if (!goodsNameTxt.getText().trim().equals("")) {
                    criteria.addCondition(new Condition(IGoods.NAME, Condition.EQUALS, goodsNameTxt.getText()));
                }
                toolBar.refresh();
            }

        });
		
		HorizontalPanel goodsSearchPanel = new HorizontalPanel();
		goodsSearchPanel.add(new Label("商品名称: "));
		goodsSearchPanel.add(goodsNameTxt);
		goodsSearchPanel.add(searchBt);
		contentPanel.createPanel(IGoods.NAME, null,goodsSearchPanel);
		
		final ListStore<BeanObject> store= onInitailization();
		
		final ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(880, 350);
		
		
		panel.setBottomComponent(toolBar);
		add(panel);
		HorizontalPanel datePickerOPeratePanel = initDatePickerPanel(store);
		add(datePickerOPeratePanel);

	}
	
	private void setWigetsDisable()
	{
		dateField.disable();
		onSellInBatchBt.disable();
		downSellInBatchBt.disable();
	}
	
	private void setWigetsEnable()
	{
		onSellInBatchBt.enable();
		downSellInBatchBt.enable();
	}
	
	public HorizontalPanel initDatePickerPanel(final ListStore<BeanObject> store)
	{
		HorizontalPanel datePickerOPeratePanel = new HorizontalPanel();
		
		dateField.addListener(Events.OnMouseOver, new Listener(){
			public void handleEvent(BaseEvent be) {
				Date date = dateField.getValue();
				
				if(dateField.getValue() != null)
				{
					setWigetsEnable();
				}
			}	
		});
		datePickerOPeratePanel.add(dateField);
		datePickerOPeratePanel.setSpacing(3);
		
		setWigetsDisable();
		
		datePickerOPeratePanel.add(onSellInBatchBt);
		datePickerOPeratePanel.add(downSellInBatchBt);
		
		datePickerOPeratePanel.add(new Button("选择", new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				dateField.enable();
			}
		}));

		datePickerOPeratePanel.add(new Button(Resources.constants.GoodsList_save(),
				new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				List<Record> modifies = store.getModifiedRecords();
				final UpdateListener listener = new UpdateListener();
				for(final Record re:modifies)
				{
					String id =  (String) re.get(IGoods.ID);
					ReadService reader = new ReadService();
					
					reader.getBean(ModelNames.GOODS, id, new ReadService.Listener(){
						public void onSuccess(BeanObject goods) {
							goods.set(IGoods.ONSALE, Boolean.TRUE);
							Map changes = re.getChanges();
							
							updateGoods(goods, listener);
						}
					});
				}
				new WaitService(new WaitService.Job() {
					public boolean isReady() {
						if(listener.finished)
						{
							return true;
						}
						return false;
					}

					public void run() {
						store.commitChanges();
						toolBar.refresh();
					}
				});
			}
		}));
		
		return datePickerOPeratePanel;
	}
	
	public List initColumnConfig()
	{
		List resultList = new ArrayList();
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IGoods.ID, "ID", 50));
		ColumnConfig col = new ColumnConfig(IGoods.NAME, Resources.constants
				.Goods_name(), 100);
		columns.add(col);
		col = new ColumnConfig(IGoods.SN, Resources.constants.Goods_SN(), 100);
		columns.add(col);

		col = new ColumnConfig(IGoods.PROMOTESTART, Resources.constants.Goods_promoteStart(), 200);
		col.setEditor(new CellEditor(generateDatePickerObj()));
		columns.add(col);
		
		col = new ColumnConfig(IGoods.PROMOTEEND, Resources.constants.Goods_promoteEnd(), 200);
		col.setEditor(new CellEditor(generateDatePickerObj()));
		columns.add(col);
		
		ColumnConfig actcol = new ColumnConfig(IGoods.ID, Resources.constants
				.GoodsList_action(), 100);
		columns.add(actcol);
	
		
		resultList.add(columns);
		resultList.add(smRowSelection);
		resultList.add(actcol);
		return resultList;
	}
	
	public ListStore<BeanObject> onInitailization()
	{
		List initResultList = new ArrayList();
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.GOODS, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateGoods(bean, null);
				}
			}
		});

		toolBar.bind(loader);
		
		List resultList = initColumnConfig();
		
		ColumnModel cm = new ColumnModel((List<ColumnConfig>) resultList.get(0));

		final CheckBoxSelectionModel<BeanObject> smRowSelection = (CheckBoxSelectionModel<BeanObject>) 
																				resultList.get(1);

		grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		
		BooleanPropertyActionCellRendere render = new BooleanPropertyActionCellRendere(ModelNames.GOODS, 
				                                      IGoods.ONSALE, grid, "", "撤消");
		act = new ActionCellRenderer.ActionInfo();
		act.setAction("undoAndRefresh($id)");
		render.addAction(act);
		ColumnConfig actcol = (ColumnConfig)resultList.get(2);
		actcol.setRenderer(render);
		onSellInBatchBt.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				final List<BeanObject> items = smRowSelection.getSelectedItems();
				String dateStr = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(dateField.getValue());
				for (BeanObject item : items) {
					item.set(IGoods.PROMOTESTART, dateStr);
					item.set(IGoods.ONSALE, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					updateGoods(item, listener);
					waitForRefreshByUpdate(listener);
				}
				
				setWigetsDisable();
			}
		});
		
		downSellInBatchBt.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				final List<BeanObject> items = smRowSelection.getSelectedItems();
				String dateStr = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(dateField.getValue());
				for (BeanObject item : items) {
					item.set(IGoods.PROMOTEEND, dateStr);
					item.set(IGoods.ONSALE, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					updateGoods(item, listener);
					waitForRefreshByUpdate(listener);
				}
				
				setWigetsDisable();
			}
		});
		
		initResultList.add(store);
		return store;
	}
	
	private void updateGoods(BeanObject goods, UpdateService.Listener listener) {
		new UpdateService().updateBean(goods.getString(IGoods.ID), goods,
				listener);
	}
	
	private AdapterField generateDatePickerObj()
	{
		final DatePicker dateObj = new DatePicker();
		final AdapterField field = new AdapterField(dateObj);
		field.setToolTip("点击修改内容");
		dateObj.addListener(Events.Select, new Listener<ComponentEvent>() {
                public void handleEvent(ComponentEvent be) {
                    String d = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(dateObj.getValue());
    //              dateObj.setData(d);
                    field.setValue(d);
                    
                    dateObj.hide();                
                }
			});
		return field;
	}
	
	private void waitForRefreshByUpdate(final UpdateListener listener)
	{
		new WaitService(new WaitService.Job() {
			public boolean isReady() {
			    return listener.finished;
			}

			public void run() {
				toolBar.refresh();
			}
		});
	}
	
	private void undoAndRefresh(String id) {
		
		final List goodsList = new ArrayList();
		new ReadService().getBean(ModelNames.GOODS, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						goodsList.add(bean);
						long l = 0;
						Date date = new Date(l);
						String d = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss").format(date);
						bean.set(IGoods.PROMOTESTART, d);
						bean.set(IGoods.PROMOTEEND, d);
						bean.set(IGoods.ONSALE, Boolean.FALSE);
						
						final UpdateListener listener = new UpdateListener();
						updateGoods(bean,listener);
						
						waitForRefreshByUpdate(listener);
					}
				});
		
	}
	
	class UpdateListener extends UpdateService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}
}