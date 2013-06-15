/**
 * Author: Bob Chen
 * 		   Kylin Soong	
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "admin_action", catalog = "ishop")
public class AdminAction extends ModelObject {

	private static final long serialVersionUID = -8978118927927771797L;
	
	private AdminAction parent;
	private String code;
	private Set<AdminAction> adminActions = new HashSet<AdminAction>();

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
	public AdminAction getParent() {
		return parent;
	}

	public void setParent(AdminAction parent) {
		this.parent = parent;
	}

	@Basic( optional = true )
	@Column( name = "action_code", length = 255  )
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "action_id", nullable = false  )
	public Set<AdminAction> getAdminActions() {
		return this.adminActions;
		
	}
	
	public void addAdminAction(AdminAction adminAction) {
		adminAction.setParent(this);
		this.adminActions.add(adminAction);
	}
	
	public void setAdminActions(final Set<AdminAction> adminAction) {
		this.adminActions = adminAction;
	}

}
