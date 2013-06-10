/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.FeedbackDAO;
import com.jcommerce.core.model.Feedback;

public class FeedbackDAOImpl extends DAOImpl implements FeedbackDAO {
    public FeedbackDAOImpl() {
        modelClass = Feedback.class;
    }

    public List<Feedback> getFeedbackList() {
        return getList();
    }

    public Feedback getFeedback(String id) {
        return (Feedback)getById(id);
    }

    public void saveFeedback(Feedback obj) {
        save(obj);
    }

    public void removeFeedback(String id) {
        deleteById(id);
    }
}
