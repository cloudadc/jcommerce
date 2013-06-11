/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_AccountLog")
public class AccountLog extends ModelObject {

	private static final long serialVersionUID = 807356556179952819L;
	public static final int ACCOUNT_SAVING = Constants.ACCOUNT_SAVING;
	public static final int ACCOUNT_DRAWING = Constants.ACCOUNT_DRAWING;
	public static final int ACCOUNT_ADJUSTING = Constants.ACCOUNT_ADJUSTING;
	public static final int ACCOUNT_OTHER = Constants.ACCOUNT_OTHER;

	private User user;
	private double userMoney;
	private double frozenMoney;
	private int rankPoints;
	private int payPoints;
	private Timestamp changeTime;
	private String changeDescription;
	private int changeType;

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
	}

	public double getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	public int getRankPoints() {
		return rankPoints;
	}

	public void setRankPoints(int rankPoints) {
		this.rankPoints = rankPoints;
	}

	public int getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(int payPoints) {
		this.payPoints = payPoints;
	}

	public Timestamp getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}

	public String getChangeDescription() {
		return changeDescription;
	}

	public void setChangeDescription(String changeDescription) {
		this.changeDescription = changeDescription;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

}
