package com.jcommerce.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;



public class GoodsBatchUploadService extends RemoteService{
	public void getBeans(String modelName, String path, String category,
			String encoding, final Listener listener) {
	
		IShopServiceAsync service = getService();
		service.getBeansFromFile(modelName, path, category, encoding,
				new AsyncCallback<List<BeanObject>>() {
					public synchronized void onSuccess(List<BeanObject> objs) {
						if (listener != null) {
							listener.onSuccess(objs);
						}
					}

					public synchronized void onFailure(Throwable caught) {
						if (listener != null) {
							listener.onFailure(caught);
						}
					}
				});
	}
	public static abstract class Listener {
		public abstract void onSuccess(List<BeanObject> objs);

		public void onFailure(Throwable caught) {
		}
	}
}
