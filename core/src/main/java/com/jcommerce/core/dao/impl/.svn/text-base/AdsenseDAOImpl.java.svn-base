/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AdsenseDAO;
import com.jcommerce.core.model.Adsense;

public class AdsenseDAOImpl extends DAOImpl implements AdsenseDAO {
    public AdsenseDAOImpl() {
        modelClass = Adsense.class;
    }

    public List<Adsense> getAdsenseList() {
        return getList();
    }

    public Adsense getAdsense(String id) {
        return (Adsense)getById(id);
    }

    public void saveAdsense(Adsense obj) {
        save(obj);
    }

    public void removeAdsense(String id) {
        deleteById(id);
    }
}
