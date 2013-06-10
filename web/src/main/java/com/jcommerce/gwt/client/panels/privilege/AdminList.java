package com.jcommerce.gwt.client.panels.privilege;

import java.util.ArrayList;
import java.util.List;

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
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.member.AssignRole;
import com.jcommerce.gwt.client.panels.member.MemberAddresses;
import com.jcommerce.gwt.client.panels.member.AssignRole.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
/**
 * @author monkey 
 */
public class AdminList extends ContentWidget {
    public static interface Constants {
        String AdminList_userName();
        String AdminList_email();
        String AdminList_title();
        String AdminList_addTime();
        String AdminList_lastLogin();
    }

	ColumnPanel contentPanel = new ColumnPanel();
	TextBox commentContent = new TextBox();
	Criteria criteria = new Criteria();
	int deleteSize = 1;
	PagingToolBar toolBar;

	public static class State extends PageState {
		public String getPageClassName() {
			return AdminList.class.getName();
		}

		public String getMenuDisplayName() {
			return "管理员列表";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public AdminList() {
		add(contentPanel);
		initJS(this);
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "管理员列表";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.ADMINUSER, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig(IAdminUser.NAME, "用户名", 120));
		columns.add(new ColumnConfig(IAdminUser.EMAIL, "Email地址", 180));
		columns.add(new ColumnConfig(IAdminUser.ADDTIME, "加入时间", 180));
		columns.add(new ColumnConfig(IAdminUser.LASTLOGIN, "最后登录时间", 180));

		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 140);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_trash.gif");
		act.setAction("deleteAdminAction($id)");
		act.setTooltip("删除");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("editAdminAction($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("assign_role.gif");
		act.setAction("assignRoleAction($id)");
		act.setTooltip("分派权限");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("view_log.gif");
		act.setAction("viewLogAction($id)");
		act.setTooltip("查看日志");
		render.addAction(act);
		
		actcol.setRenderer(render);
		
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setHeading("管理员列表");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		Button addAdmin = new Button("添加管理员");
		addAdmin.addSelectionListener(new SelectionListener<ButtonEvent>() {
		      public void componentSelected(ButtonEvent ce) {
		          NewAdminUser.State state = new NewAdminUser.State();
		          state.execute();
		      }
		    });
		panel.addButton(addAdmin);

		add(panel);
	}

	private native void initJS(AdminList me) /*-{
	   $wnd.deleteAdminAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.privilege.AdminList::deleteAdminAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.editAdminAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.privilege.AdminList::editAdmin(Ljava/lang/String;)(id);
	   };
	   $wnd.assignRoleAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.privilege.AdminList::assignRole(Ljava/lang/String;)(id);
	   };
	   $wnd.viewLogAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.privilege.AdminList::viewLog(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void assignRole(final String id) {
		new ReadService().getBean(ModelNames.ADMINUSER, id, new ReadService.Listener() {
			public synchronized void onSuccess(BeanObject result) {
				AssignRole.State state = new AssignRole.State();
				state.setRole(result);
				state.execute();
			}
		});
	}
	
	private void viewLog(final String id) {
		AdminLog.State state = new AdminLog.State();
		state.execute();
	}
	
	/**
	 * 删除管理员
	 * @param id
	 */
	private void deleteAdminAndRefrsh(final String id) {
		final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		      public void handleEvent(MessageBoxEvent ce) {
		          Button btn = ce.getButtonClicked();
				if (btn.getText().equals("是")) {
					new DeleteService().deleteBean(ModelNames.ADMINUSER, id,
							new DeleteService.Listener() {
								public void onSuccess(Boolean success) {
									refresh();
									AdminLog.createAdminLog("删除管理员；");
								}
							});
				}
			}
		};
		MessageBox.confirm("Confirm", "Are you sure you want to do that?",
				deleteListener);
	}

	private void editAdmin(String id) {
		new ReadService().getBean(ModelNames.ADMINUSER, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						NewAdminUser.State state = new NewAdminUser.State();
						state.setAdminUser(bean);
						state.execute();
					}
				});
	}

	private void checkMemberAddress(String id) {
		MemberAddresses.State state = new MemberAddresses.State();
		state.setMember(id);
		state.execute();
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
