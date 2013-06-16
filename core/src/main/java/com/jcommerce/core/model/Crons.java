/**
 * @author KingZhao
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
@Table(name = "crons", catalog = "ishop")
public class Crons extends ModelObject {
	
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

	private static final long serialVersionUID = -1636939899692014524L;
	private String cronCode;
	private String cronName;
	private String cronDesc;
	private String cronConfig;
	private int cronOrder;
	private Timestamp thisTime;
	private Timestamp nextTime;
	private int day;//每月第几天
	private int week;//每周星期几
	private int hour;//每天几点开始	
	private String minutes;//当前小时内第几分钟，可并列多个时间点	
	private boolean enable;//是否启用计划任务
	private boolean runOnce;//是否只运行一次
	private String allowIp;
	private String allowFiles;

	@Basic( optional = true )
	@Column( name = "cron_code", length = 20  )
	public String getCronCode() {
		return cronCode;
	}

	public void setCronCode(String cronCode) {
		this.cronCode = cronCode;
	}

	public String getCronName() {
		return cronName;
	}

	@Basic( optional = true )
	@Column( name = "cron_name", length = 120  )
	public void setCronName(String cronName) {
		this.cronName = cronName;
	}

	@Basic( optional = true )
	@Column( name = "cron_desc", length = 2147483647  )
	public String getCronDesc() {
		return cronDesc;
	}

	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}

	@Basic( optional = true )
	@Column( name = "cron_config", length = 2147483647  )
	public String getCronConfig() {
		return cronConfig;
	}

	public void setCronConfig(String cronConfig) {
		this.cronConfig = cronConfig;
	}

	@Basic( optional = true )
	@Column( name = "cron_order"  )
	public int getCronOrder() {
		return cronOrder;
	}

	public void setCronOrder(int cronOrder) {
		this.cronOrder = cronOrder;
	}

	public Timestamp getThisTime() {
		return thisTime;
	}

	public void setThisTime(Timestamp thisTime) {
		this.thisTime = thisTime;
	}

	public Timestamp getNextTime() {
		return nextTime;
	}

	public void setNextTime(Timestamp nextTime) {
		this.nextTime = nextTime;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Basic( optional = true )
	@Column( name = "run_once"  )
	public boolean isRunOnce() {
		return runOnce;
	}

	public void setRunOnce(boolean runOnce) {
		this.runOnce = runOnce;
	}

	@Basic( optional = true )
	@Column( name = "allow_ip", length = 100  )
	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}

	@Basic( optional = true )
	@Column( name = "allow_files", length = 255  )
	public String getAllowFiles() {
		return allowFiles;
	}

	public void setAllowFiles(String allowFiles) {
		this.allowFiles = allowFiles;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public int[] getMinute() {
		String[] minute = this.minutes.split(" ");
		int[] minut = new int[minute.length];
		for (int i = 0; i < minute.length; i++) {
			try {
				minut[i] = Integer.parseInt(minute[i]);
			} catch (Exception e) { //...   
			}
		}
		return minut;
	}

	public void setMinutes(int[] minute) {
		this.minutes = minute.toString();
	}
}
