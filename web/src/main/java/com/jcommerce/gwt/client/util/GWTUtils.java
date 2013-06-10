/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.util;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.IDefaultService;
import com.jcommerce.gwt.client.IDefaultServiceAsync;
import com.jcommerce.gwt.client.service.RemoteService;

public class GWTUtils {
    public static String getSelectedValue(ListBox list) {
        int i = list.getSelectedIndex();
        if (i < 0) {
            return null;
        }
        return list.getValue(i);
    }
    
    public static void setSelectedValue(ListBox list, String value) {
        if (value == null) {
            value = "";
        }        
        int size = list.getItemCount();
        for (int i = 0 ; i < size ; i++) {
            if (value.equals(list.getValue(i))) {
                list.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public static String getSelectedText(ListBox list) {
        int i = list.getSelectedIndex();
        if (i < 0) {
            return null;
        }
        return list.getItemText(i);
    }
    
    public static void setSelectedText(ListBox list, String text) {
        if (text == null) {
            return;
        }
        
        int size = list.getItemCount();
        for (int i = 0 ; i < size ; i++) {
            if (text.equals(list.getItemText(i))) {
                list.setSelectedIndex(i);
                break;
            }
        }
    }
    

    public static void showErrorDialog(String err) {
        DialogBox dlg = createDialogBox("ERROR", err);
        dlg.show();
    }
    
    public static void showWarningDialog(String msg) {
        DialogBox dlg = createDialogBox("WARNING", msg);
        dlg.show();
    }
    
    public static void showMessageDialog(String msg) {
        DialogBox dlg = createDialogBox("INFO", msg);
        dlg.show();
    }
    
    private static DialogBox createDialogBox(String caption, String message) {
        // Create a dialog box and set the caption text
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);

        // Create a table to layout the content
        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);

        // Add some text to the top of the dialog
        HTML details = new HTML(message);
        dialogContents.add(details);

        // Add a close button at the bottom of the dialog
        Button btnOK = new Button("OK", new ClickListener() {
            public void onClick(Widget sender) {
                dialogBox.hide();
            }
        });
        dialogContents.add(btnOK);
        dialogContents.setCellHorizontalAlignment(btnOK, HasHorizontalAlignment.ALIGN_CENTER);
        dialogBox.center();
        
        return dialogBox;
    }

    public static DialogBox getPromptBox(String caption, String message) {
        // Create a dialog box and set the caption text
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);

        // Create a table to layout the content
        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);

        // Add some text to the top of the dialog
        HTML details = new HTML(message);
        dialogContents.add(details);

        dialogBox.center();
        
        return dialogBox;
    }
}
