package com.jcommerce.gwt.client.service;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IDeliveryMeta;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.service.ShippingInitService.MyProxy;

public class PaymentInitService  extends RemoteService {

	private void updateIdMap(final Map<String, String> idMap) {
		if(idMap != null) {
			idMap.clear();
		}

		//read Payments from DB.
		new ListService().listBeans(ModelNames.PAYMENT,
				new ListService.Listener() {
					@Override
					public void onSuccess(List<BeanObject> beans) {
						for (BeanObject beanObject : beans) {
							idMap.put(beanObject.getString(IPayment.NAME),
									beanObject.getString(IPayment.ID));
						}
					}
				});
	}

	public BasePagingLoader getLoader( Map<String, String> idMap)
	{
		return getLoader(idMap,null, null);
	}

	public BasePagingLoader getLoader(final Map<String, String> idMap, final Criteria criteria,
			final List<String> wantedFields)
	{
		final IShopServiceAsync service = getService();
		MyProxy proxy = new MyProxy() {
			@Override
			protected void load(Object config, AsyncCallback callback) {
				service.getAllPaymentMethods((PagingLoadConfig) config, callback);
			}
		};
		proxy.setCriteria(criteria);
		
		BasePagingLoader loader = new BasePagingLoader(proxy);
		loader.setRemoteSort(true);

		
		updateIdMap(idMap);
		return loader;
	}

	public abstract class MyProxy extends RpcProxy {
		Criteria criteria = null;

		public void setCriteria(Criteria criteria) {
			this.criteria = criteria;
		}
	}
}