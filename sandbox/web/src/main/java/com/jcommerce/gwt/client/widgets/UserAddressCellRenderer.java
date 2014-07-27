package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.resources.Resources;

public class UserAddressCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	String type;
	
	String mobile;
	String tel;
	String email;
	String time;
	String build;
	String country;
	String province;
	String city;
	String district;
	String zipcode;


	public void setCountry(String country) {
		this.country = country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDistrict(String district) {
		this.district = (district == null ? "" : district);
	}
	public void setZipcode(String zipcode) {
		this.zipcode = (zipcode == null ? "" : zipcode);
	}
	public void setMobile(String mobile) {
		this.mobile = (mobile == null ? "" : mobile);
	}
	public void setTel(String tel) {
		this.tel = (tel == null ? "" : tel);
	}
	public void setEmail(String email) {
		this.email = (email == null ? "" : email);
	}
	public void setTime(String time) {
		this.time = (time == null ? "" : time);
	}
	public void setBuild(String build) {
		this.build = (build == null ? "" : build);
	}


	public UserAddressCellRenderer(Grid grid, String type) {
    	this.view = grid.getView();
    	this.type = type;
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		
			if(type.equals("contact")) {
				return "mobilePhone" + " : " + mobile + "<br>" +
				"phone" + " : " + tel + "<br>" +
				"email" + " : " + email;
//                return Resources.constants.ShippingAddress_mobilePhone() + " : " + mobile + "<br>" +
//                Resources.constants.ShippingAddress_phone() + " : " + tel + "<br>" +
//                Resources.constants.ShippingAddress_email() + " : " + email;
			}
			else if(type.equals("other")) {
				return "bestTime" + " : " + time + "<br>" +
				"build" + " : " + build ;
//                return Resources.constants.ShippingAddress_bestTime() + " : " + time + "<br>" +
//                Resources.constants.ShippingAddress_build() + " : " + build ;
			}
			else
				return country + " " + province + " " + city + "<br>" + district + "[" + zipcode + "]";
	}
}

