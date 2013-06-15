/**
 * Author: Bob Chen
 * 		   Kylin Soong	
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account_log", catalog = "ishop")
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

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "user_id", nullable = true )
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Basic( optional = true )
	@Column( name = "user_money"  )
	public double getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
	}

	@Basic( optional = true )
	@Column( name = "frozen_money"  )
	public double getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	@Basic( optional = true )
	@Column( name = "rank_points"  )
	public int getRankPoints() {
		return rankPoints;
	}

	public void setRankPoints(int rankPoints) {
		this.rankPoints = rankPoints;
	}

	@Basic( optional = true )
	@Column( name = "pay_points"  )
	public int getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(int payPoints) {
		this.payPoints = payPoints;
	}

	@Basic( optional = true )
	@Column( name = "change_time"  )
	public Timestamp getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}

	@Basic( optional = true )
	@Column( name = "change_desc", length = 255  )
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
