/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Tag;

public interface TagDAO extends DAO {
    public List<Tag> getTagList();

    public Tag getTag(String id);

    public void saveTag(Tag obj);

    public void removeTag(String id);
}
