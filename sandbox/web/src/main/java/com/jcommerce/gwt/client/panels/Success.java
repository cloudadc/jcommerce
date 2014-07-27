package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;

public class Success extends ContentWidget {
	
	private VerticalPanel contentPanel = new VerticalPanel();
	
	private static Success instance= null;
	private Success(){
	}
	public static Success getInstance() {
		if(instance == null) {
			instance = new Success();
		}
		return instance;
	}
	
	public static class State extends PageState {
		public static final String MESSAGE = "msg";
		public static final String CHOICES = "ch";
		public static final String SEPERATOR = "xxxxxx";
		
		public String getPageClassName() {
			return Success.class.getName();
		}
		
		public void addChoice(String name, String historyToken) {
			List<String> choices = (List<String>)getValue(CHOICES);
			if(choices == null) {
				choices = new ArrayList<String>();
				setValue(CHOICES, choices);
			}
			choices.add(name+SEPERATOR+historyToken);
		}
		public List<String[]> getChoices() {
			List<String> choices = (List<String>)getValue(CHOICES);
			List<String[]> res = new ArrayList<String[]>();
			for(String choice:choices) {
				int i = choice.indexOf(SEPERATOR);
				String[] ss = new String[] {choice.substring(0, i), choice.substring(i+SEPERATOR.length())};
				res.add(ss);
			}
			return res;
		}
		public void setMessage(String message) {
			setValue(MESSAGE, message);
		}
		public String getMessage() {
			return (String)getValue(MESSAGE);
		}

	}
	
	FlexTable table = new FlexTable();

	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "系统信息";
	}
	
	
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
        System.out.println("---------render Success");
        add(contentPanel);
        contentPanel.add(table);
        
//        refresh();
    }
    
    public void refresh() {
    	 System.out.println("---------refresh Success");
    	table.clear();
    	int i=0;
        table.setText(i++, 0, getCurState().getMessage());
        table.setText(i++, 0, "如果您不作出选择，将在3秒后跳到第一个链接");
        for(String[] choice:getCurState().getChoices()) {
        	table.setWidget(i++, 0, new Hyperlink("返回"+choice[0], choice[1]));
        }    	
    }
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
}
