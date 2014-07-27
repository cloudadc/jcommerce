package com.jcommerce.gwt.client.panels.goods;

import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.resources.Resources;

public class GoodsList extends GoodsListBase {
	public static class State extends PageState {
        public String getPageClassName() {
			return GoodsList.class.getName();
		}
		
        public String getMenuDisplayName() {
			return Resources.constants.GoodsList_title();
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public GoodsList() {
	    super();
        virtualCard = false;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
	    return Resources.constants.GoodsList_title();
	}
}
