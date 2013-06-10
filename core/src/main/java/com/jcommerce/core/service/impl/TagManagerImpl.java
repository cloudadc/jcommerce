/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.TagDAO;
import com.jcommerce.core.model.Tag;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.TagManager;

public class TagManagerImpl extends ManagerImpl implements TagManager {
    private static Log log = LogFactory.getLog(TagManagerImpl.class);
    private TagDAO dao;

    public void setTagDAO(TagDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Tag initialize(Tag obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getTag(obj.getId());
        }
        return obj;
    }

    public List<Tag> getTagList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Tag>)list;
    }

    public int getTagCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Tag> getTagList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Tag>)list;
    }

    public List<Tag> getTagList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Tag>)list;
    }

    public List<Tag> getTagList() {
        return dao.getTagList();
    }

    public Tag getTag(String id) {
        Tag obj = dao.getTag(id);
        return obj;
    }

    public void saveTag(Tag obj) {
        dao.saveTag(obj);
    }

    public void removeTag(String id) {
        dao.removeTag(id);
    }
}
