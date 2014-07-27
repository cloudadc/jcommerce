/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.TopicDAO;
import com.jcommerce.core.model.Topic;

@Repository
@SuppressWarnings("unchecked")
public class TopicDAOImpl extends DAOImpl implements TopicDAO {
    public TopicDAOImpl() {
        modelClass = Topic.class;
    }

    public List<Topic> getTopicList() {
        return getList();
    }

    public Topic getTopic(Long id) {
        return (Topic)getById(id);
    }

    public void saveTopic(Topic obj) {
        save(obj);
    }

    public void removeTopic(Long id) {
        deleteById(id);
    }
}
