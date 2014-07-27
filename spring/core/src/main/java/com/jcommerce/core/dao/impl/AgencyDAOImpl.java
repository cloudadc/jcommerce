/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.AgencyDAO;
import com.jcommerce.core.model.Agency;

@Repository
@SuppressWarnings("unchecked")
public class AgencyDAOImpl extends DAOImpl implements AgencyDAO {
    public AgencyDAOImpl() {
        modelClass = Agency.class;
    }

    public List<Agency> getAgencyList() {
        return getList();
    }

    public Agency getAgency(Long id) {
        return (Agency)getById(id);
    }

    public void saveAgency(Agency obj) {
        save(obj);
    }

    public void removeAgency(Long id) {
        deleteById(id);
    }
}
