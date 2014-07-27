/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.TagDAO;
import com.jcommerce.core.model.Tag;

@Repository
@SuppressWarnings("unchecked")
public class TagDAOImpl extends DAOImpl implements TagDAO {
    public TagDAOImpl() {
        modelClass = Tag.class;
    }

    public List<Tag> getTagList() {
        return getList();
    }

    public Tag getTag(Long id) {
        return (Tag)getById(id);
    }

    public void saveTag(Tag obj) {
        save(obj);
    }

    public void removeTag(Long id) {
        deleteById(id);
    }
}
