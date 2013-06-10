/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.service.Criteria;
public interface CommentManager extends Manager {
    public Comment initialize(Comment obj);

    public List<Comment> getCommentList(int firstRow, int maxRow);

    public int getCommentCount(Criteria criteria);

    public List<Comment> getCommentList(Criteria criteria);

    public List<Comment> getCommentList(int firstRow, int maxRow, Criteria criteria);

    public List<Comment> getCommentList();

    public Comment getComment(String id);

    public void saveComment(Comment obj);

    public void removeComment(String id);
}
