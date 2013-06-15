/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email_sendlist", catalog = "ishop")
public class EmailSendList extends ModelObject {

	private static final long serialVersionUID = -7336299461753947141L;
	public static final int TEMPLATEID_SENDPASSWORD  = 1;//密码找回
	public static final int TEMPLATEID_ORDERCONFIRM  = 2;//订单确认通知
	public static final int TEMPLATEID_DELIVERNOTICE = 3;//发货通知
	public static final int TEMPLATEID_ORDERCANCEL   = 4;//订单取消
	public static final int TEMPLATEID_ORDERINVALID  = 5;//订单无效
	public static final int TEMPLATEID_SENDBONUS     = 6;//发红包
	public static final int TEMPLATEID_GROUPBUY      = 7;//团购商品
	public static final int TEMPLATEID_REGISTERVALIDATE  = 8;//邮件验证
	public static final int TEMPLATEID_VIRTUALCARD   = 9;//虚拟卡片
	public static final int TEMPLATEID_ATTENTIONLIST = 10;//关注商品
	public static final int TEMPLATEID_REMINDOFNEWORDER  = 11;//新订单通知
	
	private String email;// 邮件地址
	private int templateId;// 邮件类型范本ID
	private String emailContent;
	private int error;// 发送错误次数
	private int priority;// 优先级
	private Timestamp lastSend;// 最后发送时间

	@Basic( optional = true )
	@Column( length = 100  )
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic( optional = true )
	@Column( name = "template_id"  )
	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	@Basic( optional = true )
	@Column( name = "email_content", length = 2147483647  )
	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	@Basic( optional = true )
	@Column( length = 10  )
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Basic( optional = true )
	@Column( name = "last_send"  )
	public Timestamp getLastSend() {
		return lastSend;
	}

	public void setLastSend(Timestamp lastSend) {
		this.lastSend = lastSend;
	}

}
