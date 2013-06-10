package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.ILinkGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;

public class RelatedPanel extends LayoutContainer {

	ListBox b_list = new ListBox();
	ListBox lstCategory = new ListBox();
	ListBox lstBrand = new ListBox();
	TextBox txtKeyword = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	
	ListBox lb_optGoods = new ListBox();
	ListBox lb_relGoods = new ListBox();
	Radio oneway = new Radio();
	Radio twoway = new Radio();
	RadioGroup rg = new RadioGroup();
	
	Criteria criteria = new Criteria();
	
	String goodsId;
	Map<String, Boolean> linkGoodsValue;
	
	RelatedPanel(){
		init();
	}
	
	private void init(){
	//Brand list box	
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
	oneway.setBoxLabel(Resources.constants.Goods_1wayRelated());
	oneway.setTitle(Resources.constants.Goods_1wayRelated());
	twoway.setBoxLabel(Resources.constants.Goods_2wayRelated());
	twoway.setTitle(Resources.constants.Goods_2wayRelated());
	rg.add(oneway);
	rg.add(twoway);
	rg.setOrientation(Orientation.VERTICAL);
	rg.setValue(oneway);
	Button right_all = new Button(">>");
	right_all.setWidth(25);
	Button right = new Button(">");
    right.setWidth(25);
	Button left = new Button("<");
	left.setWidth(25);
	Button left_all = new Button("<<");
	left_all.setWidth(25);
	
	right.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			relate();
		}
	});
	right_all.addSelectionListener(new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			relate_all();
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
	
	operation_bottom.add(rg);
	operation_bottom.add(right_all);
	operation_bottom.add(right);
	operation_bottom.add(left);
	operation_bottom.add(left_all);
	operation_bottom.setSpacing(20);
	operation.addText(Resources.constants.Goods_operation());
	operation.add(operation_bottom);
	
	relatedGoods.setHorizontalAlign(HorizontalAlignment.CENTER);
	relatedGoods.addText(Resources.constants.Goods_relatedGoods());
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
}
	
	private void relate() {
		if(lb_optGoods.getSelectedIndex() != -1) {
			String id = lb_optGoods.getValue(lb_optGoods.getSelectedIndex());
			String name = lb_optGoods.getItemText(lb_optGoods.getSelectedIndex());
			if(!isRelated(id)) {
				boolean bidirectional;
				if(rg.getValue().equals(oneway)){
					bidirectional = false;
					lb_relGoods.addItem(name + "[" + Resources.constants.Goods_1wayRelated() + "]", id);
				}
				else {
					bidirectional = true;
					lb_relGoods.addItem(name + "[" + Resources.constants.Goods_2wayRelated() + "]", id);
				}

				if(goodsId != null){
					addLinkItem(id, bidirectional);
				}else {
					if(linkGoodsValue == null)
						linkGoodsValue = new HashMap<String, Boolean>();
					linkGoodsValue.put(id,bidirectional);
				}
			}
		}
	}
	
	private void relate_all() {
		for(int i = 0;i < lb_optGoods.getItemCount();i++) {
			String id = lb_optGoods.getValue(i);
			String name = lb_optGoods.getItemText(i);
			if(!isRelated(id)) {
				boolean bidirectional;
				if(rg.getValue().equals(oneway)){
					bidirectional = false;
					lb_relGoods.addItem(name + "[" + Resources.constants.Goods_1wayRelated() + "]", id);
				}
				else {
					bidirectional = true;
					lb_relGoods.addItem(name + "[" + Resources.constants.Goods_2wayRelated() + "]", id);
				}
				
				if(goodsId != null)
					addLinkItem(id, bidirectional);
				else {
					if(linkGoodsValue == null)
						linkGoodsValue = new HashMap<String, Boolean>();
					linkGoodsValue.put(id,bidirectional);
				}
			}
		}
	}
	
	private boolean isRelated(String id) {
		boolean isRelated = false;
		for(int i = 0;i < lb_relGoods.getItemCount();i++) {
			if(id.equals(lb_relGoods.getValue(i))) {
				isRelated = true;
				break;
			}
		}
		if(goodsId != null && id.equals(goodsId)){
			isRelated = true;
		}
		return isRelated;
	}
	
	private void addLinkItem(String linkGoods, boolean bidirectional) {
		final Map<String, Object> value = new HashMap<String, Object>();
		value.put(ILinkGoods.GOODS, goodsId);
		value.put(ILinkGoods.LINKGOODS, linkGoods);
		value.put(ILinkGoods.BIDIRECTIONAL, bidirectional);
		
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(ILinkGoods.GOODS, Condition.EQUALS, goodsId);
		Condition linkGoodsCon = new Condition(ILinkGoods.LINKGOODS, Condition.EQUALS, linkGoods);
		c.addCondition(goodsCon);
		c.addCondition(linkGoodsCon);
		new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				if(beans.size() == 0)
					new CreateService().createBean(new BeanObject(ModelNames.LINKGOODS, value), null);					
			}
		});
		
		if(bidirectional) {
			final Map<String, Object> bidirectionalValue = new HashMap<String, Object>();
			bidirectionalValue.put(ILinkGoods.GOODS, linkGoods);
			bidirectionalValue.put(ILinkGoods.LINKGOODS, goodsId);
			bidirectionalValue.put(ILinkGoods.BIDIRECTIONAL, bidirectional);
			goodsCon.setValue(linkGoods);
			linkGoodsCon.setValue(goodsId);
			new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					if(beans.size() == 0)
						new CreateService().createBean(new BeanObject(ModelNames.LINKGOODS, bidirectionalValue), null);					
				}
			});
		}
	}
	
	private void cancel() {
		if(lb_relGoods.getSelectedIndex() != -1) {
			if(goodsId != null){
				dropLinkItem(lb_relGoods.getValue(lb_relGoods.getSelectedIndex()));
			}else{
				linkGoodsValue.remove(lb_relGoods.getValue(lb_relGoods.getSelectedIndex()));
			}
			lb_relGoods.removeItem(lb_relGoods.getSelectedIndex());
		}
	}
	
	private void cancel_all() {
		for(int i = 0; i < lb_relGoods.getItemCount();i++) {
			if(goodsId != null){
				dropLinkItem(lb_relGoods.getValue(i));
			}else{
				linkGoodsValue.remove(lb_relGoods.getValue(i));
			}
			lb_relGoods.removeItem(i);
		}
	}
	
	private void dropLinkItem(String linkGoods) {
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(ILinkGoods.GOODS, Condition.EQUALS, goodsId);
		Condition linkGoodsCon = new Condition(ILinkGoods.LINKGOODS, Condition.EQUALS, linkGoods);
		c.addCondition(goodsCon);
		c.addCondition(linkGoodsCon);
	
		new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					new DeleteService().deleteBean(ModelNames.LINKGOODS, (String) bean.get(ILinkGoods.ID), null);
				}
			}			
		});
		
		goodsCon.setValue(linkGoods);
		linkGoodsCon.setValue(goodsId);
		new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					if((Boolean)bean.get(ILinkGoods.BIDIRECTIONAL)) {
						bean.set(ILinkGoods.BIDIRECTIONAL, false);
						new UpdateService().updateBean((String) bean.get(ILinkGoods.ID), bean, null);
					}
				}			
			}
		});
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
		new ListService().listBeans(ModelNames.GOODS, criteria, new ListService.Listener() {
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

	public List<String> getValues() {
		List<String> ids = new ArrayList<String>();
		for(int i = 0;i < lb_relGoods.getItemCount();i++) {
			String id = lb_relGoods.getValue(i);
			ids.add(id);
		}
		return ids;
	}
	
	public void setGoodsId(String goodsId) {
		linkGoodsValue = new HashMap<String, Boolean>();
		this.goodsId = goodsId;
		lb_relGoods.clear();
		if(goodsId != null) {
			criteria.removeAll();
			criteria.addCondition(new Condition(ILinkGoods.GOODS, Condition.EQUALS, goodsId));
			
			new ListService().listBeans(ModelNames.LINKGOODS, criteria, new ListService.Listener() {
				public synchronized void onSuccess(List<BeanObject> result) {
					for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
						final BeanObject linkGoods = it.next();
						String id = linkGoods.getString(ILinkGoods.LINKGOODS);
						new ReadService().getBean(ModelNames.GOODS, id, new ReadService.Listener() {
							public void onSuccess(BeanObject bean) {
								if(Boolean.TRUE.equals(linkGoods.get(ILinkGoods.BIDIRECTIONAL)))
									lb_relGoods.addItem(bean.getString(IGoods.NAME) + "[" + Resources.constants.Goods_2wayRelated() + "]", bean.getString(IGoods.ID));
								else 
									lb_relGoods.addItem(bean.getString(IGoods.NAME) + "[" + Resources.constants.Goods_1wayRelated() + "]", bean.getString(IGoods.ID));									
						    }
						});
					}
				}
			});
		}
	}
	
	public Map<String, Boolean> getValue() {
		return linkGoodsValue;
	}
	
	public void setValues(String id){
		Map<String, Boolean> linkGoods = getValue();
		if(linkGoods != null) {
			for(Object key : linkGoods.keySet()) {
				boolean bidirectional = linkGoods.get(key);
				String linkGoodsId = (String)key;
				
				final Map<String, Object> value = new HashMap<String, Object>();
				value.put(ILinkGoods.GOODS, id);
				value.put(ILinkGoods.LINKGOODS, linkGoodsId);
				value.put(ILinkGoods.BIDIRECTIONAL, bidirectional);
				
				Criteria c = new Criteria();
				Condition goodsCon = new Condition(ILinkGoods.GOODS, Condition.EQUALS, id);
				Condition linkGoodsCon = new Condition(ILinkGoods.LINKGOODS, Condition.EQUALS, linkGoodsId);
				c.addCondition(goodsCon);
				c.addCondition(linkGoodsCon);
				new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
					public void onSuccess(List<BeanObject> beans) {
						if(beans.size() == 0)
							new CreateService().createBean(new BeanObject(ModelNames.LINKGOODS, value), null);					
					}
				});
				
				if(bidirectional) {
					final Map<String, Object> bidirectionalValue = new HashMap<String, Object>();
					bidirectionalValue.put(ILinkGoods.GOODS, linkGoodsId);
					bidirectionalValue.put(ILinkGoods.LINKGOODS, id);
					bidirectionalValue.put(ILinkGoods.BIDIRECTIONAL, bidirectional);
					goodsCon.setValue(linkGoodsId);
					linkGoodsCon.setValue(id);
					new ListService().listBeans(ModelNames.LINKGOODS, c, new ListService.Listener() {
						public void onSuccess(List<BeanObject> beans) {
							if(beans.size() == 0)
								new CreateService().createBean(new BeanObject(ModelNames.LINKGOODS, bidirectionalValue), null);					
						}
					});
				}
			}
		}
	}
}