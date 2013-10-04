package com.jcommerce.gwt.client.panels.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DatebaseRestoreService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.DatebaseRestoreService.Listener;
import com.jcommerce.gwt.client.service.DatebaseRestoreService.deleteFileListener;
import com.jcommerce.gwt.client.service.DatebaseRestoreService.restoreFileListener;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class DatabaseRestore extends ContentWidget {
    private PagingToolBar toolBar;
    private VerticalPanel vp;
    private FlexTable stocksFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Label lastUpdatedLabel = new Label();
    private ArrayList<String> stocks = new ArrayList<String>();
    private static final int REFRESH_INTERVAL = 5000; // ms
    private MessageBox processBar=MessageBox.wait("请等待", "正在还原...", "");

    public DatabaseRestore() {
        super();
        curState = new State();
    }

    private static DatabaseRestore instance;

    public static DatabaseRestore getInstance() {
        if (instance == null) {
            instance = new DatabaseRestore();
        }
        return instance;
    }

    public static class State extends PageState {
        public String getPageClassName() {
            return DatabaseRestore.class.getName();
        }

        public String getMenuDisplayName() {
            return "数据还原";
        }
    }

    public State getCurState() {
        return (State)curState;
    }

    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "数据还原";
    }

    /**
     * generate the backup file name based on the time
     */

    private String getBackUpFileName() {

        return DateTimeFormat.getFormat("yyyyMMddHHmmss").format(new Date());

    }

    protected void onRender(com.google.gwt.user.client.Element parent, int index) {
        super.onRender(parent, index);
        vp = new VerticalPanel();
        // createGrid();
        createBackupPanel();

        // 为了调整页面
        Label label = new Label("");
        label.setPixelSize(100, 40);
        Label label2 = new Label("服务器上备份文件");
        vp.add(label);
        vp.add(label2);

        stocksFlexTable.setText(0, 0, "文件名");
        stocksFlexTable.setText(0, 1, "大小");
        stocksFlexTable.setText(0, 2, "时间");
        stocksFlexTable.setText(0, 3, "移除");
        stocksFlexTable.setText(0, 4, "导入");

        stocksFlexTable.setCellPadding(6);

        // Add styles to elements in the stock list table.
        stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");

        stocksFlexTable.addStyleName("watchList");
        stocksFlexTable.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListTimeColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 4, "watchListRemoveColumn");

        // 提示用户等待
        stocksFlexTable.setText(1, 0, "please wait.......");

        addPanel.add(stocksFlexTable);
        addPanel.addStyleName("addPanel");
        //先关闭processBar，不显示
        processBar.close();
        new DatebaseRestoreService().getAllFileInfo(new Listener() {

            public void onSuccess(List<List<String>> success) {
                // TODO Auto-generated method stub
                stocksFlexTable.clear();
                List<List<String>> filesInfo = success;
                if (filesInfo.size() == 0) {
                    stocksFlexTable.setText(1, 0, "没有备份文件");
                }
                int row = 1;
                for (List<String> fileInfo : filesInfo) {

                    stocksFlexTable.setText(row, 0, fileInfo.get(0));
                    stocksFlexTable.setText(row, 1, fileInfo.get(1));
                    stocksFlexTable.setText(row, 2, fileInfo.get(2));
                    stocksFlexTable.getCellFormatter().addStyleName(row, 0, "watchListNumericColumn");
                    stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
                    stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListTimeColumn");
                    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
                    stocksFlexTable.getCellFormatter().addStyleName(row, 4, "watchListRemoveColumn");
                    Button deleteFileButton = new Button("删除");
                    Button restoreButton = new Button("导入");
                    stocksFlexTable.setWidget(row, 3, deleteFileButton);
                    stocksFlexTable.setWidget(row, 4, restoreButton);
                    row++;
                    final String fileName = fileInfo.get(0);
                    stocks.add(fileName);
                    deleteFileButton.addClickHandler(new ClickHandler() {

                        public void onClick(ClickEvent arg0) {

                            new DatebaseRestoreService().deleteFile(fileName, new deleteFileListener() {

                                public void onSuccess(String result) {
                                    if (result.equals("success")) {
                                        int removedIndex = stocks.indexOf(fileName);
                                        stocks.remove(removedIndex);
                                        stocksFlexTable.removeRow(removedIndex + 1);
                                        Info.display("恭喜", "删除文件成功");
                                        if (stocksFlexTable.getRowCount() == 1) {
                                            stocksFlexTable.setText(1, 0, "没有备份文件");
                                        }
                                    } else {
                                        Info.display("", "删除文件失败");
                                    }
                                }

                                public void onFailure(Throwable caught) {
                                    Info.display("", "系统存在错误");
                                }
                            });

                        }
                    });
                    restoreButton.addClickHandler(new ClickHandler() {
                    	
                        public void onClick(ClickEvent arg0) {
                        	System.out.println("fileName"+fileName);
                        	processBar.show();
                            new DatebaseRestoreService().restoreFile(fileName, new restoreFileListener() {

                                public void onSuccess(String result) {
                                	processBar.close();
                                    if (result.equals("success")) {
                                        Info.display("恭喜", "还原数据成功");
                                    }else if(result.equals("UnsupportedEncodingException")) {
                                        Info.display("", "还原数据失败，文件编码类型错误");
                                    }else if(result.equals("IOException")){
                                    	Info.display("","还原数据失败，读写文件发生错误");
                                    }else if(result.equals("SQLException")){
                                    	Info.display("","还原数据失败，数据操作失败");
                                    }
                                    
                                }

                                public void onFailure(Throwable caught) {
                                	processBar.close();
                                    Info.display("", "系统存在错误");
                                }

                            });

                        }
                    });
                }
            }
        });
        vp.add(addPanel);

        add(vp);

    }

    private void createGrid() {

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.BRAND);

        loader.load(0, 50);

        final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });

        toolBar = new PagingToolBar(50);
        toolBar.bind(loader);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        // CheckBoxSelectionModel<BeanObject> sm = new
        // CheckBoxSelectionModel<BeanObject>();
        // columns.add(sm.getColumn());
        columns.add(new ColumnConfig(IBrand.NAME, "品牌名称", 80));
        columns.add(new ColumnConfig(IBrand.SITE, "品牌网址", 150));
        columns.add(new ColumnConfig(IBrand.DESC, "品牌描述", 230));
        columns.add(new ColumnConfig(IBrand.ORDER, "排序", 50));
        columns.add(new CheckColumnConfig(IBrand.SHOW, "是否显示", 80));
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 140);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        // grid.setSelectionModel(sm);
        // grid.setAutoExpandColumn("forum");

        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
        act.setText("编辑 ");
        act.setAction("changeBrand($id)");
        act.setTooltip(Resources.constants.GoodsList_action_edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
        act.setAction("deleteBrand($id)");
        act.setTooltip(Resources.constants.GoodsList_action_delete());
        render.addAction(act);
        actcol.setRenderer(render);

        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(750, 350);
        panel.setBottomComponent(toolBar);

        panel.setButtonAlign(HorizontalAlignment.CENTER);

        vp.add(panel);

    }

    private void createBackupPanel() {
        FormPanel panel = new FormPanel();
        // Label label=new Label();
        // panel.setHeading("恢复备份");
        panel.setHeaderVisible(false);
        panel.setPadding(10);
        // panel.add(label);
        FileUploadField file = new FileUploadField();
        file.setAllowBlank(false);
        file.setFieldLabel("本地sql文件");
        panel.add(file);
        Button btn = new Button("上传并执行sql文件");
        btn.setPixelSize(150, 25);
        panel.add(btn);

        vp.add(panel);

    }

}