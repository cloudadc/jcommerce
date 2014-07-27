/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.goods;

import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.resources.Resources;

public class NewGoods extends NewGoodsBase {
	public static class State extends PageState {
	    private BeanObject goods;
	    
		public BeanObject getGoods() {
            return goods;
        }

        public void setGoods(BeanObject goods) {
            this.goods = goods;
            setEditting(goods != null);
        }

        public String getPageClassName() {
			return NewGoods.class.getName();
		}

		public String getMenuDisplayName() {
			return Resources.constants.NewGoods_title();
		}
	}

	public State getCurState() {
		return (State)curState;
	}

	/**
	 * Initialize this example.
	 */
	public NewGoods() {
	    curState = new State();
	    virtualCard = false;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		if (!getCurState().isEditting())
			return Resources.constants.NewGoods_title();
		else
			return Resources.constants.EditGoods_title();
	}

    @Override
    BeanObject getGoods() {
        return getCurState().getGoods();
    }
}
