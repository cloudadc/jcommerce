/**
 * Author: Kylin Soong
 */

package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IDeliveryMeta;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.system.ShippingInstaller;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class DiliveryMethodSet extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	PagingToolBar toolBar;

	//Save deleveryMethods which exists in DataBase, its can update dynamic
	private Set<BeanObject> beanSet = new HashSet<BeanObject>();

	// Save the all deleveryMethods which read from file.
	private Map<String, BeanObject> beanMap = new HashMap<String, BeanObject>();

	private static DiliveryMethodSet instance = null;

	public static DiliveryMethodSet getInstance() {
		if (instance == null) {
			instance = new DiliveryMethodSet();
		}
		return instance;
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return DiliveryMethodSet.class.getName();
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
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "配送方式";
	}

	public DiliveryMethodSet() {
		initBeanSet(beanSet);
		add(contentPanel);
		initJS(this);
	}

	private void initBeanSet(final Set<BeanObject> beanSet) {
		new ListService().listBeans(ModelNames.DELIVERY_METHOD_SET,
				new ListService.Listener() {
					@Override
					public void onSuccess(List<BeanObject> beans) {
						for (BeanObject beanObject : beans) {
							beanSet.add(beanObject);
						}
					}
				});
	}

	private native void initJS(DiliveryMethodSet me) /*-{
														$wnd.installDelivery = function (id) {
														me.@com.jcommerce.gwt.client.panels.DiliveryMethodSet::installDelivery(Ljava/lang/String;)(id);
														};
														$wnd.setDeliveryZone = function (id) {
														me.@com.jcommerce.gwt.client.panels.DiliveryMethodSet::setDeliveryZone(Ljava/lang/String;)(id);
														};
														$wnd.uninstallDelivery = function (id) {
														me.@com.jcommerce.gwt.client.panels.DiliveryMethodSet::uninstallDelivery(Ljava/lang/String;)(id);
														};
														}-*/;


	public void installDelivery(final String name) {
		final BeanObject bean = beanMap.get(name);
		new CreateService().createBean(bean, new CreateService.Listener() {
			@Override
			public void onSuccess(String id) {
				gotoInstallSuccessPanel();
				// update beanSet to synchronize with Database
				beanSet.add(bean);
			}
		});
	}

	private void gotoInstallSuccessPanel() {
		Success.State newState = new Success.State();
		newState.setMessage("配送方式安装成功！");

		// ------add wu ze fei---------------------------------------------------------------------
		//type用来辨别哪种配送方式安装成功,如上门取货为cac，EMS国内邮政特快转的为ems，根据ECSHOP
		String type = "cac";
//		iShop.getInstance().displayShippingInstaller(type);
		ShippingInstaller.State state = new ShippingInstaller.State();
		state.execute();
		
		
//		GoodsTypeListPanel.State choice1 = new GoodsTypeListPanel.State();
//		newState.addChoice(GoodsTypeListPanel.getInstance().getName(), choice1
//				.getFullHistoryToken());
//
//		DiliveryMethodSet.State choice2 = new DiliveryMethodSet.State();
//		newState.addChoice(DiliveryMethodSet.getInstance().getName(), choice2
//				.getFullHistoryToken());
//
//		newState.execute();
	}

	public void uninstallDelivery(final String id) {
		System.out.println(id);

		new DeleteService().deleteBean(ModelNames.SHIPPING, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						gotoUninstallSuccessPanel(); // update the primary key
						//  update beanSet to synchronize with Database
						beanSet.clear();
						initBeanSet(beanSet);
					}
				});

	}

	private void gotoUninstallSuccessPanel() {
		Success.State newState = new Success.State();
		newState.setMessage("配送方式卸载成功！");
		DiliveryMethodSet.State choice = new DiliveryMethodSet.State();
		newState.addChoice(DiliveryMethodSet.getInstance().getName(), choice);
		newState.execute();
	}

	public void setDeliveryZone(final String id) {
		// ------------add wu ze fei---z---------------------------------------------------------------
//		GoodsTypeListPanel.State newState = new GoodsTypeListPanel.State();
//		newState.execute();
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

//		BasePagingLoader loader = new ShippingInitService().getLoader();
		BasePagingLoader loader = null;
		loader.load(0, 50);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(50);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig(IDeliveryMeta.NAME, "配送方式名称", 120));
		columns.add(new ColumnConfig(IDeliveryMeta.DESC, "配送方式描述", 320));
		columns.add(new ColumnConfig(IDeliveryMeta.SUPPORT_COD, "货到付款？", 80));
		columns.add(new ColumnConfig(IDeliveryMeta.INSURE, "保价费用", 80));
		columns.add(new ColumnConfig(IDeliveryMeta.CODE, "插件版本", 80));
		ColumnConfig actcol = new ColumnConfig("Action", "操作", 100);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);

		MyActionCellRenderer render = new MyActionCellRenderer(grid);
		actcol.setRenderer(render);

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setHeading("setup the deliveryMeta");
		panel.setLayout(new FitLayout());
		panel.setSize(800, 350);
		panel.add(grid);

		this.add(panel);
	}

	protected void refresh() {
		toolBar.refresh();
	}

	public boolean isExistInDB(BeanObject obj) {
		for (Iterator<BeanObject> iterator = beanSet.iterator(); iterator
				.hasNext();) {
			BeanObject bo = iterator.next();
			if (bo.getString(IDeliveryMeta.NAME).equals(
					obj.getString(IDeliveryMeta.NAME))) {
//				obj.updateBeanObjectEntity(IDeliveryMeta.NAME, bo
//						.getString(IDeliveryMeta.ID));
				return true;
			}
		}
		return false;
	}

	class MyActionCellRenderer extends ActionCellRenderer {

		public MyActionCellRenderer(Grid grid) {
			super(grid);
		}

		public Object render(BeanObject model, String property,
				ColumnData config, int rowIndex, int colIndex,
				ListStore<BeanObject> store, Grid<BeanObject> grid) {

			beanMap.put(model.getString(IDeliveryMeta.NAME), model);
			removeAll();
			boolean enabled = isExistInDB(model);
			if (!enabled) {
				ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
				act.setText("安装");
				act.setAction("installDelivery($name)");
				addAction(act);
			} else {
				ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
				act.setText("卸载");
				act.setAction("uninstallDelivery($id)");
				addAction(act);
				act = new ActionCellRenderer.ActionInfo();
				act.setText("    设置区域");
				act.setAction("setDeliveryZone($id)");
				addAction(act);
			}
			return super.render(model, property, config, rowIndex, colIndex,
					store, grid);
		}
	}
}
