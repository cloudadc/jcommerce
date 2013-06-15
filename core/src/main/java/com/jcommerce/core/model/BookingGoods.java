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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "booking_goods", catalog = "ishop")
public class BookingGoods extends ModelObject {
    
	private static final long serialVersionUID = -920761604883319608L;
	private User user;
	private String email;
	private String linker;
	private String phone;
	private Goods goods;
	private String goodsDescription;
	private int goodsNumber;
	private Timestamp time;
	private boolean disposed;
	private String disposeUser;
	private Timestamp disposeTime;
	private String disposeNote;

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
	@Column( length = 60  )
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic( optional = true )
	@Column( name = "link_man", length = 60  )
    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
	@Column( name = "goods_desc", length = 255  )
    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    @Basic( optional = true )
	@Column( name = "goods_number"  )
    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    @Basic( optional = true )
	@Column( name = "booking_time"  )
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic( optional = true )
	@Column( name = "is_dispose"  )
    public boolean isDisposed() {
        return disposed;
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }

    @Basic( optional = true )
	@Column( name = "dispose_user", length = 30  )
    public String getDisposeUser() {
        return disposeUser;
    }

    public void setDisposeUser(String disposeUser) {
        this.disposeUser = disposeUser;
    }

    @Basic( optional = true )
	@Column( name = "dispose_time"  )
    public Timestamp getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Timestamp disposeTime) {
        this.disposeTime = disposeTime;
    }

    @Basic( optional = true )
	@Column( name = "dispose_note", length = 255  )
    public String getDisposeNote() {
        return disposeNote;
    }

    public void setDisposeNote(String disposeNote) {
        this.disposeNote = disposeNote;
    }

}
