package com.jcommerce.gwt.client.panels.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReportService;
import com.jcommerce.gwt.client.widgets.DateWidget;

public class VisitSoldReport extends ContentWidget {

	private HorizontalPanel conditionPanel = new HorizontalPanel();
	ListBox b_list = new ListBox();
	ListBox lstCategory = new ListBox();
	ListBox lstBrand = new ListBox();
	DateWidget startDate;
    DateWidget endDate;
    HtmlContainer html;
    Button button;
	
	public static class State extends PageState {
		public String getPageClassName() {
			return VisitSoldReport.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "访问购买率";
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
            return "访问购买率"; 
    }
	
	protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
	    startDate = new DateWidget();
	    endDate  = new DateWidget();
	    button = new Button("查询");
	    
	    lstBrand.addItem(Resources.constants.GoodsList_all_brand(), "all");
		new ListService().listBeans(ModelNames.BRAND,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject brand = it.next();
							lstBrand.addItem(brand.getString(IBrand.NAME),
									brand.getString(IBrand.ID));
						}
					}
				});

		lstCategory.addItem(Resources.constants.GoodsList_all_category(), "all");
		new ListService().listBeans(ModelNames.CATEGORY,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						List<String> pids = new ArrayList<String>();
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject cat = it.next();
							String name = cat.getString(ICategory.NAME);
							String id = cat.getString(ICategory.ID);
							String _pid = cat.getString(ICategory.PARENT);
							if (_pid == null) {
								pids.clear();
							} else if (!pids.contains(_pid)) {
								pids.add(_pid);
							}
							int level = pids.indexOf(_pid) + 1;
							for (int i = 0; i < level; i++) {
								name = "  " + name;
							}
							lstCategory.addItem(name, id);
						}
					}
				});
	    
	    conditionPanel.add(new Label("商品分类"));
	    conditionPanel.add(lstCategory);
        conditionPanel.add(new Label("商品品牌"));
        conditionPanel.add(lstBrand);
        conditionPanel.add(button);
        
        add(conditionPanel);
        
		html = new HtmlContainer();
		html.setHeight("100%");
		html.setWidth("100%");

		add(html);
		
        button.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent be) {
                Map<String, String> params = new HashMap<String, String>();
                
                String catId = lstCategory.getValue(lstCategory.getSelectedIndex());
                String brandId = lstBrand.getValue(lstBrand.getSelectedIndex());
                String whereClause = " tis_order_goods.order_id = tis_order_info.order_id" +
                		" AND tis_order_goods.goods_id = tis_goods.goods_id" +
                		" AND tis_order_info.order_status = 1" +
                		" AND tis_order_info.shipping_status = 2" +
                		" AND tis_order_info.pay_status = 2";
                
                if(!catId.equals("all")) {
                	String subQuery_cat = "SELECT cat_id FROM tis_category " +
            			"where parent_id = '" + catId + "'";
                	whereClause = whereClause + "  AND (tis_goods.cat_id IN ( " + subQuery_cat + " ) " +
                			"	OR tis_goods.cat_id = '" + catId + "')";
                }
                
                if(!brandId.equals("all")) {
                	whereClause = whereClause + " AND tis_goods.brand_id='" + brandId + "'";
                }
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n" +whereClause);
                
                params.put("WHERECLAUSE", whereClause);                
                
                new ReportService().generateReport("VISIT_SOLD", params, new ReportService.Listener() {
                    @Override
                    public void onSuccess(String content) {
                    	content = content.substring(content.indexOf("<html>"));
                        html.setHtml(content);
                    }
                });
                
            }
        });
    }
}
