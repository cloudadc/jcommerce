/**
* @Author: KingZhao
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.jcommerce.core.dao.GoodsArticleDAO;
import com.jcommerce.core.model.GoodsArticle;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsArticleManager;

public class GoodsArticleManagerImpl extends ManagerImpl implements GoodsArticleManager {
    private static Log log = LogFactory.getLog(GoodsArticleManagerImpl.class);
    private GoodsArticleDAO dao;

    public void setGoodsArticleDAO(GoodsArticleDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public GoodsArticle initialize(GoodsArticle obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGoodsArticle(obj.getId());
        }
        return obj;
    }

    public List<GoodsArticle> getGoodsArticleList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<GoodsArticle>)list;
    }

    public int getGoodsArticleCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<GoodsArticle> getGoodsArticleList(Criteria criteria) {
        List list = getList(criteria);
        return (List<GoodsArticle>)list;
    }

    public List<GoodsArticle> getGoodsArticleList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<GoodsArticle>)list;
    }

    public List<GoodsArticle> getGoodsArticleList() {
        return dao.getGoodsArticleList();
    }

    public GoodsArticle getGoodsArticle(String id) {
        GoodsArticle obj = dao.getGoodsArticle(id);
        return obj;
    }

    public void saveGoodsArticle(GoodsArticle obj) {
        dao.saveGoodsArticle(obj);
    }

    public void removeGoodsArticle(String id) {
        dao.removeGoodsArticle(id);
    }
}
