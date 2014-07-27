package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;

public class GoodsPanel extends LayoutContainer{

	ListBox lstCategory = new ListBox();
	ListBox lstBrand = new ListBox();
	Button btnFind = new Button("搜索");
	TextBox txtKeyword = new TextBox();
	Criteria criteria = new Criteria();
	ListBox lb_optGoods = new ListBox();
	ListBox lb_relGoods = new ListBox();
	
	public GoodsPanel() {
		init();
	}
	
	private void init(){
		
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

		lstCategory
				.addItem(Resources.constants.GoodsList_all_category(), "all");
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
		optionalGoods.addText("可选商品");
		
		lb_optGoods.setWidth("300px");
		lb_optGoods.setVisibleItemCount(15);
		optionalGoods.add(lb_optGoods);
		
		VerticalPanel operation_bottom = new VerticalPanel();
		Button right_all = new Button(">>");
		Button right = new Button(">");
		Button left = new Button("<");
		Button left_all = new Button("<<");
		operation_bottom.addText("   ");
		operation_bottom.add(right_all);
		operation_bottom.add(right);
		operation_bottom.add(left);
		operation_bottom.add(left_all);
		operation_bottom.setSpacing(20);
		operation.addText("操作");
		operation.add(operation_bottom);
		
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
		
		
		relatedGoods.setHorizontalAlign(HorizontalAlignment.CENTER);
		relatedGoods.addText("关联商品");
		
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
		
//		HorizontalPanel h_panel = new HorizontalPanel();
//		Button btn = new Button("发放");
//		
//		h_panel.setHorizontalAlign(HorizontalAlignment.CENTER);
//		h_panel.add(btn);
//		add(h_panel);
	}
	
	private void search() {
		criteria.removeAll();
		if (lstBrand.getSelectedIndex() > 0) {
			String brand = lstBrand.getValue(lstBrand.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.BRAND);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(Long.valueOf(brand));
			criteria.addCondition(cond);
		}
		if (lstCategory.getSelectedIndex() > 0) {
			String cat = lstCategory.getValue(lstCategory.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.CATEGORIES);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(Long.valueOf(cat));
			criteria.addCondition(cond);
		}

		String keyword = txtKeyword.getText();
		if (keyword != null && keyword.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IGoods.KEYWORDS);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(Long.valueOf(keyword.trim()));
			criteria.addCondition(cond);
		}

		lb_optGoods.clear();
		new ListService().listBeans(ModelNames.GOODS,criteria,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						List<String> pids = new ArrayList<String>();
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
	private void relate() {
		if(lb_optGoods.getSelectedIndex() != -1) {
			String id = lb_optGoods.getValue(lb_optGoods.getSelectedIndex());
			String name = lb_optGoods.getItemText(lb_optGoods.getSelectedIndex());
			if(!isRelated(id)) {
				lb_relGoods.addItem(name, id);
			}
		}
	}
	private boolean isRelated(String id) {
		boolean isRelated = false;
		for(int i = 0;i < lb_relGoods.getItemCount();i++) {
			System.out.println(lb_relGoods.getValue(i));
			if(id.equals(lb_relGoods.getValue(i))) {
				isRelated = true;
				break;
			}
		}
		return isRelated;
	}
	private void relate_all() {
		for(int i = 0;i < lb_optGoods.getItemCount();i++) {
			String id = lb_optGoods.getValue(i);
			String name = lb_optGoods.getItemText(i);
			if(!isRelated(id)) {
				lb_relGoods.addItem(name, id);
			}
		}
	}
	private void cancel() {
		if(lb_relGoods.getSelectedIndex() != -1) {
			lb_relGoods.removeItem(lb_relGoods.getSelectedIndex());
		}
	}
	private void cancel_all() {
		lb_relGoods.clear();
	}
	
	public List<String> getValues() {
		List<String> ids = new ArrayList<String>();
		for(int i = 0;i < lb_relGoods.getItemCount();i++) {
			String id = lb_relGoods.getValue(i);
			ids.add(id);
		}
		return ids;
	}
	
	public void setArticleId(Long articleId) {
		lb_relGoods.clear();
		if(articleId != null) {
			criteria.removeAll();
			criteria.addCondition(new Condition("goods", Condition.EQUALS, articleId));
			
			new ListService().listBeans(ModelNames.LINKGOODS, criteria, 
					new ListService.Listener() {
						public synchronized void onSuccess(List<BeanObject> result) {
							for (Iterator<BeanObject> it = result.iterator(); it
									.hasNext();) {
								BeanObject linkGoods = it.next();
								Long id = linkGoods.getLong("linkGoods");
								new ReadService().getBean(ModelNames.GOODS, id, 
										new ReadService.Listener() {
											public void onSuccess(BeanObject bean) {
												lb_relGoods.addItem(bean.getString(IGoods.NAME), bean.getString(IGoods.ID));
									        }
										});
							}
						}
					});
		}
	}
}
