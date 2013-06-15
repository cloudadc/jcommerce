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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "advertisement", catalog = "ishop")
public class Advertisement extends ModelObject {

	private static final long serialVersionUID = -5562240234839018466L;
	public static final int IMG_AD = 0; // 图片广告
    public static final int FALSH_AD = 1; // flash广告
    public static final int CODE_AD = 2; // 代码广告
    public static final int TEXT_AD = 3; // 文字广告
    
    private Set<Adsense> adsenses = new HashSet<Adsense>();

	private AdPosition adPosition;
	private int mediaType;
	private String adName;
	private String adLink;
	private String adCode;
	private Timestamp startTime;
	private Timestamp endTime;
	private String linkMan;
	private String linkEmail;
	private String linkPhone;
	private int clickCount;
	private boolean enabled;
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "advertisement"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "ad_id", nullable = false  )
	public Set<Adsense> getAdsenses() {
		return this.adsenses;
	}
	
	public void addAdsense(Adsense adsense) {
		adsense.setAdvertisement(this);
		this.adsenses.add(adsense);
	}
	
	public void setAdsenses(final Set<Adsense> adsense) {
		this.adsenses = adsense;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "position_id", nullable = true )
	public AdPosition getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(AdPosition adPosition) {
		this.adPosition = adPosition;
	}

	@Basic( optional = true )
	@Column( name = "media_type"  )
	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	@Basic( optional = true )
	@Column( name = "ad_name", length = 60  )
	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	@Basic( optional = true )
	@Column( name = "ad_link", length = 255  )
	public String getAdLink() {
		return adLink;
	}

	public void setAdLink(String adLink) {
		this.adLink = adLink;
	}

	@Basic( optional = true )
	@Column( name = "ad_code", length = 2147483647  )
	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	@Basic( optional = true )
	@Column( name = "start_time"  )
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Basic( optional = true )
	@Column( name = "end_time"  )
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Basic( optional = true )
	@Column( name = "link_man", length = 60  )
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Basic( optional = true )
	@Column( name = "link_email", length = 60  )
	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	@Basic( optional = true )
	@Column( name = "link_phone", length = 60  )
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Basic( optional = true )
	@Column( name = "click_count"  )
	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
