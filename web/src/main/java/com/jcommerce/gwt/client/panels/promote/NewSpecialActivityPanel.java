package com.jcommerce.gwt.client.panels.promote;

import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;

public class NewSpecialActivityPanel extends ContentWidget {

	public static class State extends PageState {
		public String getPageClassName() {
			return NewSpecialActivityPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "专题管理 ";
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
        return "专题列表";
    }
    
    protected void buttonClicked() {
    	SpecialActivityListPanel.State state = new SpecialActivityListPanel.State();
    	state.execute();
    }

}
