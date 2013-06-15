/**
* Author: Kylin Soong
*         
*/

package com.jcommerce.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cat_recommend", catalog = "ishop")
public class CatRecommend extends ModelObject {

	private static final long serialVersionUID = 5522845420454737337L;

	private String catId;

	private int type = 0;

	public CatRecommend() {
	}

	@Basic( optional = true )
	@Column( name = "cat_rec_id", length = 30  )
	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
