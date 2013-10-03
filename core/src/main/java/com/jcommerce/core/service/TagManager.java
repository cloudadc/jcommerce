/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Tag;
import com.jcommerce.core.service.Criteria;
public interface TagManager extends Manager {
    public Tag initialize(Tag obj);

    public List<Tag> getTagList(int firstRow, int maxRow);

    public int getTagCount(Criteria criteria);

    public List<Tag> getTagList(Criteria criteria);

    public List<Tag> getTagList(int firstRow, int maxRow, Criteria criteria);

    public List<Tag> getTagList();

    public Tag getTag(Long id);

    public void saveTag(Tag obj);

    public void removeTag(Long id);
}
