package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * 生成商品代码
 * @author Daniel
 *
 */
public class GenerateGoodsCode extends ContentWidget {

	private IShopServiceAsync service = getService();
	private Button generateCodesButton = new Button();
	private ListBox categorysList = new ListBox();
	private ListBox mainCategory = new ListBox();
	private ListBox brandsList = new ListBox();
	private ListBox show = new ListBox();
	private TextBox number = new TextBox();
	private ListBox sortorder = new ListBox();
	private ListBox charset = new ListBox();
	private TextBox site = new TextBox();
	private TextArea codeArea = new TextArea();
	private ColumnPanel contentPanel = new ColumnPanel();

	private BeanObject category = null;
	private Map<String, BeanObject> categorys = new HashMap<String, BeanObject>();

	private BeanObject brand = null;
	private Map<String, BeanObject> brands = new HashMap<String, BeanObject>();

	private static GenerateGoodsCode instance;

	public static GenerateGoodsCode getInstance() {
		if (instance == null) {
			instance = new GenerateGoodsCode();
		}
		return instance;
	}

	public GenerateGoodsCode() {
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return GenerateGoodsCode.class.getName();
		}

		public String getMenuDisplayName() {
			return "生成商品代码";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {

		return "生成商品代码";
	}

	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);

		add(contentPanel);
		contentPanel.createPanel(IGoods.CATEGORIES, "选择商品分类: ", categorysList);
		contentPanel.createPanel(IGoods.BRAND, "选择商品品牌: ", brandsList);

		mainCategory.addItem("请选择所有推荐类型");
		mainCategory.addItem("精品", IGoods.BESTSOLD);
		mainCategory.addItem("新品", IGoods.NEWADDED);
		mainCategory.addItem("热销", IGoods.HOTSOLD);
		contentPanel.createPanel(IGoods.MAINCATEGORY, "选择推荐类型: ", mainCategory);

		show.addItem("显示", "true");
		show.addItem("不显示", "false");
		contentPanel.createPanel(ICategory.SHOW, "是否显示:", show);

		number.setText("1");
		contentPanel.createPanel(IGoods.NUMBER, "显示商品数量: ", number);

		sortorder.addItem("横排", "horizontal ");
		sortorder.addItem("竖排", "vertical");
		contentPanel.createPanel(ICategory.SORTORDER, "选择商品排列方式: ", sortorder);

		charset.addItem("国际化编码(utf8)", "utf8");
		charset.addItem("简体中文", "gb2312");
		charset.addItem("繁体中文", "BIG5");
		contentPanel.createPanel("charset", "选择编码: ", charset);

		contentPanel.createPanel(IBrand.SITE, "引用站点名称: ", site);

		VerticalPanel panel = new VerticalPanel();
		generateCodesButton.setText("生成代码");
		panel.add(generateCodesButton);
		panel.setSpacing(20);
		codeArea.setCharacterWidth(80);
		codeArea.setVisibleLines(10);
		panel.add(codeArea);

		contentPanel.createPanel(null, null, panel);

		generateCodesButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				float i = (float) 0.0;
				try {
					i = Float.valueOf(number.getText()).floatValue();
				} catch (Exception e) {
					Window.alert("商品数量应该为整数!");
					return;
				}

				int num = (int) i;
				if (num != i) {
					Window.alert("商品数量应该为整数!");
					return;
				}
				String code, front_c, end_c;
				front_c = "<script src=\"http://localhost/goods_script.php?\n";
				end_c = "\"></script>";
				String cat_id = "", brand_id = "", intro_type = "", need_image = "";
				String goods_num = "", arrange = "", charsetStr = "", sitename = "";
				if (categorysList.getSelectedIndex() > 0) {
					cat_id = "cat_id=" + categorysList.getSelectedIndex() + "&";
				}
				if (brandsList.getSelectedIndex() > 0) {
					brand_id = "brand_id=" + brandsList.getSelectedIndex()
							+ "&";
				}
				if (mainCategory.getSelectedIndex() > 0) {
					intro_type = "intro_type="
							+ mainCategory.getValue(mainCategory
									.getSelectedIndex()) + "&";
				}

				need_image = "need_image="
						+ show.getValue(show.getSelectedIndex());
				goods_num = "goods_num=" + num;
				arrange = "arrange="
						+ sortorder.getValue(sortorder.getSelectedIndex());
				charsetStr = "charset="
						+ charset.getValue(charset.getSelectedIndex());
				sitename = "sitename=" + site.getText();

				code = front_c + cat_id + brand_id + intro_type + need_image
						+ "&" + goods_num + "&" + arrange + "&" + charsetStr
						+ "&" + sitename + end_c;

				codeArea.setText(code);
			}

		});

	}

	public void clearValues() {
		categorysList.clear();
		categorysList.addItem("请选择商品分类");
		mainCategory.setSelectedIndex(0);
		brandsList.clear();
		brandsList.addItem("请选择商品品牌");
		number.setText("1");
		codeArea.setText("");
		sortorder.setSelectedIndex(0);
		show.setSelectedIndex(0);
		charset.setSelectedIndex(0);
		site.setText("");

	}

	public void refresh() {
		clearValues();
		ListService listService = new ListService();
		listService.listBeans(ModelNames.CATEGORY, new ListService.Listener() {
			public synchronized void onSuccess(List<BeanObject> result) {
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
						name = "   " + name;
					}
					categorysList.addItem(name, id);
				}
			}
		});

		listService.listBeans(ModelNames.BRAND, new ListService.Listener() {
			@Override
			public synchronized void onSuccess(List<BeanObject> beans) {
				for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
					BeanObject brand = it.next();
					brandsList.addItem(brand.getString(IBrand.NAME), brand
							.getString(IBrand.ID));
				}
			}

		});
	}

}
