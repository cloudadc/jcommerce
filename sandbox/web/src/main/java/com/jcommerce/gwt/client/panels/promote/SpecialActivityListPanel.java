package com.jcommerce.gwt.client.panels.promote;

import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;

public class SpecialActivityListPanel extends ContentWidget {

	public static class State extends PageState {
		public String getPageClassName() {
			return SpecialActivityListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "专题管理  ";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "专题管理 ";
    }
    
    public String getButtonText() {
        return "添加专题";
    }
    
    protected void buttonClicked() {
    	NewSpecialActivityPanel.State state = new NewSpecialActivityPanel.State();
    	state.execute();
    }
}
