/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

public class VoteData extends ModelObject {

	private Vote vote;
	private String itemName;
	private int itemCount;

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}
