/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class SearchEngine extends ModelObject {

	private Timestamp date;
	private String searchEngine;
	private int count;

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
