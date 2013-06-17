/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stats", catalog = "ishop")
public class Stats extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = 4132338209757756703L;
	private Timestamp accessTime;
	private String IP;
	private int visitTimes;
	private String browser;
	private String system;
	private String language;
	private String area;
	private String refererDomain;
	private String refererPath;
	private String accessUrl;

	@Basic( optional = true )
	@Column( name = "access_time"  )
    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

    @Basic( optional = true )
	@Column( name = "ip_address", length = 15  )
    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        IP = ip;
    }

    @Basic( optional = true )
	@Column( name = "visit_times"  )
    public int getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(int visitTimes) {
        this.visitTimes = visitTimes;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Basic( optional = true )
	@Column( length = 20  )
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Basic( optional = true )
	@Column( length = 20  )
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic( optional = true )
	@Column( length = 30  )
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic( optional = true )
	@Column( name = "referer_domain", length = 100  )
    public String getRefererDomain() {
        return refererDomain;
    }

    public void setRefererDomain(String refererDomain) {
        this.refererDomain = refererDomain;
    }

    @Basic( optional = true )
	@Column( name = "referer_path", length = 200  )
    public String getRefererPath() {
        return refererPath;
    }

    public void setRefererPath(String refererPath) {
        this.refererPath = refererPath;
    }

    @Basic( optional = true )
	@Column( name = "access_url", length = 255  )
    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

}
