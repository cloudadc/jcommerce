/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.GalleryDAO;
import com.jcommerce.core.model.Gallery;

@Repository
@SuppressWarnings("unchecked")
public class GalleryDAOImpl extends DAOImpl implements GalleryDAO {
    public GalleryDAOImpl() {
        modelClass = Gallery.class;
    }

    public List<Gallery> getGalleryList() {
        return getList();
    }

    public Gallery getGallery(Long id) {
        return (Gallery)getById(id);
    }

    public void saveGallery(Gallery obj) {
        save(obj);
    }

    public void removeGallery(Long id) {
        deleteById(id);
    }
}
