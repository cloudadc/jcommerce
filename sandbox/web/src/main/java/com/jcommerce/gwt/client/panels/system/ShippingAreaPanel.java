package com.jcommerce.gwt.client.panels.system;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.UserAddressForm;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RegionService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.util.MyListLoader;

public class ShippingAreaPanel extends BaseEntityEditPanel {
    public static class State extends BaseEntityEditPanel.State {
    	public static final String SHIPPING_ID = "SHIPPINGID";
    	public String getPageClassName() {
            return ShippingAreaPanel.class.getName();
        }
        public void setShippingId(String shippingId) {
        	setValue(SHIPPING_ID, shippingId);
        }
        public String getShippingId() {
        	return (String)getValue(SHIPPING_ID);
        }
    }
    
    public ShippingAreaPanel() {
    }
    
	@Override
	protected State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	@Override
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		if(getCurState().getIsEdit()) {
			newState.setMessage("编辑配送区域成功");
		}else {
			newState.setMessage("添加配送区域成功");
		}
    	
    	ShippingAreaListPanel.State choice1 = new ShippingAreaListPanel.State();
    	choice1.setShippingId(getCurState().getShippingId());
    	newState.addChoice(ShippingAreaListPanel.getInstance().getName(), choice1);
    	
    	newState.execute();
	}

	@Override
	protected void postSuperRefresh() {
		generateDynaFields();
	}
	
	LayoutContainer lc1 = new LayoutContainer();
	LayoutContainer lc2 = new LayoutContainer();
	LayoutContainer cgSelectedRegions;

	private ListStore<BeanObject> countryList;
    private ListStore<BeanObject> provinceList;
    private ListStore<BeanObject> cityList;
    private ListStore<BeanObject> districtList;
    
	ComboBox<BeanObject> cbCountry;
	String lastSelectedCountryId;
	ComboBox<BeanObject> cbProvince;
	String lastSelectedProvinceId;
	ComboBox<BeanObject> cbCity;
	String lastSelectedCityId;
	ComboBox<BeanObject> cbDistrict;

	@Override
	protected void setupPanelLayout() {
        FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
        lc1.setLayout(fl);
        
		TableLayout layout = new TableLayout(1);
		layout.setCellSpacing(5);
		layout.setCellPadding(5);
        lc2.setLayout(layout);
        
        FieldSet fs = new FieldSet();
        fs.setHeading("所辖地区");
        lc2.add(fs);
        
		final LayoutContainer lcc = new LayoutContainer();
		TableRowLayout cl = new TableRowLayout();
		cl.setWidth("100%");
		cl.setCellVerticalAlign(VerticalAlignment.TOP);
		cl.setCellSpacing(10);
		lcc.setLayout(cl);
		
		fs.add(lcc);
        
		TableData td = new TableData();
//		td.setWidth("10%");

		Text lblCountry = new Text("国家：");
//		lblCountry.setWidth(50);
		lcc.add(lblCountry, td);

        countryList = new ListStore<BeanObject>();
        cbCountry = UserAddressForm.getCountryField();
        
        cbCountry.setStore(countryList);
        cbCountry.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbCountry.setWidth(150);   
        cbCountry.setTypeAhead(true);   
        cbCountry.setTriggerAction(TriggerAction.ALL);  
        cbCountry.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {          
            @Override
            public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
                BeanObject bo = se.getSelectedItem();
                if(bo==null) {
                    // this happen when formPanel.clear()
                    return;
                }
                changeCountry(bo.getString(IRegion.ID));                
            }
        });
		
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.TYPE,Condition.EQUALS, "0"));
        new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){
            @Override
            public void onSuccess(List<BeanObject> beans) {
                countryList.removeAll();
                countryList.add(beans);
            }            
        });
        
        lcc.add(cbCountry, td);
        
		Text lblProvince = new Text("省份：");
		lcc.add(lblProvince, td);
		
        provinceList = new ListStore<BeanObject>();
        cbProvince = UserAddressForm.getProvinceField();
        cbProvince.setStore(provinceList);
        cbProvince.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbProvince.setWidth(150);   
        cbProvince.setTypeAhead(true);   
        cbProvince.setTriggerAction(TriggerAction.ALL);  
        cbProvince.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {         
            @Override
            public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
                BeanObject bo = se.getSelectedItem();
                if(bo==null) {
                    // this happen when formPanel.clear()
                    return;
                }
                changeProvince(bo.getString(IRegion.ID));
            }
        });
		
        lcc.add(cbProvince, td);
        
		Text lblCity = new Text("城市：");
		lcc.add(lblCity, td);
		
        cityList = new ListStore<BeanObject>();
        cbCity = UserAddressForm.getCityField();
        cbCity.setStore(cityList);
        cbCity.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbCity.setWidth(150);   
        cbCity.setTypeAhead(true);   
        cbCity.setTriggerAction(TriggerAction.ALL);  
        cbCity.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {         
            @Override
            public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
                BeanObject bo = se.getSelectedItem();
                if(bo==null) {
                    // this happen when formPanel.clear()
                    return;
                }
                changeCity(bo.getString(IRegion.ID));
            }
        });

        lcc.add(cbCity, td);
        
		Text lblDistrict = new Text("区/县：");
		lcc.add(lblDistrict, td);
		
        districtList = new ListStore<BeanObject>();
        cbDistrict = UserAddressForm.getDistrictField(Resources.constants.Consignee_Area()+":");
        cbDistrict.setStore(districtList);
        cbDistrict.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbDistrict.setWidth(150);   
        cbDistrict.setTypeAhead(true);   
        cbDistrict.setTriggerAction(TriggerAction.ALL);  
        lcc.add(cbDistrict, td);
                
        Button link = new Button("[+]");
        lcc.add(link, td);
        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				onAddButtonClicked();
			}
        });
        
        cgSelectedRegions = new LayoutContainer();
        TableRowLayout tl = new TableRowLayout();
        cgSelectedRegions.setLayout(tl);
        fs.add(cgSelectedRegions);        
        
//		TableData td = new TableData();
//		td.setWidth("100%");
//		formPanel.add(lc1, td);
//		formPanel.add(lc2, td);
        
        formPanel.add(lc1);
        formPanel.add(lc2);
	}

    private void changeCountry(String pid) {
        new RegionService().getRegionChildList(pid, new RegionService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                provinceList.removeAll();
                provinceList.add(beans);
            }
        });
    }
    private void changeProvince(String pid) {
        new RegionService().getRegionChildList(pid, new RegionService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                cityList.removeAll();
                cityList.add(beans);
            }           
        });
    }
    private void changeCity(String pid) {
        new RegionService().getRegionChildList(pid, new RegionService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                districtList.removeAll();
                districtList.add(beans);
            }           
        });
    }
	
	private boolean isBothNullOrEqual(String id, String lastId) {
		boolean res = false;
		if(id==null) {
			if(lastId == null) {
				res = true;
			}
		}
		else {
			if(id.equals(lastId)) {
				res = true;
			}
		}		
		return res;
	}
	private void clearCountry() {
		lastSelectedCountryId = null;
		cbCountry.setValue(null);
		cbCountry.getStore().removeAll();
	}
	private void clearProvince() {
		lastSelectedProvinceId = null;
		cbProvince.setValue(null);
		cbProvince.getStore().removeAll();
	}
	private void clearCity() {
		lastSelectedCityId = null;
		cbCity.setValue(null);
		cbCity.getStore().removeAll();
	}
	private void clearDistrict() {
		cbDistrict.setValue(null);
		cbDistrict.getStore().removeAll();
	}
	private void onAddButtonClicked() {
//		lfDistrict.getSelection()
//		List<BeanObject> countries = cbCountry.getSelection();
		BeanObject selected = cbDistrict.getValue();
		if(selected == null) {
			selected = cbCity.getValue();
			if(selected == null) {
				selected = cbProvince.getValue();
				if(selected == null){
					selected = cbCountry.getValue();
				}
			}
		}
		if(selected == null) {
			return;
		}
		
		String id = selected.get(IRegion.ID);
		String name = selected.get(IRegion.NAME);
		addSelectedRegion(id, name);

		List<Component> selectedRegions = (List<Component>)cgSelectedRegions.getItems();
		for(Component c:selectedRegions) {
			CheckBox cb = (CheckBox)c;
			log("cb name: "+cb.getValueAttribute()+", checked? "+cb.getValue());
		}
		cgSelectedRegions.layout();
	}

	Set<String> selectedRegions = new HashSet<String>();
	private void addSelectedRegion(String regionId, String regionName) {
		if(selectedRegions.contains(regionId)) {
			// already existed
			return;
		}
		
		selectedRegions.add(regionId);
        CheckBox cb = new CheckBox();
        cb.setName(IShippingArea.REGIONS+selectedRegions.size());
        cb.setValueAttribute(regionId);
        cb.setBoxLabel(regionName);
        cgSelectedRegions.add(cb);
//        cb.setRawValue("true");
        cb.setValue(true);
        cb.setOriginalValue(true);
        
	}
	private void clearSelectedRegion() {
//		regionCount=0;
		selectedRegions.clear();
        cgSelectedRegions.removeAll();
        cgSelectedRegions.layout();
	}
	
	private void onParentRegionSelected(String id, MyListLoader loader) {
		if(id==null) {
			return;
		}
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.PARENT, Condition.EQUALS, id));
        loader.setCriteria(criteria);
        loader.load();
		
	}
	
    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "编辑配送区域";
    }
    
    @Override
	protected void submit() {
        System.out.println(">>> submit <<<");
		// default implementation is thru GWT-RPC
		Map<String, String> props = FormUtils.getPropsFromForm(formPanel);
		List<Component> selectedRegions = (List<Component>)cgSelectedRegions.getItems();
		StringBuffer selectedRegionIds = new StringBuffer();
		for(Component c:selectedRegions) {
			CheckBox cb = (CheckBox)c;
			if(cb.getValue()) {
				selectedRegionIds.append(cb.getValueAttribute()+",");
			}
		}
		System.out.println("selectedRegionIds:"+selectedRegionIds);
		props.put(IShippingArea.REGIONS, selectedRegionIds.toString());
		props.put(IShippingArea.SHIPPING, getCurState().getShippingId());
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getId();
    		props.put(IShippingArea.ID, id);
    	}
    	BeanObject form = new BeanObject(getEntityClassName(), (Map<String, Object>)(Map)props);
    	
		RemoteService.getSpecialService().saveShippingArea(form, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(Boolean result) {
                System.out.println("onSuccess ");
                log("result: \n");
                log(result.toString());
//                gotoSuccessPanel();
                ShippingAreaListPanel.State newState = new ShippingAreaListPanel.State();
                newState.setShippingId(getCurState().getShippingId());                
                newState.execute();
            }
             
        });
	}
    
    @Override
    public void refresh() {
        System.out.println("ShippingAreaPanel refresh 1");
    	// clear
    	try {
    		List<Field<?>> fields = formPanel.getFields();
    		for(Field<?> f:fields) {
    			if(lc1.getItems().contains(f)) {
    				lc1.remove(f);
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	clearSelectedRegion();
    	
    	clearCountry();
    	clearProvince();
    	clearCity();
    	clearDistrict();
    	
    	System.out.println("ShippingAreaPanel refresh 2");
        State state = getCurState();
        String shippingAreaId = state.getId();
        String shippingId = state.getShippingId();
        
        System.out.println("ShippingAreaPanel refresh 3");
        RemoteService.getSpecialService().getShippingAreaMeta(shippingAreaId, shippingId, new AsyncCallback<ShippingAreaMetaForm>() {
            public void onFailure(Throwable caught) {
                log("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(ShippingAreaMetaForm result) {
                log("result: \n");
                System.out.println("ShippingAreaPanel refresh 4");
                log(result.toString());
                String res = result.getString(IShippingArea.REGIONS);
                obj = result;
                generateDynaFields();
            }
             
        });
        
        if(state.getIsEdit()) {
            new ReadService().getBean(ModelNames.SHIPPING_AREA, getCurState().getId(), new ReadService.Listener() {
                public void onSuccess(BeanObject area) {
                    String[] ids = area.getIDs(IShippingArea.REGIONS);
                    if (ids == null) {
                        return;
                    }
                    
                    new ReadService().getBeans(ModelNames.REGION, ids, new ReadService.Listener() {
                        public void onSuccess(List<BeanObject> regions) {
                            for (BeanObject region : regions) {
                                addSelectedRegion(region.getString(IRegion.ID), region.getString(IRegion.NAME));
                            }
                            cgSelectedRegions.layout();
                        }
                    });
                }
            });
//        	RemoteService.getSpecialService().getAreaRegionListWithName(shippingAreaId, 
//        			new AsyncCallback<ListLoadResult<BeanObject>>() {
//						public void onFailure(Throwable caught) {
//							log("failed!!!! "+caught.getMessage());
//						}
//						public void onSuccess(ListLoadResult<BeanObject> result) {
//			                log("result: \n");
//			                log(result.toString());
//			                
//			                renderAreaRegions(result.getData());
//						}
//        	});
        }
    }
	
	@Override
	protected void retrieveEntity() {
	}

	public void generateDynaFields() {
		ShippingAreaMetaForm result = (ShippingAreaMetaForm)obj; 
			
        TextField<String> tb = new TextField<String>();
        tb.setFieldLabel("配送区域名称");
        tb.setName(ShippingAreaMetaForm.NAME);
        tb.setValue(result.getName());
        tb.setOriginalValue(result.getName());
        lc1.add(tb);
        
        Map<String, ShippingAreaFieldMetaForm> fieldMetas = result.getFieldMetas();
        Map<String, String> keyValues = result.getFieldValues();
        for(String key:fieldMetas.keySet()) {
            String value = keyValues.get(key);
            System.out.println("key: "+key+", value: "+value);
            
            ShippingAreaFieldMetaForm meta = fieldMetas.get(key);

            TextField<String> textBox = new TextField<String>();
			textBox.setFieldLabel(meta.getLable());
			textBox.setName(key);
			textBox.setValue(value);
			textBox.setOriginalValue(value);
			lc1.add(textBox);

        }
        lc1.layout();
        lc1.repaint();
	}
//	private void renderAreaRegions(List<BeanObject> ars ) {
//		log(ars==null? "null" : "how many existing regions? "+ars.size());
//		for(BeanObject ar : ars) {
//			String regionId = ar.get(IAreaRegion.REGION_ID);
//			String regionName = ar.get(IAreaRegion.REGION_NAME);
//			addSelectedRegion(regionId, regionName);
//		}
//		cgSelectedRegions.layout();
//	}
}
