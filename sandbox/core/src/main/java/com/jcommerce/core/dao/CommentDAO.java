/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Comment;

public interface CommentDAO extends DAO {
    public List<Comment> getCommentList();

    public Comment getComment(Long id);

    public void saveComment(Comment obj);

    public void removeComment(Long id);
}
