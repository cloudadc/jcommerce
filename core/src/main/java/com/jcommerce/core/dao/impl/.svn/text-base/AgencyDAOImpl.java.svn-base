/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.AgencyDAO;
import com.jcommerce.core.model.Agency;

public class AgencyDAOImpl extends DAOImpl implements AgencyDAO {
    public AgencyDAOImpl() {
        modelClass = Agency.class;
    }

    public List<Agency> getAgencyList() {
        return getList();
    }

    public Agency getAgency(String id) {
        return (Agency)getById(id);
    }

    public void saveAgency(Agency obj) {
        save(obj);
    }

    public void removeAgency(String id) {
        deleteById(id);
    }
}
