package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.List;

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
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.IdToStringRenderer;

public class UserComments extends ContentWidget {	

	ColumnPanel contentPanel = new ColumnPanel();	
	TextBox commentContent = new TextBox();		
	Button btnFind = new Button(Resources.constants.GoodsList_search());	
	ListBox lstAction = new ListBox();
	Button btnAct = new Button(Resources.constants.GoodsList_action_OK());
	Criteria criteria = new Criteria();
    int deleteSize = 1;
	PagingToolBar toolBar;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return UserComments.class.getName();
		}
		public String getMenuDisplayName() {
			return "用户评论";
		}
	}
	public State getCurState() {
		return (State)curState;
	}

	public UserComments() {
	    curState = new State();
		add(contentPanel);
		initJS(this);

	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "用户评论";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		Condition cond = new Condition();
		cond.setField(IComment.PARENT);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(null);
		criteria.addCondition(cond);
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.COMMENT, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IComment.ID, "编号", 80));
		columns.add(new ColumnConfig(IComment.USERNAME, "用户名", 100));		
		
		ColumnConfig commentType = new ColumnConfig(IComment.COMMENTTYPE, "类型", 60);
		columns.add(commentType);
		
		
		columns.add(new ColumnConfig(IComment.IDVALUE,"评论对象",60));
		columns.add(new ColumnConfig(IComment.IPADDRESS, "IP地址", 100));		
		columns.add(new ColumnConfig(IComment.ADDTIME,"评论时间", 150));		
		
		
		ColumnConfig status = new ColumnConfig(IComment.STATUS, "状态", 60);
		columns.add(status);
		
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.GoodsList_action(), 65);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		//   grid.setAutoExpandColumn("forum");
		IdToStringRenderer commentTypeCell = new IdToStringRenderer(grid);
		commentTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("0","商品"));
		commentTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("1","文章"));
		commentType.setRenderer(commentTypeCell);
		
		IdToStringRenderer statusCell = new IdToStringRenderer(grid);
		statusCell.addIdInfo(new IdToStringRenderer.IdInfo("false","隐藏"));
		statusCell.addIdInfo(new IdToStringRenderer.IdInfo("true","显示"));
		status.setRenderer(statusCell);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("checkCommentAction($id)");
		act.setTooltip("查看详情");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteCommentAction($id)");
		act.setTooltip("删除");
		render.addAction(act);

		actcol.setRenderer(render);
		
	
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("输入评论内容"));
		header.add(commentContent);		
		header.add(btnFind);
		add(header);

		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search();
			}
		});

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);

		add(panel);
		//添加下面一行的操作BUTTON 及 listener  包括  删除评论 允许显示 禁止显示
		lstAction.addItem("请选择...", "---");
		lstAction.addItem("删除评论", "delete");		
		lstAction.addItem("允许显示", "agree");
		lstAction.addItem("禁止显示", "forbid");
		btnAct.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				int index = lstAction.getSelectedIndex();
				if (index == 0) {
					// it is prompt
					return;
				}

				String sel = lstAction.getValue(index);
				final List<BeanObject> items = smRowSelection
						.getSelectedItems();
				executeAction(items, sel);
			}
		});

		HorizontalPanel footer = new HorizontalPanel();
		footer.add(lstAction);
		footer.add(btnAct);
		add(footer);
	}

	private void updateComment(BeanObject comments, UpdateService.Listener listener) {
		new UpdateService().updateBean(comments.getString(IComment.ID), comments,
				listener);
	}
	
	private void search() {
		criteria.removeAll();
		Condition normal = new Condition();
		normal.setField(IComment.PARENT);
		normal.setOperator(Condition.EQUALS);
		normal.setValue(null);
		criteria.addCondition(normal);
		if (commentContent.getText().length() != 0) {
			String content = commentContent.getText();
			Condition cond = new Condition();
			cond.setField(IComment.CONTENT);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(content.trim());
			criteria.addCondition(cond);
		}	
		else{
			System.out.println("Input nothing!");
		}

		toolBar.refresh();
	}

	private void executeAction(final List<BeanObject> items, String action) {
		if (items == null) {
			return;
		}

		final List listeners = new ArrayList();

		for (BeanObject item : items) {
			if ("delete".equals(action)) {
				DeleteListener listener = new DeleteListener();
				listeners.add(listener);
				deleteComment(item.getString(IComment.ID), listener);
			} 
			else if ("forbid".equals(action)) {
				if (!Boolean.FALSE.equals(item.get(IComment.STATUS))) {
					item.set(IComment.STATUS, Boolean.FALSE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateComment(item, listener);
				}
			} 
			else if ("agree".equals(action)) {
				if (!Boolean.TRUE.equals(item.get(IComment.STATUS))) {
					item.set(IComment.STATUS, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateComment(item, listener);
				}
			} 
		}

		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				if (listeners.size() != items.size()) {
					return false;
				}
				for (int i = 0; i < listeners.size(); i++) {
					if (listeners.get(i) instanceof DeleteListener) {
						if (!((DeleteListener) listeners.get(i)).isFinished()) {
							return false;
						}
					} else if (listeners.get(i) instanceof UpdateListener) {
						if (!((UpdateListener) listeners.get(i)).isFinished()) {
							return false;
						}
					} else {
						throw new RuntimeException("Unknown listener type:"
								+ listeners.get(i));
					}
				}
				return false;
			}

			public void run() {
				toolBar.refresh();
			}
		});
	}

	private native void initJS(UserComments me) /*-{
	   $wnd.deleteCommentAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.article.UserComments::deleteCommentAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.checkCommentAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.article.UserComments::checkComment(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void deleteComment(String id, DeleteService.Listener listener) {
		new DeleteService().deleteBean(ModelNames.COMMENT, id, listener);
	}

	private void deleteCommentAndRefrsh(final String id) {
		new DeleteService().deleteBean(ModelNames.COMMENT, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    UserComments.State state = new UserComments.State();
					    state.execute();
					}
				});		
	}
	private void checkComment(String id) {
		new ReadService().getBean(ModelNames.COMMENT, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
					    CommentInfo.State state = new CommentInfo.State();
					    state.setComment(bean);
					    state.execute();
					}
				});
	}

	public void refresh(){
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
