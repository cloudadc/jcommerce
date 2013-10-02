/**
 * @author Kylin Soong     
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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "snatch")
public class Snatch extends ModelObject {
	
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

	private static final long serialVersionUID = 7472289754726786363L;

	private String snachname;
	
	private String goodName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private double maxPrice;
	
	private double minPrice;
	
	private int scoreConsum;
	
	private double maxpaidPrice;
	
	private String description;
	
	private Set<SnatchLog> snatchLogs = new HashSet<SnatchLog>();

	@Basic( optional = true )
	@Column( length = 255  )
	public String getSnachname() {
		return snachname;
	}

	public void setSnachname(String snachname) {
		this.snachname = snachname;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
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

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public int getScoreConsum() {
		return scoreConsum;
	}

	public void setScoreConsum(int scoreConsum) {
		this.scoreConsum = scoreConsum;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getMaxpaidPrice() {
		return maxpaidPrice;
	}

	public void setMaxpaidPrice(double maxpaidPrice) {
		this.maxpaidPrice = maxpaidPrice;
	}

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( nullable = false  )
	public Set<SnatchLog> getSnatchLogs() {
		return snatchLogs;
	}

	public void setSnatchLogs(Set<SnatchLog> snatchLogs) {
		this.snatchLogs = snatchLogs;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
