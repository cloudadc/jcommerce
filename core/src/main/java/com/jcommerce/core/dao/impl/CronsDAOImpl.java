/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.CronsDAO;
import com.jcommerce.core.model.Crons;

@Repository
@SuppressWarnings("unchecked")
public class CronsDAOImpl extends DAOImpl implements CronsDAO {
    public CronsDAOImpl() {
        modelClass = Crons.class;
    }

    public List<Crons> getCronsList() {
        return getList();
    }

    public Crons getCrons(String id) {
        return (Crons)getById(id);
    }

    public void saveCrons(Crons obj) {
        save(obj);
    }

    public void removeCrons(String id) {
        deleteById(id);
    }
}
