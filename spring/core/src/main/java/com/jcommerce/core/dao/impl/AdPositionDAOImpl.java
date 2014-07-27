/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AdPositionDAO;
import com.jcommerce.core.model.AdPosition;

@Repository
@SuppressWarnings("unchecked")
public class AdPositionDAOImpl extends DAOImpl implements AdPositionDAO {
    public AdPositionDAOImpl() {
        modelClass = AdPosition.class;
    }

    public List<AdPosition> getAdPositionList() {
        return getList();
    }

    public AdPosition getAdPosition(Long id) {
        return (AdPosition)getById(id);
    }

    public void saveAdPosition(AdPosition obj) {
        save(obj);
    }

    public void removeAdPosition(Long id) {
        deleteById(id);
    }
}
