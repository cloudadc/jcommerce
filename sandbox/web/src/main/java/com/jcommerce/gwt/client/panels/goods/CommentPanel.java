package com.jcommerce.gwt.client.panels.goods;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CommentForm;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

public class CommentPanel extends ContentWidget {
	public static interface Constants {
		String Comment_title();
		String Comment_email();
		String Comment_replyContent();
		String Comment_replyCommentHead();
		
		
	}
	public interface CommentPanelMessage extends Messages {
		String Comment_to(String email,String userName,String addTime,String idValue);
		String Comment_reply(String email,String userName,String addTime);
	}
	private LayoutContainer basePanel = new LayoutContainer();
	private FormPanel formPanel = new FormPanel();
	private ContentPanel commentPanel = new ContentPanel();
	private ContentPanel replyPanel = new ContentPanel();
	private BeanObject comment = new BeanObject();
	private BeanObject reply = new BeanObject();
	Button btnNew = new Button();    
	Button btnReset = new Button();  
	Button btnActive = new Button();  
	TextField<String> userNameField ;
	TextField<String> emailField ;
	TextField<String> ipAddressField ;
	
	public static class State extends PageState {

		public static final String PK_ID = "pkId";
		
		public void setId(String gtid) {
			setValue(PK_ID, gtid);
		}
		public String getPkId() {
			return (String)getValue(PK_ID);
		}
		public String getPageClassName() {
			return CommentPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.Comment_title();
		}
	}
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	int pageSize = 5;

	private static CommentPanel instance;
    private CommentPanel() { 
    }
    public static CommentPanel getInstance() {
    	if(instance == null) {
    		instance = new CommentPanel();
    	}
    	return instance;
    }
    
    @Override
    protected void onRender(Element parent, int index) {

    	super.onRender(parent, index);
    	basePanel.setLayout(new BorderLayout());
    	basePanel.setStyleAttribute("padding", "10px");
    	super.add(basePanel);

    	FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
    	
    	formPanel.setLayout(fl);
        initCommentLayout();
		initReplyLayout();

		btnNew.setText(Resources.constants.ok());        
		btnReset.setText(Resources.constants.reset());
      
	    formPanel.setButtonAlign(HorizontalAlignment.CENTER);
	    formPanel.addButton(btnNew);
	    formPanel.addButton(btnReset);
	      
	    btnNew.addSelectionListener(selectionListener);
	    btnReset.addSelectionListener(
	    	new SelectionListener<ButtonEvent>() {
	      		public void componentSelected(ButtonEvent sender) {
	      			formPanel.reset();
	      		}
	      	}
	    );
	
      
    }
    
    private void setupPanelLayout(LayoutRegion pos) {
    	if( formPanel.getFields().size() > 0 ){
    		formPanel.clear();
    	}
    	else {
	    	formPanel.setHeading(Resources.constants.Comment_replyCommentHead());
	    	
	    	userNameField = CommentForm.getUserNameField();
	        userNameField.setFieldLabel(Resources.constants.CommentList_userName());
	        userNameField.setReadOnly(true);
	        formPanel.add(userNameField);
	        
	        emailField = CommentForm.getEmailField();
	        emailField.setFieldLabel(Resources.constants.Comment_email());
	        emailField.setReadOnly(true);
	        formPanel.add(emailField);
	        
	        TextArea contentField = CommentForm.getContentField(Resources.constants.Comment_replyContent()+"：");
	        contentField.setHeight("60px");
	        contentField.setWidth("180px"); 
	        contentField.setFieldLabel(Resources.constants.Comment_replyContent());
	        contentField.setValue("");
	        formPanel.add(contentField);  
	
	        ipAddressField = CommentForm.getIpAddressField();
	        ipAddressField.setVisible(false);
	        formPanel.add(ipAddressField);		
	        
    	}
    	RemoteService.getSpecialService().getAdminUserInfo(new AsyncCallback<Map<String,String>>(){


			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert("ERROR: "+caught.getMessage());
			}


			public void onSuccess(Map<String, String> result) {
				userNameField.setValue(result.get(IAdminUser.NAME));
				emailField.setValue(result.get(IAdminUser.EMAIL));
				ipAddressField.setValue(result.get(IComment.IPADDRESS));
				formPanel.repaint();
			}
        	
        });
    	
    	BorderLayoutData formData = new BorderLayoutData(pos, 250);  
    	formData.setMargins(new Margins(5, 0, 5, 0)); 
    	basePanel.add( formPanel ,formData);
	}
	private void setupReplyLayout() {
		replyPanel.removeAll();
    	replyPanel.setHeading(Resources.messages.Comment_reply((String)reply.get(IComment.EMAIL),(String)reply.get(IComment.USERNAME),new Timestamp((Long)reply.get(IComment.ADDTIME)).toString()));
		
    	TableLayout tl = new TableLayout();
		tl.setWidth("100%");
        tl.setCellSpacing(5);
        replyPanel.setLayout(tl);
        
        TableData td = new TableData();
        td.setWidth("100%");
        Label content = new Label();
        content.setText(reply.getString(IComment.CONTENT));
        replyPanel.add(content,td);
    	
        td = new TableData();
        td.setWidth("100%");
        td.setHorizontalAlign(HorizontalAlignment.RIGHT);
        Label ipAddress = new Label();
        ipAddress.setText(Resources.constants.CommentList_ipAddress()+":"+reply.get(IComment.IPADDRESS));
		replyPanel.add(ipAddress,td);
		
		BorderLayoutData replyData = new BorderLayoutData(LayoutRegion.CENTER ,150);
		replyData.setMargins(new Margins(5,0,5,0));
		basePanel.add( replyPanel , replyData );
	}
	private void setupCommentLayout() {
		commentPanel.removeAll();
		if( comment.getString( IComment.USER ) == null ){
			comment.set(IComment.USERNAME, Resources.constants.CommentList_anonymous());
		}
		
		TableLayout tl = new TableLayout();
		tl.setWidth("100%");
        tl.setCellSpacing(5);
		commentPanel.setLayout(tl);
		commentPanel.setHeading(Resources.messages.Comment_to((String)comment.get(IComment.EMAIL),(String)comment.get(IComment.USERNAME),new Timestamp((Long)comment.get(IComment.ADDTIME)).toString(),(String)comment.get(IComment.IDVALUE)));
		
		TableData td = new TableData();
        td.setWidth("100%");
        Label content = new Label();
        content.setText(comment.getString(IComment.CONTENT));
		commentPanel.add(content,td);

		td = new TableData();
        td.setWidth("100%");
        td.setHorizontalAlign(HorizontalAlignment.RIGHT);
        Label ipAddress = new Label();
        ipAddress.setText(Resources.constants.CommentList_rank()+":"+comment.getString(IComment.COMMENTRANK)+"  "+Resources.constants.CommentList_ipAddress()+":"+comment.getString(IComment.IPADDRESS));
		commentPanel.add(ipAddress , td);
		
		td = new TableData();
        td.setWidth("100%");
        td.setHorizontalAlign(HorizontalAlignment.CENTER);
        
        if(comment.get(IComment.STATUS).equals(IComment.STATUS_ACTIVE)){
        	btnActive.setText(Resources.constants.CommentList_action_statusInactive());
        }
        else{
        	btnActive.setText(Resources.constants.CommentList_action_statusActive());
        }
        btnActive.addSelectionListener(statusSelectionListener);
		commentPanel.add(btnActive,td);
        
		BorderLayoutData commentData = new BorderLayoutData(LayoutRegion.NORTH, 100);  
	    commentData.setMargins(new Margins(5, 0, 5, 0)); 
		
		basePanel.add(commentPanel,commentData);
	}
	
	protected SelectionListener<ButtonEvent> statusSelectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
	    	try {
	    		if(comment.get(IComment.STATUS).equals(IComment.STATUS_ACTIVE)){
	    			comment.set(IComment.STATUS, IComment.STATUS_INACTIVE);
	    			new UpdateService().updateBean(comment.getString(IComment.ID), comment, new UpdateService.Listener(){

						@Override
						public void onSuccess(Boolean success) {
							btnActive.setText(Resources.constants.CommentList_action_statusActive());
							btnActive.repaint();
						}
	    				
	    			});
	    		}
	    		else{
	    			comment.set(IComment.STATUS, IComment.STATUS_ACTIVE);
	    			new UpdateService().updateBean(comment.getString(IComment.ID), comment, new UpdateService.Listener(){

						@Override
						public void onSuccess(Boolean success) {
							btnActive.setText(Resources.constants.CommentList_action_statusInactive());
							btnActive.repaint();
						}
	    				
	    			});
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	
	};
	    
	protected SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
	    	try {
	    	if(!formPanel.isValid()) {
	    		Window.alert("Please check input before submit.");
	    		return;
	    	}
	    	String error = validateForm();
	    	if(error!=null && error.length()>0) {
	    		Window.alert("Please check input before submit, error is: "+error);
	    		return;
	    	}
	    	
	    	if(!formPanel.isDirty()) {
	    		// TODO this is optimisitic. should change it to based on a strategy configuration?
	    		Window.alert("the form is not changed!!!");
	    		return;
	    	}
	    	submit();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	
	};
	private String validateForm() {
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
		return CommentForm.validate(props);
	}
	

	private void submit() {
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
		BeanObject form = new BeanObject(ModelNames.COMMENT, (Map)props);
		if(reply.get(IComment.ID) != null ){
			reply.set(IComment.CONTENT,form.get(IComment.CONTENT));
			reply.set(IComment.ADDTIME, System.currentTimeMillis());
			reply.set(IComment.STATUS, IComment.STATUS_ACTIVE);
			new UpdateService().updateBean((String)reply.get(IComment.ID), reply, new UpdateService.Listener(){

				@Override
				public void onSuccess(Boolean success) {
					basePanel.remove(replyPanel);
					basePanel.remove(formPanel);
					refresh();
					
				}
				
			});
		}
		else{
	    	form.set(IComment.ADDTIME, System.currentTimeMillis());
	    	form.set(IComment.COMMENTTYPE, comment.get(IComment.COMMENTTYPE));
	    	form.set(IComment.IDVALUE, comment.get(IComment.IDVALUE));
	    	form.set(IComment.PARENT, comment.get(IComment.ID));
	    	form.set(IComment.STATUS, IComment.STATUS_ACTIVE);
	        new CreateService().createBean(form, new CreateService.Listener() {
	          public synchronized void onSuccess(String id) {
	        	  log("new onSuccess( "+id); 
	        	  basePanel.remove(formPanel);
	              refresh();
	          }
	        });
		}
		
	}

    public Button getShortCutButton() {
      Button buttonAddClone = new Button(Resources.constants.CommentList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    
    public void onButtonListClicked() {
		CommentListPanel.State newState = new CommentListPanel.State();
		newState.execute();
    }

	
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	
	public String getName() {
		return Resources.constants.Comment_title();
	}
	private void initCommentLayout(){
		new ReadService().getBean(ModelNames.COMMENT, getCurState().getPkId() , new ReadService.Listener(){
			public void onSuccess(BeanObject bean) {
				comment = bean;
				setupCommentLayout();
				basePanel.layout();
			}
	        
	        public void onFailure(Throwable caught) {
	        	comment = new BeanObject();
	        }
		});
	}
	private void initReplyLayout(){
	    Criteria criteria = new Criteria();
	    criteria.addCondition(new Condition(IComment.PARENT, Condition.EQUALS, getCurState().getPkId()));
		new ListService().listBeans(ModelNames.COMMENT, criteria, new ListService.Listener(){
			@Override
	        public void onFailure(Throwable caught) {
	        	reply = new BeanObject();
	        }

			@Override
			public void onSuccess(List<BeanObject> beans) {
				if( beans.size() > 0 ){
					reply = beans.get(0);
					setupReplyLayout();
					setupPanelLayout(LayoutRegion.SOUTH);
					basePanel.setHeight(500);
					basePanel.layout();
				}
				else{
					reply = new BeanObject();
					basePanel.setHeight(400);
					setupPanelLayout(LayoutRegion.CENTER);
					basePanel.layout();
				}
				
				
			}
		});
	}
	
	public void refresh() {
    	try {
    		basePanel.removeAll();
    		initCommentLayout();
    		initReplyLayout();
    		basePanel.repaint();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

    }

}
