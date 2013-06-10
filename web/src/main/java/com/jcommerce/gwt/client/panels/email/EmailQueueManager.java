package com.jcommerce.gwt.client.panels.email;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IEmailSendList;
import com.jcommerce.gwt.client.model.IMailTemplate;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.BeanCellRenderer;

public class EmailQueueManager extends ContentWidget {

	private Criteria criteria = new Criteria();
	private PagingToolBar toolBar;  
	private ListStore<BeanObject> store;
	private Grid<BeanObject> grid;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return EmailQueueManager.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "邮件队列管理";
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
		return "EmailQueueManagerDescription";
	}

	@Override
	public String getName() {
		return "邮件队列管理";
	}

	/**
	 * 初始化界面。
	 */
	public EmailQueueManager() {
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.EMAILSENDLIST, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();  
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();		
		columns.add(smRowSelection.getColumn());
		ColumnConfig number = new ColumnConfig("number", "编号", 80);
		number.setAlignment(HorizontalAlignment.CENTER);
        columns.add(number);
        ColumnConfig title = new ColumnConfig(IEmailSendList.TEMPLATEID, "邮件标题", 80);
        columns.add(title);
        ColumnConfig address = new ColumnConfig(IEmailSendList.EMAIL, "邮件地址", 180);
        address.setAlignment(HorizontalAlignment.CENTER);
        columns.add(address);
        ColumnConfig priority = new ColumnConfig(IEmailSendList.PRIORITY, "优先级", 80); 
        columns.add(priority);
        ColumnConfig type = new ColumnConfig(IEmailSendList.TEMPLATEID, "邮件类型", 80);
        columns.add(type);
        ColumnConfig lastSend = new ColumnConfig(IEmailSendList.LASTSEND, "上次发送", 180);
        lastSend.setAlignment(HorizontalAlignment.CENTER);
        columns.add(lastSend);
        columns.add(new ColumnConfig(IEmailSendList.ERROR, "错误次数", 80));    
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 80);
		columns.add(actcol);
        ColumnModel cm = new ColumnModel(columns);
        
        grid = new Grid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);        
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteSendEmail($id)");
		act.setTooltip("删除");
		render.addAction(act);

		actcol.setRenderer(render);
        
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);
        
        // id to email title
        title.setRenderer(new BeanCellRenderer(ModelNames.MAILTEMPLATE, IMailTemplate.SUBJECT, grid));
        // id to email type
        type.setRenderer(new BeanCellRenderer(ModelNames.MAILTEMPLATE, IMailTemplate.TYPE, grid));
        
        priority.setRenderer(new BooleanDecriptionActionCellRenderer(grid, "高", "普通"));
        
        NumberActionCellRenderer numberRenderer = new NumberActionCellRenderer(grid);
        number.setRenderer(numberRenderer);
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(900, 400);
        panel.setBottomComponent(toolBar);     
        panel.setHeading("发送邮件列表");
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
        
        // 删除
        final com.extjs.gxt.ui.client.widget.button.Button delete = new com.extjs.gxt.ui.client.widget.button.Button("删除");
        delete.setEnabled(false);
        delete.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	List<BeanObject> selectedData = smRowSelection.getSelectedItems();
            	for(BeanObject item : selectedData) {
            		String id = item.getString(IEmailSendList.ID);
            		new DeleteService().deleteBean(ModelNames.EMAILSENDLIST, id, new DeleteService.Listener() {
            			public void onSuccess(Boolean success) {
            				toolBar.refresh();
            			}
            		});
            	}
            }
          });
        panel.addButton(selectAll);
        panel.addButton(disselectAll);
        panel.addButton(delete);
        
        smRowSelection.addSelectionChangedListener(new SelectionChangedListener() {  

			public void handleEvent(BaseEvent be) {
				boolean isSelected = smRowSelection.getSelectedItems().size() > 0;
				if ( isSelected ) {
					delete.setEnabled(true);
				} else { 
					delete.setEnabled(false);
				}
			}

			@Override
			public void selectionChanged(SelectionChangedEvent se) {
				
			}  
		}); 
        
        add(panel);
        initJS(this);
	}
	
	private native void initJS(EmailQueueManager me) /*-{
	   $wnd.deleteSendEmail = function (id) {
	       me.@com.jcommerce.gwt.client.panels.email.EmailQueueManager::deleteSendEmailAndRefresh(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	private void deleteSendEmailAndRefresh(String id) {
		new DeleteService().deleteBean(ModelNames.EMAILSENDLIST, id, new DeleteService.Listener() {
			public void onSuccess(Boolean success) {
				toolBar.refresh();
			}
		});
	}
	
	class NumberActionCellRenderer extends ActionCellRenderer{
		private Integer number = 1;
		@SuppressWarnings("unchecked")
		public NumberActionCellRenderer(Grid grid) {
			super(grid);
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {

			StringBuffer sb = new StringBuffer();
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			
			act.setText(number.toString());
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}
			number++;
			
			return sb.toString();
		}		
	}
	
	class BooleanDecriptionActionCellRenderer extends ActionCellRenderer{
		private String isTruthStr;
		private String isFailureStr;
		
		@SuppressWarnings("unchecked")
		public BooleanDecriptionActionCellRenderer(Grid grid, String isTruthStr, String isFailureStr) {
			super(grid);
			this.isTruthStr = isTruthStr;
			this.isFailureStr = isFailureStr;
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {
			
			String booleanValue = (String) model.get(property).toString();

			StringBuffer sb = new StringBuffer();
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			
			if(booleanValue.equals("1")){
				act.setText(isTruthStr);
			}else if(booleanValue.equals("0")){
				act.setText(isFailureStr);
			}
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}
	        return sb.toString();
		}
		
	}
}
