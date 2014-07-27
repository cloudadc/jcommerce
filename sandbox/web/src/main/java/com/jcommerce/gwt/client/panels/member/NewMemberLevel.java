/**
 * 
 */
package com.jcommerce.gwt.client.panels.member;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.model.RegularExConstants;
import com.jcommerce.gwt.client.panels.goods.NewAttribute.State;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author dell
 *
 */
public class NewMemberLevel extends ContentWidget {    	
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
    
    private TextField<String> name = new TextField<String>();
    private TextField<String> maxPionts = new TextField<String>();
    private TextField<String> minPionts = new TextField<String>();
    private TextField<String> discount = new TextField<String>();
    
//    private boolean editting = false;
    
//    private BeanObject memberLevel = null;
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject memberLevel = null;
        
        public BeanObject getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(BeanObject memberLevel) {
            this.memberLevel = memberLevel;
            setEditting(memberLevel != null);
        }

        public String getPageClassName() {
            return NewMemberLevel.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "添加会员级别" : "编辑会员级别";
        }
    }
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }

    /**
     * Initialize this example.
     */
    private static NewMemberLevel instance; 
    
    public static NewMemberLevel getInstance() {
    	if(instance==null) {
    		instance = new NewMemberLevel();
    	}
    	return instance;
    }
    
    private NewMemberLevel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().isEditting())
        	return "添加会员级别";
        else
            return "编辑会员级别"; 
    	
    }
    
//    public void setMemberLevel(BeanObject memberLevel) {
//        this.memberLevel = memberLevel;
//        editting = memberLevel != null;
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setValidateReg();
        System.out.println("----------NewMemberLevel");
        add(contentPanel);
       
        contentPanel.createPanel(IUserRank.NAME, "会员等级名称:", name);
        contentPanel.createPanel(IUserRank.MINPOINTS, "积分下限:", minPionts);
        contentPanel.createPanel(IUserRank.MAXPOINTS, "积分上限:", maxPionts);
//        contentPanel.createPanel(IUserRank.SHOWPRICE, "在商品详情页显示该会员等级的商品价格:", new CheckBox());
//        contentPanel.createPanel(IUserRank.SPECIAL, "特殊会员组:", new CheckBox());
        contentPanel.createPanel(IUserRank.DISCOUNT, "初始折扣率:", discount);
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnNew);
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        btnNew.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be)
			{
				if(isTextFieldValid())
				{
				    BeanObject memberLevel = getCurState().getMemberLevel();
	                String id = memberLevel != null ? memberLevel.getString(IUserRank.ID) : null;
	                memberLevel = new BeanObject(ModelNames.USERRANK, contentPanel.getValues());
	                if (getCurState().isEditting()) {
	                    new UpdateService().updateBean(id, memberLevel, null);
	                    Info.display("Success", "Complete to modify the member.");
                        MemberLevelList.State state = new MemberLevelList.State();
                        state.execute();
	                } else {
	                    new CreateService().createBean(memberLevel, new CreateService.Listener() {
	                        public void onSuccess(String id) {
	                            Info.display("Success", "Complete to add the member.");
	                            MemberLevelList.State state = new MemberLevelList.State();
	                            state.execute();
	                        }
	                    });
	                }
				}
				else
				{
					Info.display("Error", "The user rank info is not correct.");
				}
            
        }});

        btnCancel.addListener(Events.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be)
			{
//                contentPanel.clearValues();
				name.setValue("");
				maxPionts.setValue("");
				minPionts.setValue("");
				discount.setValue("");
            }            
        });        
    }  
    
    public boolean isTextFieldValid()
    {
    	return maxPionts.isValid() && minPionts.isValid() && discount.isValid();
    }
    
    public void setValidateReg()
    {
    	minPionts.setRegex(RegularExConstants.NATURALNO);
    	minPionts.getMessages().setRegexText(RegularExConstants.NATURALNOREGMSG);
    	
    	maxPionts.setRegex(RegularExConstants.NATURALNO);
    	maxPionts.getMessages().setRegexText(RegularExConstants.NATURALNOREGMSG);
    	
//    	maxPionts.setValidator(new Validator<String, TextField<String>>(){
//
//			public String validate(TextField<String> field, String value) 
//			{
//				 if (maxPionts.getRegex() != null && !value.matches(maxPionts.getRegex()))
//				 {
//					 maxPionts.markInvalid(maxPionts.getMessages().getRegexText());
//				      
//					 return maxPionts.getMessages().getRegexText();
//				 }
//				 
//				 if(!(Integer.parseInt(value) > Integer.parseInt(minPionts.getValue())))
//				 {
//					 return "MaxPoints has to be greater than minpoints";
//				 }
//					 return null;
//			}
//    	});
    	
    	discount.setRegex(RegularExConstants.NATURALNO);
    	discount.getMessages().setRegexText(RegularExConstants.NATURALNOREGMSG);
    	
    }
    
    public void refresh() {
        BeanObject memberLevel = getCurState().getMemberLevel();
      if (memberLevel!=null&& memberLevel.getString(IUserRank.ID) != null)
      { 
    	  Map<String, Object> mapMemberLevel = memberLevel.getProperties();
    	  
    	  name.setValue(mapMemberLevel.get(IUserRank.NAME).toString());
    	  maxPionts.setValue(mapMemberLevel.get(IUserRank.MAXPOINTS).toString());
    	  minPionts.setValue(mapMemberLevel.get(IUserRank.MINPOINTS).toString());
    	  discount.setValue(mapMemberLevel.get(IUserRank.DISCOUNT).toString());
    	  
      }
      else
      {
    	  name.setValue("");
    	  maxPionts.setValue("");
    	  minPionts.setValue("");
    	  discount.setValue("");
    	  
    	  getCurState().setEditting(false);
      }
  }
    
//    public void refresh() {
//        if (this.memberLevel!=null&&this.memberLevel.getString(IUserRank.ID) != null) {            
//            Map<String, Object> mapMemberLevel = memberLevel.getProperties();
//            contentPanel.updateValues(mapMemberLevel);
//        }
//        else{
//          contentPanel.clearValues();
//          editting = false;
//          }
//    }
}
