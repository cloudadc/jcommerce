/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vote", catalog = "ishop")
public class Vote extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = -1310380498065393350L;
	private String voteName;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean MultipleSelection;//能否多选
	private int voteCount;
	
	private Set<VoteLog> voteLogs = new HashSet<VoteLog>();
	private Set<VoteOption> voteOptions = new HashSet<VoteOption>();
	private Set<VoteData> voteDatas = new HashSet<VoteData>();

	@Basic( optional = true )
	@Column( name = "vote_name", length = 250  )
	public String getVoteName() {
		return voteName;
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}	

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public boolean isMultipleSelection() {
		return MultipleSelection;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		MultipleSelection = multipleSelection;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "vote"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "vote_id", nullable = false  )
	public Set<VoteLog> getVoteLogs() {
		return this.voteLogs;	
	}
	
	public void addVoteLog(VoteLog voteLog) {
		voteLog.setVote(this);
		this.voteLogs.add(voteLog);
	}
	
	public void setVoteLogs(final Set<VoteLog> voteLog) {
		this.voteLogs = voteLog;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "vote"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "vote_id", nullable = false  )
	public Set<VoteOption> getVoteOptions() {
		return this.voteOptions;
	}
	
	public void addVoteOption(VoteOption voteOption) {
		voteOption.setVote(this);
		this.voteOptions.add(voteOption);
	}
	
	public void setVoteOptions(final Set<VoteOption> voteOption) {
		this.voteOptions = voteOption;
	}

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "vote"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "vote_id", nullable = false  )
	public Set<VoteData> getVoteDatas() {
		return voteDatas;
	}
	
	public void addVoteVoteData(VoteData voteData) {
		voteData.setVote(this);
		this.voteDatas.add(voteData);
	}

	public void setVoteDatas(Set<VoteData> voteDatas) {
		this.voteDatas = voteDatas;
	}

}
