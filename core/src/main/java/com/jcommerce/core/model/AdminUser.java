/**
 * Author: Bob Chen
 * 		   Kylin Soong	
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admin_user", catalog = "ishop")
public class AdminUser extends ModelObject {

	private static final long serialVersionUID = 3377560175109077693L;
	private String name;
    private String password;
    private String email;
    private String lastIP;
    private Timestamp lastLogin;
    private Timestamp addTime;
    private String actionList;
    private String navigatorList;
    private String languageType;
    private String todolist;
    private Agency agency;

    @Basic( optional = true )
	@Column( name = "user_name", length = 50  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "user_pass", length = 50  )
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic( optional = true )
	@Column( name = "last_ip", length = 15  )
    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    @Basic( optional = true )
	@Column( name = "last_login"  )
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Basic( optional = true )
	@Column( name = "add_time"  )
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Basic( optional = true )
	@Column( name = "action_list", length = 2147483647  )
    public String getActionList() {
        return actionList;
    }

    public void setActionList(String actionList) {
        this.actionList = actionList;
    }

    @Basic( optional = true )
	@Column( name = "nav_list", length = 2147483647  )
    public String getNavigatorList() {
        return navigatorList;
    }

    public void setNavigatorList(String navigatorList) {
        this.navigatorList = navigatorList;
    }

    @Basic( optional = true )
	@Column( name = "lang_type", length = 50  )
    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    @Basic( optional = true )
	@Column( length = 2147483647  )
    public String getTodolist() {
        return todolist;
    }

    public void setTodolist(String todolist) {
        this.todolist = todolist;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "agency_id", nullable = true )
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

}
