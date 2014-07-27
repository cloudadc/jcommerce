package com.jcommerce.web.to;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.UserAddress;

public class UserAddressWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = -540864110403187412L;
	UserAddress userAddress;

    @Override
    protected Object getWrapped() {
        return getUserAddress();
    }

    public UserAddressWrapper(ModelObject userAddress) {
        super();
        this.userAddress = (UserAddress) userAddress;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public String getAddressId() {
        String id = getUserAddress() == null ? "" : getUserAddress().getId();
        if (id == null) {
            id = "";
        }
        return id;
    }

    public String getAddress() {
        return StringUtils.defaultIfEmpty(getUserAddress().getAddress(), "");
    }

    public void setAddress(java.lang.String newAddress) {
        userAddress.setAddress(newAddress);
    }

    public java.lang.String getAddressName() {
        return StringUtils.defaultIfEmpty(getUserAddress().getName(), "");
    }

    public void setAddressName(java.lang.String newAddressName) {
        userAddress.setName(newAddressName);
    }

//    public java.lang.String getUser() {
//        return StringUtils.defaultIfEmpty(getUserAddress().getUser().getId(), "");
//    }
//
//    public void setUser(java.lang.String newUserId) {
//        userAddress.setUser(SpringUtil.getUserManager().getUser(newUserId));
//    }

    public java.lang.String getConsignee() {
        return StringUtils.defaultIfEmpty(getUserAddress().getConsignee(), "");
    }

    public void setConsignee(java.lang.String newConsignee) {
        userAddress.setConsignee(newConsignee);
    }

    public java.lang.String getEmail() {
        return StringUtils.defaultIfEmpty(getUserAddress().getEmail(), "");
    }

    public void setEmail(java.lang.String newEmail) {
        userAddress.setEmail(newEmail);
    }

    public String getZip() {
        return StringUtils.defaultIfEmpty(getUserAddress().getZip(), "");
    }

    public void setZip(String newZipcode) {
        userAddress.setZip(newZipcode);
    }

    public String getPhone() {
        return StringUtils.defaultIfEmpty(getUserAddress().getPhone(), "");
    }

    public void setPhone(String newTel) {
        userAddress.setPhone(newTel);
    }

    public String getMobile() {
        return StringUtils.defaultIfEmpty(getUserAddress().getMobile(), "");
    }

    public void setMobile(String newMobile) {
        userAddress.setMobile(newMobile);
    }

    public java.lang.String getSignBuilding() {
        return StringUtils.defaultIfEmpty(getUserAddress().getSignBuilding(), "");
    }

    public void setSignBuilding(java.lang.String newSignBuilding) {
        userAddress.setSignBuilding(newSignBuilding);
    }

    public java.lang.String getBestTime() {
        return StringUtils.defaultIfEmpty(getUserAddress().getBestTime(), "");
    }

    public void setBestTime(java.lang.String newBestTime) {
        userAddress.setBestTime(newBestTime);
    }

    // public java.lang.String getCountry() {
    // return StringUtils.defaultIfEmpty(getUserAddress().getCountry(), "");
    // }
    //
    //
    // public void setCountry(java.lang.String country) {
    // userAddress.setCountry(country);
    // }
    //
    //
    // public java.lang.String getProvince() {
    // return StringUtils.defaultIfEmpty(getUserAddress().getProvince(), "");
    // }
    //
    //
    // public void setProvince(java.lang.String province) {
    // userAddress.setProvince(province);
    // }
    //
    //
    // public java.lang.String getCity() {
    // return StringUtils.defaultIfEmpty(getUserAddress().getCity(), "");
    // }
    //
    //
    // public void setCity(java.lang.String city) {
    // userAddress.setCity(city);
    // }
    //
    //
    // public java.lang.String getDistrict() {
    // return StringUtils.defaultIfEmpty(getUserAddress().getDistrict(), "");
    // }
    //
    //
    // public void setDistrict(java.lang.String district) {
    // userAddress.setDistrict(district);
    // }
}
