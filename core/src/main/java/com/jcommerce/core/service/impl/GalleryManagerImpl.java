/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcommerce.core.dao.GalleryDAO;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GalleryManager;

@Service("galleryManager")
public class GalleryManagerImpl extends ManagerImpl implements GalleryManager {
    private static Log log = LogFactory.getLog(GalleryManagerImpl.class);
    
    @Autowired
    private GalleryDAO dao;

    public void setGalleryDAO(GalleryDAO dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public Gallery initialize(Gallery obj) {
        if (obj != null && !Hibernate.isInitialized(obj)) {
            obj = dao.getGallery(obj.getId());
        }
        return obj;
    }

    public List<Gallery> getGalleryList(int firstRow, int maxRow) {
        List list = getList(firstRow, maxRow);
        return (List<Gallery>)list;
    }

    public int getGalleryCount(Criteria criteria) {
        return getCount(criteria);
    }

    public List<Gallery> getGalleryList(Criteria criteria) {
        List list = getList(criteria);
        return (List<Gallery>)list;
    }

    public List<Gallery> getGalleryList(int firstRow, int maxRow, Criteria criteria) {
        List list = getList(firstRow, maxRow, criteria);
        return (List<Gallery>)list;
    }

    public List<Gallery> getGalleryList() {
        return dao.getGalleryList();
    }

    public Gallery getGallery(String id) {
        Gallery obj = dao.getGallery(id);
        return obj;
    }

    public void saveGallery(Gallery obj) {
        dao.saveGallery(obj);
    }

    public void removeGallery(String id) {
        dao.removeGallery(id);
    }
}
