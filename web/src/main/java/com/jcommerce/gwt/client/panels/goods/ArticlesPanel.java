package com.jcommerce.gwt.client.panels.goods;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IGoodsArticle;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;

public class ArticlesPanel extends LayoutContainer {
	public static int n = 0;
	
	ListBox b_list = new ListBox();
	TextBox articleName = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	
	ListBox lb_optGoods = new ListBox();
	ListBox lb_relGoods = new ListBox();
	
	Criteria criteria = new Criteria();
	
	Long goodsId;
	Map<String, String> ArticleValue;
	
	ArticlesPanel(){
		init();
	}
	
	private void init(){
		
	HorizontalPanel header = new HorizontalPanel();
	header.add(Resources.images.icon_search().createImage());
	header.add(new Label("  " + Resources.constants.Goods_articleName()));
	header.add(articleName);
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
	Button right_all = new Button(">>");	
	Button right = new Button(">");
	Button left = new Button("<");
	Button left_all = new Button("<<");
	right_all.setWidth(25);
	right.setWidth(25);
	left.setWidth(25);
	left_all.setWidth(25);
	
	operation_bottom.addText("   ");
	operation_bottom.add(right_all);
	operation_bottom.add(right);
	operation_bottom.add(left);
	operation_bottom.add(left_all);
	operation_bottom.setSpacing(20);
	operation.addText(Resources.constants.Goods_operation());
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
				lb_relGoods.addItem(name, id);
				if(goodsId != null){
					addLinkItem(Long.valueOf(id));
				}else {
					if(ArticleValue == null)
						ArticleValue = new HashMap<String, String>();
					ArticleValue.put(id,name);
				}
			}
		}
	}
	
	private void relate_all() {
		for(int i = 0;i < lb_optGoods.getItemCount();i++) {
			String id = lb_optGoods.getValue(i);
			String name = lb_optGoods.getItemText(i);
			if(!isRelated(id)) {
				lb_relGoods.addItem(name, id);
				if(goodsId != null)
					addLinkItem(Long.valueOf(id));
				else {
					if(ArticleValue == null)
						ArticleValue = new HashMap<String, String>();
					ArticleValue.put(id,name);
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
	
	private void addLinkItem(Long articleId) {
		final Map<String, Object> value = new HashMap<String, Object>();
		value.put(IGoodsArticle.GOODS, goodsId);
		value.put(IGoodsArticle.ARTICLE, articleId);
		
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(IGoodsArticle.GOODS, Condition.EQUALS, goodsId);
		Condition articleCon = new Condition(IGoodsArticle.ARTICLE, Condition.EQUALS, articleId);
		c.addCondition(goodsCon);
		c.addCondition(articleCon);
		new ListService().listBeans(ModelNames.GOODSARTICLE, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				if(beans.size() == 0)
					new CreateService().createBean(new BeanObject(ModelNames.GOODSARTICLE, value), null);					
			}
		});
	}
	
	private void cancel() {
		if(lb_relGoods.getSelectedIndex() != -1) {
			if(goodsId != null){
				dropLinkItem(Long.valueOf(lb_relGoods.getValue(lb_relGoods.getSelectedIndex())));
			}else{
				ArticleValue.remove(lb_relGoods.getValue(lb_relGoods.getSelectedIndex()));
			}
			lb_relGoods.removeItem(lb_relGoods.getSelectedIndex());
		}
	}
	
	private void cancel_all() {
		for(int i = 0; i < lb_relGoods.getItemCount();i++) {
			if(goodsId != null){
				dropLinkItem(Long.valueOf(lb_relGoods.getValue(i)));
			}else{
				ArticleValue.remove(lb_relGoods.getValue(i));
			}
			lb_relGoods.removeItem(i);
		}
	}
	
	private void dropLinkItem(Long articleId) {
		Criteria c = new Criteria();
		Condition goodsCon = new Condition(IGoodsArticle.GOODS, Condition.EQUALS, goodsId);
		Condition articleCon = new Condition(IGoodsArticle.ARTICLE, Condition.EQUALS, articleId);
		c.addCondition(goodsCon);
		c.addCondition(articleCon);
		new ListService().listBeans(ModelNames.GOODSARTICLE, c, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject bean : beans) {
					new DeleteService().deleteBean(ModelNames.GOODSARTICLE, (Long) bean.get(IGoodsArticle.ID), null);
				}
			}			
		});
	}
	
	private void search() {
		criteria.removeAll();
		String articlename = articleName.getText();
		if (articlename != null && articlename.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IArticle.TITLE);
			cond.setOperator(Condition.LIKE);
			cond.setValue(Long.valueOf(articlename.trim()));
			criteria.addCondition(cond);
		}

		lb_optGoods.clear();
		new ListService().listBeans(ModelNames.ARTICLE, criteria, new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject article = it.next();
							String name = article.getString(IArticle.TITLE);
							String id = article.getString(IArticle.ID);
							lb_optGoods.addItem(name, id);
						}
					}
		});
	}
	
	public void setGoodsId(Long goodsId) {
		ArticleValue = new HashMap<String, String>();
		this.goodsId = goodsId;
		lb_relGoods.clear();
		if(goodsId != null) {
			criteria.removeAll();
			criteria.addCondition(new Condition(IGoodsArticle.GOODS, Condition.EQUALS, goodsId));
			new ListService().listBeans(ModelNames.GOODSARTICLE, criteria, new ListService.Listener() {
				public synchronized void onSuccess(List<BeanObject> result) {
					for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
						final BeanObject goodsArticle = it.next();
						Long id = goodsArticle.getLong(IGoodsArticle.ARTICLE);
						new ReadService().getBean(ModelNames.ARTICLE, id, new ReadService.Listener() {
							public void onSuccess(BeanObject bean) {
								lb_relGoods.addItem(bean.getString(IArticle.TITLE), bean.getString(IArticle.ID));
						    }
						});
					}
				}
			});
		}
	}
	
	public Map<String, String> getValue() {
		return ArticleValue;
	}
	
	public void setValues(Long id){
		Map<String, String> goodsArticle = getValue();
		if(goodsArticle != null) {
			for(Object key : goodsArticle.keySet()) {
				Long articleId = (Long)key;
				
				final Map<String, Object> value = new HashMap<String, Object>();
				value.put(IGoodsArticle.GOODS, id);
				value.put(IGoodsArticle.ARTICLE, articleId);
				
				//判断文章是否已关联
				Criteria c = new Criteria();
				Condition goodsCon = new Condition(IGoodsArticle.GOODS, Condition.EQUALS, id);
				Condition articleCon = new Condition(IGoodsArticle.ARTICLE, Condition.EQUALS, articleId);
				c.addCondition(goodsCon);
				c.addCondition(articleCon);
				new ListService().listBeans(ModelNames.GOODSARTICLE, c, new ListService.Listener() {
					public void onSuccess(List<BeanObject> beans) {
						if(beans.size() == 0)
							new CreateService().createBean(new BeanObject(ModelNames.GOODSARTICLE, value), null);					
					}
				});
			}
		}
	}
}
