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
@Table(name = "topic", catalog = "ishop")
public class Topic extends ModelObject {

	private static final long serialVersionUID = 7531273570554580848L;
	private String title;
	private String intro;
	private Timestamp startTime;
	private Timestamp endTime;
	private String data;
	private String template;
	private String css;

	@Basic( optional = true )
	@Column( length = 255  )
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
}
