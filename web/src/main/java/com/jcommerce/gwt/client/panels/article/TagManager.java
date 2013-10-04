package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.ITagManager;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.BeanCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

	public class  TagManager  extends ContentWidget {
		
		boolean editting = false;
		private BeanObject tagManager = null;
		ColumnPanel contentPanel = new ColumnPanel();
		Button batchButton = new Button();
		
		
		Criteria criteria = new Criteria();
		int deleteSize = 1;
		PagingToolBar toolBar;

		public static class State extends PageState {
			public String getPageClassName() {
				return TagManager.class.getName();
			}

			public String getMenuDisplayName() {
				return "标签管理";
			}
		}

		public State getCurState() {
			return (State)curState;
		}

		public TagManager() {
		    curState = new State();
		    
			add(contentPanel);
			initJS(this);
		}

		public String getDescription() {
			return "cwBasicTextDescription";
		}
		public String getName() {
	    	return "标签管理";	        
	    	
	    }
	    
	    public void setTagManager(BeanObject tagManager) {
	        this.tagManager = tagManager;
	        editting = tagManager != null;
	    }

	    public String getButtonText() {
	        return "添加标签";
	    }
	    
	    protected void buttonClicked() {
            NewTag.State state = new NewTag.State();
            state.execute();
	    }
	    
		protected void onRender(Element parent, int index) {
			super.onRender(parent, index);
			
			BasePagingLoader loader = new PagingListService().getLoader(ModelNames.TAG, criteria);
			loader.load(0, 10);
			final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
			toolBar = new PagingToolBar(10);
			
			toolBar.bind(loader);

			List<ColumnConfig> columns = new ArrayList<ColumnConfig>();						
			final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
			
			columns.add(smRowSelection.getColumn());
			columns.add(new ColumnConfig(ITagManager.ID, "编号", 120));
			columns.add(new ColumnConfig(ITagManager.TAGWORDS, "标签名称", 100));
			columns.add(new ColumnConfig(ITagManager.USER, "会员名", 180));
			ColumnConfig goodsNameItem= new ColumnConfig(ITagManager.GOODS, "商品名称", 150);
			columns.add(goodsNameItem);
			
			ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.GoodsList_action(), 120);
			columns.add(actcol);

			ColumnModel cm = new ColumnModel(columns);		
			final Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
			goodsNameItem.setRenderer(new BeanCellRenderer(ModelNames.GOODS, IGoods.NAME, grid));
			grid.setLoadMask(true);
			grid.setBorders(true);
			grid.setSelectionModel(smRowSelection);		    
				
			grid.addListener(Events.CellClick, new Listener<GridEvent>() {				 
				public void handleEvent(GridEvent be) {
					List<BeanObject> s = smRowSelection.getSelectedItems();				
					checkBatchRemove(s);					
				}	            
	        });

			ActionCellRenderer render = new ActionCellRenderer(grid);
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_edit.gif");
			act.setAction("checkTagAction($id)");
			act.setTooltip("编辑");
			render.addAction(act);
			act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_trash.gif");
			act.setAction("deleteTagAction($id)");
			act.setTooltip("移除");
			render.addAction(act);

			actcol.setRenderer(render);			
			
			ContentPanel panel = new ContentPanel();
			panel.setHeading("标签列表");
			panel.setFrame(true);
			panel.setCollapsible(true);
			panel.setAnimCollapse(false);
			panel.setButtonAlign(HorizontalAlignment.CENTER);
			panel.setIconStyle("icon-table");
			panel.setLayout(new FitLayout());
			panel.add(grid);
			panel.setSize(800, 350);
			panel.setBottomComponent(toolBar);
			panel.setButtonAlign(HorizontalAlignment.CENTER);
			
			batchButton.setText("批量删除");
			panel.addButton(batchButton);
			batchButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			      public void componentSelected(ButtonEvent be) {			    	  
			    	  MessageBox.confirm("提示", "确定删除选定的标签吗？", new Listener<MessageBoxEvent>() {
			    	      public void handleEvent(MessageBoxEvent ce) {
			    	          Button btn = ce.getButtonClicked();
								List<BeanObject> s = smRowSelection.getSelectedItems();
								if (btn.getItemId().equals(Dialog.YES)) {									
									final List listeners = new ArrayList();
									for (BeanObject item : s) {
											new DeleteService().deleteBean(ModelNames.TAG, item.getLong(ITagManager.ID),
													new DeleteService.Listener() {
														public void onSuccess(Boolean success) {
														    TagManager.State state = new TagManager.State();
														    state.execute();
														}
													}
											);}
									toolBar.refresh();			
								}
							}
						});
			      }
			 });

//			panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button(
//					"添加新标签", new SelectionListener<ButtonEvent>() {
//						public void componentSelected(ButtonEvent ce) {
//							NewTag.State state = new NewTag.State();
//							state.execute();
//						}
//					}));
			add(panel);
		}		
        
		private void updateComment(BeanObject tags, UpdateService.Listener listener) {
			new UpdateService().updateBean(tags.getLong(ITagManager.ID), tags, listener);
		}

		private void executeAction(final List<BeanObject> items, String action) {
			if (items == null) {
				return;
			}

			final List listeners = new ArrayList();

			for (BeanObject item : items) {
				if ("delete".equals(action)) {
					DeleteListener listener = new DeleteListener();
					listeners.add(listener);
					deleteComment(item.getLong(ITagManager.ID), listener);
				}
			}

			new WaitService(new WaitService.Job() {
				public boolean isReady() {
					if (listeners.size() != items.size()) {
						return false;
					}
					for (int i = 0; i < listeners.size(); i++) {
						if (listeners.get(i) instanceof DeleteListener) {
							if (!((DeleteListener) listeners.get(i)).isFinished()) {
								return false;
							}
						} else if (listeners.get(i) instanceof UpdateListener) {
							if (!((UpdateListener) listeners.get(i)).isFinished()) {
								return false;
							}
						} else {
							throw new RuntimeException("Unknown listener type:"
									+ listeners.get(i));
						}
					}
					return false;
				}

				public void run() {
					toolBar.refresh();
				}
			});
		}

		private native void initJS(TagManager me) /*-{
		   $wnd.deleteTagAction = function (id) {
		       me.@com.jcommerce.gwt.client.panels.article.TagManager::deleteTagAndRefrsh(Ljava/lang/Long;)(id);
		   };
		   $wnd.checkTagAction = function (id) {
		       me.@com.jcommerce.gwt.client.panels.article.TagManager::checkTag(Ljava/lang/Long;)(id);
		   };
		   		   
		   }-*/;

		private void deleteComment(Long id, DeleteService.Listener listener) {
			new DeleteService().deleteBean(ModelNames.TAG, id, listener);
		}

		private void deleteTagAndRefrsh(final Long id) {
			final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
			      public void handleEvent(MessageBoxEvent ce) {
			          Button btn = ce.getButtonClicked();
					if (btn.getText().equals("是")) {
						new DeleteService().deleteBean(ModelNames.TAG, id,
								new DeleteService.Listener() {
									public void onSuccess(Boolean success) {
				                        TagManager.State state = new TagManager.State();
				                        state.execute();

										batchButton.setEnabled(false);
									}
								});
					}

				}
			};
			MessageBox.confirm("提示", "确定要删除吗？", deleteListener);

		}

		private void checkTag(Long id) {
			new ReadService().getBean(ModelNames.TAG, id,
					new ReadService.Listener() {
						public void onSuccess(BeanObject bean) {
                            NewTag.State state = new NewTag.State();
                            state.setTag(bean);
                            state.execute();
						}
					});
		}
		
		public void refresh() {
			toolBar.refresh();
			batchButton.setEnabled(false);
		}

		class DeleteListener extends DeleteService.Listener {
			private boolean finished = false;

			public void onSuccess(Boolean sucess) {
				finished = true;
			}

			public void onFailure(Throwable caught) {
				finished = true;
			}

			boolean isFinished() {
				return finished;
			}
		}

		class UpdateListener extends UpdateService.Listener {
			private boolean finished = false;

			public void onSuccess(Boolean sucess) {
				finished = true;
			}

			public void onFailure(Throwable caught) {
				finished = true;
			}

			boolean isFinished() {
				return finished;
			}
		}
		private void checkBatchRemove(List list){			 
			if(list.size() == 0){
				batchButton.setEnabled(false);
			}else{
				batchButton.setEnabled(true);
			}
		}
		
	}

