package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer.ActionInfo;


/**
 * 对Boolean值的数据生成链接处理方法
 * @author Daniel
 *
 */
public class BooleanPropertyActionCellRendere extends ActionCellRenderer
											  implements GridCellRenderer<BeanObject> 
											  
{
	private String modelName;
	private String propName;
	private GridView view;
    private String isFaliureStr;
    private String isTruthStr;
    
	public BooleanPropertyActionCellRendere(String modelName, String propName,
			Grid view, String isFaliureStr, String isTruthStr) {
		super(view);
		this.modelName = modelName;
		this.propName = propName;
		this.view = view.getView();
		this.isFaliureStr = isFaliureStr;
		this.isTruthStr = isTruthStr;
	}
	
	public String render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, final ListStore<BeanObject> store) {
		final String id = (String) model.get(property);
		final StringBuffer sb = new StringBuffer();
		ReadService reader = new ReadService();
		reader.getBean(ModelNames.GOODS, id, new ReadService.Listener()
		{
			public synchronized void onSuccess(BeanObject props)
			{
				Element el = view.getCell(rowIndex, colIndex);
				Boolean bool = false;
				if(props.get(propName).toString().equals("true"))
				{
					bool = true;
				}	
				
		        for (ActionInfo act : acts) {
		        	if(bool == true)
					{
		        		act.setText(isTruthStr);
					}
		        	else
		        	{
		        		act.setText(isFaliureStr);
		        	}
		        	
		            sb.append("<a href=\"");

		            String a = act.getAction();
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
		                sb.append(act.getText());
		            }
		            sb.append("</a>");
		            el.setInnerHTML(sb.toString());
		        }			
		        
			}

			public synchronized void onFailure(Throwable caught) {
				System.out.println("getBean onFailure("+caught);
			}
		});
		
		return "waiting";
	}
	
}
