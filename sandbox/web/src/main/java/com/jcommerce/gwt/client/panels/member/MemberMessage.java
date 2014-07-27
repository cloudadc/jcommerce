/**
 * @author monkey 
 * @time 2010.03.02
 */
package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IFeedback;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class MemberMessage extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	TextBox commentContent = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	ListBox lstAction = new ListBox();
	ListBox msgType = new ListBox();
	TextBox msgTitle = new TextBox();
	Button btnAct = new Button(Resources.constants.GoodsList_action_OK());
	Criteria criteria = new Criteria();
	int deleteSize = 1;
	PagingToolBar toolBar;

	public static class State extends PageState {
		public String getPageClassName() {
			return MemberMessage.class.getName();
		}

		public String getMenuDisplayName() {
			return "会员留言";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public MemberMessage() {
		add(contentPanel);
		initJS(this);
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "会员留言";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.FEEDBACK, criteria);
		loader.load(0, 10);

		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IFeedback.ID, "编号", 90));
		columns.add(new ColumnConfig(IFeedback.USERNAME, "用户名", 120));
		columns.add(new ColumnConfig(IFeedback.USEREMAIL, "邮件", 150));
		columns.add(new ColumnConfig(IFeedback.MSGTITLE, "留言标题", 180));
		// 留言类型
		ColumnConfig messageType = new ColumnConfig(IFeedback.MSGTYPE, "类型", 100);
		columns.add(messageType);
		columns.add(new ColumnConfig(IFeedback.MSGTIME, "留言时间", 150));
		// 回复状态
		ColumnConfig reply = new ColumnConfig(IFeedback.ORDERID, "回复", 100);
		columns.add(reply);

		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 120);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		// grid.setAutoExpandColumn("forum");

		// add data 
		Map<Integer, String> data = new HashMap<Integer, String>();
		data.put(IFeedback.MESSAGE_TYPE_LIU, "留言");
		data.put(IFeedback.MESSAGE_TYPE_TOU, "投诉");
		data.put(IFeedback.MESSAGE_TYPE_SHOU, "售后");
		data.put(IFeedback.MESSAGE_TYPE_XUN, "询问");
		data.put(IFeedback.MESSAGE_TYPE_QIU, "求购");
		data.put(IFeedback.MESSAGE_TYPE_SHANG, "商家留言");
		MessageTypeActionCellRenderer typeRender = new MessageTypeActionCellRenderer(grid, data);
		messageType.setRenderer(typeRender);
		
		ReplyStatusActionCellRenderer replyRender = new ReplyStatusActionCellRenderer(grid, "已回复", "未回复");
		reply.setRenderer(replyRender);
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_trash.gif");
		act.setAction("deleteMessageAction($id)");
		act.setTooltip("删除");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("replyMessageAction($id)");
		act.setTooltip("回复");
		render.addAction(act);
		
		actcol.setRenderer(render);
		
		msgType.addItem("请选择...", "---");
		msgType.addItem("留言", "---");
		msgType.addItem("投诉", "---");
		msgType.addItem("售后", "---");
		msgType.addItem("询问", "---");
		msgType.addItem("求购", "---");
		msgType.addItem("商家留言", "---");
		

		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("类型"));
		header.add(msgType);
		header.add(new Label("留言标题"));
		header.add(msgTitle);
		
		header.add(btnFind);
		add(header);

		// 对标题的搜索采用分词后进行模糊查询
		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search(); // 该功能有两个约束条件，一是流言类型的约束，而是留言标题的约束。
			}
		});

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		// panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);

		add(panel);
	}

	/**
	 * 假如搜索的关键词为空，显示该分类下的所有留言信息；
	 * 否则，首先对关键词进行分词，然后将分词结果作为关键字进行模糊查询，显示所有结果。
	 * 提高搜索的匹配度。
	 */
	private void search() {
		criteria.removeAll();
		String title = msgTitle.getText().trim(); // 用户搜索的标题
		
		if (msgType.getSelectedIndex() > 0) {
			int index = msgType.getSelectedIndex();
			System.out.println(index + "=================");
			Condition cond = new Condition();
			cond.setField(IFeedback.MSGTYPE);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(String.valueOf(index));
			criteria.addCondition(cond);
		} else {
			System.out.println("Input nothing!");
		}

		// 对关键字直接进行模糊查询，待扩展
		if( title != null && !title.equals("")) {
			Condition cond = new Condition();
			cond.setField(IFeedback.MSGTITLE);
			cond.setOperator(Condition.LIKE);
			cond.setValue(title);
			criteria.addCondition(cond);
		}
		toolBar.refresh();
	}

	/**
	 * JAVA和JS互调的初始化
	 * @param memberMessage
	 */
	private native void initJS(MemberMessage me) /*-{
	   $wnd.deleteMessageAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberMessage::deleteMessageAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.replyMessageAction = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberMessage::replyMessage(Ljava/lang/String;)(id);
	   };
	   }-*/;

	
	/**
	 * 删除评论信息并且刷新界面
	 * @param id 评论ID
	 */
	private void deleteMessageAndRefrsh(final String id) {
		final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		      public void handleEvent(MessageBoxEvent ce) {
		          Button btn = ce.getButtonClicked();
				if (btn.getText().equals("是")) {
					new DeleteService().deleteBean(ModelNames.FEEDBACK, id,
						new DeleteService.Listener() {
							public void onSuccess(Boolean success) {
								refresh();
							}
					});
				}
			}
		};
		// 提示信息
		MessageBox.confirm("Confirm", "Are you sure you want to do that?",
				deleteListener);
	}

	/**
	 * 管理员回复留言
	 * @param id
	 */
	private void replyMessage(String id) {
		new ReadService().getBean(ModelNames.FEEDBACK, id,
			new ReadService.Listener() {
				public void onSuccess(BeanObject bean) {
					// 跳转
					MemberMessageReply.State state = new MemberMessageReply.State();
					state.setComment(bean);
					state.execute();
				}
			});
	}

	/**
	 * 刷新界面
	 */
	public void refresh() {
		toolBar.refresh();
	}
	
	/**
	 * @author monkey
	 */
	class ReplyStatusActionCellRenderer extends ActionCellRenderer{
		private String isTruthStr;
		private String isFailureStr;
		
		@SuppressWarnings("unchecked")
		public ReplyStatusActionCellRenderer(Grid grid, String isTruthStr, String isFailureStr) {
			super(grid);
			this.isTruthStr = isTruthStr;
			this.isFailureStr = isFailureStr;
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {
			
			String booleanValue = (String) model.get(property).toString();

			StringBuffer sb = new StringBuffer();
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			
			if(booleanValue.equals("1")){
				act.setText(isTruthStr);
			}else if(booleanValue.equals("0")){
				act.setText(isFailureStr);
			}
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}
	        return sb.toString();
		}
	}
	
	/**
	 * @author monkey
	 */
	class MessageTypeActionCellRenderer extends ActionCellRenderer{
		private Map<Integer, String> tpyes = new HashMap<Integer, String>();
		
		@SuppressWarnings("unchecked")
		public MessageTypeActionCellRenderer(Grid grid, Map<Integer, String> values) {
			super(grid);
			this.tpyes.putAll(values);
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {
			
			String booleanValue = (String) model.get(property).toString();

			StringBuffer sb = new StringBuffer();
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			
			act.setText(tpyes.get(Integer.parseInt(booleanValue))); // Map中对应key的类型
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}
	        return sb.toString();
		}
	}
	
}
