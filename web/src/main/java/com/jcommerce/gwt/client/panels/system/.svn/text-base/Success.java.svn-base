package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
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
		List<PageState> choices = new ArrayList<PageState>();
        List<String> labels = new ArrayList<String>();
		
		public String getPageClassName() {
			return Success.class.getName();
		}
		
        public void addChoice(String label, PageState choice) {
            labels.add(label);
            choices.add(choice);
        }

//        public void addChoice(String name, String historyToken) {
//			List<String> choices = (List<String>)getValue(CHOICES);
//			if(choices == null) {
//				choices = new ArrayList<String>();
//				setValue(CHOICES, choices);
//			}
//			choices.add(name+SEPERATOR+historyToken);
//		}
//		
        public List<PageState> getChoices() {
            return choices;
        }
                
        public List<String> getLabels() {
            return labels;
        }

        //		public List<String[]> getChoices() {
//			List<String> choices = (List<String>)getValue(CHOICES);
//			List<String[]> res = new ArrayList<String[]>();
//			for(String choice:choices) {
//				int i = choice.indexOf(SEPERATOR);
//				String[] ss = new String[] {choice.substring(0, i), choice.substring(i+SEPERATOR.length())};
//				res.add(ss);
//			}
//			return res;
//		}
//		
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
        add(contentPanel);
        contentPanel.add(table);
        
//        refresh();
    }
    
    public void refresh() {
    	table.clear();
    	int i=0;
        table.setText(i++, 0, getCurState().getMessage());
        table.setText(i++, 0, "");
        
        int index = 0;
        for(final PageState choice : getCurState().getChoices()) {
        	table.setWidget(i++, 0, new Button(getCurState().getLabels().get(index++), new SelectionListener<ButtonEvent>() {
                public void componentSelected(ButtonEvent ce) {
                    choice.execute();
                }
        	}));
        }    	
    }
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
}
