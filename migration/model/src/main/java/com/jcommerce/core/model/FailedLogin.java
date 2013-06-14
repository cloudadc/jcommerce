/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class FailedLogin extends ModelObject {
    
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
