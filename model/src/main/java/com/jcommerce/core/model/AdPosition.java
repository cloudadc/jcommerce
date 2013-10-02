/**
 * Author: Kylin Soong	   	
 */

package com.jcommerce.core.model;

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
@Table(name = "ad_position")
public class AdPosition extends ModelObject {
	
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

	private static final long serialVersionUID = 4521841867219529430L;
	private String positionName;
	private int adWidth;
	private int adHeight;
	private String positionDescription;
	private String positionStyle;
	
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();

	@Basic( optional = true )
	@Column( name = "position_name", length = 60  )
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Basic( optional = true )
	@Column( name = "ad_width"  )
	public int getAdWidth() {
		return adWidth;
	}

	public void setAdWidth(int adWidth) {
		this.adWidth = adWidth;
	}

	@Basic( optional = true )
	@Column( name = "ad_height"  )
	public int getAdHeight() {
		return adHeight;
	}

	public void setAdHeight(int adHeight) {
		this.adHeight = adHeight;
	}

	@Basic( optional = true )
	@Column( name = "position_desc", length = 255  )
	public String getPositionDescription() {
		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	@Basic( optional = true )
	@Column( name = "position_style", length = 2147483647  )
	public String getPositionStyle() {
		return positionStyle;
	}

	public void setPositionStyle(String positionStyle) {
		this.positionStyle = positionStyle;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "adPosition"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "position_id", nullable = false  )
	public Set<Advertisement> getAdvertisements() {
		return this.advertisements;
	}
	
	public void addAdvertisement(Advertisement advertisement) {
		advertisement.setAdPosition(this);
		this.advertisements.add(advertisement);
	}
	
	public void setAdvertisements(final Set<Advertisement> advertisement) {
		this.advertisements = advertisement;
	}

}
