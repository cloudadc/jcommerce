package com.jcommerce.gwt.client.panels.promote;

import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICard;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

public class NewCardPanel extends ContentWidget {
	
	private ColumnPanel contentPanel = new ColumnPanel();
	private Button btnNew = new Button();    
    private Button btnCancel = new Button(); 

	 public static class State extends PageState {
	        private BeanObject card = null;
	        
	        public BeanObject getCard() {
				return card;
			}

			public void setCard(BeanObject card) {
				this.card = card;
				setEditting(card != null);
			}

	        public String getPageClassName() {
	            return NewCardPanel.class.getName();
	        }
	        public String getMenuDisplayName() {
	            return !isEditting() ? "添加红包类型" : "编辑贺卡 ";
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
	    	if(!getCurState().isEditting())
	        	return "添加新贺卡";
	        else
	            return "编辑祝福贺卡 "; 	
	    }
	    
	    protected void onRender(Element parent, int index) {
			super.onRender(parent, index);
			add(contentPanel);
			
			contentPanel.createPanel(ICard.NAME, "贺卡名称", new TextBox());
	        contentPanel.createPanel(ICard.FEE, "贺卡费用", new TextBox());
	        contentPanel.createPanel(ICard.FREEMONEY, "贺卡免费额度", new TextBox());
	        final FileUploader logoUpload = new FileUploader();
	        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
	        if (getCurState().isEditting()) {
	            logoUpload.setImageInfo(ModelNames.CARD, ICard.ID, ICard.IMAGE);
	        }
	        contentPanel.createPanel(ICard.IMAGE, "贺卡图纸", logoUpload);
	        contentPanel.createPanel(ICard.DESC, "贺卡描述", new TextArea());
	        
	        HorizontalPanel panel = new HorizontalPanel();
	        panel.setSpacing(10);
	        btnNew.setText("确定");
	        btnCancel.setText("重置");
	        panel.add(btnNew);
	        panel.add(btnCancel);
	        contentPanel.createPanel(null, null, panel);     
	        
		btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if (!logoUpload.submit()) {
					return;
				}
				new WaitService(new WaitService.Job() {
					
					public void run() {
						BeanObject card = getCurState().getCard();
						String id = card != null ? card.getString(IPack.ID) : null;
						card = new BeanObject(ModelNames.CARD, contentPanel.getValues());
						if(getCurState().isEditting()) {
							new UpdateService().updateBean(id, card, null);
							CardListPanel.State state = new CardListPanel.State();
							state.execute();
						} else {
							new CreateService().createBean(card, new CreateService.Listener() {
                                public void onSuccess(String id) {
                                	CardListPanel.State state = new CardListPanel.State();
        							state.execute();
                                }
                            });
						}
					}
					
					public boolean isReady() {
						return logoUpload.isFinish();
					}
				});
			}
		});
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				contentPanel.clearValues();
			}
		});
	}
	    
	    public void refresh() {
	        BeanObject card = getCurState().getCard();
	        
	        if (card!=null && card.getString(ICard.ID) != null) {            
	            Map<String, Object> mapPack = card.getProperties();
	            contentPanel.updateValues(mapPack);
	        } else {
	            contentPanel.clearValues();
	            getCurState().setCard(null);
	        }
	    }

}
