/**
* Author: Bob Chen
*         Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.FriendLinkDAO;
import com.jcommerce.core.model.FriendLink;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.FriendLinkManager;

@Service("FriendLinkManager")
public class FriendLinkManagerImpl extends ManagerImpl implements FriendLinkManager {
    private static Log log = LogFactory.getLog(FriendLinkManagerImpl.class);
    
    @Autowired
    private FriendLinkDAO dao;

    public void setFriendLinkDAO(FriendLinkDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public FriendLink initialize(FriendLink obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getFriendLink(obj.getId());
        }
        return obj;
    }

    public List<FriendLink> getFriendLinkList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<FriendLink>)list;
    }

    public int getFriendLinkCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<FriendLink> getFriendLinkList(Criteria criteria) {
        List list = getList(criteria);
        return (List<FriendLink>)list;
    }

    public List<FriendLink> getFriendLinkList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<FriendLink>)list;
    }

    public List<FriendLink> getFriendLinkList() {
        return dao.getFriendLinkList();
    }

    public FriendLink getFriendLink(Long id) {
        FriendLink obj = dao.getFriendLink(id);
        return obj;
    }

    public void saveFriendLink(FriendLink obj) {
        dao.saveFriendLink(obj);
    }

    public void removeFriendLink(Long id) {
        dao.removeFriendLink(id);
    }
}
