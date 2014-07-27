package com.jcommerce.gwt.client.panels.order;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.service.OrderService;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

/**
 * Example file.
 */
public class PrintTemplatePanel extends ContentWidget {    
	
	public static class State extends PageState {
		public String getPageClassName() {
			return PrintTemplatePanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "打印模版";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

    /**
     * Initialize this example.
     */
    public static PrintTemplatePanel getInstance() {
    	if(instance==null) {
    		instance = new PrintTemplatePanel();
    	}
    	return instance;
    }
    private static PrintTemplatePanel instance;
    
    private PrintTemplatePanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
		return "打印模版";
    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        final RichTextArea area = new RichTextArea();
        area.setSize("1000", "600");
        new OrderService().getOrderTemplate(new OrderService.Listener() {
            public void onSuccess(String content) {
                area.setHTML(content);
            }
        });
        RichTextToolbar toolbar = new RichTextToolbar(area);
//        toolbar.setWidth("100%");

        // Add the components to a panel
        Grid grid = new Grid(2, 1);
        grid.setStyleName("cw-RichText");
        grid.setWidget(0, 0, toolbar);
        grid.setWidget(1, 0, area);
//        grid.setSize("100%", "100%");
        add(grid);
        
        Button button = new Button("Save");
        add(button);
    }
}
