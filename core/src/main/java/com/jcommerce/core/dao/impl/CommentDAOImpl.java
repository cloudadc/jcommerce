/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.CommentDAO;
import com.jcommerce.core.model.Comment;

@Repository
@SuppressWarnings("unchecked")
public class CommentDAOImpl extends DAOImpl implements CommentDAO {
    public CommentDAOImpl() {
        modelClass = Comment.class;
    }

    public List<Comment> getCommentList() {
        return getList();
    }

    public Comment getComment(String id) {
        return (Comment)getById(id);
    }

    public void saveComment(Comment obj) {
        save(obj);
    }

    public void removeComment(String id) {
        deleteById(id);
    }
}
