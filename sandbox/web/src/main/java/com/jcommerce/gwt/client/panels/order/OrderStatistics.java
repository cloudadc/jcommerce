package com.jcommerce.gwt.client.panels.order;

import java.util.Date;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.ReportService;

public class OrderStatistics extends ContentWidget {    	
    
    private VerticalPanel topStatistics = new VerticalPanel();//顶端统计信息显示
    private HorizontalPanel topDate = new HorizontalPanel();//顶端日期显示
    private VerticalPanel searchconditions = new VerticalPanel();//统计搜索与显示
    private VerticalPanel orderReportPanel = new VerticalPanel();
    private VerticalPanel shippingReportPanel = new VerticalPanel();
    private VerticalPanel payReportPanel = new VerticalPanel();
    
    private DatePicker dp_start = new DatePicker();
    private DatePicker dp_end = new DatePicker();
    
	private TextBox txt_start_date = new TextBox();
	private TextBox txt_end_date = new TextBox();
	
	private Date start_date;
	private Date end_date;
	
	private PopupPanel pp_start = new PopupPanel();
	private PopupPanel pp_end = new PopupPanel();
	
	private DecoratedTabPanel tabPanel = new DecoratedTabPanel();
	
    private static OrderStatistics instance; 
    
    
    /**
     * Initialize this example.
     */
    public static OrderStatistics getInstance() {
    	if(instance==null) {
    		instance = new OrderStatistics();
    	}
    	return instance;
    }
    
    public OrderStatistics() {
    }
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public String getPageClassName() {
			return OrderStatistics.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "订单统计";
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
            return "订单统计"; 
    }
        
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        topStatistics.setWidth("100%");
        topStatistics.setHeight("30");
        topDate.setHeight("30");
        searchconditions.setWidth("100%");
        add(topStatistics);
        add(getTopDate());
        add(searchconditions);
        searchconditions.setHorizontalAlignment(searchconditions.ALIGN_CENTER);        
        tabPanel.setWidth("100%");
        tabPanel.setAnimationEnabled(true);
        tabPanel.add(orderReportPanel , "订单概况");
        tabPanel.add(shippingReportPanel , "配送方式");
        tabPanel.add(payReportPanel , "支付方式");
        tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
            
            public void onBeforeSelection(BeforeSelectionEvent<Integer> sender) {
                int tabIndex  = sender.getItem();
				if(tabIndex == 0){
					setOrderReportPanel();
				}else if(tabIndex == 1){
					setShippingReportPanel();
				}else if(tabIndex == 2){
					setPayReportPanel();
				}else{
					System.out.println("Error in tab select");
				}
			}
		});
        searchconditions.add(tabPanel);
    }
    
    private void setOrderReportPanel(){
    	orderReportPanel.clear();
//		new ReportService().generatePieCharts("orderStatus" , start_date , end_date , new ReportService.Listener() {
//			public void onSuccess(String path) {
//				setReport(path , orderReportPanel);
//			}
//		});
    }
    
    private void setShippingReportPanel(){
    	shippingReportPanel.clear();
//		new ReportService().generatePieCharts("shippingStatus", start_date , end_date , new ReportService.Listener() {
//			public void onSuccess(String path) {
//				setReport(path , shippingReportPanel);
//			}
//		});
    }
    
    private void setPayReportPanel(){
    	payReportPanel.clear();
//		new ReportService().generatePieCharts("payStatus", start_date , end_date , new ReportService.Listener() {
//			public void onSuccess(String path) {
//				setReport(path , payReportPanel);
//			}
//		});
    }
    
    private void setReport(String path , VerticalPanel panel){
    	System.out.println(path);
    	Image img = new Image(path);
    	panel.add(img);
    }
    
    private HorizontalPanel getTopDate(){
    	Label lbl_start = new Label("开始日期");
    	Label lbl_end = new Label("结束日期");
    	Button btn_search = new Button("查询");
   	
    	HTML btn_img_start = new HTML("<div style='width:20px;'><input type='image' src='C://date.gif'/></div>");
    	btn_img_start.setWidth("20");

        
        HTML btn_img_end = new HTML("<div style='width:20px;'><input type='image' src='C://date.gif'/></div>");
        btn_img_end.setWidth("20");
        btn_img_end.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
			}
		});
        
    	topDate.add(lbl_start);
    	topDate.add(txt_start_date);
    	topDate.add(btn_img_start);
    	topDate.add(lbl_end);
    	topDate.add(txt_end_date);
    	topDate.add(btn_img_end);
    	topDate.add(btn_search);
    	
    	
    	txt_start_date.setWidth("100");
    	txt_end_date.setWidth("100");
    	
    	dp_start.setVisible(false);
    	dp_end.setVisible(false);
    	pp_start.setWidget(dp_start);
    	pp_start.setPopupPosition(350, 200);
    	pp_start.hide();
    	pp_end.setWidget(dp_end);
    	pp_end.setPopupPosition(510, 200);
    	pp_end.hide();
    	
    	dp_start.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				start_date = (Date) dp_start.getValue();
				String str_start_date = DateTimeFormat.getFormat("yyyy-MM-dd").format(start_date);
				txt_start_date.setText(str_start_date);
				dp_start.setVisible(false);
				pp_start.hide();
			}
		});
    	txt_start_date.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if(dp_start.isVisible()){
					dp_start.setVisible(false);
					pp_start.hide();
				}else{
					pp_start.show();
					dp_start.setVisible(true);
				}
			}
		});
    	btn_img_start.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if(dp_start.isVisible()){
					dp_start.setVisible(false);
					pp_start.hide();
				}else{
					pp_start.show();
					dp_start.setVisible(true);
				}
			}
		});
    	//-->
    	dp_end.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				end_date = (Date) dp_end.getValue();
				String str_end_date = DateTimeFormat.getFormat("yyyy-MM-dd").format(end_date);
				txt_end_date.setText(str_end_date);
				dp_end.setVisible(false);
				pp_end.hide();
			}
		});
    	txt_end_date.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if(dp_end.isVisible()){
					dp_end.setVisible(false);
					pp_end.hide();
				}else{
					pp_end.show();
					dp_end.setVisible(true);
				}
			}
		});
    	btn_img_end.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if(dp_end.isVisible()){
					dp_end.setVisible(false);
					pp_end.hide();
				}else{
					pp_end.show();
					dp_end.setVisible(true);
				}
			}
		});
    	//-->
    	btn_search.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
		        tabPanel.selectTab(0);
			}
		});
    	
    	
    	return topDate;
    }
    
    public void refresh() {                  
        	topStatistics.clear();
        	topStatistics.setHorizontalAlignment(topStatistics.ALIGN_CENTER);
        	topStatistics.add(new HTML("<div align=\"left\">有效订单总金额:  ￥3000.00元    总点击数:  128    每千点击订单数:  7.81    每千点击购物额:  ￥23437.50元 </div>"));           
    }
}
