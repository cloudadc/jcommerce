/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

public class EmailList extends ModelObject {

	private String email;
	private boolean confirm;//订阅者是否已确认
	private String hash;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
