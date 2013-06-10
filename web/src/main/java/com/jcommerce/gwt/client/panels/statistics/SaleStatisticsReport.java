package com.jcommerce.gwt.client.panels.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.ReportService;

public class SaleStatisticsReport extends ContentWidget {

	private VerticalPanel datePanel = new VerticalPanel();
	private VerticalPanel searchconditions = new VerticalPanel();
	private VerticalPanel orderReportPanel = new VerticalPanel();
	private VerticalPanel saleReportPanel = new VerticalPanel();

	private ListBox lstYearStartYear = new ListBox();
	private ListBox lstYearEndYear = new ListBox();
	private ListBox lstMonthStartYear = new ListBox();
	private ListBox lstMonthEndYear = new ListBox();
	private ListBox lstMonthStartMonth = new ListBox();
	private ListBox lstMonthEndMonth = new ListBox();
	private HtmlContainer orderHtml;
	private HtmlContainer saleHtml;

	private DecoratedTabPanel tabPanel = new DecoratedTabPanel();

	public static class State extends PageState {
		public String getPageClassName() {
			return SaleStatisticsReport.class.getName();
		}

		public String getMenuDisplayName() {
			return "销售概况";
		}
	}

	public State getCurState() {
		if (curState == null) {
			curState = new State();
		}
		return (State) curState;
	}

	// end of block

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "销售概况";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		datePanel.setHeight("30");
		searchconditions.setWidth("100%");
		add(getTopDate());
		add(searchconditions);
		searchconditions.setHorizontalAlignment(searchconditions.ALIGN_CENTER);
		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);
		tabPanel.add(orderReportPanel, "订单走势");
    	orderHtml = new HtmlContainer();
		orderReportPanel.add(orderHtml);
		tabPanel.add(saleReportPanel, "销售额走势");
    	saleHtml = new HtmlContainer();
    	saleReportPanel.add(saleHtml);

        tabPanel.selectTab(0);
		searchconditions.add(tabPanel);
	}

	private VerticalPanel getTopDate() {
		
		Integer startYear = 2010;
		for(int i = 0; i < 10;i++) {
			lstYearStartYear.addItem(startYear.toString());
			lstYearEndYear.addItem(startYear.toString());
			lstMonthStartYear.addItem(startYear.toString());
			lstMonthEndYear.addItem(startYear.toString());
			startYear++;
		}
		Integer startMonth = 1;
		for(int i = 0; i < 12;i++) {
			lstMonthStartMonth.addItem(startMonth.toString());
			lstMonthEndMonth.addItem(startMonth.toString());
			startMonth++;
		}
		
		HorizontalPanel yearPanel = new HorizontalPanel();
		yearPanel.add(new Label("年走势"));
		yearPanel.add(lstYearStartYear);
		yearPanel.add(new Label("-"));
		yearPanel.add(lstYearEndYear);

		Button searchByYear = new Button("查询");
		searchByYear.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				String startYear = lstYearStartYear.getItemText(lstYearStartYear.getSelectedIndex());
				String startDate = startYear + "-01-01 00:00:00";//开始时间设为当年第一天的0点
				
				String endYear = lstYearEndYear.getItemText(lstYearEndYear.getSelectedIndex());
				String endDate = endYear + "-12-31 23:59:59";//结束时间设为当年最后一天的23:59:59
				
				getReport(startDate, endDate, "Y");
			}
		});
		yearPanel.add(searchByYear);

		datePanel.add(yearPanel);

		HorizontalPanel monthPanel = new HorizontalPanel();
		monthPanel.add(new Label("月走势"));
		monthPanel.add(lstMonthStartYear);
		monthPanel.add(lstMonthStartMonth);
		monthPanel.add(new Label("-"));
		monthPanel.add(lstMonthEndYear);
		monthPanel.add(lstMonthEndMonth);

		Button searchByMonth = new Button("查询");
		searchByMonth.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				String startYear = lstMonthStartYear.getItemText(lstMonthStartYear.getSelectedIndex());
				String startMonth = lstMonthStartMonth.getItemText(lstMonthStartMonth.getSelectedIndex());
				if(Integer.parseInt(startMonth) < 10)
					startMonth = "0" + startMonth;
				String startDate = startYear + "-" + startMonth + "-01" + " 00:00:00"; //开始时间加上 天时分秒 分别为当月第一天的0点整
				String endYear = lstMonthEndYear.getItemText(lstMonthEndYear.getSelectedIndex());
				String endMonth = lstMonthEndMonth.getItemText(lstMonthEndMonth.getSelectedIndex());
				if(Integer.parseInt(endMonth) < 10)
					endMonth = "0" + endMonth;
				String endDate = endYear + "-" + endMonth + "-31" + " 23:59:59";//结束时间加上 天时分秒 分别为当月最后一天的23:59:59				
				
				getReport(startDate, endDate, "M");
			}
		});
		monthPanel.add(searchByMonth);
		datePanel.add(monthPanel);

		return datePanel;
	}
	
	private void getReport(String startDate, String endDate, String type) {
		Map<String, String> params = new HashMap<String, String>();
                
        String format = type.equals("Y") ? "%Y" : "%Y-%M";
        
        params.put("STARTDATE", startDate);
        params.put("ENDDATE", endDate);
        params.put("FORMAT", format);
        
        new ReportService().generateReport("OrderStatisticsReport", params, new ReportService.Listener() {
            @Override
            public void onSuccess(String content) {
            	content = content.substring(content.indexOf("<html>"));
            	orderHtml.setHtml(content);
            	System.out.println(content);
            }
        });
        new ReportService().generateReport("SaleStatisticsReport", params, new ReportService.Listener() {
            @Override
            public void onSuccess(String content) {
            	content = content.substring(content.indexOf("<html>"));
            	saleHtml.setHtml(content);
            }
        });
	}
}
