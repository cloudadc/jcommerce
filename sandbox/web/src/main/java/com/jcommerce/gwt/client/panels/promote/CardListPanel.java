package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICard;
import com.jcommerce.gwt.client.model.IWholesale;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class CardListPanel extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	private Criteria criteria = new Criteria();
    private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;   
    
	public static class State extends PageState {
		public String getPageClassName() {
			return CardListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "祝福贺卡 ";
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
        return "祝福贺卡 ";
    }
    
    public String getButtonText() {
        return "添加新贺卡";
    }
    
    protected void buttonClicked() {
    	NewCardPanel.State state = new NewCardPanel.State();
    	state.execute();
    }
    
    public CardListPanel() {
    	initJS(this);
    }
    
    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.CARD, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>(); 
		ColumnConfig picture = new ColumnConfig("", "", 20);
		columns.add(picture);
		columns.add(new ColumnConfig(ICard.NAME, "贺卡名称", 100));
		columns.add(new ColumnConfig(ICard.FEE, "贺卡费用", 100));
		columns.add(new ColumnConfig(ICard.FREEMONEY, "贺卡免费额度", 100));
		columns.add(new ColumnConfig(ICard.DESC, "贺卡描述", 400));
		ColumnConfig action = new ColumnConfig("", "操作", 100);
        columns.add(action);
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setHeight(350);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("changeCard($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteCard($id)");
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
//        panel.setSize(850, 350);
        panel.setBottomComponent(toolBar);
        add(panel);
    }
    
    private native void initJS(CardListPanel me) /*-{
    $wnd.changeCard = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.CardListPanel::modifyCardAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteCard = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.CardListPanel::deleteCardAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyCardAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.CARD, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject card = it.next();                    
                    if(card.getString(IWholesale.ID).trim().equals(id.trim())){
                    	NewCardPanel.State state = new NewCardPanel.State();
                        state.setCard(card);
                        state.execute();
                    }
                }               
            }
        });        
    }
    
    private void deleteCardAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.CARD, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						CardListPanel.State state = new CardListPanel.State();
					    state.execute();
					    refresh();
					}
				});
    }
    
    public void refresh() {
		toolBar.refresh();
	}


}
