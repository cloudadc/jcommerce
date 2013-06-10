/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.panels.goods;

import com.jcommerce.gwt.client.PageState;

public class VirtualCardList extends GoodsListBase {
    public static class State extends PageState {
        public String getPageClassName() {
            return VirtualCardList.class.getName();
        }
        public String getMenuDisplayName() {
            return "虚拟卡列表";
        }
    }
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }

    public VirtualCardList() {
        super();
        virtualCard = true;
    }

    public String getName() {
        return "虚拟卡列表";
    }
    
    public String getDescription() {
        return null;
    }
    
}
