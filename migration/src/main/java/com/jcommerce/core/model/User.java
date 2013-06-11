/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_User")
public class User extends ModelObject {
  
	private static final long serialVersionUID = -2426789348173666555L;
	public static final int SEX_UNKNOWN = 0; 
    public static final int SEX_MALE = 1; 
    public static final int SEX_FEMALE = 2; 
    
    private String name;
    private String password;
    private String email;
    private String question;
    private String answer;
    private int sex;
    private Timestamp birthday;
    private double money;
    private double frozenMoney;
    private int payPoints;
    private int rankPoints;
    private Set<UserAddress> addresses = new HashSet<UserAddress>();
    private Timestamp registerTime;
    private Timestamp lastLogin;
    private Timestamp lastTime;
    private String lastIP;
    private int visitCount;
    private String rank;   // rank in UserRank table
    /**
     * 特殊会员
     */
    private boolean special;
    /**
     * 加密串
     */
    private String salt;
    /**
     * 推荐人
     */
    private User parent;
    private int flag;
    private String alias;
    private String MSN;
    private String QQ;
    private String officePhone;
    private String homePhone;
    private String mobilePhone;
    private boolean validated;
    /**
     * 信用额度
     */
    private double creditLine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(double frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public int getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(int payPoints) {
        this.payPoints = payPoints;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    public Set<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<UserAddress> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(UserAddress address) {
        if (addresses == null) {
            addresses = new HashSet<UserAddress>();
        }
        addresses.add(address);
    }
    
    public void removeAddress(UserAddress address) {
        if (addresses != null) {
            addresses.remove(address);
        }
    }
    
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMSN() {
        return MSN;
    }

    public void setMSN(String msn) {
        MSN = msn;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String qq) {
        QQ = qq;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public double getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}
