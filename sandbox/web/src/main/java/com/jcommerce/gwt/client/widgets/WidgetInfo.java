/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.widgets;

import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.validator.IValidator;

public class WidgetInfo {
    private Widget widget;
    private Widget appendWidget;
    private String name;
    private String label;
    private String note;
    // if true, the note will apear at the tail, otherwise at tne next line
    private boolean appendNote = false;
    private boolean allowEmpty = true;
    private IValidator validator;

    public WidgetInfo() {
    }
    
    public WidgetInfo(String name, String label, Widget widget) {
        this.name = name;
        this.label = label;
        this.widget = widget;
    }
    
    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Widget getAppendWidget() {
        return appendWidget;
    }

    public void setAppendWidget(Widget appendWidget) {
        this.appendWidget = appendWidget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isAppendNote() {
        return appendNote;
    }

    public void setAppendNote(boolean appendNote) {
        this.appendNote = appendNote;
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public IValidator getValidator() {
        return validator;
    }

    public void setValidator(IValidator validator) {
        this.validator = validator;
        if (validator != null) {
            allowEmpty = validator.allowEmpty();
        }
    }
}
