/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Feedback;
import com.jcommerce.core.service.Criteria;
public interface FeedbackManager extends Manager {
    public Feedback initialize(Feedback obj);

    public List<Feedback> getFeedbackList(int firstRow, int maxRow);

    public int getFeedbackCount(Criteria criteria);

    public List<Feedback> getFeedbackList(Criteria criteria);

    public List<Feedback> getFeedbackList(int firstRow, int maxRow, Criteria criteria);

    public List<Feedback> getFeedbackList();

    public Feedback getFeedback(String id);

    public void saveFeedback(Feedback obj);

    public void removeFeedback(String id);
}
