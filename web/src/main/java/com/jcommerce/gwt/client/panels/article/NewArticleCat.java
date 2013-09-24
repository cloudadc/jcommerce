package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticleCatagory;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class NewArticleCat extends ContentWidget {
    private static NewArticleCat instance;

    public static NewArticleCat getInstance() {
        if (instance == null) {
            instance = new NewArticleCat();
        }
        return instance;
    }

    private ListBox c_parent = new ListBox();
    private Button btnNew = new Button();
    private Button btnCancel = new Button();
    private ColumnPanel contentPanel = new ColumnPanel();
    private Map<String, BeanObject> categorys = new HashMap<String, BeanObject>();

    // private boolean editting = false;
    // private BeanObject articleCat = null;

    public static class State extends PageState {
        private BeanObject articleCategory = null;

        public BeanObject getArticleCategory() {
            return articleCategory;
        }

        public void setArticleCategory(BeanObject articleCategory) {
            this.articleCategory = articleCategory;
            setEditting(articleCategory != null);
        }

        public String getPageClassName() {
            return NewArticleCat.class.getName();
        }

        public String getMenuDisplayName() {
            return "添加文章分类";
        }
    }

    public NewArticleCat() {
        curState = new State();
    }

    public State getCurState() {
        return (State) curState;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        if (!getCurState().isEditting())
            return "添加文章分类";
        else
            return "编辑文章分类";
    }

    // public void setArticleCat(BeanObject articleCat) {
    // this.articleCat = articleCat;
    // editting = articleCat != null;
    // }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);

        add(contentPanel);

        contentPanel.createPanel(IArticleCatagory.NAME, "文章分类名称:", new TextBox());
        contentPanel.createPanel(IArticleCatagory.PARENT, "上级分类:", c_parent);

        contentPanel.createPanel(IArticleCatagory.SORTRORDER, "排序:", new TextBox());

        contentPanel.createPanel(IArticleCatagory.NAVIGATOR, "是否显示在导航栏:", new CheckBox());

        TextArea area = new TextArea();
        area.setSize("600", "60");        
        contentPanel.createPanel(IArticleCatagory.KEYWORDS, "关键字:", area);
        area = new TextArea();
        area.setSize("600", "100");        
        contentPanel.createPanel(IArticleCatagory.DESCRIPTION, "分类描述:", area);

        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnNew);
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);

        btnNew.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                BeanObject articleCat = getCurState().getArticleCategory();
                String id = articleCat != null ? articleCat.getString(IArticleCatagory.ID) : null;
                articleCat = new BeanObject(ModelNames.ARTICLECATAGORY, contentPanel.getValues());
                if (getCurState().isEditting()) {
                    new UpdateService().updateBean(id, articleCat, null);
                    ArticleCatogory.State state = new ArticleCatogory.State();
                    state.execute();
                } else {
                    new CreateService().createBean(articleCat, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) {
                            Success.State newState = new Success.State();
                            newState.setMessage("添加文章分类成功");

                            ArticleCatogory.State choice1 = new ArticleCatogory.State();
                            // choice1.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
                            newState.addChoice(ArticleCatogory.getInstance().getName(), choice1);

                            newState.execute();
                        }
                    });

                }
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                contentPanel.clearValues();
            }
        });
    }

    public void refresh() {
        c_parent.clear();
        c_parent.addItem("");

        new ListService().listBeans(ModelNames.ARTICLECATAGORY, new ListService.Listener() {
            public synchronized void onSuccess(List<BeanObject> result) {
                List<String> pids = new ArrayList<String>();
                for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
                    BeanObject cat = it.next();
                    String name = cat.getString(IArticleCatagory.NAME);
                    String id = cat.getString(IArticleCatagory.ID);
                    String _pid = cat.getString(IArticleCatagory.PARENT);
                    if (_pid == null) {
                        pids.clear();
                    } else if (!pids.contains(_pid)) {
                        pids.add(_pid);
                    }
                    int level = pids.indexOf(_pid) + 1;
                    for (int i = 0; i < level; i++) {
                        name = "  " + name;
                    }
                    c_parent.addItem(name, id);
                }
            }
        });

        BeanObject articleCat = getCurState().getArticleCategory();
        if (articleCat != null && articleCat.getString(IArticleCatagory.ID) != null) {
            categorys.put(articleCat.getString(IArticleCatagory.ID), articleCat);
            Map<String, Object> mapCategory = articleCat.getProperties();
            contentPanel.updateValues(mapCategory);

            // articleCat.getProperties().get("IArticleCatagory.ID");
        } else {
            contentPanel.clearValues();
            getCurState().setEditting(false);
        }
    }
}
