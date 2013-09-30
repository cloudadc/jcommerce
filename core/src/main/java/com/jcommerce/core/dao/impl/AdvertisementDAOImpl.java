/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AdvertisementDAO;
import com.jcommerce.core.model.Advertisement;

@Repository
@SuppressWarnings("unchecked")
public class AdvertisementDAOImpl extends DAOImpl implements AdvertisementDAO {
    public AdvertisementDAOImpl() {
        modelClass = Advertisement.class;
    }

    public List<Advertisement> getAdvertisementList() {
        return getList();
    }

    public Advertisement getAdvertisement(String id) {
        return (Advertisement)getById(id);
    }

    public void saveAdvertisement(Advertisement obj) {
        save(obj);
    }

    public void removeAdvertisement(String id) {
        deleteById(id);
    }
}
