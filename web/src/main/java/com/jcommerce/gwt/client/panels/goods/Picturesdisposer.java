package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
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
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
/**
 * 批量处理图片
 * @author Daniel
 *
 */
public class Picturesdisposer extends ContentWidget {
	private IShopServiceAsync service = getService();
	private static Picturesdisposer instance;
	private ColumnPanel contentPanel = new ColumnPanel();
	private ListBox categorysList = new ListBox();
	private ListBox brandsList = new ListBox();
	private ListBox goodsList = new ListBox();
	private CheckBox isGoodsPictureCheck = new CheckBox();
	private CheckBox isGoodsPhotosCheck = new CheckBox();
	private CheckBox isGenerateThumbnailsCheck = new CheckBox();
	private CheckBox isGenerateDetailsCheck = new CheckBox();
	private RadioButton delPictures = null;
	private RadioButton overWritePics = null;
	private RadioButton skip_errors = null;
	private RadioButton exit_errors = null;
	private Button okCmd = null;
	private Button addGoodsbt = null;
	private FlexTable goodsTable = new FlexTable();
	private BeanObject goodsObject = null;
	private Map<String, String> goodsMap = new HashMap<String, String>();
	private Map<Integer, String> goodsRows = new HashMap<Integer, String>();
	private int rowIndex = 0;
	private List delBtList = new ArrayList();
	private boolean isBorken = false;
	private boolean isSuccess = true;
	private TextArea err_resultBox = new TextArea();
	private static String err_resultInBox = "";

	public static Picturesdisposer getInstance() {
		if (instance == null) {
			instance = new Picturesdisposer();
		}
		return instance;
	}

	public Picturesdisposer() {
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return Picturesdisposer.class.getName();
		}

		public String getMenuDisplayName() {
			return "图像批量处理";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "图像批量处理";
	}

	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);

		add(contentPanel);
		VerticalPanel labelPanel = new VerticalPanel();
		labelPanel.add(new Label("图片批量处理允许您重新生成商品的缩略图以及重新添加水印。\n"
				+ "该处理过程可能会比较慢，请您耐心等候。"));
		contentPanel.createPanel(null, null, labelPanel);

		HorizontalPanel listsHorizontalPanel = new HorizontalPanel();
		categorysList.addItem("所有分类");
		brandsList.addItem("所有品牌");
		goodsList.addItem("所有商品");
		listsHorizontalPanel.add(categorysList);
		listsHorizontalPanel.add(brandsList);
		listsHorizontalPanel.add(goodsList);
		addGoodsbt = new Button();
		addGoodsbt.setText("+");
		addGoodsbt.setHeight("28");
		listsHorizontalPanel.add(addGoodsbt);
		contentPanel.createPanel(null, null, listsHorizontalPanel);

		// goodsPanel.setVisible(false);
		contentPanel.createPanel(null, null, goodsTable);

		HorizontalPanel checksHorizontalPanel = new HorizontalPanel();
		isGoodsPictureCheck.setText("处理商品图片");
		isGoodsPictureCheck.setChecked(true);
		isGoodsPhotosCheck.setText("处理商品相册");
		isGoodsPhotosCheck.setChecked(true);
		checksHorizontalPanel.add(isGoodsPictureCheck);
        Label sep = new Label();
        sep.setWidth("20");
        checksHorizontalPanel.add(sep);
		checksHorizontalPanel.add(isGoodsPhotosCheck);
		contentPanel.createPanel(null, null, checksHorizontalPanel);

		isGenerateThumbnailsCheck.setText("重新生成缩略图");
		isGenerateThumbnailsCheck.setChecked(true);
		isGenerateDetailsCheck.setText("重新生成商品详情图");
		isGenerateDetailsCheck.setChecked(true);

		contentPanel.createPanel(null, null, isGenerateThumbnailsCheck);
		contentPanel.createPanel(null, null, isGenerateDetailsCheck);

		delPictures = new RadioButton("first", "新生成图片使用新名称，并删除旧图片");
		delPictures.setChecked(true);
		overWritePics = new RadioButton("first", "新生成图片覆盖旧图片");
		HorizontalPanel firstRowRadios = new HorizontalPanel();
		firstRowRadios.add(delPictures);
        sep = new Label();
        sep.setWidth("20");
        firstRowRadios.add(sep);
		firstRowRadios.add(overWritePics);
		contentPanel.createPanel(null, null, firstRowRadios);

		skip_errors = new RadioButton("second", "出错时忽略错误,继续执行程序");
		skip_errors.setChecked(true);
		exit_errors = new RadioButton("second", "出错时立即提示，并中止程序");
		HorizontalPanel secondRowRadios = new HorizontalPanel();
		secondRowRadios.add(skip_errors);
		sep = new Label();
		sep.setWidth("20");
		secondRowRadios.add(sep);
		secondRowRadios.add(exit_errors);
		contentPanel.createPanel(null, null, secondRowRadios);

		VerticalPanel btPanel = new VerticalPanel();
		okCmd = new Button();
		okCmd.setText("确定");
		btPanel.add(okCmd);
		err_resultBox.setReadOnly(true);
		err_resultBox.setCharacterWidth(80);
		err_resultBox.setVisibleLines(16);
		err_resultBox.setVisible(false);
		btPanel.add(err_resultBox);
		contentPanel.createPanel(null, null, btPanel);

		addListener();
	}

	private void addListener()
	{
		categorysList.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				clearGoods();
				Criteria cri = updateGoods(categorysList, brandsList);
				ListService listService = new ListService();
				listService.listBeans(ModelNames.GOODS, cri,
						new ListService.Listener() {
							public synchronized void onSuccess(
									List<BeanObject> result) {
								for (Iterator<BeanObject> it = result
										.iterator(); it.hasNext();) {
									BeanObject goods = it.next();
									goodsList.addItem(goods
											.getString(IGoods.NAME), goods
											.getString(IGoods.ID));
								}
							}
						});
			}

		});

		brandsList.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				clearGoods();
				Criteria cri = updateGoods(categorysList, brandsList);
				if (cri == null) {
					return;
				}
				ListService listService = new ListService();
				listService.listBeans(ModelNames.GOODS, cri,
						new ListService.Listener() {
							public synchronized void onSuccess(
									List<BeanObject> result) {
								for (Iterator<BeanObject> it = result
										.iterator(); it.hasNext();) {
									BeanObject goods = it.next();
									goodsList.addItem(goods
											.getString(IGoods.NAME), goods
											.getString(IGoods.ID));
								}
							}
						});
			}

		});

		addGoodsbt.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				int goodsIndex = goodsList.getSelectedIndex();
				String label = goodsList.getItemText(goodsIndex);
				if (goodsIndex != 0 && (goodsMap.get(label) == null)) {
					rowIndex = goodsTable.getRowCount();
					Button delGoodsbt = new Button();
					delGoodsbt.setText("X");
					delGoodsbt.setTabIndex(rowIndex);
					delBtList.add(delGoodsbt);

					delGoodsbt.addClickListener(new ClickListener() {
						public void onClick(Widget sender) {
							Button bt = (Button) sender;
							int currentRow = bt.getTabIndex();
							if (currentRow < (goodsTable.getRowCount() - 1)) {
								int btTabIndex = bt.getTabIndex();
								goodsTable.removeRow(btTabIndex);
								goodsMap.remove(goodsRows.get(currentRow));
								goodsRows.remove(currentRow);
								for (int i = currentRow + 1; i < delBtList
										.size(); i++) {
									Button bt1 = (Button) goodsTable.getWidget(
											currentRow, 1);
									bt1.setTabIndex(currentRow);

									String label = goodsRows.get(i);
									goodsRows.remove(i);
									goodsRows.put(i - 1, label);
								}
							} else {
								goodsTable.removeRow(currentRow);
								goodsMap.remove(goodsRows.get(currentRow));
								goodsRows.remove(currentRow);
							}
							delBtList.remove(currentRow);
						}

					});

					goodsTable.getCellFormatter().setVerticalAlignment(
							rowIndex, 0, HasVerticalAlignment.ALIGN_TOP);
					goodsTable.setText(rowIndex, 0, label);
					goodsTable.setWidget(rowIndex, 1, delGoodsbt);

					goodsRows.put(rowIndex, label);
					goodsMap.put(label, goodsList.getValue(goodsIndex));
				}
			}

		});

		okCmd.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				err_resultBox.setVisible(false);
				isSuccess = true;
				isBorken = false;
				err_resultInBox = "";
				updateGoodsByGoodsID();
			}
		});
	}
	private void updateGoodsByGoodsID() {
		final WaitStatus status = new WaitStatus();
		final Iterator iter = goodsMap.entrySet().iterator();

		if (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			final String id = (String) entry.getValue();
			waitGoodsDone(id, status, iter);
		}
	}

	private void waitGoodsDone(String id, final WaitStatus status,
			final Iterator iter) {
		if (isBorken == true && skip_errors.isChecked() == false)
			return;
		processGoodsPicturesORPhotos(id, status);

		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				return !status.isNeedWait();
			}

			public void run() {
				if (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					final String id = (String) entry.getValue();

					waitGoodsDone(id, status, iter);
				} else {
					if (isSuccess == true) {
						Window.alert("成功批量处理图片");
					} else {
						if (skip_errors.isChecked() == true) {
							err_resultBox.setVisible(true);
							err_resultBox.setText(err_resultInBox);
						}
					}
				}
			}
		});
	}

	private void processGoodsPicturesORPhotos(String id, final WaitStatus status) {

		status.setStatusAsWorking();
		new ReadService().getBean(ModelNames.GOODS, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject goods) {
						processGoodsPhotos(goods, status);
						processGoodsPictures(goods, status);
					}
				});
	}

	private void processGoodsPhotos(BeanObject goods, final WaitStatus status) {
		/**
		 * 根据选择的checkBox 来确定处理方法
		 */

		if (!isGoodsPhotosCheck.isChecked()) {
			return;
		}

		boolean isGenerateDetails = false;
		boolean isGenerateThumbnails = false;
		boolean isErroSkip = false;

		if (isGenerateDetailsCheck.isChecked())
			isGenerateDetails = true;

		if (isGenerateThumbnailsCheck.isChecked())
			isGenerateThumbnails = true;

		if (skip_errors.isChecked())
			isErroSkip = true;
		service.disposePhotos(goods, isGenerateDetails, isGenerateThumbnails,
				isErroSkip, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						status.setStatusAsError();
					}

					public void onSuccess(String err) {
						status.setStatusAsFinshed();
						if (err != null) {
							isSuccess = false;
							if (skip_errors.isChecked() == false) {
								Window.alert(err);
								isBorken = true;
							} else {
								err_resultInBox += err + "\n";
							}
						}
					}
				});
	}

	private void processGoodsPictures(BeanObject goods, final WaitStatus status) {
		if (!isGoodsPictureCheck.isChecked()) {
			return;
		}

		boolean isGenerateDetails = false;
		boolean isGenerateThumbnails = false;
		boolean isErroSkip = false;

		if (isGenerateDetailsCheck.isChecked())
			isGenerateDetails = true;

		if (isGenerateThumbnailsCheck.isChecked())
			isGenerateThumbnails = true;

		if (skip_errors.isChecked())
			isErroSkip = true;
		service.disposePictures(goods, isGenerateDetails, isGenerateThumbnails,
				isErroSkip, new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						status.setStatusAsError();
					}

					public void onSuccess(String err) {
						status.setStatusAsFinshed();
						if (err != null) {
							if (skip_errors.isChecked() == false) {
								Window.alert(err);
								isBorken = true;
								isSuccess = false;
							} else {
								err_resultInBox += err + "\n";
							}
						}
					}
				});
	}

	/* 生成合适的Criteria对象.. 用于生成所需要的商品 */
	private Criteria updateGoods(ListBox lstCategorys, ListBox lstBrands) {
		int catIndex = lstCategorys.getSelectedIndex();
		int braIndex = lstBrands.getSelectedIndex();
		if (catIndex < 1 && braIndex < 1) {
			return null;
		}

		if (catIndex > 0 && braIndex < 1) {
			final String cat = lstCategorys.getValue(catIndex);
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IGoods.CATEGORIES,
					Condition.CONTAINS, cat));
			return criteria;
		}

		if (braIndex > 0 && catIndex < 1) {
			final String bra = lstBrands.getValue(braIndex);
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IGoods.BRAND, Condition.EQUALS,
					bra));
			return criteria;
		}

		if (braIndex > 0 && catIndex > 0) {
			final String cat = lstCategorys.getValue(catIndex);
			final String bra = lstBrands.getValue(braIndex);
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IGoods.BRAND, Condition.EQUALS,
					bra));
			criteria.addCondition(new Condition(IGoods.CATEGORIES,
					Condition.CONTAINS, cat));
			return criteria;
		}

		return null;
	}

	public void clearValues() {
		categorysList.clear();
		brandsList.clear();
		categorysList.addItem("所有分类");
		brandsList.addItem("所有品牌");
	}

	public void clearGoods() {
		goodsList.clear();
		goodsList.addItem("所有商品");
	}

	public void refresh() {
		clearValues();
		ListService listService = new ListService();
		listService.listBeans(ModelNames.CATEGORY, new ListService.Listener() {
			public synchronized void onSuccess(List<BeanObject> result) {
				List<String> pids = new ArrayList<String>();
				for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
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
			public synchronized void onSuccess(List<BeanObject> beans) {
				for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
					BeanObject brand = it.next();
					brandsList.addItem(brand.getString(IBrand.NAME), brand
							.getString(IBrand.ID));
				}
			}

		});
	}

	private class WaitStatus {

		private int status = 0;
		private static final int STARTING = 0;
		private static final int WORKING = 1;
		private static final int FINISHED = 2;
		private static final int ERROR = 3;

		public void setStatusAsStarting() {
			status = STARTING;
		}

		public void setStatusAsWorking() {
			status = WORKING;
		}

		public void setStatusAsFinshed() {
			status = FINISHED;
		}

		public void setStatusAsError() {
			status = ERROR;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public boolean isNeedWait() {
			if (status != WORKING)
				return false;
			else
				return true;
		}

		public WaitStatus() {
			setStatusAsStarting();
		}
	}
}
