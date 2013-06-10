package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.Utils;
import com.jcommerce.gwt.client.validator.IValidator;

public class ColumnPanel extends LayoutContainer {
	 public FlexTable getTable() {
			return table;
		}

		public void setTable(FlexTable table) {
			this.table = table;
		}
    private Map<String, Widget> widgets;
    private Map<String, IValidator> validators = new HashMap<String, IValidator>();
    private int row;
    FlexTable table;
    
    public ColumnPanel() {
        super();
        init();
    }

    public ColumnPanel(Layout layout) {
        super(layout);
        init();
    }

    private void init() {
        widgets = new HashMap<String, Widget>();
        table = new FlexTable();
        table.setCellSpacing(6);        
        add(table);
        row = 0;
    }
    
    public void createPanel(String name, String label, Widget ctl, IValidator validator) {
        boolean allowEmpty = validator != null && validator.allowEmpty();
        createPanel(name, label, ctl, allowEmpty);
        if (validator != null && name != null && name.trim().length() > 0) {
            validators.put(name, validator);            
        }        
    }

    public void createPanel(String name, String label, Widget ctl) {
        createPanel(name, label, ctl, true);
    }
    
    public void createPanel(WidgetInfo info) {
        String name = info.getName();
        String label = info.getLabel();
        Widget ctl = info.getWidget();
        boolean allowEmpty = info.isAllowEmpty();
        
        if (label != null) {
            table.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
            table.setText(row, 0, label);            
        }        
        HorizontalPanel p = new HorizontalPanel();
        p.add(ctl);
        if (!allowEmpty) {
            p.add(new HTML("<font color=\"red\">*</font>"));
        }
        if (info.getAppendWidget() != null) {
            p.add(info.getAppendWidget());
        }
        
        String note = info.getNote();
        if (note != null) {
            if (info.isAppendNote() || ctl instanceof CheckBox) {
                p.add(new Label(note));
            }            
        }
        table.setWidget(row, 1, p);
        row++;
        if (note != null) {
            if (info.isAppendNote() || ctl instanceof CheckBox) {
            } else {
                table.setWidget(row, 1, new Label(note));
                row++;
            }
        }
        
        if (name != null && name.trim().length() > 0) {
            widgets.put(name, ctl);
        }        
    }
    
    public void createPanel(String name, String label, Widget ctl, boolean allowEmpty) {
        if (label != null) {
        	table.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
            table.setText(row, 0, label);            
        }        
        if (allowEmpty) {
            table.setWidget(row, 1, ctl);
        } else {
            HorizontalPanel p = new HorizontalPanel();
            p.add(ctl);
            p.add(new HTML("<font color=\"red\">*</font>"));
            table.setWidget(row, 1, p);
        }
        row++;
        if (name != null && name.trim().length() > 0) {
            widgets.put(name, ctl);
        }
    }
    
    public void createPanel(String name, String label, Widget ctl, Widget ct2) {
    	if (label != null) {
        	table.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
            table.setText(row, 0, label);            
        }
        HorizontalPanel p = new HorizontalPanel();
        p.add(ctl);
        p.add(ct2);
        table.setWidget(row, 1, p);
        row++;
        if (name != null && name.trim().length() > 0) {
            widgets.put(name, ctl);
        }
    }
    
    public void clearValues() {
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            setValue(name, null);
        }
    }
    
    // by leon: a true reset
    public void reset() {
        remove(table);
        init();
    }
    
    public void resetTable() {
        for(int i=0;i<table.getRowCount();i++) {
            table.removeRow(i);
        }
        row=0;
        widgets = new HashMap<String, Widget>();
    }

    
    public void updateValues(Map<String, Object> values) {
    	//System.out.println("----------updateValues("+values);
        if (values == null) {
            throw new IllegalArgumentException("values == null");
        }
        
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();            
            Object value = values.get(name);
            setValue(name, value);
        }
    }
    
    public List<String> validate() {
        List<String> errs = new ArrayList<String>();
        for (Iterator<String> it = validators.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            IValidator validator = validators.get(name);
            String err = validator.validate(getValue(name));
            if (err != null && err.trim().length() > 0) {
                errs.add(err);
            }
        }
        return errs;
        
    }
    
    public Map<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<String, Object>();
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            values.put(name, getValue(name));
        }
        //System.out.println("values:"+values);
        return values;
    }
    
    public Object getValue(String widgetName) {
        Widget widget = widgets.get(widgetName);
        if (widget instanceof NumberField) {
            return ((NumberField)widget).getValue();
        } else if (widget instanceof TextField) {
            return ((TextField)widget).getRawValue();
        } else if (widget instanceof TextBoxBase) {
            return ((TextBoxBase)widget).getText();
        } else if (widget instanceof ListBox) {
            return Utils.getSelectedValue((ListBox)widget);
        } else if (widget instanceof CheckBox) {
            return ((CheckBox)widget).isChecked();
        } else if (widget instanceof DateWidget) {
            return ((DateWidget)widget).getValue().getTime();
        } else if (widget instanceof ValueSelector) {
            return ((ValueSelector)widget).getValue();
        } else if (widget instanceof ChoicePanel){
        	return ((ChoicePanel)widget).getValue();
        } else if (widget instanceof Hidden){
            return ((Hidden)widget).getValue();            
        } else if (widget instanceof MultiValueSelector) {
            return ((MultiValueSelector)widget).getValue();
        } else if (widget instanceof FileUploader) {
            return ((FileUploader)widget).getValue();
        } else if (widget instanceof RadioButton) {
            return ((RadioButton)widget).getText();
        } else if (widget instanceof RadioPanel) {
            return ((RadioPanel)widget).getValue();
//        } else if(widget instanceof DatePicker){
//        	return ((DatePicker)widget).getValue();
        } else if (widget == null) {
            System.out.println("Widget not found: "+widgetName);
        } else {
            throw new RuntimeException("Unknown widget: "+widget.getClass().getName());
        }
        
        return null;
    }
    
    public void setValue(String widgetName, Object value) {
        Widget widget = widgets.get(widgetName);        
        if (widget instanceof TextBoxBase) {
            ((TextBoxBase)widget).setText(value == null ? "" : value+"");
        } else if (widget instanceof ListBox) {
            Utils.setSelectedValue((ListBox)widget, value+"");
        } else if (widget instanceof TextField) {
        	((TextField)widget).setValue(value == null ? "" : value+"");
        } else if (widget instanceof RadioButton) {
        	((RadioButton)widget).setText(value == null ? "" : value+"");
        } else if (widget instanceof CheckBox) {
            ((CheckBox)widget).setChecked(value == null ? Boolean.FALSE : (Boolean)value);
        } else if (widget instanceof DateWidget) {        	
            ((DateWidget)widget).setValue(value == null ? new java.util.Date():new java.util.Date((Long)value));
        } else if (widget instanceof ValueSelector) {
            ((ValueSelector)widget).setValue(value == null ? "" : value+"");
        } else if (widget instanceof ChoicePanel){
        	((ChoicePanel)widget).setSelectValue(String.valueOf(value));  
        } else if (widget instanceof Hidden){
            ((Hidden)widget).setValue(value==null?"":value+"");            
        } else if (widget instanceof MultiValueSelector) {
            ((MultiValueSelector)widget).setValue(value == null ? "" : value+"");
        } else if (widget instanceof FileUploader) {
            ((FileUploader)widget).setValue(value+"");
        } else if (widget instanceof Label){
        	((Label)widget).setText(String.valueOf(value));
        }else if(widget instanceof TextArea){
        	((TextArea)widget).setValue(value == null ? "" : value+"");
        }else if(widget instanceof RadioPanel){
            if (value != null) {
                ((RadioPanel)widget).setValue((Integer)value);
            }
//        }else if(widget instanceof DatePicker){
//        	((DatePicker)widget).setValue(value == null ? "" : value+"");
//        } else if (widget instanceof DecoratedTabPanel) {
        }else if (widget == null) {
            System.out.println("Widget not found: "+widgetName);
        } else {
            throw new RuntimeException("Unknown widget: "+widget.getClass().getName());
        }
    }
}
