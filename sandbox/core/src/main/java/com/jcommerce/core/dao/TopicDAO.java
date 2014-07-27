/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Topic;

public interface TopicDAO extends DAO {
    public List<Topic> getTopicList();

    public Topic getTopic(Long id);

    public void saveTopic(Topic obj);

    public void removeTopic(Long id);
}
