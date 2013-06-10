package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class OrderService extends RemoteService {

    public void getOrderTemplate(final Listener listener) {
        final IShopServiceAsync service = getService();

        service.getOrderTemplate(new AsyncCallback<String>() {

            public void onSuccess(String content) {
                listener.onSuccess(content);
            }

            public void onFailure(Throwable caught) {
                listener.onFailure(caught);
            }
        });
    }

    public static abstract class Listener {
        public abstract void onSuccess(String content);

        public void onFailure(Throwable caught) {
        }
    }
}
