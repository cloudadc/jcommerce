/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.FeedbackDAO;
import com.jcommerce.core.model.Feedback;

@Repository
@SuppressWarnings("unchecked")
public class FeedbackDAOImpl extends DAOImpl implements FeedbackDAO {
    public FeedbackDAOImpl() {
        modelClass = Feedback.class;
    }

    public List<Feedback> getFeedbackList() {
        return getList();
    }

    public Feedback getFeedback(Long id) {
        return (Feedback)getById(id);
    }

    public void saveFeedback(Feedback obj) {
        save(obj);
    }

    public void removeFeedback(Long id) {
        deleteById(id);
    }
}
