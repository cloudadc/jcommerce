package com.jcommerce.gwt.client.panels.article;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.service.ListService.Listener;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class CommentInfo extends ContentWidget {    	
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private Button btnAllow = new Button();
    private ColumnPanel contentPanel = new ColumnPanel();
//    private BeanObject comment = null;
    private BeanObject replyBeanObject = null;
    private VerticalPanel commentInfo = new VerticalPanel();//评论信息显示
    private VerticalPanel replyInfo = new VerticalPanel();//管理员回复信息显示
    private VerticalPanel replyInput = new VerticalPanel();
    private List<BeanObject> replyBean = null;
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject comment = null;
        
        public BeanObject getComment() {
            return comment;
        }
        public void setComment(BeanObject comment) {
            this.comment = comment;
            setEditting(comment != null);
        }
        public String getPageClassName() {
            return CommentInfo.class.getName();
        }
        public String getMenuDisplayName() {
            return "评论详情";
        }
    }

    public State getCurState() {
        return (State)curState;
    }
    
    /**
     * Initialize this example.
     */
    public static CommentInfo getInstance() {
    	if(instance==null) {
    		instance = new CommentInfo();
    	}
    	return instance;
    }
    private static CommentInfo instance; 
    public CommentInfo() {
        curState = new State();
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {    	
            return "评论详情"; 
    }
    
//    public void setComment(BeanObject comment) {
//        this.comment = comment;
//        
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        System.out.println("----------CommentInfo");
        commentInfo.setWidth("100%");
        replyInfo.setWidth("100%");
        replyInput.setWidth("100%");
        add(commentInfo);
        add(replyInfo);
        add(replyInput);
        replyInput.setHorizontalAlignment(replyInput.ALIGN_CENTER);
        replyInput.add(new HTML("<font textAlign = 'center'><b>回复评论</b></font>"));
        replyInput.add(contentPanel);
        replyInput.add(new HTML("<font textAlign = 'center'>提示: 此条评论若已有回复, 如果继续回复将更新原来回复的内容!</font>"));
        TextBox userName = new TextBox();
        userName.setReadOnly(true);
        TextBox email = new TextBox();
        email.setReadOnly(true);
        contentPanel.createPanel(IComment.USERNAME, "用户名:", userName);
        contentPanel.createPanel(IComment.EMAIL, "Email:", email); 
        final TextArea commentArea = new TextArea();
        commentArea.setSize("350", "100");
        contentPanel.createPanel(IComment.CONTENT, "回复内容:", commentArea);        
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定"); 
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        //点击确定进行回复或更新回复
        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                final BeanObject comment = getCurState().getComment();
	            if (comment!=null&&comment.getString(IComment.ID) != null) {
	            	if((replyBeanObject == null)){
		            	String id = comment.getString(IComment.ID);
						Map<String, Object> replyComment = new HashMap<String, Object>();
						replyComment = contentPanel	.getValues();
						replyComment.put(IComment.PARENT, id);
						Date currentTime = new Date();
			        	Timestamp nowTime = new Timestamp(currentTime.getTime());
						replyComment.put(IComment.ADDTIME, nowTime);
						replyComment.put(IComment.IDVALUE, comment.getString(IComment.IDVALUE));
						replyComment.put(IComment.COMMENTTYPE, comment.getString(IComment.COMMENTTYPE));
											
						BeanObject replyCommentBean = new BeanObject(ModelNames.COMMENT, replyComment);
						new CreateService().createBean(replyCommentBean, new CreateService.Listener() {
	                        public void onSuccess(String id) {
								Info.display("恭喜", "完成评论回复.");
                                CommentInfo.State state = new CommentInfo.State();
                                state.setComment(comment);
                                state.execute();
							}
	                    });
					}
	            	else{	            		
	            		Map<String, Object> replyChange = new HashMap<String, Object>();
	            		replyChange = contentPanel.getValues();
	            		BeanObject replyChangeBean = new BeanObject(ModelNames.COMMENT, replyChange);
	            		new UpdateService().updateBean(replyBeanObject.getString(IComment.ID), replyChangeBean, new UpdateService.Listener(){
							public void onSuccess(Boolean success) {
								if(success){
									Info.display("恭喜", "完成回复更新.");
	                                CommentInfo.State state = new CommentInfo.State();
	                                state.setComment(comment);
	                                state.execute();
								}
								else{
									Info.display("对不起", "系统错误，操作未完成.");
									UserComments.State state = new UserComments.State();
	                                state.execute();
								}								
							}	            			
	            		});
	            		replyBeanObject=null;
	            	}
				}
			}
		});

        //重置
        btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
            	commentArea.setText("");
            }
        });
        
        //允许显示或禁止显示
        btnAllow.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject comment = getCurState().getComment();
        		String id = comment != null ? comment.getString(IComment.ID) : null;
        		 Map<String, Object> value = new HashMap<String, Object>();
        		if(comment.getString(IComment.STATUS).equals("false")){
        			value.put(IComment.STATUS, true);
        			comment.set(IComment.STATUS, "true");
        			btnAllow.setText("禁止显示");
        		}
        		else if(comment.getString(IComment.STATUS).equals("true")){
        		    value.put(IComment.STATUS, false);        		    
        		    comment.set(IComment.STATUS, "false");
        		    btnAllow.setText("允许显示");
        		}
        		else{
        			Info.display("Error","Sorry系统错误！");
                    UserComments.State state = new UserComments.State();
                    state.execute();
        		}
        		comment = new BeanObject(ModelNames.COMMENT, value); 
        		new UpdateService().updateBean(id, comment, null);
        	}            
        });        
    }  
    
    public void refresh() {
        BeanObject comment = getCurState().getComment();
        if ( comment!=null&& comment.getString(IComment.ID) != null) {            
        	commentInfo.clear();
        	replyInfo.clear();
        	//填充客户留言内容
        	commentInfo.setHorizontalAlignment(commentInfo.ALIGN_CENTER);
			commentInfo.add(new HTML("<div align=\"left\"><a href=\"mailto:"+comment.getString(IComment.EMAIL)+"\">"
					+ comment.getString(IComment.USERNAME) + "</a>于"
					+ comment.getString(IComment.ADDTIME) + "对<b>"
					+ comment.getString(IComment.IDVALUE) + "</b>发表评论</div>"));
			commentInfo.add(new HTML("<hr>"));
			commentInfo.add(new HTML("<div align=\"left\">"
					+ comment.getString(IComment.CONTENT) + "</div>"));
			commentInfo.add(new HTML("<div align=\"right\"><b>评论等级:"
					+ comment.getString(IComment.COMMENTRANK) + "  IP地址:"
					+ comment.getString(IComment.IPADDRESS) + "</b></div>"));
        	
        	if(comment.getString(IComment.STATUS).equals("false")){
        		btnAllow.setText("允许显示");
    		}
    		else if(comment.getString(IComment.STATUS).equals("true")){
    			btnAllow.setText("禁止显示");
    		}
    		else{
    			Info.display("Error","Sorry系统错误！");    			
                UserComments.State state = new UserComments.State();
                state.execute();
    		}
        	commentInfo.add(btnAllow);       	
        	
        	Map<String, Object> reply = new HashMap<String, Object>();
        	reply.put(IComment.USERNAME, "administrator");
        	reply.put(IComment.EMAIL, "zj36083@163.com");
            contentPanel.updateValues(reply);
           
            Criteria criteria = new Criteria();
            Condition cond = new Condition();
    		cond.setField(IComment.PARENT);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue( comment.getString(IComment.ID));
    		criteria.addCondition(cond);			
    		new ListService().listBeans(ModelNames.COMMENT, criteria, new Listener(){
				@Override
				public void onSuccess(List<BeanObject> beans) {
					replyBean = new ArrayList<BeanObject>();
					replyBean = beans;
				}            	
            });
            new WaitService(new WaitService.Job() {
    			public boolean isReady() {
    				
    				if(replyBean == null){    					
    					return false;
    			    }
    				else
    					return true;
    			}

    			public void run() {
    				//填充管理员回复内容				
    				if (!replyBean.isEmpty()) {
    					replyBeanObject = replyBean.get(0);						
						replyInfo.setHorizontalAlignment(commentInfo.ALIGN_CENTER);
						replyInfo.add(new HTML("<div align=\"left\">管理员<a href=\"mailto:"+replyBeanObject.getString(IComment.EMAIL)+"\">"
								+ replyBeanObject.getString(IComment.USERNAME) + "</a>于"
								+ replyBeanObject.getString(IComment.ADDTIME) + "回复</div>"));
						replyInfo.add(new HTML("<hr>"));
						replyInfo.add(new HTML("<div align=\"left\">"
								+ replyBeanObject.getString(IComment.CONTENT)
								+ "</div>"));
						replyInfo.add(new HTML("<div align=\"right\"><b>IP地址:"
								+ replyBeanObject.getString(IComment.IPADDRESS)
								+ "</b></div>"));
						//研究报表显示情况（可删）
						replyInfo.add(new HTML("<html><head><title></title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style type=\"text/css\">a {text-decoration: none}</style></head><body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td width=\"50%\">&nbsp;</td><td align=\"center\"><a name=\"JR_PAGE_ANCHOR_0_1\"/><table style=\"width: 595px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"white\"><tr valign=\"top\"><td><img alt=\"\" src=\"orderInfo.html_files/px\" style=\"width: 85px; height: 111px;\"/></td><td><img src=\"orderInfo.html_files/img_0_0_0\" style=\"height: 111px\" alt=\"\"/></td><td><img alt=\"\" src=\"orderInfo.html_files/px\" style=\"width: 86px; height: 111px;\"/></td></tr></table></td><td width=\"50%\">&nbsp;</td></tr></table></body></html>"));
					}
    				replyBean=null;
    			}
            	
            });
        }
        else{
			Info.display("Error","Sorry系统错误！");      
            UserComments.State state = new UserComments.State();
            state.execute();
        }
    }
}
