/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jcommerce.gwt.client.panels.goods;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.Utils;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.ILinkGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.validator.IntegerChecker;
import com.jcommerce.gwt.client.validator.PriceChecker;
import com.jcommerce.gwt.client.validator.SpaceChecker;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;
import com.jcommerce.gwt.client.widgets.MultiValueSelector;
import com.jcommerce.gwt.client.widgets.WidgetInfo;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

public abstract class NewGoodsBase extends ContentWidget {
	public static interface Constants {
		String NewGoods_title();

		String NewGoods_create();

		String NewGoods_cancel();

		String NewGoods_tabGeneral();

		String NewGoods_tabDetail();

		String NewGoods_tabOther();

		String NewGoods_tabProperty();

		String NewGoods_tabGallery();

		String NewGoods_tabLink();

		String NewGoods_tabAccessories();

		String NewGoods_tabArticle();
		
		String EditGoods_title();
        String NewGoods_recommend();
        String NewGoods_promote();
        String NewGoods_calculateByMaretPrice();
        String NewGoods_onSaleOrNot();
        String NewGoods_imageDescription();
        String NewGoods_imageFile();
        String NewGoods_type();
        String NewGoods_tipSN();
        String NewGoods_tipGiveIntegral();
        String NewGoods_tipRankIntegral();
        String NewGoods_tipIntegral();
        String NewGoods_editGoods();
        String NewGoods_addSuccessfully();
	}

	private ColumnPanel contentPanelGeneral = new ColumnPanel();
	private ColumnPanel contentPanelOther = new ColumnPanel();
	private AttributePanel attrPanel = new AttributePanel();
	private GalleryPanel galleryPanel = null;
	private RelatedPanel relatedPanel = new RelatedPanel();
	private AccessoriesPanel accessoriesPanel = new AccessoriesPanel();
	private ArticlesPanel articlesPanel = new ArticlesPanel();
	private RichTextArea txtDetail;
	
	private ListBox lstBrand = new ListBox();
    private ListBox lstCategory = new ListBox();
    
	private Button btnOK = new Button();
	private Button btnCancel = new Button();

	boolean virtualCard = false;
	
	public NewGoodsBase() {
	}

	abstract BeanObject getGoods();
	    
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		System.out.println("onRender "+hashCode()+" "+getCurState().isEditting());
		
		BeanObject goods = getGoods();
		boolean editting = getCurState().isEditting();
		final String goodsId = goods != null ? goods.getString(IGoods.ID) : null;

		contentPanelGeneral.createPanel(IGoods.NAME, Resources.constants.Goods_name(), new TextBox(), new SpaceChecker(
				Resources.constants.Goods_name()));

		WidgetInfo info = new WidgetInfo(IGoods.SN, Resources.constants.Goods_SN(), new TextBox());
        info.setNote("如果您不输入商品货号，系统将自动生成一个唯一的货号。");
        contentPanelGeneral.createPanel(info);

        Button btnAddBrand = new Button("添加品牌");
        btnAddBrand.addClickHandler(new ClickHandler() {            
            public void onClick(ClickEvent arg0) {
                NewBrand.State state = new NewBrand.State();
                state.setBackPage("继续"+getCurState().getMenuDisplayName(), getCurState());
                state.execute();                
            }
        });
		contentPanelGeneral.createPanel(IGoods.BRAND, Resources.constants.Goods_brand(), lstBrand, btnAddBrand);

        Button btnAddCat = new Button("添加商品分类");
        btnAddCat.addClickHandler(new ClickHandler() {            
            public void onClick(ClickEvent arg0) {
                NewCategory.State state = new NewCategory.State();
                state.setBackPage("继续"+getCurState().getMenuDisplayName(), getCurState());
                state.execute();                
            }
        });
        info = new WidgetInfo(IGoods.MAINCATEGORY, Resources.constants.Goods_category(), lstCategory);
        info.setValidator(new SpaceChecker(Resources.constants.Goods_category()));
        info.setAppendWidget(btnAddCat);
		contentPanelGeneral.createPanel(info);
		
		MultiValueSelector mselector = new MultiValueSelector();
		mselector.setBean(ModelNames.CATEGORY);
		mselector.setCaption("Select Category");
		mselector.setMessage("Select Category");
		contentPanelGeneral.createPanel(IGoods.CATEGORIES, Resources.constants.Goods_category_extended(), mselector);
		contentPanelGeneral.createPanel(IGoods.SHOPPRICE, Resources.constants.Goods_shopPrice(), new TextBox(), new PriceChecker(
				Resources.constants.Goods_shopPrice(), 0, false));
		contentPanelGeneral.createPanel(IGoods.MARKETPRICE, Resources.constants.Goods_marketPrice(), new TextBox(), new PriceChecker(
				Resources.constants.Goods_marketPrice(), 0, true));
		contentPanelGeneral.createPanel(IGoods.GIVEINTEGRAL,
				Resources.constants.Goods_giveIntegral(), new TextBox());
		contentPanelGeneral.createPanel(IGoods.INTEGRAL, Resources.constants.Goods_integral(), new TextBox());

		contentPanelGeneral.createPanel(IGoods.PROMOTEPRICE, Resources.constants.Goods_promotePrice(), new TextBox(), new PriceChecker(
						Resources.constants.Goods_promotePrice(), 0, true));
		final FileUploader imageUpload = new FileUploader();
		imageUpload.addAllowedTypes(new String[] { ".jpg", ".gif" });
//		contentPanelGeneral.createPanel(IGoods.IMAGE, Resources.constants.Goods_image(), imageUpload);
		final FileUploader thumbUpload = new FileUploader();
		thumbUpload.addAllowedTypes(new String[] { ".jpg", ".gif" });
		
		if(editting){
		    imageUpload.setImageInfo(ModelNames.GOODS, goodsId, IGoods.IMAGE);
		    thumbUpload.setImageInfo(ModelNames.GOODS, goodsId, IGoods.THUMB);
		}

		contentPanelGeneral.createPanel(IGoods.IMAGE, Resources.constants.Goods_image(), imageUpload);
		contentPanelGeneral.createPanel(IGoods.THUMB, Resources.constants.Goods_thumb(), thumbUpload);
		
		contentPanelOther.createPanel(IGoods.WEIGHT, Resources.constants.Goods_weight(), new TextBox());
		contentPanelOther.createPanel(IGoods.NUMBER, Resources.constants.Goods_number(), new TextBox(), new IntegerChecker(
				Resources.constants.Goods_number(), 0, true));
		contentPanelOther.createPanel(IGoods.WARNNUMBER, Resources.constants.Goods_warnNumber(), new TextBox(), new IntegerChecker(
				Resources.constants.Goods_number(), 0, true));
		contentPanelOther.createPanel(IGoods.HOTSOLD, Resources.constants.Goods_hotsold(), new CheckBox());
		contentPanelOther.createPanel(IGoods.NEWADDED, Resources.constants.Goods_newAdded(), new CheckBox());
		contentPanelOther.createPanel(IGoods.BESTSOLD, Resources.constants.Goods_bestSold(), new CheckBox());
		
		info = new WidgetInfo(IGoods.ONSALE, Resources.constants.Goods_onSale(), new CheckBox());
		info.setNote("打勾表示允许销售，否则不允许销售。");
        contentPanelOther.createPanel(info);
        info = new WidgetInfo(IGoods.ALONESALE, "能作为普通商品销售", new CheckBox());
        info.setNote("打勾表示能作为普通商品销售，否则只能作为配件或赠品销售");
        contentPanelOther.createPanel(info);
        info = new WidgetInfo(IGoods.KEYWORDS, Resources.constants.Goods_keywords(), new TextBox());
        info.setNote("用空格分隔");
        info.setAppendNote(true);
        contentPanelOther.createPanel(info);
        TextArea area = new TextArea();
        area.setSize("600", "150");
		contentPanelOther.createPanel(IGoods.BRIEF, Resources.constants.Goods_brief(), area);
        area = new TextArea();
        area.setSize("600", "80");
        
        info = new WidgetInfo(IGoods.SELLERNOTE, Resources.constants.Goods_sellerNote(), area);
        info.setNote("仅供商家自己看的信息");
        contentPanelOther.createPanel(info);

		galleryPanel = new GalleryPanel(editting, goods);
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		btnOK.setText("确定");
		btnCancel.setText("重置");
		panel.add(btnOK);
		panel.add(btnCancel);

		// Create a tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);

		// Add a home tab
		tabPanel.add(contentPanelGeneral, Resources.constants.NewGoods_tabGeneral());

		// Create the text area and toolbar
		txtDetail = new RichTextArea();
		txtDetail.setSize("100%", "14em");
		if(editting){
			new ReadService().getBean(ModelNames.GOODS, goodsId, new ReadService.Listener() {
				public void onSuccess(BeanObject bean) {
					txtDetail.setHTML(bean.getString(IGoods.DESCRIPTION));
			    }
			});
		}
		RichTextToolbar toolbar = new RichTextToolbar(txtDetail);
//		toolbar.setWidth("100%");

		// Add the components to a panel
		Grid grid = new Grid(2, 1);
		grid.setStyleName("cw-RichText");
		grid.setWidget(0, 0, toolbar);
		grid.setWidget(1, 0, txtDetail);

		// Add a detail tab
		tabPanel.add(grid, Resources.constants.NewGoods_tabDetail());

		// Add a other tab
		tabPanel.add(contentPanelOther, Resources.constants.NewGoods_tabOther());

		// Add a Properties tab
		tabPanel.add(attrPanel, Resources.constants.NewGoods_tabProperty());

		// Add a Pictures tab
		tabPanel.add(galleryPanel, Resources.constants.NewGoods_tabGallery());

		// Add a Connet other goods tab
		// HTML conngoods = new HTML("connect goods");
		tabPanel.add(relatedPanel, Resources.constants.NewGoods_tabLink());

		// Add a Accessories tab
		// HTML accessories = new HTML("accessories");
		if (!virtualCard) {
		    tabPanel.add(accessoriesPanel, Resources.constants.NewGoods_tabAccessories());
		}

		// Add a Connet articles tab
		// HTML articles = new HTML("articles");
		tabPanel.add(articlesPanel, Resources.constants.NewGoods_tabArticle());

		if (editting) {
		    refresh();
		}
		
		// Return the content
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		add(tabPanel);
		add(panel);
		createList(null, null);
		btnOK.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                imageUpload.setStoreType("img");
				if (!imageUpload.submit()) {
					return;
				}
				
				thumbUpload.setStoreType("thumb");
				if (!thumbUpload.submit()) {
					return;
				}

				List<FileUploader> fileUploaders = galleryPanel.getUploaders();
				FileUploader fu = new FileUploader();
				for (Iterator it = fileUploaders.iterator(); it.hasNext();) {
					fu = (FileUploader) it.next();
					fu.setStoreType("img_thumb");
					if (!fu.submit()) {
						return;
					}
				}

				new WaitService(new WaitService.Job() {
					public boolean isReady() {
					  List<FileUploader> fileUploaders2 = galleryPanel.getUploaders();
						FileUploader fu2 = new FileUploader();
						for (Iterator it = fileUploaders2.iterator(); it.hasNext();) {
							fu2 = (FileUploader) it.next();
							if (!fu2.isFinish()) {
								return false;
							}
						}
						return imageUpload.isFinish() && thumbUpload.isFinish();
					}

					public void run() {
						if (!validate()) {
							return;
						}

						Date currentTime = new Date();
						Map<String, Object> argsLeft = contentPanelGeneral.getValues();
						Map<String, Object> argsDetail = new HashMap<String, Object>();
						argsDetail.put(IGoods.DESCRIPTION, txtDetail.getHTML());
						Map<String, Object> argsRight = contentPanelOther.getValues();
						Map<String, Object> argsAttrs = attrPanel.getValues();

						// Gallery
						Map<String, Object> argsGallery = galleryPanel.getValues();
						
						argsLeft.putAll(argsDetail);
						argsLeft.putAll(argsRight);
						argsLeft.putAll(argsAttrs);
						argsLeft.putAll(argsGallery);
						argsLeft.put("addTime", currentTime.getTime());// addTime information
						argsLeft.put(IGoods.REALGOODS, !virtualCard + "");
                        argsLeft.put(IGoods.DELETED, "false");
						
						if (getCurState().isEditting()) {
							new UpdateService().updateBean(goodsId, new BeanObject(ModelNames.GOODS, argsLeft),null);
                            
							if (virtualCard) {
                                VirtualCardList.State state = new VirtualCardList.State();
                                state.execute();                                
                            } else {
                                GoodsList.State state = new GoodsList.State();
                                state.execute();
                            }
						} else {
							new CreateService().createBean(new BeanObject(ModelNames.GOODS, argsLeft), new CreateService.Listener() {
								public void onSuccess(final String id) {
                                    relatedPanel.setValues(id);
                                    if (!virtualCard) {
                                        accessoriesPanel.setValues(id);
                                    }
                                    articlesPanel.setValues(id);
                                    
									Map<String, Boolean> linkGoods = relatedPanel.getValue();
									if(linkGoods != null) {
										for(Object key : linkGoods.keySet()) {
											boolean bidirectional = linkGoods.get(key);
											String linkGoodsId = (String)key;
											
											final Map<String, Object> value = new HashMap<String, Object>();
											value.put(ILinkGoods.GOODS, id);
											value.put(ILinkGoods.LINKGOODS, linkGoodsId);
											value.put(ILinkGoods.BIDIRECTIONAL, bidirectional);
											
											//判断是否已关											
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
							});

							if (virtualCard) {
                                VirtualCardList.State state = new VirtualCardList.State();
                                state.execute();							    
							} else {
    							GoodsList.State state = new GoodsList.State();
                                state.execute();
							}
						}
					}
				});
			}
		});

		btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				contentPanelGeneral.clearValues();
				contentPanelOther.clearValues();
				attrPanel.updateValues(null);
			}
		});
	}

	private void displayError(List<String> errs) {
		StringBuffer sb = new StringBuffer();
		for (String err : errs) {
			sb.append(err).append("<br>");
		}
		MessageBox.alert("ERROR", sb.toString(), null);
	}

	private boolean validate() {
		List<String> errs = contentPanelGeneral.validate();
		if (errs == null || errs.size() == 0) {
			errs = contentPanelOther.validate();
		}

		if (errs != null && errs.size() > 0) {
			displayError(errs);
			return false;
		}

		return true;
	}

	private void createList(final String defBrand, final String defCategory) {
		new ListService().listBeans(ModelNames.BRAND,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> beans) {
						for (BeanObject brand : beans) {
							lstBrand.addItem(brand.getString(IBrand.NAME), brand
									.getString(IBrand.ID));
							
							if (defCategory != null) {
	                            Utils.setSelectedValue(lstBrand, defBrand);
	                        }
						}
					}
				});
        new ListService().listBeans(ModelNames.CATEGORY,
                new ListService.Listener() {
                    public void onSuccess(List<BeanObject> beans) {
                        for (BeanObject category : beans) {
                            lstCategory.addItem(category.getString(ICategory.NAME), category
                                    .getString(ICategory.ID));
                        }
                        if (defCategory != null) {
                            Utils.setSelectedValue(lstCategory, defCategory);
                        }
                    }
                });
	}

	public void refresh() {
	    if (getCurState().getPageInstance() == this) {
	        refreshLists();
	        return;
	    }
	    
	    BeanObject goods = getGoods();	   
		if ( goods != null && goods.getString(ICategory.ID) != null) {
			contentPanelGeneral.updateValues(goods.getProperties());
			contentPanelOther.updateValues(goods.getProperties());
			attrPanel.updateValues(goods);
			
			String goodsId = goods.getString(ICategory.ID);
			relatedPanel.setGoodsId(goodsId);
			if (!virtualCard) {
			    accessoriesPanel.setGoodsId(goodsId);
			}
			articlesPanel.setGoodsId(goodsId);
			goods = null;
		} else {
			contentPanelGeneral.clearValues();
			contentPanelOther.clearValues();
			attrPanel.updateValues(null);
			
			getCurState().setEditting(false);
		}

        getCurState().setPageInstance(this);        
	}

    private void refreshLists() {
        String brandSelected = Utils.getSelectedValue(lstBrand); 
        lstBrand.clear();
        
        String catSelected = Utils.getSelectedValue(lstCategory);
        lstCategory.clear();
        
        createList(brandSelected, catSelected);
    }
}
