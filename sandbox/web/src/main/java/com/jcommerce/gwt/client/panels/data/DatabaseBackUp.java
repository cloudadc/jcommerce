package com.jcommerce.gwt.client.panels.data;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Random;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.DatabaseService;

public class DatabaseBackUp extends ContentWidget {
	private FormData formData;
	private VerticalPanel vp; 
	private FormPanel dbPanel=new FormPanel();
	private TextField<String> backupName = new TextField<String>();  
//	private MessageBox processBar=MessageBox.progress("请等待","", "备份中");
	private MessageBox processBar=MessageBox.wait("请等待", "备份中...", "");
	
	public DatabaseBackUp() {
		super();
		curState = new State();
	}

	private static DatabaseBackUp instance;

	public static DatabaseBackUp getInstance() {
		if (instance == null) {
			instance = new DatabaseBackUp();
		}
		return instance;
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return DatabaseBackUp.class.getName();
		}

		public String getMenuDisplayName() {
			return "数据备份";
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

		return "数据备份";
	}



	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);
		formData=new FormData("-20");
		vp = new VerticalPanel(); 
		createForm();
		

		add(vp);

	}
	
	private void createForm(){
	
		dbPanel.setHeaderVisible(false);
		dbPanel.setFrame(false);
		dbPanel.setWidth(700);
		dbPanel.setLabelWidth(300);
		dbPanel.setFieldWidth(200);
		dbPanel.setFooter(true);
		
		
		Radio backupAll=new Radio();
		backupAll.setBoxLabel("全部备份");
		backupAll.setToolTip("备份所有数据库表");
		backupAll.setValue(true);
		
		Radio backupStandard=new Radio();
		backupStandard.setBoxLabel("标准备份");
		backupStandard.setToolTip("备份常用的数据表");
		
		
		Radio backupSimple=new Radio();
		backupSimple.setBoxLabel("最小备份");
		backupSimple.setToolTip("仅包括商品表，订单表，用户表");
		
		RadioGroup backupType = new RadioGroup();  
		backupType.setFieldLabel("备份类型");  
		backupType.add(backupAll);  
		dbPanel.add(backupType, formData);  		

		backupName.setFieldLabel("备份文件名");  
		backupName.setAllowBlank(false);  
		backupName.setSelectOnFocus(true);
	
		backupName.setValue(createRandomBackupFileName());
		backupName.setMaxLength(18);
		//set the length of backup file name to max 18 
		backupName.setRegex("^[0-9a-zA-Z]{1,14}(.sql)?$");
		backupName.getMessages().setRegexText("文件名以字母或数字开头,长度 1~14");
		backupName.setWidth(500);
		dbPanel.add(backupName, formData); 
		
		Button btnOK = new Button("开始备份");  
		dbPanel.addButton(btnOK);  
		dbPanel.setButtonAlign(HorizontalAlignment.CENTER);
		
	
		processBar.setTitle("请等待");
		processBar.setProgressText("备份中...");
		processBar.setMaxWidth(300);	
		processBar.close();
	
		
		btnOK.addSelectionListener(new SelectionListener<ButtonEvent>() {


			public void componentSelected(ButtonEvent ce) {
				
				  if (!dbPanel.isValid()) {  
					  return;  
				  }
	
				  processBar.show();
				  //用于控制进度条
			    
				  
				new DatabaseService().Backup(backupName.getValue(),
						new DatabaseService.Listener() {

							@Override
							public void onSuccess(String result) {
								processBar.close();
								if (result.equals("sameFileName")) {
									Info.display("提示", "文件名相同，请更换备份文件名");
								} else if (result.equals("success")) {
									Info.display("提示", "备份成功");
								} else if (result.equals("IOException")) {
									Info.display("提示", "备份失败，文件写入失败");
								} else if(result.equals("SQLException")){
									Info.display("提示","备份失败，数据操作错误");
								}
								
							}
							public void onFailure(Throwable caught) {
								processBar.close();
								Info.display("", "系统存在错误");
							}
							
						});					
				  
				 
			}
			
			
		 
		});
				 
				

			
		vp.add(dbPanel);
	}
	
	private String createRandomBackupFileName(){
		
		String characterStr="";
		String str="abcdefghijklmnopqrstuvwxyz";
		for(int i=0;i<6;i++){
			int k=Random.nextInt(26);
			characterStr=characterStr+str.charAt(k);
		}
//		dateStr=DateTimeFormat.getFormat("yyyyMMddHHmmss").format(new Date());
		String dateStr=DateTimeFormat.getFormat("yyyyMMdd").format(new Date());
		return dateStr+characterStr+".sql";
		
	}
	
	
	
	
	
	
	
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}