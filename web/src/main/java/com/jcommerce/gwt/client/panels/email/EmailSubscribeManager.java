package com.jcommerce.gwt.client.panels.email;

/**
 * monkey
 */

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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IEmailList;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class EmailSubscribeManager extends ContentWidget {

	private ColumnPanel total = new ColumnPanel();
	private Criteria criteria = new Criteria();
	private PagingToolBar toolBar;  
	private ListStore<BeanObject> store;
	private Grid<BeanObject> grid;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return EmailSubscribeManager.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "邮件订阅管理";
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
		return "EmailSubscribeManagerDescription";
	}

	@Override
	public String getName() {
		return "邮件订阅管理";
	}
	
	/**
	 * 初始化界面。
	 */
	public EmailSubscribeManager() {
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.EMAILLIST, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		
		columns.add(smRowSelection.getColumn());
		ColumnConfig number = new ColumnConfig("number", "编号", 150);
        columns.add(number);
        columns.add(new ColumnConfig(IEmailList.EMAIL, "邮件地址", 400));
        ColumnConfig status = new ColumnConfig(IEmailList.CONFIRM, "状态", 150);
        columns.add(status);
        ColumnModel cm = new ColumnModel(columns);
        
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);
        
        BooleanDecriptionActionCellRenderer booleanRenderer = new BooleanDecriptionActionCellRenderer(grid, "已确认", "未确认");
        status.setRenderer(booleanRenderer);
        
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
        panel.setSize(850, 350);
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
        
        // 退订
        final com.extjs.gxt.ui.client.widget.button.Button cancel = new com.extjs.gxt.ui.client.widget.button.Button("退订");
        cancel.setEnabled(false);
        cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	List<BeanObject> selectedData = smRowSelection.getSelectedItems();
            	for(BeanObject item : selectedData) {
            		Long id = item.get(IEmailList.ID);
            		String confirmState = item.getString(IEmailList.CONFIRM);
            		item.remove(IEmailList.CONFIRM);
            		
            		// 用户已经确认，是退订的合法状态。否则，是退订的不合法状态，不做任何操作。
            		if( "true".equals(confirmState) ) {
            			item.set(IEmailList.CONFIRM, false);
            			new UpdateService().updateBean(id, item, new UpdateService.Listener() {
							public void onSuccess(Boolean success) {
								toolBar.refresh();
							}
						});
            		} else {
            			item.set(IEmailList.CONFIRM, false);
            		}
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
            		Long id = item.get(IEmailList.ID);
            		new DeleteService().deleteBean(ModelNames.EMAILLIST, id, new DeleteService.Listener() {
						
						public void onSuccess(Boolean success) {
							toolBar.refresh();
						}
					});
            	}
            }
          });
        
        // 确认
        final com.extjs.gxt.ui.client.widget.button.Button confirm = new com.extjs.gxt.ui.client.widget.button.Button("确认");
        confirm.setEnabled(false);
        confirm.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	List<BeanObject> selectedData = smRowSelection.getSelectedItems();
            	for(BeanObject item : selectedData) {
            		Long id = item.get(IEmailList.ID);
            		String confirmState = item.getString(IEmailList.CONFIRM);
            		item.remove(IEmailList.CONFIRM);
            		
            		// 用户未确认，是确认的合法状态。否则，是确认的不合法状态，不做任何操作。
            		if( "false".equals(confirmState) ) {
            			item.set(IEmailList.CONFIRM, true);
            			new UpdateService().updateBean(id, item, new UpdateService.Listener() {
							public void onSuccess(Boolean success) {
								toolBar.refresh();
							}
						});
            		} else {
            			item.set(IEmailList.CONFIRM, true);
            		}
            	}
            }
          });
        
        // add buttons into panel
        panel.addButton(selectAll);
        panel.addButton(disselectAll);
        panel.addButton(cancel);
        panel.addButton(delete);
        panel.addButton(confirm);
        
        smRowSelection.addSelectionChangedListener(new SelectionChangedListener() {  

			public void handleEvent(BaseEvent be) {
				boolean isSelected = smRowSelection.getSelectedItems().size() > 0;
				if ( isSelected ) {
					cancel.setEnabled(true);
					delete.setEnabled(true);
					confirm.setEnabled(true);
				} else { 
					cancel.setEnabled(false);
					delete.setEnabled(false);
					confirm.setEnabled(false);
				}
			}

			@Override
			public void selectionChanged(SelectionChangedEvent se) {
				
			}  
		}); 
        
		total.add(getExportButtonPanel());
		total.add(panel);
		add(total);
	}
	
	private ContentPanel getExportButtonPanel() {
		final ContentPanel exportButtonPanel = new ContentPanel();
		// export file button
		Button exportButton = new Button("导出列表");
		exportButton.addClickListener(new ClickListener() {
        	
			public void onClick(Widget sender) {
				// download the file from the server
				Window.Location.assign(GWT.getModuleBaseURL() + "downloadService/");
			}
        	
        });
		exportButtonPanel.add(exportButton);
		exportButtonPanel.setWidth(850);
		exportButtonPanel.setHeight(70);
		exportButtonPanel.setFrame(true);
		
		return exportButtonPanel;
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
			
			if(booleanValue.equals("true")){
				act.setText(isTruthStr);
			}else if(booleanValue.equals("false")){
				act.setText(isFailureStr);
			}
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}

	        return sb.toString();
		}
		
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
	
}
