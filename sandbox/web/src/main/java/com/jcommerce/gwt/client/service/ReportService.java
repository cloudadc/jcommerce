package com.jcommerce.gwt.client.service;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class ReportService extends RemoteService {

    public void generateReport(String name, Map<String, String> params, final Listener listener) {
        final IShopServiceAsync service = getService();

        service.generateReport(name, params, new AsyncCallback<String>() {

            public void onSuccess(String content) {
                listener.onSuccess(GWT.getModuleBaseURL() + content);
            }

            public void onFailure(Throwable caught) {
                listener.onFailure(caught);
            }
        });
    }

    public static abstract class Listener {
        public abstract void onSuccess(String path);

        public void onFailure(Throwable caught) {
        }
    }
}
