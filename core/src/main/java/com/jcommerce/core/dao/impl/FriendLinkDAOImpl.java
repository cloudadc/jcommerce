/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.FriendLinkDAO;
import com.jcommerce.core.model.FriendLink;

public class FriendLinkDAOImpl extends DAOImpl implements FriendLinkDAO {
    public FriendLinkDAOImpl() {
        modelClass = FriendLink.class;
    }

    public List<FriendLink> getFriendLinkList() {
        return getList();
    }

    public FriendLink getFriendLink(String id) {
        return (FriendLink)getById(id);
    }

    public void saveFriendLink(FriendLink obj) {
        save(obj);
    }

    public void removeFriendLink(String id) {
        deleteById(id);
    }
}
