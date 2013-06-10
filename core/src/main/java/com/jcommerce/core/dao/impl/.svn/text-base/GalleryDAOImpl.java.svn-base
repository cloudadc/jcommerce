/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.GalleryDAO;
import com.jcommerce.core.model.Gallery;

public class GalleryDAOImpl extends DAOImpl implements GalleryDAO {
    public GalleryDAOImpl() {
        modelClass = Gallery.class;
    }

    public List<Gallery> getGalleryList() {
        return getList();
    }

    public Gallery getGallery(String id) {
        return (Gallery)getById(id);
    }

    public void saveGallery(Gallery obj) {
        save(obj);
    }

    public void removeGallery(String id) {
        deleteById(id);
    }
}
