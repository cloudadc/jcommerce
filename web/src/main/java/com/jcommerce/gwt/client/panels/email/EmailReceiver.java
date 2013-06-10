package com.jcommerce.gwt.client.panels.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IEmailReceiver;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.EmailSettings;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author monkey
 */

public class EmailReceiver extends ContentWidget{

	private ColumnPanel total = new ColumnPanel();
	private Criteria criteria = new Criteria();
	private PagingToolBar toolBar;
	private ListStore<BeanObject> store;
	private Grid<BeanObject> grid;
	private CheckBoxSelectionModel<BeanObject> smRowSelection;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return EmailReceiver.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "收件箱";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	@Override
	public String getDescription() {
		return "EmailReceiverDescription";
	}

	@Override
	public String getName() {
		return "收件箱";
	}

	public EmailReceiver() {
		Condition condition = new Condition(IEmailReceiver.ISJUNK, Condition.EQUALS, "0");
		criteria.addCondition(condition);
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.EMAILRECEIVER, criteria);
		loader.setSortField(IEmailReceiver.RECEIVETIME);
		loader.setSortDir(SortDir.DESC);
		loader.load(0, 20);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(20);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig actcol = new ColumnConfig(IEmailReceiver.ISREAD, "", 30);
		columns.add(actcol);
        columns.add(new ColumnConfig(IEmailReceiver.SENDERNAME, "发件人", 200));
        ColumnConfig status = new ColumnConfig(IEmailReceiver.MAILSUBJECT, "主题", 400);
        columns.add(status);
        ColumnConfig time = new ColumnConfig(IEmailReceiver.RECEIVETIME, "时间", 200);
        columns.add(time);
        ColumnModel cm = new ColumnModel(columns);
        
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);

		actcol.setRenderer( new IconActionCellRenderer(grid, "is_read.gif", "not_read.gif") );
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(850, 450);
        panel.setBottomComponent(toolBar);     
               
        panel.setButtonAlign(HorizontalAlignment.LEFT);
        
        // 全选功能
        final com.extjs.gxt.ui.client.widget.button.Button selectAll = new com.extjs.gxt.ui.client.widget.button.Button("全选");
        selectAll.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if ( store.getCount() > 0 ) {
					smRowSelection.selectAll();
				}			
			}
        	
        });
        
        // 全不选功能
        final com.extjs.gxt.ui.client.widget.button.Button disselectAll = new com.extjs.gxt.ui.client.widget.button.Button("全不选");
        disselectAll.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if ( store.getCount() > 0 ) {
					smRowSelection.deselectAll();
				}
			}
        	
        });
        
     	// 全不选功能
        final com.extjs.gxt.ui.client.widget.button.Button disAll = new com.extjs.gxt.ui.client.widget.button.Button("全不选");
        disAll.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if ( store.getCount() > 0 ) {
					List<BeanObject> selected = smRowSelection.getSelectedItems();
					
				}
			}
        	
        });
        
        // 选已读邮件功能
        final com.extjs.gxt.ui.client.widget.button.Button isReadEmail = new com.extjs.gxt.ui.client.widget.button.Button("已读");
        isReadEmail.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if ( store.getCount() > 0 ) {
					List<BeanObject> allEmails = store.getModels();
					List<BeanObject> hasRead = new ArrayList<BeanObject>();
					for( BeanObject email : allEmails ) {
						Boolean isRead = email.get(IEmailReceiver.ISREAD);
						if( isRead.booleanValue() ) {
							hasRead.add(email);
						}
					}
					smRowSelection.select(hasRead, true);
				}
			}
        	
        });
        
        // 选未读邮件功能
        final com.extjs.gxt.ui.client.widget.button.Button unReadEmail = new com.extjs.gxt.ui.client.widget.button.Button("未读");
        unReadEmail.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if ( store.getCount() > 0 ) {
					List<BeanObject> allEmails = store.getModels();
					List<BeanObject> notRead = new ArrayList<BeanObject>();
					for( BeanObject email : allEmails ) {
						Boolean isRead = email.get(IEmailReceiver.ISREAD);
						if( !isRead.booleanValue() ) {
							notRead.add(email);
						}
					}
					smRowSelection.select(notRead, true);
				}
			}
        	
        });
        
        panel.addButton(selectAll);
        panel.addButton(disselectAll);
        panel.addButton(unReadEmail);
        panel.addButton(isReadEmail);
        
        total.add(getButtonPanel());
        total.add(panel);
		add(total);
	}
	
	private ContentPanel getButtonPanel() {
		final ContentPanel buttonPanel = new ContentPanel();
		ButtonBar buttons = new ButtonBar();
		// 接受邮件
		Button receive = new Button("接收邮件");
		receive.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	EmailSettings emailSettings = new EmailSettings();
            	emailSettings.receiveNewMail( new EmailSettings.Listener() {
            		public void onFailure(Throwable caught) {		
						MessageBox.alert("Error", "please check the network!", null);
					}
            		
					public synchronized void onSuccess(Boolean result) {
						toolBar.refresh();
					}	
				});
            }
        });
		
		// 删除
		Button delete = new Button("删除");
		delete.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> selected = smRowSelection.getSelectedItems();
				if( selected.size() > 0 ) {
					for( BeanObject email : selected ) {
						String id = email.get(IEmailReceiver.ID);
						new DeleteService().deleteBean(ModelNames.EMAILRECEIVER, id, new DeleteService.Listener() {
							public void onSuccess(Boolean success) {
								
							}
						});
					}
					toolBar.refresh();
				} else {
					MessageBox.alert("选择邮件出错", "请选择要删除的邮件", null);
				}
			}
			
		});
		
//		// 举报垃圾邮件
//		Button junk = new Button("举报垃圾邮件");
//		junk.addSelectionListener(new SelectionListener<ButtonEvent>() {
//
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				List<BeanObject> selected = smRowSelection.getSelectedItems();
//				if( selected.size() > 0 ) {
//					for( BeanObject email : selected ) {
//						String id = email.getString(IEmailReceiver.ID);
//						Map<String, Object> values = email.getProperties();
//						values.remove(IEmailReceiver.ISJUNK);
//						values.put(IEmailReceiver.ISJUNK, true);
//						BeanObject bean = new BeanObject(ModelNames.EMAILRECEIVER, values);
//						new UpdateService().updateBean(id, bean, new UpdateService.Listener() {
//							public void onSuccess(Boolean success) {
//								toolBar.refresh();
//							}
//						});
//					}
//				} else {
//					MessageBox.alert("选择邮件出错", "请选择要举报的邮件", null);
//				}
//			}
//			
//		});
//		
		// 标记为已读
		Button isRead = new Button("标记为已读");
		isRead.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> selected = smRowSelection.getSelectedItems();
				int count = 0;
				if( selected.size() > 0 ) {
					for( BeanObject email : selected ) {
						Boolean mark = email.get(IEmailReceiver.ISREAD);
						String id = email.get(IEmailReceiver.ID);
						if( mark.booleanValue() ) {
							count++;
						} else {
							Map<String, Object> all = email.getProperties();
							all.remove(IEmailReceiver.ISREAD);
							all.put(IEmailReceiver.ISREAD, Boolean.TRUE);
							BeanObject bean = new BeanObject(ModelNames.EMAILRECEIVER, all);
							new UpdateService().updateBean(id, bean, new UpdateService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
									toolBar.refresh();
								}
							});
						}
					}
					if( count > 0) {
						MessageBox.alert("选择邮件出错", "您选择的邮件已经是当前状态，无需再次标记", null);
					}
				} else {
					MessageBox.alert("选择邮件出错", "请选择要标记的邮件", null);
				}
			}
			
		});
		
		// 标记为未读
		Button notRead = new Button("标记为未读");
		notRead.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> selected = smRowSelection.getSelectedItems();
				int count = 0;
				if( selected.size() > 0 ) {
					for( BeanObject email : selected ) {
						Boolean mark = email.get(IEmailReceiver.ISREAD);
						String id = email.get(IEmailReceiver.ID);
						if( mark.booleanValue() ) {
							Map<String, Object> all = email.getProperties();
							all.remove(IEmailReceiver.ISREAD);
							all.put(IEmailReceiver.ISREAD, Boolean.FALSE);
							BeanObject bean = new BeanObject(ModelNames.EMAILRECEIVER, all);
							new UpdateService().updateBean(id, bean, new UpdateService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
									toolBar.refresh();
								}
							});
						} else {
							count++;
						}
					}
					if( count > 0) {
						MessageBox.alert("选择邮件出错", "您选择的邮件已经是当前状态，无需再次标记", null);
					}
				} else {
					MessageBox.alert("选择邮件出错", "请选择要标记的邮件", null);
				}
			}
		});
		
		// 打开邮件
		Button openEmail = new Button("打开邮件");
		openEmail.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> selected = smRowSelection.getSelectedItems();
				if( selected.size() > 0 ) {
					if( selected.size() > 1 ) {
						MessageBox.alert("选择邮件出错", "每次只能打开一封邮件", null);
					} else {
						DialogBox dialogBox = createDialogBox(selected.get(0));
						dialogBox.setAnimationEnabled(true);
						dialogBox.center();
						dialogBox.show();
						// 判断是否为未读邮件，假如是标记为已读
						Boolean isRead = selected.get(0).get(IEmailReceiver.ISREAD);
						String id = selected.get(0).get(IEmailReceiver.ID);
						if( !isRead ) {
							Map<String, Object> all = selected.get(0).getProperties();
							all.remove(IEmailReceiver.ISREAD);
							all.put(IEmailReceiver.ISREAD, Boolean.TRUE);
							BeanObject bean = new BeanObject(ModelNames.EMAILRECEIVER, all);
							new UpdateService().updateBean(id, bean, new UpdateService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
									toolBar.refresh();
								}
							});
						}
					}
				} else {
					MessageBox.alert("选择邮件出错", "请选择打开的邮件", null);
				}
			}
			
		});
		
		buttons.add(openEmail);
		buttons.add(delete);
//		buttons.add(junk);
		buttons.add(isRead);
		buttons.add(notRead);
		buttons.add(receive);
		buttons.setAlignment(HorizontalAlignment.LEFT);
		buttonPanel.add(buttons);
		buttonPanel.setWidth(850);
		buttonPanel.setHeight(70);
		buttonPanel.setFrame(true);
		return buttonPanel;
	}
	
	private DialogBox createDialogBox( final BeanObject bean ) {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("邮件内容");
		// Create a panel to layout the content
		ColumnPanel contentPanel = new ColumnPanel();
		// add subject
		Label subject = new Label();
		subject.setText( (String) bean.get(IEmailReceiver.MAILSUBJECT) );
		contentPanel.createPanel(IEmailReceiver.MAILSUBJECT, null, subject);
		// add sender name
		Label sender = new Label();
		sender.setText( (String) bean.get(IEmailReceiver.SENDERNAME) );
		contentPanel.createPanel(IEmailReceiver.SENDERNAME, "发件人", sender);
		// add receive time
		Label time = new Label();
		time.setText( (String) bean.get(IEmailReceiver.RECEIVETIME) );
		contentPanel.createPanel(IEmailReceiver.RECEIVETIME, "时间", time);
		// add receiver name
		Label receiver = new Label();
		receiver.setText( (String) bean.get(IEmailReceiver.RECEIVERNAME) );
		contentPanel.createPanel(IEmailReceiver.RECEIVERNAME, "收件人", receiver);
		// add content 
		Label content = new Label();
		String mailContent = (String) bean.get(IEmailReceiver.EMAILCONTENT);
		if( mailContent.contains("<") ) {
			content.setText( mailContent.substring(0, mailContent.indexOf('<')) );
		} else {
			content.setText( mailContent );
		}
		contentPanel.createPanel(IEmailReceiver.EMAILCONTENT, null, content);
		// Add a close button at the bottom of the dialog
		com.google.gwt.user.client.ui.Button closeButton = new com.google.gwt.user.client.ui.Button("Close", new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				dialogBox.hide();
			}
		});
		// add a button to reply the email 
		com.google.gwt.user.client.ui.Button replyButton = new com.google.gwt.user.client.ui.Button("回复", new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				String name = bean.getString(IEmailReceiver.SENDERNAME);
				
				NewEmail.State state = new NewEmail.State();
				state.setSender(name);
				state.execute();
				dialogBox.hide();
			}
		});
		
		contentPanel.add(closeButton);
		contentPanel.add(replyButton);
		dialogBox.setWidget(contentPanel);
		return dialogBox;
	}
	
	/*
	 * CellRender : 当邮件标记为已读时显示打开邮件图标， 否则显示未读邮件图标
	 */
	class IconActionCellRenderer extends ActionCellRenderer {

		private String read;
		private String notRead;
		
		public IconActionCellRenderer( Grid grid, String read, String notRead ) {
			super(grid);
			this.notRead = notRead;
			this.read = read;
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {
			Boolean isRead = model.get(property);
			StringBuffer sb = new StringBuffer();
//			sb.append("<a href=\"\" title=\"");
			if ( isRead.booleanValue() ) {
//				sb.append("已读\">");
				sb.append("<img border=\"0\" src=\"" + read + "\">");
			} else {
//				sb.append("未读\">");
				sb.append("<img border=\"0\" src=\"" + notRead + "\">");
			}
//			sb.append("</a>");
			return sb.toString();
		}
	}
}
