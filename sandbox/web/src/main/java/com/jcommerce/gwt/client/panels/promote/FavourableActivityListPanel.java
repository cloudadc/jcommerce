package com.jcommerce.gwt.client.panels.promote;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IFavourableActivity;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class FavourableActivityListPanel extends ContentWidget{

	private ColumnPanel contentPanel = new ColumnPanel();
	private Criteria criteria = new Criteria();
    private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
    
	public static class State extends PageState {
		public String getPageClassName() {
			return FavourableActivityListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "优惠活动";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "优惠活动列表";
    }
    
    public String getButtonText() {
        return "添加优惠活动 ";
    }
    
    protected void buttonClicked() {
    	NewFavourableActivityPanel.State state = new NewFavourableActivityPanel.State();
        state.execute();
    }
    
    public FavourableActivityListPanel() {
    	initJS(this); 
    }
    
    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		TextBox searchText = new TextBox();
		Button searchButton = new Button("搜索");
		CheckBox searchCheckBox = new CheckBox();
		Label searchLabel = new Label("仅显示进行中的活动");
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("商品名称"));
		header.add(searchText);
		header.add(searchButton);
		header.add(searchCheckBox);
		header.add(searchLabel);
		add(header);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.FAVOURABLEACTIVITY, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig number = new ColumnConfig("number", "编号", 50);
        columns.add(number);
        columns.add(new ColumnConfig(IFavourableActivity.ACT_NAME, "优惠活动名称", 150));
        ColumnConfig startTime = new ColumnConfig(IFavourableActivity.START_TIME, "开始时间", 150);
        columns.add(startTime);
        ColumnConfig endTime = new ColumnConfig(IFavourableActivity.END_TIME, "结束时间", 150);
        columns.add(endTime);
        columns.add(new ColumnConfig(IFavourableActivity.MIN_AMOUNT, "金额下限", 100));
        columns.add(new ColumnConfig(IFavourableActivity.MAX_AMOUNT, "金额上限", 100));
        columns.add(new ColumnConfig(IFavourableActivity.SORT_ORDER, "排序", 50));
        ColumnConfig action = new ColumnConfig("", "操作", 100);
        columns.add(action);
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);
        grid.setHeight(350);
        
        DateFormatActionCellRenderer myRender = new DateFormatActionCellRenderer(grid);
//        startTime.setRenderer(myRender);
//        endTime.setRenderer(myRender);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("changeFavourableActivity($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteFavourableActivity($id)");
        act.setTooltip("删除");
        render.addAction(act);
        action.setRenderer(render);
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setBottomComponent(toolBar);
        
        contentPanel.add(panel);
        
        HorizontalPanel bottomPanel = new HorizontalPanel();
        Button deleteButton = new Button();
        deleteButton.setText("删除");
        bottomPanel.add(deleteButton);
//        contentPanel.createPanel(null, null, bottomPanel);
        
		add(contentPanel);
    }
    
    private native void initJS(FavourableActivityListPanel me) /*-{
    $wnd.changeFavourableActivity = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.FavourableActivityListPanel::modifyFavourableActivityAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteFavourableActivity = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.FavourableActivityListPanel::deleteFavourableActivityAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyFavourableActivityAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.FAVOURABLEACTIVITY, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject favourableActivity = it.next();                    
                    if(favourableActivity.getString(IFavourableActivity.ID).trim().equals(id.trim())){
                    	ModifyFavourableActivityListPanel.State state = new ModifyFavourableActivityListPanel.State();
                    	state.setFavourableActivity(favourableActivity);
                    	state.execute();
                    }
                }               
            }
        });        
    }
    
    private void deleteFavourableActivityAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.FAVOURABLEACTIVITY, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						FavourableActivityListPanel.State state = new FavourableActivityListPanel.State();
					    state.execute();
					    refresh();
					}
				});
    }
    
    public void refresh() {
		toolBar.refresh();
	}
    
    private class DateFormatActionCellRenderer extends ActionCellRenderer {

		public DateFormatActionCellRenderer(Grid grid) {
			super(grid);
		}
    	
		public Object render(BeanObject model, String property,
				ColumnData config, int rowIndex, int colIndex,
				ListStore<BeanObject> store, Grid<BeanObject> grid) {
			
//			BeanObject bean = grid.getSelectionModel().getSelectedItem();
//			String time = bean.getString(IFavourableActivity.START_TIME);
//			System.out.println(time);
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			System.out.println(act.getText() + " - " + act.getAction());
			String tmpAct = act.getText();
//			Long time = Long.parseLong(tmpAct);
			Date date = new Date();
//			act.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			act.setText(date + "");
			return act.getText();
		}
    	
    }
}
