/**
 * 
 */
package com.jcommerce.gwt.client.panels.privilege;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AdminUserForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

/**
 * @author Falcon
 *
 */
public class AdminUserPanel extends BaseEntityEditPanel {

	public static interface Constants {
        String AdminUser_userName();
        String AdminUser_email();
        String AdminUser_password();
        String AdminUser_confirmPassword();
        String AdminUser_oldPassword();
        String AdminUser_oldPasswordError();
        String AdminUser_editAdmin();
        String AdminUser_title();
        String AdminUser_addSuccessfully();
        String AdminUser_modifySuccessfully();
        String AdminUser_newPassword();
        String AdminUser_deleteSuccessfully();
        String AdminUser_deleteFailure();
    }
	
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return AdminUserPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.AdminUser_title();
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	@Override
	protected String getEntityClassName() {
		return ModelNames.ADMINUSER;
	}
	
	public static AdminUserPanel getInstance(){
		if(instance==null) {
    		instance = new AdminUserPanel();
    	}
    	return instance;
	}

	private static AdminUserPanel instance;
    private AdminUserPanel() {
    }
    
    @Override
    public String getName() {
    	if(!getCurState().getIsEdit())
			return Resources.constants.AdminUser_title();
		else
			return Resources.constants.AdminUser_editAdmin();    	
    }
    
    public void onButtonListClicked() {
        AdminList.State newState = new AdminList.State();
		newState.execute();
    }
    
	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#gotoSuccessPanel()
	 */
	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.AdminUser_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.AdminUser_modifySuccessfully());
    	}
    	
    	AdminList.State choice1 = new AdminList.State();
    	newState.addChoice(new AdminList().getName(), choice1);
    	newState.execute();
	}
	
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.AdminList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }

	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#postSuperRefresh()
	 */
	@Override
	protected void postSuperRefresh() {
		new ListService().listBeans(ModelNames.ADMINUSER, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
//		    	adminList.removeAll();
//				adminList.add(beans);
			}
		});
	}
	
	// get called when refresh(), if isEdit
	@Override
    protected void retrieveEntity() {
    	new ReadService().getBean(getEntityClassName(), getCurState().getId(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			obj = bean;
        			// populate those statically rendered fields
        			populateFields();
        			// sub-class should populate those "dynamic" fields including combox/list, etc 
        			postSuperRefresh();
        		}
        	});
    }
	@Override
	public void refresh() {
    	try {
    		List<Field<?>> fields = formPanel.getFields();
    		for(Field<?> f:fields) {
    			formPanel.remove(f);
    		}
    		setupPanelLayout();
    		formPanel.layout();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        if(getCurState().getIsEdit()) {
        	retrieveEntity();
        } else {
        	obj = new BeanObject();
        	postSuperRefresh();
        }

    }

	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#setupPanelLayout()
	 */
	@Override
	protected void setupPanelLayout() {
		System.out.println("----------AdminPanel");
		TextField<String> fUserName = AdminUserForm.getUserNameField(Resources.constants.AdminUser_userName()+"：");
		fUserName.setFieldLabel(Resources.constants.AdminUser_userName());
        formPanel.add(fUserName, sfd());
        
        TextField<String> fEmail = AdminUserForm.getEmailField(Resources.constants.AdminUser_email()+"：");
        fEmail.setFieldLabel(Resources.constants.AdminUser_email());
        formPanel.add(fEmail, sfd());
        
        if(getCurState().getIsEdit()) {
        	TextField<String> fOldPassword = AdminUserForm.getOldPasswordField(Resources.constants.AdminUser_oldPassword()+"：");
        	fOldPassword.setFieldLabel(Resources.constants.AdminUser_oldPassword());
            formPanel.add(fOldPassword, sfd());
            
            TextField<String> fPassword = AdminUserForm.getPasswordField(Resources.constants.AdminUser_password()+"：");
	        fPassword.setFieldLabel(Resources.constants.AdminUser_password());
	        fPassword.setVisible(false);
	        formPanel.add(fPassword, sfd());
            
            TextField<String> fNewPassword = AdminUserForm.getNewPasswordField(Resources.constants.AdminUser_newPassword()+"：");
            fNewPassword.setFieldLabel(Resources.constants.AdminUser_newPassword());
            formPanel.add(fNewPassword, sfd());
        }
        else{
	        TextField<String> fPassword = AdminUserForm.getPasswordField(Resources.constants.AdminUser_password()+"：");
	        fPassword.setFieldLabel(Resources.constants.AdminUser_password());
	        formPanel.add(fPassword, sfd());
        }
        HiddenField<String> fAddTime = AdminUserForm.getAddTimeField();
    	formPanel.add(fAddTime,sfd());
    	
    	HiddenField<String> fActionList = AdminUserForm.getActionListField();
    	formPanel.add(fActionList,sfd());
    	
    	HiddenField<String> fLastIp = AdminUserForm.getLastIpField();
    	formPanel.add(fLastIp , sfd());
    	
    	HiddenField<String> fLastLogin = AdminUserForm.getLastLoginField();
    	formPanel.add(fLastLogin , sfd());
    	
    	HiddenField<String> fNavList = AdminUserForm.getNavListField();
    	formPanel.add(fNavList , sfd());
    	
    	HiddenField<String> fTodoList = AdminUserForm.getTodoListField();
    	formPanel.add(fTodoList , sfd());
    	
    	HiddenField<String> fLangType = AdminUserForm.getLangTypeField();
    	formPanel.add(fLangType , sfd());   	
    	
    	
        TextField<String> fCfmPassword = AdminUserForm.getComfirmPasswordField(Resources.constants.AdminUser_confirmPassword()+"：");
        fCfmPassword.setFieldLabel(Resources.constants.AdminUser_confirmPassword());
        formPanel.add(fCfmPassword, sfd());
        		
	}
	@Override
	protected void submit() {
		// default implementation is thru GWT-RPC
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
    	BeanObject form = new BeanObject(getEntityClassName(), (Map<String, Object>)(Map)props);
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getId();
    		String oldPassword = (String)props.get("oldPassword");
    		if(oldPassword == null ){
    			updateAdminUser(props);
    		}
    		else{
	    		if(oldPassword.equals((String)props.get("password"))){
	    			props.put("password", props.get("newPassword"));
					updateAdminUser(props);
				}
				else{
					Info.display("Warning", Resources.constants.AdminUser_oldPasswordError());
				}
					
    		}
    	}else {
          new CreateService().createBean(form, new CreateService.Listener() {
          public synchronized void onSuccess(String id) {
        	  log("new onSuccess( "+id);                            
              getCurState().setId(id);
              gotoSuccessPanel();
          }
          });
    	}
	}

	@Override
	public String getDescription() {
		 return "cwBasicTextDescription";
	}
	
	private void updateAdminUser( Map<String, String> props){
    	BeanObject form = new BeanObject(getEntityClassName(), (Map)props);
		String id = getCurState().getId();
		 new UpdateService().updateBean(id, form, new UpdateService.Listener() {
       	  public synchronized void onSuccess(Boolean success) {
       		  gotoSuccessPanel();
       	  }
       	  public void onFailure(Throwable caught) {
       		  // TODO a point to define common behavior
       	  }
         });
	}
	
	@Override
	protected String validateForm() {
    	Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
    	if(getCurState().getIsEdit() && props.get("oldPassword") == null ){
    		props.put("oldPassword", "");
    	}
		return AdminUserForm.validate(props);
	}
}
