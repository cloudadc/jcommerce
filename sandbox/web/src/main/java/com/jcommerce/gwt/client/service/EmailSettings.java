package com.jcommerce.gwt.client.service;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

/**
 * @author monkey
 */
public class EmailSettings extends RemoteService{

	/**
	 * 将服务器端配置信息存放在map中，客户端可以将值取出用于初始化服务器配置。
	 * @param listener
	 * @return 配置信息
	 * key : 各种配置信息
	 * value : 配置信息的值
	 */
	public void getSettingsInfo(final Listener listener) {
		final IShopServiceAsync service = getService();
		
		service.getEmailServerSettings(
			new AsyncCallback<HashMap<String, String>>() {
	
				public void onSuccess(HashMap<String, String> result) {					
					if (listener != null) {
	                    listener.onSuccess(result);
	                }
				}

				public void onFailure(Throwable caught) {					
					System.out.println("getEmailSettings onFailure(" + caught);
					if (listener != null) {
	                    listener.onFailure(caught);
	                }
				}
			});
	}
	
	/**
	 * 当用户配置完email服务器的信息之后，调用save方法，将信息保存。
	 * @param info
	 * @key : 配置信息
	 * @value : 用户配置的值
	 * @return save state
	 */
	public void saveSettingsInfo(HashMap<String, String> info, final Listener listener) {
		final IShopServiceAsync service = getService();
		service.setEmailServerSettings(info, new AsyncCallback<Boolean>() {

				public void onFailure(Throwable caught) {					
					System.out.println("saveEmailSettings onFailure(" + caught);
					if (listener != null) {
	                    listener.onFailure(caught);
	                }
				}

				public void onSuccess(Boolean result) {
					listener.onSuccess(result);
				}
			});
		
	}
	
	public void sendTestEmailAndGetState( final Listener listener ) {
		final IShopServiceAsync service = getService();
		service.sendTestEmailAndGetState(new AsyncCallback<Boolean>() {

				public void onFailure(Throwable caught) {					
					System.out.println("sendTestEmailAndGetState onFailure(" + caught);
					if (listener != null) {
	                    listener.onFailure(caught);
	                }
				}

				public void onSuccess(Boolean result) {
					listener.onSuccess(result);
				}
			});
		
	}
	
	public void sendEmailAndGetState( HashMap<String, String> email, final Listener listener ) {
		final IShopServiceAsync service = getService();
		service.sendEmailAndGetState(email, new AsyncCallback<Boolean> (){

			public void onFailure(Throwable caught) {
				System.out.println("email receiver onFailure(" + caught);
				if (listener != null) {
                    listener.onFailure(caught);
                }
			}

			public void onSuccess(Boolean result) {
				listener.onSuccess(result);
			}
		});
	}
	
	public void receiveNewMail ( final Listener listener ) {
		final IShopServiceAsync service = getService();
		service.receiveNewEmail(new AsyncCallback<Boolean> (){

			public void onFailure(Throwable caught) {
				System.out.println("email receiver onFailure(" + caught);
				if (listener != null) {
                    listener.onFailure(caught);
                }
			}

			public void onSuccess(Boolean result) {
				listener.onSuccess(result);
			}
		});
	}
	
	public static abstract class Listener {
        
        public void onSuccess(HashMap<String, String> result) {
			
		}

        public void onSuccess(Boolean result) {
			
		}

		public void onFailure(Throwable caught) {
        	
        }
    }
}
