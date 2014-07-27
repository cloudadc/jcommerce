/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jcommerce.gwt.client.panels.goods;

import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;

public class NewVirtualCard extends NewGoodsBase {
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
			return NewVirtualCard.class.getName();
		}

		public String getMenuDisplayName() {
			return "添加新虚拟卡";
		}
	}

	public State getCurState() {
		return (State)curState;
	}

	/**
	 * Initialize this example.
	 */
	public NewVirtualCard() {
	    curState = new State();
	    virtualCard = true;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "添加新虚拟卡";
	}

    @Override
    BeanObject getGoods() {
        return getCurState().getGoods();
    }
}
