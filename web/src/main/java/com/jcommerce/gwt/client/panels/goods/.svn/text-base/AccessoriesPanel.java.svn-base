package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGroupGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.validator.PriceChecker;

public class AccessoriesPanel extends LayoutContainer {
	ListBox b_list = new ListBox();
	ListBox lstCategory = new ListBox();
	ListBox lstBrand = new ListBox();
	TextBox txtKeyword = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	
	ListBox lb_optGoods = new ListBox();
	ListBox lb_relGoods = new ListBox();
	TextBox tb_price = new TextBox();
	
	Criteria criteria = new Criteria();
	
	String goodsId;
	Map<String, String> AccessoriesValue;
	
	AccessoriesPanel(){
		init();
	}
	
	private void init(){
	// Brand list box	
	lstBrand.addItem(Resources.constants.GoodsList_all_brand(), "all");
	new ListService().listBeans(ModelNames.BRAND,
			new ListService.Listener() {
				public synchronized void onSuccess(List<BeanObject> result) {
					for (Iterator<BeanObject> it = result.iterator(); it
							.hasNext();) {
						BeanObject brand = it.next();
						lstBrand.addItem(brand.getString(IBrand.NAME),
								brand.getString(IBrand.ID));
					}
				}
			});

	//Category list box
	lstCategory.addItem(Resources.constants.GoodsList_all_category(), "all");
	new ListService().listBeans(ModelNames.CATEGORY,
			new ListService.Listener() {
				public void onSuccess(List<BeanObject> result) {
					List<String> pids = new ArrayList<String>();
					for (Iterator<BeanObject> it = result.iterator(); it
							.hasNext();) {
						BeanObject cat = it.next();
						String name = cat.getString(ICategory.NAME);
						String id = cat.getString(ICategory.ID);
						String _pid = cat.getString(ICategory.PARENT);
						if (_pid == null) {
							pids.clear();
						} else if (!pids.contains(_pid)) {
							pids.add(_pid);
						}
						int level = pids.indexOf(_pid) + 1;
						for (int i = 0; i < level; i++) {
							name = "  " + name;
						}
						lstCategory.addItem(name, id);
					}
				}
			});

	HorizontalPanel header = new HorizontalPanel();
	header.add(Resources.images.icon_search().createImage());
	header.add(lstCategory);
	header.add(lstBrand);
	//header.add(new Label("  " + Resources.constants.GoodsList_keyword()));
	header.add(txtKeyword);
	header.add(btnFind);
	add(header);
	
	btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			search();
		}
	});
	
	HorizontalPanel panel = new HorizontalPanel();
	VerticalPanel optionalGoods = new VerticalPanel();
	VerticalPanel operation = new VerticalPanel();
	VerticalPanel relatedGoods = new VerticalPanel();
	
	optionalGoods.setHorizontalAlign(HorizontalAlignment.CENTER);
	optionalGoods.addText(Resources.constants.Goods_optionalGoods());
	lb_optGoods.setWidth("300px");
	lb_optGoods.setVisibleItemCount(15);
	optionalGoods.add(lb_optGoods);
	
	VerticalPanel operation_bottom = new VerticalPanel();
	operation_bottom.addText(Resources.constants.Goods_price());
	tb_price.setWidth("50px");
	Button right = new Button(">");
	right.setWidth(25);
	Button left = new Button("<");
	left.setWidth(25);
	Button left_all = new Button("<<");
	left_all.setWidth(25);
	
	operation_bottom.add(tb_price);
	operation_bottom.add(right);
	operation_bottom.add(left);
	operation_bottom.add(left_all);
	operation_bottom.setSpacing(20);
	operation.addText(Resources.constants.Goods_operation());
	operation.add(operation_bottom);
	
	relatedGoods.setHorizontalAlign(HorizontalAlignment.CENTER);
	relatedGoods.addText(Resources.constants.Goods_accessories());
	lb_relGoods.setWidth("300px");
	lb_relGoods.setVisibleItemCount(15);
	relatedGoods.add(lb_relGoods);
	
	optionalGoods.setSize(350, 350);
	operation.setSize(120, 350);
	operation.setSize(120, 330);
	relatedGoods.setSize(350, 350);
	panel.setSize(920, 350);
	panel.add(optionalGoods);
	panel.add(operation);
	panel.add(relatedGoods);
	add(panel);
	

    lb_optGoods.addClickHandler(new ClickHandler() {
        
        public void onClick(ClickEvent arg0) {
            if (lb_optGoods.getSelectedIndex() < 0) {
                return;
            }
            
			String id = lb_optGoods.getValue(lb_optGoods.getSelectedIndex());
			new ReadService().getBean(ModelNames.GOODS, id, new ReadService.Listener() {
				public void onSuccess(BeanObject bean){
					tb_price.setText(bean.getString(IGoods.SHOPPRICE));
				}
			});
		}		
	});
	
	
	right.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			beAccessory();
		}
	});
	
	left.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			cancel();
		}
	});
	
	left_all.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			cancel_all();
		}
	});
}
	
	private void beAccessory() {
		if(lb_optGoods.getSelectedIndex() != -1) {
			String id = lb_optGoods.getValue(lb_optGoods.getSelectedIndex());
			String name = lb_optGoods.getItemText(lb_optGoods.getSelectedIndex());
			String price = tb_price.getText();
			if(!priceInvalid(price) && !isAccessory(id)) {
				lb_relGoods.addItem(name + " - [" + price + "]", id);
				if(goodsId != null){
					addLinkItem(id, price);
				}else {
					if(AccessoriesValue == null)
						AccessoriesValue = new HashMap<String, String>();
					AccessoriesValue.put(id,price);
				}
			}
		}
	}
	
	private boolean isAccessory(String id) {
		boolean isAccessory = false;
		for(int i = 0;i < lb_relGoods.getItemCount();i++) {
			if(id.equals(lb_relGoods.getValue(i))) {
				isAccessory = true;
				break;
			}
		}
		if(goodsId != null && id.equals(goodsId)){
			isAccessory = true;
		}
		return isAccessory;
	}
	
	private void cancel() {
		if(lb_relGoods.getSelectedIndex() != -1) {
			if(goodsId != null){
				dropLinkItem(lb_relGoods.getValue(lb_relGoods.getSelectedIndex()));
			}else{
				AccessoriesValue.remove(lb_relGoods.getValue(lb_relGoods.getSelectedIndex()));
			}
			lb_relGoods.removeItem(lb_relGoods.getSelectedIndex());
		}
	}
	
	private void cancel_all() {
		for(int i = 0; i < lb_relGoods.getItemCount();i++) {
			if(goodsId != null){
				dropLinkItem(lb_relGoods.getValue(i));
			}else{
				AccessoriesValue.remove(lb_relGoods.getValue(i));
			}
			lb_relGoods.removeItem(i);
		}
	}
	
	private void addLinkItem(String childId, String goodsPrice) {
		final Map<String, Object> value = new HashMap<String, Object>();
		value.put(IGroupGoods.PARENT, goodsId);
		value.put(IGroupGoods.GOODS, childId);
		value.put(IGroupGoods.GOODSPRICE, goodsPrice);
		
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(IGroupGoods.PARENT, Condition.EQUALS, goodsId);
		Condition groupGoodsCon = new Condition(IGroupGoods.GOODS, Condition.EQUALS, childId);
		c.addCondition(goodsCon);
		c.addCondition(groupGoodsCon);
		new ListService().listBeans(ModelNames.GROUPGOODS, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				if(beans.size() == 0)
					new CreateService().createBean(new BeanObject(ModelNames.GROUPGOODS, value), null);					
			}
		});
	}
	
	private Boolean priceInvalid(String price){
		String error = null;
		PriceChecker priceChecker = new PriceChecker(Resources.constants.Goods_price(),0,false);
		error = priceChecker.validate(price);
		if (error != null && !error.equals("")) {
			StringBuffer sb = new StringBuffer();
			sb.append(error).append("<br>");
			MessageBox.alert("ERROR", sb.toString(), null);
			return true;
		}else{
			return false;
		}
	}
	
	private void dropLinkItem(String childId) {
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(IGroupGoods.PARENT, Condition.EQUALS, goodsId);
		Condition groupGoodsCon = new Condition(IGroupGoods.GOODS, Condition.EQUALS, childId);
		c.addCondition(goodsCon);
		c.addCondition(groupGoodsCon);
	
		new ListService().listBeans(ModelNames.GROUPGOODS, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					new DeleteService().deleteBean(ModelNames.GROUPGOODS, (String) bean.get(IGroupGoods.ID), null);
				}
			}			
		});
	}
	
	public void setGoodsId(String goodsId) {
		AccessoriesValue = new HashMap<String, String>();
		this.goodsId = goodsId;
		lb_relGoods.clear();
		if(goodsId != null) {
			criteria.removeAll();
			criteria.addCondition(new Condition(IGroupGoods.PARENT, Condition.EQUALS, goodsId));
			
			new ListService().listBeans(ModelNames.GROUPGOODS, criteria, new ListService.Listener() {
				public synchronized void onSuccess(List<BeanObject> result) {
					for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
						final BeanObject groupGoods = it.next();
						String id = groupGoods.getString(IGroupGoods.GOODS);
						final String price = groupGoods.getString(IGroupGoods.GOODSPRICE);
						new ReadService().getBean(ModelNames.GOODS, id, new ReadService.Listener() {
							public void onSuccess(BeanObject bean) {
								lb_relGoods.addItem(bean.getString(IGoods.NAME) + " - [" + price + "]", bean.getString(IGoods.ID));
						    }
						});
					}
				}
			});
		}
	}
	
	private void search() {
		criteria.removeAll();
		if (lstBrand.getSelectedIndex() > 0) {
			String brand = lstBrand.getValue(lstBrand.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.BRAND);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(brand);
			criteria.addCondition(cond);
		}
		if (lstCategory.getSelectedIndex() > 0) {
			String cat = lstCategory.getValue(lstCategory.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.CATEGORIES);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(cat);
			criteria.addCondition(cond);
		}

		String keyword = txtKeyword.getText();
		if (keyword != null && keyword.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IGoods.NAME);
			cond.setOperator(Condition.LIKE);
			cond.setValue(keyword.trim());
			criteria.addCondition(cond);
		}

		lb_optGoods.clear();
		new ListService().listBeans(ModelNames.GOODS,criteria, new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject goods = it.next();
							String name = goods.getString(IGoods.NAME);
							String id = goods.getString(IGoods.ID);
							lb_optGoods.addItem(name, id);
						}
					}
				});
	}
	
	public Map<String, String> getValue() {
		return AccessoriesValue;
	}
	
	public void setValues(String id){
		Map<String, String> groupGoods = getValue();
		if(groupGoods != null) {
			for(Object key : groupGoods.keySet()) {
				String price = groupGoods.get(key);
				String childId = (String)key;
				
				final Map<String, Object> value = new HashMap<String, Object>();
				value.put(IGroupGoods.PARENT, id);
				value.put(IGroupGoods.GOODS, childId);
				value.put(IGroupGoods.GOODSPRICE, price);
				
				//判断是否已是配件
				Criteria c = new Criteria();
				Condition goodsCon = new Condition(IGroupGoods.PARENT, Condition.EQUALS, id);
				Condition groupGoodsCon = new Condition(IGroupGoods.GOODS, Condition.EQUALS, childId);
				c.addCondition(goodsCon);
				c.addCondition(groupGoodsCon);
				new ListService().listBeans(ModelNames.GROUPGOODS, c, new ListService.Listener() {
					public void onSuccess(List<BeanObject> beans) {
						if(beans.size() == 0)
							new CreateService().createBean(new BeanObject(ModelNames.GROUPGOODS, value), null);					
					}
				});
			}
		}
	}
}

