/**
* @Author: KingZhao
*          Kylin Soong
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.FeedbackDAO;
import com.jcommerce.core.model.Feedback;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.FeedbackManager;

@Service("FeedbackManager")
public class FeedbackManagerImpl extends ManagerImpl implements FeedbackManager {
    private static Log log = LogFactory.getLog(FeedbackManagerImpl.class);
    
    @Autowired
    private FeedbackDAO dao;

    public void setFeedbackDAO(FeedbackDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Feedback initialize(Feedback obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getFeedback(obj.getId());
        }
        return obj;
    }

    public List<Feedback> getFeedbackList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Feedback>)list;
    }

    public int getFeedbackCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Feedback> getFeedbackList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Feedback>)list;
    }

    public List<Feedback> getFeedbackList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Feedback>)list;
    }

    public List<Feedback> getFeedbackList() {
        return dao.getFeedbackList();
    }

    public Feedback getFeedback(Long id) {
        Feedback obj = dao.getFeedback(id);
        return obj;
    }

    public void saveFeedback(Feedback obj) {
        dao.saveFeedback(obj);
    }

    public void removeFeedback(Long id) {
        dao.removeFeedback(id);
    }
}
