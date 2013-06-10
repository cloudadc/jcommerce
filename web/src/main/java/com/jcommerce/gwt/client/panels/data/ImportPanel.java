package com.jcommerce.gwt.client.panels.data;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.BaseFileUploadFormPanel;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;

public class ImportPanel extends BaseFileUploadFormPanel {
	
	public static interface Constants {
        String Import_MenuName();
        String Import_Success();
        String Import_SelectFile();
        String Import_Note();
        String Import_NoteInfo();
        String Clear_MenuName();
        String Clear_Warning();
        String Clear_WarningInfo();
        String Clear_Label();
        String Clear_ToolTip();
        String Clear_Success();
    }
	
    /**
     * Initialize this example.
     */
    public static ImportPanel getInstance() {
    	if(instance==null) {
    		instance = new ImportPanel();
    	}
    	return instance;
    }
    private static ImportPanel instance; 
    private ImportPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return ImportPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return  Resources.constants.Import_MenuName();
		}
	}
	
	@Override
	public State getCurState() {
	    if (curState == null ) {
	        curState = new State();
	    }
		return (State)curState;
	}

	@Override
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
   		newState.setMessage(Resources.constants.Import_Success());
    	
    	ImportPanel.State choice1 = new ImportPanel.State();
    	newState.addChoice(ImportPanel.getInstance().getName(), choice1);
    	
    	newState.execute(); 

	}
	
	public void gotoClearedSuccessPanel(){
		Success.State newState = new Success.State();
   		newState.setMessage(Resources.constants.Clear_Success());
    	
    	ImportPanel.State choice1 = new ImportPanel.State();
    	newState.addChoice(ImportPanel.getInstance().getName(), choice1);
    	
    	newState.execute(); 
	}
	
	FileUploadField fufField;
    
	
	public void refresh() {
		fufField.reset();
		fufField.setValue("");
		fufField.setEnabled(true);
		fufField.setReadOnly(false);
	}
	

	protected void postSuperRefresh() {
	}
	

	@Override
	protected void setupPanelLayout() {
		Button bu = new Button(Resources.constants.Clear_MenuName());
		bu.setWidth(120);
		bu.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				final Dialog dialog = new Dialog();
				dialog.setButtons(Dialog.OKCANCEL);
				dialog.setHeading(Resources.constants.Clear_Warning());
				dialog.addText(Resources.constants.Clear_WarningInfo());
				dialog.setBodyStyle("fontWeight:bold;padding:13px;");
				dialog.setSize(300, 100);
				dialog.setHideOnButtonClick(true);
				dialog.addWindowListener(new WindowListener() {
					@Override
					public void windowHide(WindowEvent we) {
						Button button = we.getButtonClicked();
						Button b2 = (Button)dialog.getButtonBar().getItemByItemId(Dialog.OK);
						if(button == b2) {
							GWTHttpDynaForm form = new GWTHttpDynaForm();
							form.setUrl("/admin/imexportService.do?action=clear");
							form.SetListener(new GWTHttpDynaForm.Listener() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("error: " + caught.getMessage());
								}
								
								@Override
								public void onSuccess(String response) {
									gotoClearedSuccessPanel();
								}
							});
							form.submit();
						}
					}
				});
				dialog.show();
			}
		});

		AdapterField af = new AdapterField(bu);
		af.setHideLabel(true);

		MultiField mf = new MultiField();
		mf.setFieldLabel(Resources.constants.Clear_Label());
		mf.setToolTip(Resources.constants.Clear_ToolTip());
//		mf.setToolTip("Please be careful, all data would be clear upon clicking this button");
		mf.add(af);
		
		formPanel.add(mf, super.tfd());
		

		
		fufField = new FileUploadField();
		fufField.setFieldLabel(Resources.constants.Import_SelectFile());
		fufField.setName("file");
		fufField.setAutoValidate(true);
		formPanel.add(fufField);
		
		af = new AdapterField(new Label(Resources.constants.Import_NoteInfo()));
		af.setFieldLabel(Resources.constants.Import_Note());
		formPanel.add(af, sfd());
		
		
        formPanel.setAction("/admin/imexportService.do?action=import");

	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.Import_MenuName();
	}

    public Button getShortCutButton() {
      Button exportbutton = new Button(Resources.constants.Export_MenuName());
      exportbutton.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
        	  ExportPanel.State newState = new ExportPanel.State();
        	  newState.execute();
          }
      });
      return exportbutton;
    }
}
