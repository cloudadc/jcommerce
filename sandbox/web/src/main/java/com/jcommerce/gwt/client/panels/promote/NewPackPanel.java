/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.promote;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

/**
 * Example file.
 */
public class NewPackPanel extends ContentWidget {    	
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
   
    public static class State extends PageState {
        private BeanObject pack = null;
        
        public BeanObject getPack() {
            return pack;
        }

        public void setPack(BeanObject pack) {
            this.pack = pack;
            setEditting(pack != null);
        }

        public String getPageClassName() {
            return NewPackPanel.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "添加包装" : "编辑包装";
        }
    }
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }
    
    public NewPackPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().isEditting())
        	return "添加包装";
        else
            return "编辑包装"; 	
    }
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        add(contentPanel);
       
        contentPanel.createPanel(IPack.NAME, "商品包装名称:", new TextBox());
        contentPanel.createPanel(IPack.FEE, "费用:", new TextBox());
        contentPanel.createPanel(IPack.FREEMONEY, "免费额度:", new TextBox());
        final FileUploader logoUpload = new FileUploader();
        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        if (getCurState().isEditting()) {
            logoUpload.setImageInfo(ModelNames.PACK, IPack.ID, IPack.IMAGE);
        }
        contentPanel.createPanel(IPack.IMAGE, "包装图纸:", logoUpload);
        contentPanel.createPanel(IPack.DESC, "包装描述:", new TextArea());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnNew);
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                if (!logoUpload.submit()) {
                    return;
                }
               
                new WaitService(new WaitService.Job() {
                    public boolean isReady() {
                        return logoUpload.isFinish();
                    }

                    public void run() {
                        BeanObject pack = getCurState().getPack();
                        String id = pack != null ? pack.getString(IPack.ID) : null;
                        pack = new BeanObject(ModelNames.PACK, contentPanel.getValues());
                        if (getCurState().isEditting()) {
                            new UpdateService().updateBean(id, pack, null);
                            PackListPanel.State state = new PackListPanel.State();
                            state.execute();
                        } else {
                            new CreateService().createBean(pack, new CreateService.Listener() {
                                public void onSuccess(String id) {
                                    PackListPanel.State state = new PackListPanel.State();
                                    state.execute();
                                }
                            });
                        }
                    }
                });
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                contentPanel.clearValues();
            }            
        });        
    }  
    
    public void refresh() {
        BeanObject pack = getCurState().getPack();
        
        if (pack!=null && pack.getString(IPack.ID) != null) {            
            Map<String, Object> mapPack = pack.getProperties();
            contentPanel.updateValues(mapPack);
        } else {
            contentPanel.clearValues();
            getCurState().setPack(null);
        }
    }
}
