package com.jcommerce.gwt.client.util;

import com.extjs.gxt.ui.client.data.RpcProxy;
import com.jcommerce.gwt.client.service.Criteria;

public abstract class MyRpcProxy<D> extends RpcProxy<D> {
	private Criteria criteria = null;

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public Criteria getCriteria() {
		return this.criteria;
	}
}
