/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.service.Criteria;
public interface GalleryManager extends Manager {
    public Gallery initialize(Gallery obj);

    public List<Gallery> getGalleryList(int firstRow, int maxRow);

    public int getGalleryCount(Criteria criteria);

    public List<Gallery> getGalleryList(Criteria criteria);

    public List<Gallery> getGalleryList(int firstRow, int maxRow, Criteria criteria);

    public List<Gallery> getGalleryList();

    public Gallery getGallery(String id);

    public void saveGallery(Gallery obj);

    public void removeGallery(String id);
}
