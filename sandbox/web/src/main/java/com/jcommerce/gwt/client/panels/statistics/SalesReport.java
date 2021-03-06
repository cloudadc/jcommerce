/**
 * @author monkey 
 * @time 2010.03.02
 */
package com.jcommerce.gwt.client.panels.statistics;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.ReportService;
import com.jcommerce.gwt.client.widgets.DateWidget;

public class SalesReport extends ContentWidget {
    DateWidget startDate;
    DateWidget endDate;
    HtmlContainer html;
    Button button;

	public static class State extends PageState {
		public String getPageClassName() {
			return SalesReport.class.getName();
		}

		public String getMenuDisplayName() {
			return "销售明细";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public SalesReport() {
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "销售明细";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

	    HorizontalPanel topDate = new HorizontalPanel();//顶端日期显示
	    startDate = new DateWidget();
	    endDate  = new DateWidget();

	    button = new Button("查询");
	    
	    topDate.add(new Label("Start"));
        topDate.add(startDate);
        Label space = new Label();
        space.setWidth(50);
        topDate.add(space);
        topDate.add(new Label("End"));
        topDate.add(endDate);
        space = new Label();
        space.setWidth(10);
        topDate.add(space);
        topDate.add(button);
        
        add(topDate);
        
		html = new HtmlContainer();
		html.setHeight("100%");
		html.setWidth("100%");

		add(html);
		
        button.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent be) {
                Map<String, String> params = new HashMap<String, String>();
                
                if (startDate.getValue() != null && endDate.getValue() != null && startDate.getValue().after(endDate.getValue())) {
                    MessageBox.alert("ERROR", "Invalid date inputted!", null);
                    return;
                }
                
                DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
                
                params.put("STARTDATE", format.format(startDate.getValue()));
                params.put("ENDDATE", format.format(endDate.getValue()));
                
                new ReportService().generateReport("SalesReport", params, new ReportService.Listener() {
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
