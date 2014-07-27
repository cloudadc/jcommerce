package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAgency;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.panels.system.NewAgency.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
/**
 * @author monkey
 */

public class AgencyList extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	Criteria criteria = new Criteria();
	PagingToolBar toolBar;

	public static class State extends PageState {
		public String getPageClassName() {
			return AgencyList.class.getName();
		}
		public String getMenuDisplayName() {
			return "办事处列表";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public AgencyList() {
		add(contentPanel);
		initJS(this);
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "办事处列表";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.AGENCY, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IAgency.ID, "编号", 70));
		columns.add(new ColumnConfig(IAgency.NAME, "办事处名称", 90));
		columns.add(new ColumnConfig(IAgency.DESCRIPTION, "办事处描述", 180));

		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.GoodsList_action(), 120);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();	
		act.setImage("icon_edit.gif");
		act.setAction("checkAgencyAction($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_trash.gif");
		act.setAction("deleteAgencyAction($id)");
		act.setTooltip("删除");
		render.addAction(act);
		
		actcol.setRenderer(render);
		
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		// panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);

		//Add new agency
		final com.extjs.gxt.ui.client.widget.button.Button addAgency = new com.extjs.gxt.ui.client.widget.button.Button("添加办事处");
        addAgency.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	// add new magazine function
            	NewAgency.State state = new NewAgency.State();
            	state.execute();
            }
          });
        panel.addButton(addAgency);
		add(panel);
	}


	private native void initJS(AgencyList me) /*-{
	   $wnd.deleteAgencyAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.system.AgencyList::deleteAgencyAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.checkAgencyAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.system.AgencyList::checkAgency(Ljava/lang/String;)(id);
	   };	   
	   }-*/;

	private void checkAgency(String id) {
		new ReadService().getBean(ModelNames.AGENCY, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
		                NewAgency.State state = new NewAgency.State();
		                state.setAgency(bean);
		                state.execute();
					}
				});
	}
	
	private void deleteAgencyAndRefrsh(final String id) {
		final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
	          public void handleEvent(MessageBoxEvent be) {
	            Button btn = be.getButtonClicked();
				if (btn.getText().equals("是")) {
					Criteria criteria = new Criteria();
					Condition condition = new Condition();
					condition.setField(IRegion.AGENCY);
					condition.setOperator(Condition.EQUALS);
					condition.setValue(id);
					criteria.addCondition(condition);
					new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener() {
						@Override
						public void onSuccess(List<BeanObject> beans) {
							for( BeanObject bean : beans ) {
								Map<String, Object> values = bean.getProperties();
								values.remove(IRegion.AGENCY);
								values.put(IRegion.AGENCY, null);
								new UpdateService().updateBean(bean.getString(IRegion.ID), new BeanObject(ModelNames.REGION, values), new UpdateService.Listener(){
									@Override
									public void onSuccess(Boolean success) {
									}
								});
							}
						}
					});
					
					new DeleteService().deleteBean(ModelNames.AGENCY, id,
							new DeleteService.Listener() {
								public void onSuccess(Boolean success) {
									toolBar.refresh();
								}
							});
				}

			}
		};
		MessageBox.confirm("Confirm", "Are you sure you want to do that?",
				deleteListener);

	}

}
