/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.TopicDAO;
import com.jcommerce.core.model.Topic;

public class TopicDAOImpl extends DAOImpl implements TopicDAO {
    public TopicDAOImpl() {
        modelClass = Topic.class;
    }

    public List<Topic> getTopicList() {
        return getList();
    }

    public Topic getTopic(String id) {
        return (Topic)getById(id);
    }

    public void saveTopic(Topic obj) {
        save(obj);
    }

    public void removeTopic(String id) {
        deleteById(id);
    }
}
