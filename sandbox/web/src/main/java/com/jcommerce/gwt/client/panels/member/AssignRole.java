package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminAction;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.privilege.AdminList;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class AssignRole extends ContentWidget {

	@Override
	public String getDescription() {
		return "AssignRoleDescription";
	}
	@Override
	public String getName() {
		return "分派权限";
	}

	public static class State extends PageState {
	    BeanObject role = null;
	    
		public BeanObject getRole() {
            return role;
        }
        
		public void setRole(BeanObject role) {
            this.role = role;
        }
        
        public String getPageClassName() {
			return AssignRole.class.getName();
		}
		public String getMenuDisplayName() {
			return "分派权限";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	ColumnPanel contentPanel = new ColumnPanel();
	CheckBox goods = new CheckBox("商品管理");
	CheckBox article = new CheckBox("文章管理");
	CheckBox member = new CheckBox("会员管理");
	CheckBox role = new CheckBox("权限管理");
	CheckBox system = new CheckBox("系统管理");
	CheckBox order = new CheckBox("订单管理");
	CheckBox onsale = new CheckBox("促销管理");
	CheckBox email = new CheckBox("邮件管理");
	Button save = new Button("保存");
//	BeanObject roleBean = null;
	
	public AssignRole() {
		add(contentPanel);
	}
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		contentPanel.createPanel("goods", "商品/分类/品牌/用户评论的管理", goods);
		contentPanel.createPanel("article", "文章/分类/网店信息/在线调查的管理", article);
		contentPanel.createPanel("member", "商品/分类/品牌/用户评论的管理", member);
		contentPanel.createPanel("role", "模板/管理员/日志/办事处的管理", role);
		contentPanel.createPanel("system", "商店设置/配送方式/地区列表/数据库/支付方式的管理", system);
		contentPanel.createPanel("order", "订单/分类/流量/缺货登记的管理", order);
		contentPanel.createPanel("onsale", "专题/夺宝奇兵/贺卡/红包的管理", onsale);
		contentPanel.createPanel("email", "关注/订阅/杂志/队列的管理", email);
		save.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				List<String> actions = new ArrayList<String>();
				if( goods.isChecked() ) {
					actions.add(goods.getText());
				}
				if ( article.isChecked() ) {
					actions.add(article.getText());
				}
				if ( member.isChecked() ) {
					actions.add(member.getText());
				} 
				if ( role.isChecked() ) {
					actions.add(role.getText());
				}
				if ( system.isChecked() ) {
					actions.add(system.getText());
				}
				if ( order.isChecked() ) {
					actions.add(order.getText());
				}
				if ( onsale.isChecked() ) {
					actions.add(onsale.getText());
				}
				if ( email.isChecked() ) {
					actions.add(email.getText());
				}
				StringBuffer sb = new StringBuffer();
				for( String action : actions ) {
					Map<String, Object> values = new HashMap<String, Object>();
					values.put(IAdminAction.CODE, action);
					BeanObject bean = new BeanObject(ModelNames.ADMINACTION, values);
					new CreateService().createBean(bean, new CreateService.Listener() {
						@Override
						public void onSuccess(String id) {
							
						}
					});
					sb.append(";" + action);
				}
				
				BeanObject role = getCurState().getRole();
				Map<String, Object> adminBean = role.getProperties();
				adminBean.put(IAdminUser.ACTIONLIST, sb.substring(1));
				new UpdateService().updateBean(role.getString(IAdminUser.ID), new BeanObject(ModelNames.ADMINUSER, adminBean), new UpdateService.Listener() {
					@Override
					public void onSuccess(Boolean success) {
						
					}
				});
				AdminList.State state = new AdminList.State();
				state.execute();
			}
		});
		contentPanel.createPanel(null, null, save);
	}
	
//	public void setRole(BeanObject bean) {
//		this.roleBean = bean;
//	}
}
