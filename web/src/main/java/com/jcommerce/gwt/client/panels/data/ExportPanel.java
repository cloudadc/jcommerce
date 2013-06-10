package com.jcommerce.gwt.client.panels.data;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.goods.BrandPanel;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;


public class ExportPanel extends BaseEntityEditPanel{
	
	public static interface Constants {
        String Export_MenuName();
        String Export_Success();
        String Export_AllExport();
        String Export_Exporting();
        String Export_ExportButton();
        String Export_StandardExport();
        String Export_MinimumExport();
    }
    /**
     * Initialize this example.
     */
    public static ExportPanel getInstance() {
    	if(instance==null) {
    		instance = new ExportPanel();
    	}
    	return instance;
    }
    private static ExportPanel instance; 
    private ExportPanel() {
        curState = new State();
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return ExportPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Export_MenuName();
		}
	}
	
	@Override
	public State getCurState() {
		return (State)curState;
	}

	@Override
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
   		newState.setMessage(Resources.constants.Export_Success());

   		ExportPanel.State choice1 = new ExportPanel.State();
    	newState.addChoice(ExportPanel.getInstance().getName(), choice1);
    	
    	newState.execute(); 
		
	}

	@Override
	protected void postSuperRefresh() {
	}

	@Override
	protected void setupPanelLayout() {
		
//		AdapterField af = new AdapterField(new Label("Currently will export all data supported"));
//		af.setFieldLabel("Note");
//		formPanel.add(af, sfd());
//		
//		Button exportAll = new Button(Resources.constants.Export_ExportButton());
//		exportAll.setWidth(120);
//		exportAll.addSelectionListener(new SelectionListener<ButtonEvent>() {
//			public void componentSelected(ButtonEvent sender) {
//				   Window w = new Window();        
//				   w.setHeading(Resources.constants.Export_MenuName());
//				   w.setModal(false);
//				   w.setSize(200, 100);
//				   w.setMaximizable(false);
//				   w.setToolTip(Resources.constants.Export_Exporting());
//				   w.setUrl("/admin/imexportService.do?action=export&type=all");
//				   w.show();
//				   gotoSuccessPanel();
//			}
//		});
//
//		af = new AdapterField(exportAll);
//		af.setHideLabel(true);
//
//		MultiField mf = new MultiField();
//		mf.setFieldLabel(Resources.constants.Export_AllExport());
//		mf.add(af);
//		formPanel.add(mf, super.tfd());
		
		Button standerdExport = new Button(Resources.constants.Export_ExportButton());
		standerdExport.setWidth(120);
		standerdExport.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				   Window w = new Window();        
				   w.setHeading(Resources.constants.Export_MenuName());
				   w.setModal(false);
				   w.setSize(200, 100);
				   w.setMaximizable(false);
				   w.setToolTip(Resources.constants.Export_Exporting());
				   w.setUrl("/admin/imexportService.do?action=export&type=standerd");
				   w.show();
				   gotoSuccessPanel();
                        
			}
		});

		AdapterField af = new AdapterField(standerdExport);
		af.setHideLabel(true);

		MultiField mf = new MultiField();
		mf.setFieldLabel(Resources.constants.Export_StandardExport());
		mf.add(af);
		formPanel.add(mf, super.tfd());
		
		Button minimumExport = new Button(Resources.constants.Export_ExportButton());
		minimumExport.setWidth(120);
		minimumExport.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				   Window w = new Window();        
				   w.setHeading(Resources.constants.Export_MenuName());
				   w.setModal(false);
				   w.setSize(200, 100);
				   w.setMaximizable(false);
				   w.setToolTip(Resources.constants.Export_Exporting());
				   w.setUrl("/admin/imexportService.do?action=export&type=minimum");
				   w.show();
				   
				   gotoSuccessPanel();
			}
		});

		af = new AdapterField(minimumExport);
		af.setHideLabel(true);

		mf = new MultiField();
		mf.setFieldLabel(Resources.constants.Export_MinimumExport());
		mf.add(af);
		formPanel.add(mf, super.tfd());
		

		
	}

    @Override
    protected void submit() {
    	// do nothing
    }
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.Export_MenuName();
	}

    public Button getShortCutButton() {
      Button importbutton = new Button(Resources.constants.Import_MenuName());
      importbutton.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
        	  ImportPanel.State newState = new ImportPanel.State();
        	  newState.execute();
          }
      });
      return importbutton;
    }
}
