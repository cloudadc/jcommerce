package com.jcommerce.gwt.client.panels.statistics;

import java.util.HashMap;

import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.ReportService;

public class GuestStatisticsReport extends ContentWidget {

	HtmlContainer html;

	public static class State extends PageState {
		public String getPageClassName() {
			return GuestStatisticsReport.class.getName();
		}

		public String getMenuDisplayName() {
			return "客户统计";
		}
	}

	public State getCurState() {
		if (curState == null) {
			curState = new State();
		}
		return (State) curState;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "客户统计";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		html = new HtmlContainer();
		html.setHeight("100%");
		html.setWidth("100%");

		add(html);

		new ReportService().generateReport("GetUserNum", new HashMap(),
				new ReportService.Listener() {
					@Override
					public void onSuccess(String content) {
                    	content = content.substring(content.indexOf("<html>"));
                        html.setHtml(content);
					}
				});

	}
}
