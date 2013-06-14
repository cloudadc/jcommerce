/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

public class BookingGoods extends ModelObject {
    
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }

    public String getDisposeUser() {
        return disposeUser;
    }

    public void setDisposeUser(String disposeUser) {
        this.disposeUser = disposeUser;
    }

    public Timestamp getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Timestamp disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getDisposeNote() {
        return disposeNote;
    }

    public void setDisposeNote(String disposeNote) {
        this.disposeNote = disposeNote;
    }

}
