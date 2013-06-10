/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;

public class ActionCellRenderer implements GridCellRenderer<BeanObject> {
    List<ActionInfo> acts = new ArrayList<ActionInfo>();
    GridView view;
    
    public ActionCellRenderer(Grid grid) {
        this.view = grid.getView();
    }

    public ActionCellRenderer() {
    }
    
    public void removeAllActions() {
        acts.clear();
    }
    
    public void addAction(ActionInfo act) {
        acts.add(act);
    }
    
    public Object render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex,
            ListStore<BeanObject> store, Grid<BeanObject> grid) {

        StringBuffer sb = new StringBuffer(); 
        for (ActionInfo act : acts) {
            sb.append("<a href=\"");

            String a = act.getAction();
            // deleteGoods($id)
            if (a != null && a.indexOf("$") >= 0) {
                int i = a.indexOf("$");
                int j = i + 1;
                while (j < a.length()) {
                    if (a.charAt(j) == ' ' || a.charAt(j) == ',' || a.charAt(j) == ')') {
                        break;
                    }                    
                    j++;
                }
                String name = a.substring(i+1, j); 
                Object value = store.getAt(rowIndex).get(name);
                a = a.substring(0, i) + "'"+ value + "'" + a.substring(j);
            }
            sb.append("javascript:" + a +";");
            sb.append("\"");
            String tip = act.getTooltip();
            if (tip != null && tip.trim().length() > 0) {
                sb.append(" title=\"").append(tip).append("\"");
            }
            sb.append(">");
            if (act.getImage() != null && act.getImage().trim().length() > 0) {
                sb.append("<img border=\"0\" src=\""+act.getImage()+"\">");
            }
            if (act.getText() != null && act.getText().trim().length() > 0) {
                sb.append(act.getText().trim());
            }
            sb.append("</a>&nbsp;");
        }
//        System.out.println("sb:"+sb);
        return sb.toString();
    }
    
    public static class ActionInfo {
        String image;
        String act;
        String text;
        String tooltip;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAction() {
            return act;
        }

        public void setAction(String act) {
            this.act = act;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTooltip() {
            return tooltip;
        }

        public void setTooltip(String tooltip) {
            this.tooltip = tooltip;
        }
    }

}
