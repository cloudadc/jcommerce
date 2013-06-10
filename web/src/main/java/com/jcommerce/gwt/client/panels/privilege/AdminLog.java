package com.jcommerce.gwt.client.panels.privilege;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminLog;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.BeanCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class AdminLog extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	TextBox commentContent = new TextBox();
	
	ListBox ipAddress = new ListBox();
	ListBox deleteLog = new ListBox();
	Criteria criteria = new Criteria();
	int deleteSize = 1;
	PagingToolBar toolBar;

	public static class State extends PageState {
		public String getPageClassName() {
			return AdminLog.class.getName();
		}
		public String getMenuDisplayName() {
			return "管理员日志";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public AdminLog() {
		add(contentPanel);
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "管理员日志";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.ADMINLOG, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IAdminLog.ID, "编号", 100));
		ColumnConfig user = new ColumnConfig(IAdminLog.USER, "操作者", 120);
		columns.add(user);
		columns.add(new ColumnConfig(IAdminLog.LOGTIME, "操作日期", 150));
		columns.add(new ColumnConfig(IAdminLog.IP, "IP地址", 150));
		columns.add(new ColumnConfig(IAdminLog.LOGINFO, "操作记录", 250));

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		
		user.setRenderer(new BeanCellRenderer(ModelNames.ADMINUSER, IAdminUser.NAME, grid));
		
		ipAddress.addItem("选择IP地址...", "---");
		new ListService().listBeans(ModelNames.ADMINLOG, new ListService.Listener() {
			Set<String> address = new HashSet<String>();
			@Override
			public void onSuccess(List<BeanObject> beans) {
				for ( BeanObject bean : beans ) {
					address.add( bean.getString(IAdminLog.IP) );
				}
				for( String value : address ) {
					ipAddress.addItem(value, value);
				}
			}
		});
		
		deleteLog.addItem("选择清除的日期...", "---");
		deleteLog.addItem("一周之前", "---");
		deleteLog.addItem("一个月之前", "---");
		
		Button ipSearch = new Button(Resources.constants.ok());
		ipSearch.addSelectionListener(new SelectionListener<ButtonEvent>() {
		      public void componentSelected(ButtonEvent ce) {
		    	  search(); // 匹配IP地址
		      }
		    });
		Button clear = new Button(Resources.constants.ok());
		clear.addSelectionListener(new SelectionListener<ButtonEvent>() {
		      public void componentSelected(ButtonEvent ce) {
		    	  clear(); // 清除给定时间之前的所有信息
		      }
		    });
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("按IP地址查看"));
		header.add(ipAddress);
		header.add(ipSearch);
		header.add(new Label("清除日志"));
		header.add(deleteLog);
		header.add(clear);
		add(header);

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);

		add(panel);
	}

	private void clear() {
		Date currentTime = new Date();
		final Timestamp nowTime = new Timestamp(currentTime.getTime());
		new ListService().listBeans(ModelNames.ADMINLOG, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
				for( BeanObject bean : beans ) {
					Timestamp opTime = bean.get(IAdminLog.LOGTIME);
					if( deleteLog.getSelectedIndex() == 1 ) {
						if ( opTime.getDate() - nowTime.getDate() > 7 ) {
							new DeleteService().deleteBean(ModelNames.ADMINLOG, bean.getString(IAdminLog.ID), new DeleteService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
									createAdminLog("删除操作日志;");
									refresh();
								}
							});
						}
					} else if ( deleteLog.getSelectedIndex() == 2 ) {
						if ( opTime.getDate() - nowTime.getDate() > 30 ) {
							new DeleteService().deleteBean(ModelNames.ADMINLOG, bean.getString(IAdminLog.ID), new DeleteService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
									createAdminLog("删除操作日志");
									refresh();
								}
							});
						}
					}
				}
			}
		});
	}
	
	private void search() {
		criteria.removeAll();
		if( ipAddress.getSelectedIndex() > 0 ) {
			String ip = ipAddress.getValue(ipAddress.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IAdminLog.IP);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(ip);
			criteria.addCondition(cond);
		}
		toolBar.refresh();
	}
	
	public static void createAdminLog(String logInfo) {
		Date currentTime = new Date();
		Timestamp nowTime = new Timestamp(currentTime.getTime());
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(IAdminLog.IP, "127.0.0.1");
		values.put(IAdminLog.LOGINFO, logInfo);
		values.put(IAdminLog.LOGTIME, nowTime);
		values.put(IAdminLog.USER, "ff80808128613cbf0128614624370001");
		new CreateService().createBean(new BeanObject(ModelNames.ADMINLOG, values), new CreateService.Listener() {
			@Override
			public void onSuccess(String id) {
				
			}
		});
	}
	
	public void refresh() {
		toolBar.refresh();
	}

	class DeleteListener extends DeleteService.Listener {
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
