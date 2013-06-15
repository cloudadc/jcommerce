/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "failed_login", catalog = "ishop")
public class FailedLogin extends ModelObject {
    
	private static final long serialVersionUID = 230387268219813902L;
	private int count;
	private Timestamp lastUpdate;

    public String getIP() {
        return getId();
    }

    public void setIP(String ip) {
        setId(ip);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
