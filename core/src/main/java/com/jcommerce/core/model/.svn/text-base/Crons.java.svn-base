/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class Crons extends ModelObject {

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

	public String getCronCode() {
		return cronCode;
	}

	public void setCronCode(String cronCode) {
		this.cronCode = cronCode;
	}

	public String getCronName() {
		return cronName;
	}

	public void setCronName(String cronName) {
		this.cronName = cronName;
	}

	public String getCronDesc() {
		return cronDesc;
	}

	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}

	public String getCronConfig() {
		return cronConfig;
	}

	public void setCronConfig(String cronConfig) {
		this.cronConfig = cronConfig;
	}

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

	public boolean isRunOnce() {
		return runOnce;
	}

	public void setRunOnce(boolean runOnce) {
		this.runOnce = runOnce;
	}

	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}

	public String getAllowFiles() {
		return allowFiles;
	}

	public void setAllowFiles(String allowFiles) {
		this.allowFiles = allowFiles;
	}

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
