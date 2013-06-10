/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AdPositionDAO;
import com.jcommerce.core.model.AdPosition;

public class AdPositionDAOImpl extends DAOImpl implements AdPositionDAO {
    public AdPositionDAOImpl() {
        modelClass = AdPosition.class;
    }

    public List<AdPosition> getAdPositionList() {
        return getList();
    }

    public AdPosition getAdPosition(String id) {
        return (AdPosition)getById(id);
    }

    public void saveAdPosition(AdPosition obj) {
        save(obj);
    }

    public void removeAdPosition(String id) {
        deleteById(id);
    }
}
