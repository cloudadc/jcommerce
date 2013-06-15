/**
 * Author: Bob Chen
 *         Kylin Soong
 */

package com.jcommerce.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend_link", catalog = "ishop")
public class FriendLink extends ModelObject {
    
	private static final long serialVersionUID = -3659545048419273970L;
	private String name;
	private String url;
	private String logo;
	private int showOrder;

	@Basic( optional = true )
	@Column( name = "link_name", length = 255  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "link_url", length = 255  )
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic( optional = true )
	@Column( name = "link_logo", length = 255  )
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic( optional = true )
	@Column( name = "show_order"  )
    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }

}
