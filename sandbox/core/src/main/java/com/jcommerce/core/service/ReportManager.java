package com.jcommerce.core.service;

import java.sql.Date;
import java.util.Map;


public interface ReportManager extends Manager {
	public String getPieChart(String type , String title , Map hashMap , Date startDate , Date endDate);
}
