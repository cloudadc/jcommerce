/**
 * 
 */
package com.jcommerce.gwt.client.panels.article;

//import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.ITagManager;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author Jill Zhang
 * 
 */
public class NewTag extends ContentWidget {

    private Button btnSubmit = new Button();
    private Button btnClear = new Button();
    private ListBox goodsName = new ListBox();
    private ColumnPanel contentPanel = new ColumnPanel();
    private TextBox searchText = new TextBox();
    private TextBox tagName = new TextBox();
    private Button btnSearch = new Button();
    Criteria criteria = new Criteria();

    // private boolean editting = false;
    //    
    // private BeanObject tag = null;
    //    
    // leon to integrate with history-based page navigation mechnism.
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see
    // leontest.Attribute
    public static class State extends PageState {
        private BeanObject tag = null;

        public BeanObject getTag() {
            return tag;
        }

        public void setTag(BeanObject tag) {
            this.tag = tag;
            setEditting(tag != null);
        }

        public String getPageClassName() {
            return NewTag.class.getName();
        }

        public String getMenuDisplayName() {
            return !isEditting() ? "添加标签" : "编辑标签";
        }
    }

    public State getCurState() {
        return (State)curState;
    }

    /**
     * Initialize this example.
     */
    private static NewTag instance;

    public static NewTag getInstance() {
        if (instance == null) {
            instance = new NewTag();
        }
        return instance;
    }

    private NewTag() {
        curState = new State();
    }

    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        if (!getCurState().isEditting())
            return "添加标签";
        else
            return "编辑标签";

    }

    // public void setTag(BeanObject tag) {
    // this.tag = tag;
    // editting = tag != null;
    // }
    //    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);

        contentPanel.createPanel(ITagManager.TAGWORDS, "标签名称", tagName);
        btnSearch.setText("搜索");
        contentPanel.createPanel(ITagManager.CONTENT, "根据商品的名称或货号搜索商品", searchText, btnSearch);
        contentPanel.createPanel(ITagManager.GOODS, "商品名称", goodsName);
        // goodsName.addItem("请选择商品", "choose");

        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnSubmit.setText("确定");
        btnClear.setText("重置");
        panel.add(btnSubmit);
        panel.add(btnClear);
        contentPanel.createPanel(null, null, panel);
        add(contentPanel);

        btnSearch.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                search();
                goodsName.setFocus(true);
            }
        });

        btnSubmit.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                BeanObject tag = getCurState().getTag();
                String id = tag != null ? tag.getString(ITagManager.ID) : null;
                tag = new BeanObject(ModelNames.TAG, contentPanel.getValues());
                if (tagName.getText().trim().equals("") || goodsName.getItemCount() == 0) {
                    MessageBox.alert("ERROR", "请输入完整", null);
                } else {
                    if (getCurState().isEditting()) {
                        new UpdateService().updateBean(id, tag, null);
                        Info.display("恭喜", "完成修改标签.");
                        TagManager.State state = new TagManager.State();
                        state.execute();
                    } else {
                        new CreateService().createBean(tag, new CreateService.Listener() {
                            public void onSuccess(String id) {
                                Info.display("恭喜", "完成添加新标签.");
                                TagManager.State state = new TagManager.State();
                                state.execute();
                            }
                        });
                    }
                }
            }
        });

        btnClear.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                contentPanel.clearValues();
            }
        });
    }

    public void refresh() {
        BeanObject tag = getCurState().getTag();
        if (tag != null && tag.getString(ITagManager.ID) != null) {
            Map<String, Object> mapTag = tag.getProperties();
            contentPanel.updateValues(mapTag);
        } else {
            contentPanel.clearValues();
            search();
            getCurState().setEditting(false);
        }
    }

    private void search() {
        goodsName.clear();
        criteria.removeAll();
        String content = searchText.getText().trim();
        if (content == null) {
            new ListService().listBeans(ModelNames.GOODS, new ListService.Listener() {
                public void onSuccess(List<BeanObject> beans) {
                    for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                        BeanObject goods = it.next();
                        goodsName.addItem(goods.getString(IGoods.NAME), goods.getString(IGoods.ID));
                    }
                }
            });
        }
        Condition cond = new Condition();
        // 按商品货号查找
        cond.setField(IGoods.SN);
        cond.setOperator(Condition.EQUALS);
        cond.setValue(content);
        criteria.addCondition(cond);
        new ListService().listBeans(ModelNames.GOODS, criteria, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject goods = it.next();
                    goodsName.addItem(goods.getString(IGoods.NAME), goods.getString(IGoods.ID));
                }
            }
        });
        // 按商品名称查找
        cond.setField(IGoods.NAME);
        cond.setOperator(Condition.GREATERTHAN);
        cond.setValue(content);
        criteria.addCondition(cond);
        new ListService().listBeans(ModelNames.GOODS, criteria, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject goods = it.next();
                    goodsName.addItem(goods.getString(IGoods.NAME), goods.getString(IGoods.ID));
                }
            }
        });

    }

}
