/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "goods_activity")
public class GoodsActivity extends ModelObject {
	
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
    
	private static final long serialVersionUID = 8787172027468081897L;
	public static final int TYPE_SNATCH = Constants.GOODSACT_SNATCH;
    public static final int TYPE_GROUP_BUY = Constants.GOODSACT_GROUP_BUY;
    public static final int TYPE_AUCTION = Constants.GOODSACT_AUCTION;
    public static final int TYPE_POINTS_BUY = Constants.GOODSACT_POINTS_BUY;
    
    private String name;
    private String description;
    private int type;
    private Timestamp startTime;
    private Timestamp endTime;
    private Goods goods;
    private boolean finished;
    private String extraInfo;

    @Basic( optional = true )
	@Column( name = "act_name", length = 255  )
	public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "act_desc", length = 2147483647  )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic( optional = true )
	@Column( name = "act_type"  )
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "goods_id", nullable = true )
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Basic( optional = true )
	@Column( name = "is_finished"  )
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Basic( optional = true )
	@Column( name = "ext_info", length = 2147483647  )
    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

}
