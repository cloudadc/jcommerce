/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Topic;
import com.jcommerce.core.service.Criteria;
public interface TopicManager extends Manager {
    public Topic initialize(Topic obj);

    public List<Topic> getTopicList(int firstRow, int maxRow);

    public int getTopicCount(Criteria criteria);

    public List<Topic> getTopicList(Criteria criteria);

    public List<Topic> getTopicList(int firstRow, int maxRow, Criteria criteria);

    public List<Topic> getTopicList();

    public Topic getTopic(String id);

    public void saveTopic(Topic obj);

    public void removeTopic(String id);
}
