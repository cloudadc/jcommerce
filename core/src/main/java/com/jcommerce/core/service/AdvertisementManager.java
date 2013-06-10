/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Advertisement;
import com.jcommerce.core.service.Criteria;
public interface AdvertisementManager extends Manager {
    public Advertisement initialize(Advertisement obj);

    public List<Advertisement> getAdvertisementList(int firstRow, int maxRow);

    public int getAdvertisementCount(Criteria criteria);

    public List<Advertisement> getAdvertisementList(Criteria criteria);

    public List<Advertisement> getAdvertisementList(int firstRow, int maxRow, Criteria criteria);

    public List<Advertisement> getAdvertisementList();

    public Advertisement getAdvertisement(String id);

    public void saveAdvertisement(Advertisement obj);

    public void removeAdvertisement(String id);
}
