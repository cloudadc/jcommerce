package com.jcommerce.gwt.client.panels.email;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IEmailList;
import com.jcommerce.gwt.client.model.IEmailSendList;
import com.jcommerce.gwt.client.model.IMailTemplate;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.EmailSettings;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author monkey
 */

public class MagazineManager extends ContentWidget {

	private Criteria criteria = new Criteria();
	private PagingToolBar toolBar;  
	private ListStore<BeanObject> store;
	private Grid<BeanObject> grid;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return MagazineManager.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "邮件杂志管理";
		}
	}
	
	public State getCurState() {
		return (State)curState;
	}
	
	@Override
	public String getDescription() {
		return "MagazineManagerDescription";
	}

	@Override
	public String getName() {
		return "邮件杂志管理";
	}

	/**
	 * 初始化界面。
	 */
	public MagazineManager() {
	    curState = new State();
	    
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.MAILTEMPLATE, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		ColumnConfig title = new ColumnConfig(IMailTemplate.SUBJECT, "杂志标题", 200);
        columns.add(title);
        columns.add(new ColumnConfig(IMailTemplate.LASTMODIFY, "杂志上次编辑时间", 160));
        columns.add(new ColumnConfig(IMailTemplate.LASTSEND, "杂志上次发送时间", 160));     
        ColumnConfig insert = new ColumnConfig("insert", "插入发送队列", 160);
        columns.add(insert);
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 100);
		columns.add(actcol);
        ColumnModel cm = new ColumnModel(columns);
        
        grid = new Grid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer insertRender = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("editMagazine($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteMagazine($id)");
		act.setTooltip("删除");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();
		act.setText("插入队列");
		act.setAction("insertMagazine($id)");
		act.setTooltip("插入队列");
		insertRender.addAction(act);

		actcol.setRenderer(render);
		insert.setRenderer(insertRender);
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(850, 350);
        panel.setBottomComponent(toolBar);     
        panel.setHeading("邮件杂志列表");
        panel.setButtonAlign(HorizontalAlignment.LEFT);
        
        // 添加新杂志
        final com.extjs.gxt.ui.client.widget.button.Button addMagazine = new com.extjs.gxt.ui.client.widget.button.Button("添加新杂志");
        addMagazine.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	// add new magazine function
            	NewMagazine.State state = new NewMagazine.State();
            	state.execute();
            }
          });
        panel.addButton(addMagazine);
        
        add(panel);
        initJS(this);
	}

	private native void initJS(MagazineManager me) /*-{
	   $wnd.deleteMagazine = function (id) {
	       me.@com.jcommerce.gwt.client.panels.email.MagazineManager::deleteMagazineAndRefresh(Ljava/lang/Long;)(id);
	   };
	   $wnd.insertMagazine = function (id) {
	       me.@com.jcommerce.gwt.client.panels.email.MagazineManager::insertMagazine(Ljava/lang/Long;)(id);
	   };
	   $wnd.editMagazine = function (id) {
	       me.@com.jcommerce.gwt.client.panels.email.MagazineManager::editMagazine(Ljava/lang/Long;)(id);
	   };
	   }-*/;

	/**
	 * If this method is called, show the insert into queue panel.
	 * @param id
	 */
	private void insertMagazine( Long id ) {
		new ReadService().getBean(ModelNames.MAILTEMPLATE, id, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				DialogBox dialogBox = createDialogBox(bean);
				dialogBox.setAnimationEnabled(true);
				dialogBox.center();
				dialogBox.show();
	        }
		});	
	}
	
	/**
	 * If this method is called, show the edit panel.
	 * @param id
	 */
	private void editMagazine(Long id) {
		new ReadService().getBean(ModelNames.MAILTEMPLATE, id, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				NewMagazine.State state = new NewMagazine.State();
				state.setMagazine(bean);
				state.execute();
	        }
		});		
	}
	
	private void deleteMagazineAndRefresh(Long id) {
		new DeleteService().deleteBean(ModelNames.MAILTEMPLATE, id, new DeleteService.Listener() {
			public void onSuccess(Boolean success) {
				toolBar.refresh();
			}
		});
	}
	
	private DialogBox createDialogBox( final BeanObject bean ) {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("插入邮件队列");
		// Create a panel to layout the content
		ColumnPanel contentPanel = new ColumnPanel();
		final ListBox list = new ListBox();
		list.addItem("普通");
		list.addItem("高");	
		contentPanel.createPanel(IEmailSendList.PRIORITY, "选择优先级", list);
		// add a button to commit  
		com.google.gwt.user.client.ui.Button commitButton = new com.google.gwt.user.client.ui.Button("确定", new ClickListener() {
			public void onClick(Widget sender) {
				// update the mail template 
				final Map<String, Object> emails = bean.getProperties();
				emails.remove(IMailTemplate.LASTSEND);
				Date currentTime = new Date();
				final Timestamp nowTime = new Timestamp(currentTime.getTime());
				emails.put(IMailTemplate.LASTSEND, nowTime);
				BeanObject emailBean = new BeanObject(ModelNames.MAILTEMPLATE, emails);
				new UpdateService().updateBean((Long) emails.get(IMailTemplate.ID), emailBean, new UpdateService.Listener() {

					@Override
					public void onSuccess(Boolean success) {
						
					}
					
				});
				
				new EmailSettings().getSettingsInfo(new EmailSettings.Listener() {
					public void onSuccess(HashMap<String, String> result) {					
						// create new mail send list 
						int index = list.getSelectedIndex();
						Map<String, Object> sendlist = new HashMap<String, Object>();
						sendlist.put(IEmailSendList.EMAIL, result.get("account"));
						sendlist.put(IEmailSendList.TEMPLATEID, emails.get(IMailTemplate.ID));
						sendlist.put(IEmailSendList.EMAILCONTENT, emails.get(IMailTemplate.CONTENT));
						sendlist.put(IEmailSendList.ERROR, 0);
						sendlist.put(IEmailSendList.LASTSEND, nowTime);
						sendlist.put(IEmailSendList.PRIORITY, index);
						// new bean object of send list
						BeanObject listBean = new BeanObject(ModelNames.EMAILSENDLIST, sendlist);
						new CreateService().createBean(listBean, new CreateService.Listener() {
							public void onSuccess(String id) {
								
							}
						});
						dialogBox.hide();
						toolBar.refresh();
						
						// 采用BFS算法的原理，处理邮件队列中的邮件。
						new ListService().listBeans(ModelNames.EMAILLIST, new ListService.Listener() {

							@Override
							public void onSuccess(List<BeanObject> beans) {
								// 取出所有订阅者的订阅地址,构造发送列表
								StringBuffer sendTo = new StringBuffer();
								for (BeanObject bean : beans) {
									Boolean isConfirm = bean.get(IEmailList.CONFIRM);
									if( isConfirm.booleanValue() ) {
										sendTo.append(";" + bean.getString(IEmailList.EMAIL));
									}
								}
								HashMap<String, String> subscribe = new HashMap<String, String>();
								subscribe.put("sendTo", sendTo.substring(1));
								subscribe.put("subject", (String) emails.get(IMailTemplate.SUBJECT));
								subscribe.put("content", (String) emails.get(IMailTemplate.CONTENT));
								new EmailSettings().sendEmailAndGetState(subscribe, new EmailSettings.Listener() {
									public void onSuccess(Boolean result) {
										
									}
									public void onFailure(Throwable caught) {
										
									}
								});
							}
							
						});
					}
				});
			}
		});
		// Add a close button at the bottom of the dialog
		com.google.gwt.user.client.ui.Button closeButton = new com.google.gwt.user.client.ui.Button("Close", new ClickListener() {
			public void onClick(Widget sender) {
				dialogBox.hide();
			}
		});
		
		
		contentPanel.add(closeButton);
		contentPanel.add(commitButton);
		dialogBox.setWidget(contentPanel);
		return dialogBox;
	}
}
