package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class GoodsService extends RemoteService {

    public void purgeGoods(Long id, final Listener listener) {
        final IShopServiceAsync service = getService();

        service.purgeGoods(id, new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean status) {
                listener.onSuccess(status);
            }

            public void onFailure(Throwable caught) {
                listener.onFailure(caught);
            }
        });
    }

    public void undoDeletedGoods(Long id, final Listener listener) {
        final IShopServiceAsync service = getService();

        service.undoDeletedGoods(id, new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean status) {
                listener.onSuccess(status);
            }

            public void onFailure(Throwable caught) {
                listener.onFailure(caught);
            }
        });
    }

    public static abstract class Listener {
        public abstract void onSuccess(Boolean status);

        public void onFailure(Throwable caught) {
        }
    }
}
