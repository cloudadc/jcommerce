package com.jcommerce.gwt.client.panels.data;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.DatabaseService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class DatabaseInitialize extends ContentWidget {
    private Button btnOK = new Button();
    private ColumnPanel contentPanel = new ColumnPanel();
    private boolean editting = false;
    private String backUpFileName = "";
    private Label label = new Label();
    private TextBox textBox = new TextBox();
    private MessageBox processBar=MessageBox.wait("请等待", "正在初始化...", "");

    public DatabaseInitialize() {
        super();
        curState = new State();
    }

    private static DatabaseInitialize instance;

    public static DatabaseInitialize getInstance() {
        if (instance == null) {
            instance = new DatabaseInitialize();
        }
        return instance;
    }

    public static class State extends PageState {
        public String getPageClassName() {
            return DatabaseInitialize.class.getName();
        }

        public String getMenuDisplayName() {
            return "数据初始化";
        }
    }

    public State getCurState() {
        return (State)curState;
    }

    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {

        return "数据初始化";
    }

    protected void onRender(com.google.gwt.user.client.Element parent, int index) {
        super.onRender(parent, index);


        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnOK.setText("开始初始化");

        label.setText("初始化数据库");
        label.setVisible(true);

        panel.add(label);
        panel.add(btnOK);
    
        contentPanel.createPanel(null, null, panel);

        add(contentPanel);
        
		processBar.close();
        
        btnOK.addClickHandler(new ClickHandler() {
        	
            public void onClick(ClickEvent arg0) {
            	processBar.show();
            	
				new DatabaseService().Initialize(new DatabaseService.Listener() {
							public void onSuccess(String result) {
								processBar.close();
								if (result.equals("success")) {
									Info.display("", "初始化成功");
								} else if (result.equals("UnsupportedEncodingException")) {
									Info.display("", "初始化失败，文件编码类型错误");
								} else if (result.equals("IOException")) {
									Info.display("", "初始化失败，读写文件发生错误");
								} else if (result.equals("SQLException")) {
									Info.display("", "初始化失败，插入重复数据或数据错误");
								}

							}

							public void onFailure(Throwable caught) {
								processBar.close();
								Info.display("", "系统存在错误");
							}
						});
						
            }
            
        });
    }
}
