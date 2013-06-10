package com.jcommerce.gwt.client.form;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Random;
import com.jcommerce.gwt.client.ClientHttpRequestHelper;

public class GWTHttpDynaForm {
	
	// either set action/method or set url
	private String action;
	private String method;
	private String url;
	
	private Map<String, String> params = new HashMap<String, String>();
	private Listener listener;
	

	public static String constructURL(String action, String method) {
//		String url = GWT.getModuleBaseURL() + "gwtHttpService.do?XXAction="+action+"&XXMethod="+method;
		String url = "/admin/gwtHttpService.do?XXAction="+action+"&XXMethod="+method;
		return url;
	}

	public void addParam(String name, String value) {
		params.put(name, value);
	}
	
	public void SetListener(Listener listener) {
		this.listener = listener;
	}
	public Listener getListener() {
		return this.listener;
	}
	public void submit() {
		if(url==null || "".equals(url)) {
			url = constructURL(getAction(), getMethod());
		}
    	RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
    	builder.setHeader("Content-type", "application/x-www-form-urlencoded");
    	StringBuffer dataBuf = new StringBuffer();
    	StringBuffer dataBufEncoded = new StringBuffer();
    	
    	ClientHttpRequestHelper helper = new ClientHttpRequestHelper();
    	for(String name:params.keySet()) {
    		dataBufEncoded.append(URL.encode(name)).append("=").append(URL.encode(params.get(name))).append("\r\n");
    		// TODO a proper encoding/decoding of request data
    		dataBuf.append(URL.encode(name)).append("=").append(params.get(name)).append("\r\n");
//    		helper.setParameter(name, params.get(name));
    	}
    	
//    	String data = helper.getRequestData();
    	String data = dataBuf.toString();
    	System.out.println("data: "+data);
    	
    	String encodedData = dataBufEncoded.toString();
    	System.out.println("encodedData: "+encodedData);
    	
    	try {
      	  Request request = builder.sendRequest(data, new RequestCallback() {
      	    public void onError(Request request, Throwable exception) {
      	    	getListener().onFailure(exception);
      	    }

      	    public void onResponseReceived(Request request, Response response) {
      	    	String result = response.getText();
      	    	int status = response.getStatusCode();
      	      if (200 == response.getStatusCode()) {
//      	    	  if("0".equals(text)) {
      	    	  if(result!=null && result.contains("200 OK")) {
      	    		  getListener().onSuccess(result);
      	    	  }
      	    	  else {
      	    		getListener().onFailure(new Throwable(result));
      	    	  }
      	      } else {
      	    	  getListener().onFailure(new Throwable(String.valueOf(status)));
      	      }
      	    }       
      	  });
      	} catch (RequestException e) {
      		getListener().onFailure(e);        
      	}
    	
	}

	

	
    public static abstract class Listener {
        public abstract void onSuccess(String response);
        
        public void onFailure(Throwable caught) {
        }
    }



	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
