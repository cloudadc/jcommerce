package com.jcommerce.gwt.client.panels.system;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.SimpleOptionData;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.widgets.SimpleStaticComboBox;

public class PaymentMetaPanel extends BaseEntityEditPanel {

    private static PaymentMetaPanel instance;
    public static PaymentMetaPanel getInstance() {
        if(instance == null) {
            instance = new PaymentMetaPanel();
        }
        return instance;
    }
    private PaymentMetaPanel() {
    }
    public static class State extends BaseEntityEditPanel.State {
        public String getPageClassName() {
            return PaymentMetaPanel.class.getName();
        }
    }

    HiddenField<String> isonline;
    HiddenField<String> iscod;
    HiddenField<String> idhidden;
    HiddenField<String> code;
    HiddenField<String> order;
    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "编辑支付方式";
    }
    
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button("支付方式");
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		PaymentMetaListPanel.State newState = new PaymentMetaListPanel.State();
		newState.execute();
    }

    public void retrieveEntity() {
        System.out.println("----- refresh PaymentMeta---");
        State state = getCurState();
        String id = state.getId();
        System.out.println("id: "+id);
        
        RemoteService.getSpecialService().getPaymentConfigMeta(id, new AsyncCallback<PaymentConfigMetaForm>() {
            public void onFailure(Throwable caught) {
                System.out.println("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(PaymentConfigMetaForm result) {
                System.out.println("result: \n");
                System.out.println(result.toString());
                
                obj = result;
                generateDynaFields();
            }
             
        });
        
        System.out.println("----- finish PaymentMeta---");
    }
    
    protected void submit() {
		// default implementation is thru GWT-RPC
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
        RemoteService.getSpecialService().savePayment(props, new AsyncCallback<Boolean>() {
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
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }
    
    public void gotoSuccessPanel() {
        Success.State newState = new Success.State();
        newState.setMessage("编辑成功");
    
        PaymentMetaListPanel.State listState = new PaymentMetaListPanel.State();
        newState.addChoice("支付方式列表", listState);
    
        newState.execute();
    }
    
	protected String getEntityClassName() {
		return null;
	}

	protected void postSuperRefresh() {
	}
	
    public void refresh() {
    	try {
    		List<Field<?>> fields = formPanel.getFields();
    		for(Field<?> f:fields) {
    			formPanel.remove(f);
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	// should always be editing, so neglect state.getIsEdit() 
       	retrieveEntity();

    }

	private void generateDynaFields() {
		PaymentConfigMetaForm result = (PaymentConfigMetaForm)obj; 
			
        TextField<String> tb = new TextField<String>();
        tb.setFieldLabel("支付方式名称");
        tb.setName(PaymentConfigMetaForm.NAME);
        tb.setValue(result.getPayName());
        tb.setOriginalValue(result.getPayName());
        formPanel.add(tb);
        
        TextArea ta = new TextArea();
        ta.setName(PaymentConfigMetaForm.DESCRIPTION);
        ta.setFieldLabel("支付方式描述");
        ta.setHeight("180px");
        ta.setWidth("250px");
        ta.setValue(result.getDescription());
        ta.setOriginalValue(result.getDescription());
        formPanel.add(ta);
        
        Map<String, PaymentConfigFieldMetaForm> fieldMetas = result.getFieldMetas();
        Map<String, String> keyValues = result.getFieldValues();
        for(String key:fieldMetas.keySet()) {
            String value = keyValues.get(key);
            System.out.println("key: "+key+", value: "+value);
            
            PaymentConfigFieldMetaForm meta = fieldMetas.get(key);
            Object options = meta.getOptions();
            if(options==null) {
                TextField<String> textBox = new TextField<String>();
                textBox.setFieldLabel(meta.getLable());
                textBox.setName(key);
                textBox.setValue(value);
                textBox.setOriginalValue(value);
                formPanel.add(textBox);
            }
            else {
                Map<String, String> optionMap = (Map<String, String>)options;
                SimpleStaticComboBox<BeanObject> list = new SimpleStaticComboBox<BeanObject>();
                list.setName(key);
                list.setFieldLabel(meta.getLable());
                ListStore<BeanObject> choiceStore = new ListStore<BeanObject>();
                for(String option:optionMap.keySet()) {
                    String label = optionMap.get(option);
                    choiceStore.add(new SimpleOptionData(label, option));
                }
                list.setStore(choiceStore);
                formPanel.add(list);
            }
        }

        tb = new TextField<String>();
        tb.setFieldLabel("支付手续费");
        tb.setName(PaymentConfigMetaForm.FEE);
        tb.setValue(result.getPayFee());
        tb.setOriginalValue(result.getPayFee());
        formPanel.add(tb);
        
        
        LabelField lb = new LabelField();
        lb.setFieldLabel("货到付款？");
        if(result.isCod()){
            lb.setText("是");
        }else {
            lb.setText("否");
        }
        formPanel.add(lb);
        
        lb = new LabelField();
        lb.setFieldLabel("在线支付？");
        if(result.isOnline()){
            lb.setText("是");
        }else {
            lb.setText("否");
        }
        formPanel.add(lb);
        

        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);

        formPanel.add(panel);
        
        isonline = new HiddenField<String>();
        isonline.setName(PaymentConfigMetaForm.ONLINE);
        isonline.setValue(String.valueOf(result.isOnline()));
        
        iscod = new HiddenField<String>();
        iscod.setName(PaymentConfigMetaForm.COD);
        iscod.setValue(String.valueOf(result.isCod()));
        idhidden = new HiddenField<String>();
        idhidden.setName(PaymentConfigMetaForm.ID);
        idhidden.setValue(String.valueOf(result.getPkId()));
        code = new HiddenField<String>();
        code.setName(PaymentConfigMetaForm.CODE);
        code.setValue(String.valueOf(result.getCode()));
        order = new HiddenField<String>();
        order.setName(PaymentConfigMetaForm.ORDER);
        order.setValue("0");
        formPanel.add(isonline);
        formPanel.add(iscod);
        formPanel.add(idhidden);
        formPanel.add(code);
        formPanel.add(order);
        
        formPanel.layout();
	}
	@Override
	protected void setupPanelLayout() {
	}

}
