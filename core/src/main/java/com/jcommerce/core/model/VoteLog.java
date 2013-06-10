/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

public class VoteLog extends ModelObject {

	private Vote vote;
	private String ipAddress;
	private Timestamp voteTime;

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Timestamp getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Timestamp voteTime) {
		this.voteTime = voteTime;
	}

}
