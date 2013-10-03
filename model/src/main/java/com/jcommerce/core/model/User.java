/**
 * Author: Bob Chen
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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_")
public class User extends ModelObject {
	
	private Long id;
	
	@Id 
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = 5770487304958940671L;
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
    private Timestamp registerTime;
    private Timestamp lastLogin;
    private Timestamp lastTime;
    private String lastIP;
    private int visitCount;
    private String rank;   // rank in UserRank table
    
    /**
     * 信用额度
     */
    private double creditLine;
    
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
    
    private Set<AccountLog> accountLogs = new HashSet<AccountLog>();
    private Set<AdminLog> adminLogs = new HashSet<AdminLog>();
    private Set<AffiliateLog> affiliateLogs = new HashSet<AffiliateLog>();
    private Set<BookingGoods> bookingGoodss = new HashSet<BookingGoods>();
    private Set<Cart> carts = new HashSet<Cart>();
    private Set<CollectGoods> collectGoodss = new HashSet<CollectGoods>();
    private Set<Comment> comments = new HashSet<Comment>();
    private Set<Feedback> feedbacks = new HashSet<Feedback>();
    private Set<Order> orders = new HashSet<Order>();
    private Set<Session> sessions = new HashSet<Session>();
    private Set<Tag> tags = new HashSet<Tag>();
    private Set<User> users = new HashSet<User>();
    private Set<UserAccount> userAccounts = new HashSet<UserAccount>();
    private Set<UserAddress> userAddresses = new HashSet<UserAddress>();
  
    @Basic( optional = true )
	@Column( name = "user_name", length = 60  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( length = 32  )
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
	@Column( length = 255  )
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic( optional = true )
	@Column( length = 255  )
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

    @Basic( optional = true )
	@Column( name = "last_ip", length = 15  )
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

    @Basic( optional = true )
	@Column( name = "user_rank", length = 32  )
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

    @Basic( optional = true )
	@Column( length = 10  )
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

    @Column( length = 60  )
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic( optional = true )
	@Column( length = 60  )
    public String getMSN() {
        return MSN;
    }

    public void setMSN(String msn) {
        MSN = msn;
    }

    @Basic( optional = true )
	@Column( length = 20  )
    public String getQQ() {
        return QQ;
    }

    public void setQQ(String qq) {
        QQ = qq;
    }

    @Basic( optional = true )
	@Column( name = "office_phone", length = 20  )
    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Basic( optional = true )
	@Column( name = "home_phone", length = 20  )
    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    @Basic( optional = true )
	@Column( name = "mobile_phone", length = 20  )
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

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<AccountLog> getAccountLogs() {
		return this.accountLogs;
	}
    
    public void addAccountLog(AccountLog accountLog) {
		accountLog.setUser(this);
		this.accountLogs.add(accountLog);
	}
    
    public void setAccountLogs(final Set<AccountLog> accountLog) {
		this.accountLogs = accountLog;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<AdminLog> getAdminLogs() {
		return this.adminLogs;
	}
    
    public void addAdminLog(AdminLog adminLog) {
		adminLog.setUser(this);
		this.adminLogs.add(adminLog);
	}
    
    public void setAdminLogs(final Set<AdminLog> adminLog) {
		this.adminLogs = adminLog;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<AffiliateLog> getAffiliateLogs() {
		return this.affiliateLogs;
	}
    
    public void addAffiliateLog(AffiliateLog affiliateLog) {
		affiliateLog.setUser(this);
		this.affiliateLogs.add(affiliateLog);
	}
    
    public void setAffiliateLogs(final Set<AffiliateLog> affiliateLog) {
		this.affiliateLogs = affiliateLog;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<BookingGoods> getBookingGoodss() {
		return this.bookingGoodss;
	}
    
    public void addBookingGoods(BookingGoods bookingGoods) {
		bookingGoods.setUser(this);
		this.bookingGoodss.add(bookingGoods);
	}
    
    public void setBookingGoodss(final Set<BookingGoods> bookingGoods) {
		this.bookingGoodss = bookingGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<Cart> getCarts() {
		return this.carts;
	}
    
    public void addCart(Cart cart) {
		cart.setUser(this);
		this.carts.add(cart);
	}
    
    public void setCarts(final Set<Cart> cart) {
		this.carts = cart;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<CollectGoods> getCollectGoodss() {
		return this.collectGoodss;
	}
    
    public void addCollectGoods(CollectGoods collectGoods) {
		collectGoods.setUser(this);
		this.collectGoodss.add(collectGoods);
	}
    
    public void setCollectGoodss(final Set<CollectGoods> collectGoods) {
		this.collectGoodss = collectGoods;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<Comment> getComments() {
		return this.comments;
	}
    
    public void addComment(Comment comment) {
		comment.setUser(this);
		this.comments.add(comment);
	}
    
    public void setComments(final Set<Comment> comment) {
		this.comments = comment;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<Feedback> getFeedbacks() {
		return this.feedbacks;
	}
    
    public void addFeedback(Feedback feedback) {
		feedback.setUser(this);
		this.feedbacks.add(feedback);
	}
    
    public void setFeedbacks(final Set<Feedback> feedback) {
		this.feedbacks = feedback;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}
    
    public void addOrder(Order order) {
		order.setUser(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
    public Set<Session> getSessions() {
		return sessions;
	}
    
    public void addSessions(Session session) {
		session.setUser(this);
		this.sessions.add(session);
	}

	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<Tag> getTags() {
		return this.tags;
	}
    
    public void addTag(Tag tag) {
		tag.setUser(this);
		this.tags.add(tag);
	}
    
    public void setTags(final Set<Tag> tag) {
		this.tags = tag;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<User> getUsers() {
		return this.users;
	}
    
    public void addUser(User user) {
		user.setParent(this);
		this.users.add(user);
	}
    
    public void setUsers(final Set<User> user) {
		this.users = user;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<UserAccount> getUserAccounts() {
		return this.userAccounts;
	}
    
    public void addUserAccount(UserAccount userAccount) {
		userAccount.setUser(this);
		this.userAccounts.add(userAccount);
	}
    
    public void setUserAccounts(final Set<UserAccount> userAccount) {
		this.userAccounts = userAccount;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "user_id", nullable = false  )
	public Set<UserAddress> getUserAddresses() {
		return this.userAddresses;
	}
    
    public void addUserAddress(UserAddress userAddress) {
		userAddress.setUser(this);
		this.userAddresses.add(userAddress);
	}
    
    public void setUserAddresses(final Set<UserAddress> userAddress) {
		this.userAddresses = userAddress;
	}
}
