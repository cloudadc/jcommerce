/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mail_templates", catalog = "ishop")
public class MailTemplate extends ModelObject {
    
	private static final long serialVersionUID = -6717819890566721173L;
	private String code;
	private boolean html;
	private String subject;
	private String content;
	private String type;
	private Timestamp lastSend;
	private Timestamp lastModify;

	@Basic( optional = true )
	@Column( name = "template_code", length = 30  )
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic( optional = true )
	@Column( name = "is_html"  )
    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    @Basic( optional = true )
	@Column( name = "template_subject", length = 200  )
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic( optional = true )
	@Column( name = "template_content", length = 2147483647  )
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic( optional = true )
	@Column( length = 10  )
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic( optional = true )
	@Column( name = "last_send"  )
    public Timestamp getLastSend() {
        return lastSend;
    }

    public void setLastSend(Timestamp lastSend) {
        this.lastSend = lastSend;
    }

    @Basic( optional = true )
	@Column( name = "last_modify"  )
    public Timestamp getLastModify() {
        return lastModify;
    }

    public void setLastModify(Timestamp lastModify) {
        this.lastModify = lastModify;
    }

}
