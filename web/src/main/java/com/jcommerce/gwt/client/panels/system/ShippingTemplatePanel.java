package com.jcommerce.gwt.client.panels.system;

import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.service.RemoteService;

public class ShippingTemplatePanel extends BaseEntityEditPanel {

	private static ShippingTemplatePanel instance;

	public static ShippingTemplatePanel getInstance() {
		if (instance == null) {
			instance = new ShippingTemplatePanel();
		}
		return instance;
	}

	private ShippingTemplatePanel() {
	}

	public static class State extends BaseEntityEditPanel.State {
		public String getPageClassName() {
			return ShippingTemplatePanel.class.getName();
		}
	}

	@Override
	protected State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	TextArea fTemplate;
	
	
	@Override
	protected String getEntityClassName() {
		return ModelNames.SHIPPING;
	}

	@Override
	public void gotoSuccessPanel() {
        Success.State newState = new Success.State();
        newState.setMessage("快递模板已经成功编辑");
    
        ShippingMetaListPanel.State listState = new ShippingMetaListPanel.State();
        newState.addChoice("配送方式列表", listState);
    
        newState.execute();

	}

	@Override
	protected void postSuperRefresh() {
	}
	
	
    @Override
    public void retrieveEntity() {
        System.out.println("----- refresh Shipping Template---");
        State state = getCurState();
        String id = state.getId();
        System.out.println("id: "+id);
        
        RemoteService.getSpecialService().getShippingConfigMeta(id, new AsyncCallback<ShippingConfigMetaForm>() {
            public void onFailure(Throwable caught) {
                System.out.println("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(ShippingConfigMetaForm result) {
                System.out.println("result: \n");
                System.out.println(result.toString());
                
                obj = result;
//                fTemplate.setValue(result.getShippingPrint());
//                fTemplate.setOriginalValue(result.getShippingPrint());
            }
             
        });
        
        System.out.println("----- finish PaymentMeta---");
    }
    
    @Override
    protected void submit() {
		// default implementation is thru GWT-RPC
    	ShippingConfigMetaForm form = (ShippingConfigMetaForm)obj;
//    	form.setShippingPrint(fTemplate.getValue());
    	
        RemoteService.getSpecialService().saveShipping(form, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("save failure: "+caught.getMessage());
            }
            public void onSuccess(Boolean result) {
                System.out.println("onSuccess: "+result.toString());
                if(result) {
                	gotoSuccessPanel();
                }
                else {
                    Info.display("oops", "保存结果失败！！！");
                }
               
            }
            
        });
    	
    }
	@Override
	protected void setupPanelLayout() {
		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());
		FormData formData = new FormData("95%");

		LayoutContainer left = new LayoutContainer();
		left.setStyleAttribute("paddingRight", "10px");
		FormLayout layout = new FormLayout();
		layout.setLabelAlign(LabelAlign.TOP);
		left.setLayout(layout);
		fTemplate = new TextArea();
//		first.setHideLabel(true);
		fTemplate.setFieldLabel("快递单模板");
		fTemplate.setHeight(400);
		left.add(fTemplate, formData);

		// VerticalPanel right = new VerticalPanel();
//		LayoutContainer right = new LayoutContainer();
//		right.setStyleAttribute("paddingRight", "10px");
//		layout = new FormLayout();
//		layout.setLabelAlign(LabelAlign.TOP);
//		right.setLayout(layout);
//		TextArea last = new TextArea();
//		last.setHideLabel(true);
//		last.setValue("abc\r\ndef");
//		last.setEnabled(false);
//		right.add(last, formData);
		
//		ContentPanel right = new ContentPanel();
//		right.setHeaderVisible(false);
		HtmlContainer right = new HtmlContainer();
		right.setHtml("订单模板变量说明:<br>"+
				"{$shop_name}表示网店名称<br>"+
				"{$province}表示网店所属省份<br>"+
				"{$city}表示网店所属城市<br>"
				);


		main.add(left, new ColumnData(.7));
		main.add(right, new ColumnData(.3));

		formPanel.add(main);

	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "editing template";
	}

}
