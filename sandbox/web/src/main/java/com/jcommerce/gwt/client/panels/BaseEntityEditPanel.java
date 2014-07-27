package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.SimpleOptionData;
import com.jcommerce.gwt.client.model.IModelObject;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.widgets.SimpleStaticComboBox;


public abstract class BaseEntityEditPanel extends ContentWidget  {
	
	public abstract static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String ID = "Id";
		
		public abstract String getPageClassName();
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setId(Long gtid) {
			setValue(ID, gtid);
		}
		public Long getId() {
			return (Long)getValue(ID);
		}
	}
	
	protected abstract State getCurState();
	
	protected abstract void setupPanelLayout();
	protected abstract String getEntityClassName();
	
    Button btnNew = new Button();    
    Button btnReset = new Button();    
    
//    protected void onRender(Element parent, int index) {
//    super.onRender(parent, index);
    
    @Override
    protected void afterRender() {
    	super.afterRender();

//        add(formPanel);
        super.add(formPanel);
//        formPanel.setBodyBorder(false);
        //formPanel.setWidth(800);
        
        FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
        formPanel.setLayout(fl);
        formPanel.setWidth("100%");

        setupPanelLayout();

      btnNew.setText(Resources.constants.ok());        
      btnReset.setText(Resources.constants.reset());
//      panel.add(btnNew);         
//      panel.add(btnCancel);
      
      formPanel.setButtonAlign(HorizontalAlignment.CENTER);
      formPanel.addButton(btnNew);
      formPanel.addButton(btnReset);
      
      
      btnNew.addSelectionListener(selectionListener);
      
      btnReset.addSelectionListener(
      		new SelectionListener<ButtonEvent>() {
      			public void componentSelected(ButtonEvent sender) {
      				formPanel.reset();
      			}
      		}
      	);
    }
    // get the setting for long field
	public FormData lfd() {
		FormData fd = new FormData(500, -1);
		return fd;
	}
    // get the setting for short field
	public FormData sfd() {
		FormData fd = new FormData(300, -1);
		return fd;
	}
    // get the setting for tiny field
	public FormData tfd() {
		FormData fd = new FormData(100, -1);
		return fd;
	}
	protected SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
//	    	log("on Submit: formPanel="+formPanel);
	    	try {
	    	if(!formPanel.isValid()) {
	    		Window.alert("Please check input before submit.");
	    		return;
	    	}
	    	String error = validateForm();
	    	if(error!=null && error.length()>0) {
	    		Window.alert("Please check input before submit, error is: "+error);
	    		return;
	    	}
	    	
	    	if(!formPanel.isDirty()) {
	    		// TODO this is optimisitic. should change it to based on a strategy configuration?
	    		Window.alert("the form is not changed!!!");
	    		gotoSuccessPanel();
	    		return;
	    	}
	    	submit();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	};
	
	
	protected String validateForm() {
		return null;
	}
	
	protected void submit() {
		// default implementation is thru GWT-RPC
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
		
    	BeanObject form = new BeanObject(getEntityClassName(), (Map)props);
    	if (getCurState().getIsEdit()) {
    		Long id = getCurState().getId();
          new UpdateService().updateBean(id, form, new UpdateService.Listener() {
        	  public synchronized void onSuccess(Boolean success) {
        		  gotoSuccessPanel();
        	  }
        	  public void onFailure(Throwable caught) {
        		  // TODO a point to define common behavior
        	  }
          });
    	}else {
          new CreateService().createBean(form, new CreateService.Listener() {
          public synchronized void onSuccess(String id) {
        	  log("new onSuccess( "+id);                            
              getCurState().setId(Long.valueOf(id));
              gotoSuccessPanel();
          }
          });
    	}
	}
	
	protected FormPanel formPanel = new FormPanel();
	
	public abstract void gotoSuccessPanel();
	
	protected abstract void postSuperRefresh();
	
    public void refresh() {
    	try {
    		formPanel.clear();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        if(getCurState().getIsEdit()) {
        	retrieveEntity();
        } else {
        	obj = new BeanObject();
        	postSuperRefresh();
        }

    }
    
    // get called when refresh(), if isEdit
    protected void retrieveEntity() {
    	new ReadService().getBean(getEntityClassName(), getCurState().getId(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			obj = bean;
        			// populate those statically rendered fields
        			populateFields();
        			// sub-class should populate those "dynamic" fields including combox/list, etc 
        			postSuperRefresh();
        		}
        	});
    }
    
    
    public void log(String s) {
    	
//    	StringBuffer buf = new StringBuffer();
//    	buf.append("[").append(this.getClass().getName()).append("]:").append(s);
//    	Logger.getClientLogger().log(buf.toString());
//    	System.out.println(buf.toString());
    }
    public BeanObject getEntity() {
    	return obj;
    }

	public void populateField(Field field) {
		Map<String, Object> mapAttribute = obj.getProperties();
		String name = field.getName();
		Object value = mapAttribute.get(name);
		log("[populateField]: name:"+name+", value:"+(value==null?"null":value.toString()+", valueclass: "+value.getClass().getName()));
		populateField(field, value);
	}
	
	public void populateField(Field field, Object value) {

			if(field instanceof CheckBoxGroup) {
				List<Field<?>> boxes = (List<Field<?>>)((CheckBoxGroup)field).getAll();
				for(Field b:boxes) {
					CheckBox box = (CheckBox)b;
					populateField(box);
				}
				return;
			}
			if(field instanceof RadioGroup) {
				// go on, to avoid going into MultiField(see below)
			}
			else if(field instanceof MultiField) {
				List<Field<?>> subFields = ((MultiField)field).getAll();
				for(Field subField:subFields) {
					populateField(subField);
				}
				return;
			}
			
			
			if(value==null) {
				return;
			}
			
			if(field instanceof SimpleStaticComboBox) {
				SimpleStaticComboBox<BeanObject> box = (SimpleStaticComboBox<BeanObject>)field;
				ListStore<BeanObject> store = box.getStore();
				List<BeanObject> selection = new ArrayList<BeanObject>();
				BeanObject bo = store.findModel(SimpleOptionData.VALUE, value);
				if(bo!=null) {
					selection.add(bo);
				}
				box.setSelection(selection);
			}
			else if(field instanceof ComboBox) {
				ComboBox<BeanObject> box = (ComboBox<BeanObject>)field;
				
				ListStore<BeanObject> store = box.getStore();
				List<BeanObject> selection = new ArrayList<BeanObject>();

				// store may not have been initialized with dyna data yet. skip it
				if(store.getCount()>0) {
					BeanObject bo = store.findModel(IModelObject.ID, value);
					if(bo!=null) {
						selection.add(bo);
					}
					box.setSelection(selection);
				}
				
				
			} else if(field instanceof ListField){
				ListField<BeanObject> lf = (ListField<BeanObject>)field;
				ListStore<BeanObject> store = lf.getStore();
				
				Collection<String> v = (Collection<String>)value;
				List<BeanObject> selection = new ArrayList<BeanObject>();
				if(store.getCount()>0) {
					for(String vv:v) {
						BeanObject bo = store.findModel(IModelObject.ID, vv);
						if(bo!=null) {
							selection.add(bo);
						}
					}
					lf.setSelection(selection);
				}
				
			} else if(field instanceof DateField) {
				log("TODO: convert date format?? using gxt-DateTimePropertyEditor");
				DateField df = ((DateField)field);
				if(value instanceof Long) {
					df.setValue(new Date((Long)value));
				}
				else if(value instanceof Date){
					df.setValue((Date)value);
				}				
			} else if(field instanceof TextField || field instanceof TextArea || field instanceof HtmlEditor) {
				field.setOriginalValue(value);
				field.setValue(value);
			} else if (field instanceof CheckBox) {
				((CheckBox)field).setValue((Boolean)value);

			} else if (field instanceof HiddenField) {
				((HiddenField)field).setValue(value);
			} else if(field instanceof NumberField) {
				((NumberField)field).setOriginalValue((Number)value);
				((NumberField)field).setValue((Number)value);

			} else if(field instanceof RadioGroup) {
				RadioGroup rg = (RadioGroup)field;
				List<Field<?>> radios = (List<Field<?>>)rg.getAll();
				for(Field<?> r:radios) {
					Radio radio = (Radio)r;
					if(value.toString().equals(radio.getValueAttribute())) {
						radio.setValue(true);
//						radio.setRawValue("true");						
					}
					else {
						radio.setValue(false);
//						radio.setRawValue("false");
					}
				}
				
			}
			else if(field instanceof CheckBox) {
				CheckBox box = (CheckBox)field;
				if(value.toString().equals(box.getValueAttribute())) {
					box.setRawValue("true");
				}
				else {
					box.setRawValue("false");
				}
			}

	}
	
	public void populateFields() {
		List<Field<?>> fields = formPanel.getFields();
		for(Field field:fields) {
			populateField(field);
		}
	}
    protected BeanObject obj;
}
