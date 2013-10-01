/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.CommentDAO;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CommentManager;

@Service("CommentManager")
public class CommentManagerImpl extends ManagerImpl implements CommentManager {
    private static Log log = LogFactory.getLog(CommentManagerImpl.class);
    
    @Autowired
    private CommentDAO dao;

    public void setCommentDAO(CommentDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Comment initialize(Comment obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getComment(obj.getId());
        }
        return obj;
    }

    public List<Comment> getCommentList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Comment>)list;
    }

    public int getCommentCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Comment> getCommentList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Comment>)list;
    }

    public List<Comment> getCommentList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Comment>)list;
    }

    public List<Comment> getCommentList() {
        return dao.getCommentList();
    }

    public Comment getComment(String id) {
        Comment obj = dao.getComment(id);
        return obj;
    }

    public void saveComment(Comment obj) {
        dao.saveComment(obj);
    }

    public void removeComment(String id) {
        dao.removeComment(id);
    }
}
