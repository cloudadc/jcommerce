/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class BeanCellRenderer implements GridCellRenderer<BeanObject> {
    String modelName;
    String propName;
    GridView view;
    
    public BeanCellRenderer(String modelName, String propName, Grid grid) {
        this.modelName = modelName;
        this.propName = propName;
        this.view = grid.getView();
    }
    
    public Object render(BeanObject model, String property, ColumnData config, final int rowIndex, final int colIndex,
            ListStore<BeanObject> store, Grid<BeanObject> grid) {
//    public String render(BeanObject model, String property, ColumnData config,
//            final int rowIndex, final int colIndex, ListStore<BeanObject> store) {
        final IShopServiceAsync service = (IShopServiceAsync)Registry.get("service");
        
        String id = (String) model.get(property);
        service.getBean(modelName, id, new AsyncCallback<BeanObject>() {
            public synchronized void onSuccess(BeanObject props) {
                String value = props.get(propName);
                Element el = view.getCell(rowIndex, colIndex);
                String text = el.getInnerHTML();
                if (text.indexOf(">") > 0 && text.indexOf("</") > 0) {
                    text = text.substring(0, text.indexOf(">") + 1) + value + text.substring(text.indexOf("</"));
                }
                el.setInnerHTML(text);
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("getBean onFailure("+caught);
            }
        });
        
        return "waiting";
    }
}
