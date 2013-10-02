/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "keywords")
public class Keywords extends ModelObject {
	
private String id;
    
	@Id 
	@GeneratedValue
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = -8152693265343125599L;
	private Timestamp date;
	private String searchEngine;//所使用的搜索引擎
	private String keyword;//搜索的關鍵字
	private int count;//搜索過的次數

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Basic( optional = true )
	@Column( length = 20  )
	public String getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	@Basic( optional = true )
	@Column( length = 90  )
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
