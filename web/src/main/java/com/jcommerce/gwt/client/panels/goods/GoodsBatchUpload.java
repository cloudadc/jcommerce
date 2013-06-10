package com.jcommerce.gwt.client.panels.goods;

import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.GoodsBatchUploadService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

public class GoodsBatchUpload extends ContentWidget{

	
	public static class State extends PageState {
		public String getPageClassName() {
			return GoodsBatchUpload.class.getName();
		}

		public String getMenuDisplayName() {
			return "商品批量上传";
		}
	}

	private State curState = new State();

	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "商品批量上传";
	}
	
	ColumnPanel contentPanel = new ColumnPanel();
	private ListBox categoryLstBox = new ListBox();
	private ListBox encodingLstBox = new ListBox();
	private Button okButton = new Button();
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		createCategoryLstBox();
		createEncodingLstBox();
		
		contentPanel.createPanel(IGoods.CATEGORIES, Resources.constants
				.Goods_category(), categoryLstBox);
		contentPanel.createPanel("encoder", "文件编码", encodingLstBox);
		
		final FileUploader CSVLoader = new FileUploader();
		CSVLoader.addAllowedTypes(new String[] { ".csv" });
		CSVLoader.setStoreType("csv");
		contentPanel.createPanel("uploader", "上传批量csv文件", CSVLoader);
		
		HTML en_a = new HTML(
			"<a href=\""+GWT.getModuleBaseURL()+"/downloadService/?fileName=goods_list.csv\">下载批量csv文件(美国英语)</a>");
		HTML cn_s = new HTML(
			"<a href=\""+GWT.getModuleBaseURL()+"/downloadService/?fileName=goods_list_cn.csv\">下载批量csv文件(简体中文)</a>");
		HTML cn_t = new HTML(
			"<a href=\""+GWT.getModuleBaseURL()+"/downloadService/?fileName=goods_list_cht.csv\">下载批量csv文件(繁体中文)</a>");
		
		contentPanel.createPanel(null, null, en_a);
		contentPanel.createPanel(null, null, cn_s);
		contentPanel.createPanel(null, null, cn_t);
		
		okButton.setText("确定");
		contentPanel.createPanel(null, null, okButton);
		okButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			public void componentSelected(ButtonEvent be) {
				if (!CSVLoader.submit()) {
					return;
				}

				new WaitService(new WaitService.Job() {
					public boolean isReady() {
						return CSVLoader.isFinish();
					}

					public void run() {
						int index = categoryLstBox.getSelectedIndex();
						if (index == 0) {
							return;
						}
						String category = categoryLstBox.getValue(index);
						String encoding = encodingLstBox
								.getValue(encodingLstBox.getSelectedIndex());
						String path = CSVLoader.getValue();/* images/yymm/uuid */

						System.out.println("");
						System.out.println("");
						System.out.println(path);
						System.out.println("");
						System.out.println("");
						executeAction(category, encoding, path);
					}
				});
			}
		});
		
		contentPanel
				.add(new HTML(
						"<html>使用说明："
						+ "<br>1.根据使用习惯，下载相应语言的csv文件，例如中国内地用户下载简体中文语言的文件，港台用户下载繁体语言的文件；"
						+ "<br>2.填写csv文件，可以使用excel或文本编辑器打开csv文件；碰到“是否精品”之类，填写数字0或者1，0代表“否”，1代表“是”；"
						+ "商品图片和商品缩略图请填写带路径的图片文件名，其中路径是相对于 [根目录]/images/ 的路径，例如图片路径为[根目录]/images/200610/abc.jpg，"
						+ "只要填写 200610/abc.jpg 即可；"
						+ "<br>3.将填写的商品图片和商品缩略图上传到相应目录，"
						+ "例如：[根目录]/images/200610/；请首先上传商品图片和商品缩略图再上传csv文件，否则图片无法处理。"
						+ "<br>4.选择所上传商品的分类以及文件编码，上传csv文件" + "</html>"));

		add(contentPanel);
	}
	private void createCategoryLstBox() {
		categoryLstBox.addItem("请选择...", "---");
		new ListService().listBeans(ModelNames.CATEGORY,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> beans) {
						for (Iterator<BeanObject> it = beans.iterator(); it
								.hasNext();) {
							BeanObject cat = it.next();
							if (cat.getString(ICategory.PARENT) == null) {
								categoryLstBox.addItem(cat
										.getString(ICategory.NAME), cat
										.getString(ICategory.ID));
							} else {
								categoryLstBox.addItem("\t\t"
										+ cat.getString(ICategory.NAME), cat
										.getString(ICategory.ID));
							}
						}

					}
				});
	}
	private void createEncodingLstBox() {
		encodingLstBox.addItem("国际化编码utf8", "utf-8");
		encodingLstBox.addItem("简体中文", "gbk");
		encodingLstBox.addItem("繁体中文", "Big5");
	}
	private void executeAction(String category, String encoding, String path) {
;
		new GoodsBatchUploadService().getBeans(ModelNames.GOODS, path, category,encoding, 
				new GoodsBatchUploadService.Listener() {
					
					public void onSuccess(List<BeanObject> objs) {
						GoodsList.State state = new GoodsList.State();
						state.execute();
						
					}
				});
	}

}
