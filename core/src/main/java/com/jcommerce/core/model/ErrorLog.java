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
@Table(name = "error_log", catalog = "ishop")
public class ErrorLog extends ModelObject {
    
	private static final long serialVersionUID = 7767213243118384225L;
	private String info;
	private String file;
	private Timestamp time;

	@Basic( optional = true )
	@Column( length = 255  )
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic( optional = true )
	@Column( length = 100  )
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}
