/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.FriendLink;

public interface FriendLinkDAO extends DAO {
    public List<FriendLink> getFriendLinkList();

    public FriendLink getFriendLink(String id);

    public void saveFriendLink(FriendLink obj);

    public void removeFriendLink(String id);
}
