package com.jcommerce.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class DatabaseService extends RemoteService {

    public void Backup(String backUpFileName, final Listener listener) {

        final IShopServiceAsync service = getService();

        service.Backup(backUpFileName, new AsyncCallback<String>() {

            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }

            public void onSuccess(String result) {
                // TODO Auto-generated method stub
                if (listener != null) {
                    listener.onSuccess(result);

                }
            }
        });
    }

    public void Initialize(final Listener InitListener) {
        final IShopServiceAsync service = getService();

        service.initialize((new AsyncCallback<String>() {

            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                if (InitListener != null) {
                    InitListener.onFailure(caught);
                }
            }

            public void onSuccess(String result) {
                // TODO Auto-generated method stub
                if (InitListener != null) {
                    InitListener.onSuccess(result);

                }
            }
        }));
    }

    public static abstract class Listener {

        public abstract void onSuccess(String result);

        public void onFailure(Throwable caught) {
        }
    }

}
