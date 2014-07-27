package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShopConfig;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ShopSetup extends ContentWidget {
	private Button btnOK = new Button();
	private Button btnReset = new Button();	
	private ColumnPanel generalPanel = new ShopGeneralPanel();//网店信息
	private ColumnPanel showPanel = new ShopShowPanel();//显示设置
	private ColumnPanel goodsPanel = new ShopGoodsPanel();//商品显示设置
    private ColumnPanel orderPanel = new ShopOrderFlowPanel();//商品显示设置
	
	private Map<String, Object> configs = new HashMap<String, Object>();
	private Map<String, Object> nameIDs = new HashMap<String, Object>();
	private Map<String, Object> update = new HashMap<String, Object>();
	private Map<String, Object> object = new HashMap<String, Object>();
	private List<String> name = new ArrayList<String>();
	
	public ShopSetup() {
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return ShopSetup.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Shop_setup();
		}
	}
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	// end of block
	
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return Resources.constants.Shop_setup();
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		btnOK.setText(Resources.constants.GoodsList_action_OK());
		btnReset.setText(Resources.constants.GoodsList_reset());
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(30);
		panel.add(btnOK);
		panel.add(btnReset);
		//refresh();
		// Create a tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setAnimationEnabled(true);

		// Add a Shop Info tab
		tabPanel.add(generalPanel, Resources.constants.ShopSetup_tabShopInfo());

		//Add a Basic Set tab		
		HTML basicSet = new HTML("basic Set");
		tabPanel.add(basicSet, Resources.constants.ShopSetup_tabBasicSet());
		
		tabPanel.add(showPanel, Resources.constants.ShopSetup_tabShowSet());

		//Add a Shopping Flow tab
		tabPanel.add(orderPanel, Resources.constants.ShopSetup_tabBuyFlow());

		tabPanel.add(goodsPanel, Resources.constants.ShopSetup_tabGoodsShow());

		//Add a SMS Set tab
		HTML smsSet = new HTML("SMS Set");
		tabPanel.add(smsSet, Resources.constants.ShopSetup_tabSMS());

		//Add a WAP Set tab
		HTML wapSet = new HTML("WAP Set");
		tabPanel.add(wapSet, Resources.constants.ShopSetup_tabWAP());

		btnReset.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
				refresh();
			}
		});
		btnOK.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
				//save information
				update = generalPanel.getValues();
				update.putAll(showPanel.getValues());
				update.putAll(goodsPanel.getValues());
				for(int index = 0;index<nameIDs.size();index++){
					object.put("value",update.get(name.get(index)));
					new UpdateService().updateBean((String)nameIDs.get(name.get(index)), new BeanObject(ModelNames.SHOPCONFIG,object), null);
					object.clear();
				}
			}
		});

		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		add(tabPanel);
		add(panel);
	}

	public void refresh() {		
		new ListService().listBeans(ModelNames.SHOPCONFIG,new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject config = it.next();							
							configs.put(config.getString(IShopConfig.CODE), config.getString(IShopConfig.VALUE));
						    nameIDs.put(config.getString(IShopConfig.CODE), config.getString(IShopConfig.ID));
						    name.add(config.getString(IShopConfig.CODE));
						}						
						generalPanel.updateValues(configs);
						showPanel.updateValues(configs);
						goodsPanel.updateValues(configs);
					}
				});			
	}
}
