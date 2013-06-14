/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class AutoManage extends ModelObject {

	public static final int AUTOTYPE_MANAGEGOODS = 1;//自動處理貨物上架下架
	public static final int AUTOTYPE_MANAGEARTICLE = 2;//自動處理文章發布撤銷

	private int type;//自動處理類型(文章或是貨物)
	private Timestamp startTime;
	private Timestamp endTime;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

}
