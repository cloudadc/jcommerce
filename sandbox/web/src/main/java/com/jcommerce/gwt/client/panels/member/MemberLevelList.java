/**
 * 
 */
package com.jcommerce.gwt.client.panels.member;

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
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.goods.NewBrand;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author dell
 * 
 */
public class MemberLevelList extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	PagingToolBar toolBar;
	Criteria criteria = new Criteria();

	private int userUpdatedSize = 0;
	public static class State extends PageState {
		public String getPageClassName() {
			return MemberLevelList.class.getName();
		}

		public String getMenuDisplayName() {
			return "会员等级";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	/**
	 * 
	 */
	public MemberLevelList() {
		add(contentPanel);
		initJS(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jcommerce.gwt.client.ContentWidget#getDescription()
	 */
	@Override
	public String getDescription() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jcommerce.gwt.client.ContentWidget#getName()
	 */
	@Override
	public String getName() {
		return "会员等级";
	}

    public String getButtonText() {
        return "添加会员等级";
    }
    
    protected void buttonClicked() {
        NewMemberLevel.State state = new NewMemberLevel.State();
        state.execute();
    }
    	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.USERRANK, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IUserRank.NAME, "会员等级名称", 100));
		columns.add(new ColumnConfig(IUserRank.MINPOINTS, "积分下限", 70));
		columns.add(new ColumnConfig(IUserRank.MAXPOINTS, "积分上限", 70));
		columns.add(new ColumnConfig(IUserRank.DISCOUNT, "初始折扣率", 70));
//		columns.add(new CheckColumnConfig(IUserRank.SPECIAL, "特殊会员组", 120));
//		columns.add(new CheckColumnConfig(IUserRank.SHOWPRICE, "显示价格", 80));

		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 120);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act = new ActionCellRenderer.ActionInfo();
		
		act.setImage("icon_edit.gif");
		act.setAction("updateMemberAction($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_trash.gif");
		act.setAction("deleteMemberAction($id)");
		act.setTooltip("删除");
		render.addAction(act);
		actcol.setRenderer(render);

		final ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setHeading("会员等级");
		panel.setLayout(new FitLayout());
		panel.add(grid);
        panel.setHeight(500);
        panel.setWidth("100%");
		panel.setBottomComponent(toolBar);

//		panel.setButtonAlign(HorizontalAlignment.CENTER);
//		panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button(
//				"添加会员级别", new SelectionListener<ButtonEvent>() {
//					public void componentSelected(ButtonEvent ce) {
//					    NewMemberLevel.State state = new NewMemberLevel.State();
//					    state.execute();
//					}
//				}));

		add(panel);

        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
	}

	private native void initJS(MemberLevelList me) /*-{
	   $wnd.deleteMemberAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberLevelList::deleteMemberAndRefrsh(Ljava/lang/String;)(id);
	   };
	   
	   $wnd.updateMemberAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberLevelList::updateMemberAndRefrsh(Ljava/lang/String;)(id);
	   };
	   
	   }-*/;

	private void deleteMemberAndRefrsh(final String id) {
	    final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
	        public void handleEvent(MessageBoxEvent ce) {
	          Button btn = ce.getButtonClicked();
				if (btn.getText().equals("是")) {
					
					Criteria orderCriteria = new Criteria();
			    	orderCriteria.addCondition(new Condition(IUser.RANK, Condition.EQUALS, id));
			    	new ListService().listBeans(ModelNames.USER, orderCriteria, new ListService.Listener(){
						public void onSuccess(final List<BeanObject> beans) 
						{
							BeanObject user = new BeanObject(ModelNames.USER);
							userUpdatedSize = beans.size();
							for(int i=0; i<beans.size(); i++)
							{
								user = beans.get(i);
								user.set(IUser.RANK, null);
								final UpdateListener listener = new UpdateListener();
								new UpdateService().updateBean(user.getString(IUser.ID), user, listener);

								new WaitService(new WaitService.Job() {			
									public boolean isReady()
									{
										return listener.isFinished();
									}
									public void run() 
									{
										userUpdatedSize--;
									}
						          }); 
							}
						
						}
			    	});
					
			    	
			    	new WaitService(new WaitService.Job() {			
						public boolean isReady()
						{
							return userUpdatedSize == 0;
						}
						public void run() 
						{
							new DeleteService().deleteBean(ModelNames.USERRANK, id,
									new DeleteService.Listener() {
										public void onSuccess(Boolean success) {
											toolBar.refresh();
					                        MemberLevelList.State state = new MemberLevelList.State();
					                        state.execute();
										}
									});
						}
			          });
			    	
					
				}

			}
		};
		MessageBox.confirm("Confirm", "Are you sure you want to do that?",
				deleteListener);
	}

	private void updateMemberAndRefrsh(final String id) 
	{
		new ReadService().getBean(ModelNames.USERRANK, id,
			new ReadService.Listener() {
				public void onSuccess(BeanObject bean)
				{
                    NewMemberLevel.State state = new NewMemberLevel.State();
                    state.setMemberLevel(bean);
                    state.execute();
				}
		});
		
	}
	
	public void refresh() {
		toolBar.refresh();
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
