package org.jboss.as.quickstarts.gwthelloworld.client.local;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class HelloWorldApp implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(new HelloWorldClient().getElement());
	}

}
