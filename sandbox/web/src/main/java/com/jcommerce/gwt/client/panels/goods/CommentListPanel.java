package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CommentForm;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.TimeCellRenderer;

public class CommentListPanel extends ContentWidget {
	public static interface Constants {
		String CommentList_title();
		String CommentList_deleteSuccessfully();
		String CommentList_deleteFailure();
		String CommentList_rank();
		String CommentList_userName();
		String CommentList_commentType();
		String CommentList_idValue();
		String CommentList_ipAddress();
		String CommentList_addTime();
		String CommentList_status();
		String CommentList_keyword();
		String CommentList_search();
		String CommentList_action_OK();
		String CommentList_action_deleteComment();
		String CommentList_action_statusActive();
		String CommentList_action_statusInactive();
		String CommentList_anonymous();
		String CommentList_goods();
		String CommentList_article();
		String CommentList_active();
		String CommentList_inactive();
	}
	
	public static class State extends PageState {
		public String getPageClassName() {
			return CommentListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.CommentList_title();
		}
	}

	public State getCurState() {
		return (State)curState;
	}
	
	int pageSize = 5;
	
	Criteria criteria = new Criteria();

	PagingToolBar toolBar;
	private static CommentListPanel instance;
	private CommentListPanel() {
		super();
		curState = new State();
		System.out.println("----------CommentList");
		initJS(this);
	}
	public static CommentListPanel getInstance(){
		if(instance == null) {
			instance = new CommentListPanel();
		}
		return instance;
	}
	TextBox txtKeyword = new TextBox();
	Button btnFind = new Button(Resources.constants.CommentList_search());
	ListBox lstAction = new ListBox();
	Button btnAct = new Button(Resources.constants.CommentList_action_OK());
	String objectName = "";
	
	private native void initJS(CommentListPanel me) /*-{

    $wnd.viewComment = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.CommentListPanel::viewComment(Ljava/lang/String;)(id);
    };
    
    $wnd.deleteComment = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.CommentListPanel::deleteComment(Ljava/lang/String;)(id);
    };
    }-*/;
	
	private void viewComment(final String id) {
		System.out.println("viewComment:id= "+id);
		
		CommentPanel.State newState = new CommentPanel.State();
		newState.setId(id);
		newState.execute();
	}
	private void deleteComment(final String id) {
		System.out.println("deleteComment:id= "+id);
		
        MessageBox.confirm(Resources.constants.deleteConfirmTitle(), Resources.constants.deleteConfirmContent(), new com.extjs.gxt.ui.client.event.Listener<MessageBoxEvent>() {  
        	public void handleEvent(MessageBoxEvent ce) {  
                Button btn = ce.getButtonClicked(); 
                if ( btn.getItemId().equals("yes")){
                	new DeleteService().deleteBean(ModelNames.COMMENT, id, new DeleteService.Listener() {
            			public void onSuccess(Boolean success) {
            	        	if(success) {
            	        		Info.display(Resources.constants.OperationSuccessful(), Resources.constants.CommentList_deleteSuccessfully());
            	        	} else {
            	        		Info.display(Resources.constants.OperationFailure(), Resources.constants.CommentList_deleteFailure());
            	        	}
            	    		refresh();
            	        }
            	        public void onFailure(Throwable caught) {
            	        	Info.display(Resources.constants.OperationFailure(), Resources.constants.CommentList_deleteFailure());
            	        };
            		});
                }
              }  
            });  
		
	}
	protected void onRender(Element parent, int index) {
	    super.onRender(parent, index);
	    Criteria criteria = new Criteria();
	    criteria.addCondition(new Condition(IComment.PARENT,Condition.EQUALS, null ));
	    BasePagingLoader loader = new PagingListService().getLoader(ModelNames.COMMENT , criteria);

	    loader.load(0, 10);
  	
	    final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

	    store.addStoreListener(new StoreListener<BeanObject>() {
	    	public void storeDataChanged(StoreEvent<BeanObject> se) {
				List<BeanObject> storeData = (List<BeanObject>)se.getStore().getModels();
				for (BeanObject object : storeData) {
					String userId = object.get(IComment.USER);
					if ( userId == null ){
						object.set(IComment.USERNAME, Resources.constants.CommentList_anonymous());
					}
					
					int commentType = ((Number)object.get(IComment.COMMENTTYPE)).intValue();
					if ( commentType == IComment.TYPE_GOODS ) {
						object.set(IComment.COMMENTTYPE, Resources.constants.CommentList_goods());
						
					}
					else if ( commentType == IComment.TYPE_ARTICLE) {
						object.set(IComment.COMMENTTYPE, Resources.constants.CommentList_article());
						
					}
					
					int status = ((Number)object.get(IComment.STATUS)).intValue();
					if ( status == IComment.STATUS_ACTIVE){
						object.set(IComment.STATUS, Resources.constants.CommentList_active());
					}
					else if ( status == IComment.STATUS_INACTIVE ){
						object.set(IComment.STATUS, Resources.constants.CommentList_inactive());
					}
				}
	    	}
	    });
      
	    toolBar = new PagingToolBar(pageSize);
	    toolBar.bind(loader);
	    
	    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
	    
		columns.add(new ColumnConfig(CommentForm.USERNAME, Resources.constants.CommentList_userName(), 120));
		columns.add(new ColumnConfig(CommentForm.COMMENTTYPE, Resources.constants.CommentList_commentType(), 80));
		ColumnConfig objcol = new ColumnConfig(CommentForm.IDVALUE, Resources.constants.CommentList_idValue(), 150);
		columns.add(objcol);
		
		columns.add(new ColumnConfig(CommentForm.IPADDRESS, Resources.constants.CommentList_ipAddress(), 120));
		
		ColumnConfig addTimeCol = new ColumnConfig(CommentForm.ADDTIME, Resources.constants.CommentList_addTime(), 150);
	    addTimeCol.setRenderer(new TimeCellRenderer());
	    columns.add(addTimeCol);
		
		columns.add(new ColumnConfig(CommentForm.STATUS, Resources.constants.CommentList_status(), 40));
		
		
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.action(),150);
		
      columns.add(actcol);
      
      ColumnModel cm = new ColumnModel(columns);

      Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
      grid.setLoadMask(true);
      grid.setBorders(true);
      grid.setSelectionModel(smRowSelection);
      grid.setAutoExpandColumn("Action");

      ActionCellRenderer render = new ActionCellRenderer(grid);
      ActionCellRenderer.ActionInfo act = null;     
      act = new ActionCellRenderer.ActionInfo();
      act.setText("商品");
      act.setAction("viewObject($idValue)");
      render.addAction(act);
      objcol.setRenderer(render);
      
      render = new ActionCellRenderer(grid);
      
      act = new ActionCellRenderer.ActionInfo();
      act.setImage(GWT.getModuleBaseURL()+"icon_view.gif");
      act.setAction("viewComment($id)");
      render.addAction(act);
      act = new ActionCellRenderer.ActionInfo();
      act.setImage(GWT.getModuleBaseURL()+"icon_trash.gif");
      act.setAction("deleteComment($id)");
      act.setTooltip(Resources.constants.delete());
      render.addAction(act);
     
      actcol.setRenderer(render);
      
      HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("  " + Resources.constants.CommentList_keyword()));
		header.add(txtKeyword);
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
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setHeight(350);
		panel.setBottomComponent(toolBar);

		
		add(panel);

		lstAction.addItem(Resources.constants.CommentList_action_deleteComment(), "deleteComment");
		lstAction.addItem(Resources.constants.CommentList_action_statusActive(), "statusActive");
		lstAction.addItem(Resources.constants.CommentList_action_statusInactive(), "statusInactive");

		btnAct.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				int index = lstAction.getSelectedIndex();

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
	
	private void search() {
		criteria.removeAll();
		
		String keyword = txtKeyword.getText();
		if (keyword != null && keyword.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IComment.CONTENT);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(keyword.trim());
			criteria.addCondition(cond);
		}

		toolBar.refresh();
	}
	
	private void executeAction(final List<BeanObject> items, String action) {
		if (items == null) {
			return;
		}

		final List listeners = new ArrayList();

		for (BeanObject item : items) {
			if ("deleteComment".equals(action)) {
				DeleteListener listener = new DeleteListener();
				listeners.add(listener);
				deleteComment(item.getString(IComment.ID), listener);
			} else if ("statusActive".equals(action)) {
				if (IComment.STATUS_ACTIVE != item.getInt(IComment.STATUS)) {
					item.set(IComment.STATUS, IComment.STATUS_ACTIVE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateComment(item, listener);
				}
			} else if ("statusInactive".equals(action)) {
				if (IComment.STATUS_INACTIVE != item.getInt(IComment.STATUS)) {
					item.set(IComment.STATUS, IComment.STATUS_INACTIVE);
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
				return true;
			}

			public void run() {
				toolBar.refresh();
			}
		});
	}
	private void updateComment(BeanObject comment, UpdateService.Listener listener) {
		new UpdateService().updateBean(comment.getString(IComment.ID), comment,
				listener);
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
	private void deleteComment(String id, DeleteService.Listener listener) {
		new DeleteService().deleteBean(ModelNames.COMMENT, id, listener);
	}

	private void deleteCommentAndRefrsh(String id) {
		new DeleteService().deleteBean(ModelNames.COMMENT, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	
    public Button getShortCutButton() {
      return null;
    }

	@Override
	public String getName() {
		return Resources.constants.CommentList_title();
	}
	
	public void refresh() {    	
    	System.out.println("----- refresh commentList---");
		toolBar.refresh();
	}

}
