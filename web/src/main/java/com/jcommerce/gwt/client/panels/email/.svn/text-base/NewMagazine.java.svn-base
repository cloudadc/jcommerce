package com.jcommerce.gwt.client.panels.email;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IMailTemplate;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

public class NewMagazine extends ContentWidget {
	public static interface Constants {
		String EmailSendType_Subscribe();
	}
	
	private ColumnPanel contentPanel = new ColumnPanel();
//	private boolean editting = false; // 判断是新建还是修改杂志
//	private BeanObject magazine = null; // 假如操作是修改，数据存储的地方
//	private String magazineId = null;
	private TextBox title = new TextBox(); // 标题
	private final RichTextArea area = new RichTextArea(); // 正文
	
	public static class State extends PageState {
	    private BeanObject magazine = null; // 假如操作是修改，数据存储的地方
	    
		public BeanObject getMagazine() {
            return magazine;
        }

        public void setMagazine(BeanObject magazine) {
            this.magazine = magazine;
            setEditting(magazine != null);
        }

        public String getPageClassName() {
			return NewMagazine.class.getName();
		}

		public String getMenuDisplayName() {
			return "添加新杂志";
		}
	}

	public State getCurState() {
		return (State)curState;
	}

	@Override
	public String getDescription() {
		return "NewMagazineDescription";
	}

	@Override
	public String getName() {
		if ( !getCurState().isEditting()) {
			return "添加新杂志";
		} else {
			return "编辑杂志信息";
		}
	}
	
	public NewMagazine() {	
	    curState = new State();
	}
	
//	public void setMagazine(BeanObject data) {
//		magazine = data;
//		magazineId = data != null ? data.getString(IMailTemplate.ID) : null;
//		editting = data != null;
//	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		// Create a tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);

		// Create the text area and toolbar
		area.setSize("100%", "14em");
		final RichTextToolbar toolbar = new RichTextToolbar(area);
		toolbar.setWidth("100%");

		// Add the components to a panel
		final Grid grid = new Grid(2, 1);
		grid.setStyleName("cw-RichText");
		grid.setWidget(0, 0, toolbar);
		grid.setWidget(1, 0, area);

		// Add a detail tab
		HTML properties2 = new HTML("properites");
		tabPanel.add(grid, "杂志内容");
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		
		// button panel
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.setSize(1024, 0);   
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        Button commit = new Button("确定");
        commit.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	Map<String, Object> magazineInfo = new HashMap<String, Object>();
				
				Date currentTime = new Date();
				Timestamp nowTime = new Timestamp(currentTime.getTime());
				String magazineTitle = title.getText();
				String magazineContent = area.getText();
				
				// collect the information and create the bean object.
				magazineInfo.put(IMailTemplate.LASTMODIFY, nowTime);
				magazineInfo.put(IMailTemplate.SUBJECT, magazineTitle);
				magazineInfo.put(IMailTemplate.CONTENT, magazineContent);
				magazineInfo.put(IMailTemplate.TYPE, Resources.constants.EmailSendType_Subscribe());
				
				BeanObject bean = new BeanObject(ModelNames.MAILTEMPLATE, magazineInfo);
				
				if( !getCurState().isEditting() ) {
					// 新建
					new CreateService().createBean(bean, new CreateService.Listener() {
						@Override
						public void onSuccess(String id) {
							title.setText("");
							area.setText("");
							Info info = new Info();
							info.show(new InfoConfig("恭喜", "新建杂志成功！"));
							MagazineManager.State state = new MagazineManager.State();
							state.execute();
						}
					});
				} else {
					
					// 修改
					new UpdateService().updateBean(getCurState().getMagazine().getString(IMailTemplate.ID), bean, new UpdateService.Listener() {
						@Override
						public void onSuccess(Boolean success) {
							title.setText("");
							area.setText("");
							Info info = new Info();
							info.show(new InfoConfig("恭喜", "修改杂志成功！"));
                            MagazineManager.State state = new MagazineManager.State();
                            state.execute();
						}
					});
				}
            	
            }
          });
        panel.addButton(commit);
		
        title.setWidth("250px");
		contentPanel.createPanel(IMailTemplate.SUBJECT, "标题", title);
		contentPanel.createPanel(IMailTemplate.CONTENT, "", tabPanel);
		contentPanel.createPanel("submit", "", panel);

		add(contentPanel);
	}
	
	public void refresh() {
		// editting
	    BeanObject magazine = getCurState().getMagazine();
		if ( magazine != null ) {
			Map<String, Object> data = magazine.getProperties();
			title.setText((String) data.get(IMailTemplate.SUBJECT));
			area.setText((String) data.get(IMailTemplate.CONTENT));
		}
	}
	
}
