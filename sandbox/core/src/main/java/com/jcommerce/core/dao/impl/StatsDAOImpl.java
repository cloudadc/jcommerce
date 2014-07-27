/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.StatsDAO;
import com.jcommerce.core.model.Stats;

@Repository
@SuppressWarnings("unchecked")
public class StatsDAOImpl extends DAOImpl implements StatsDAO {
    public StatsDAOImpl() {
        modelClass = Stats.class;
    }

    public List<Stats> getStatsList() {
        return getList();
    }

    public Stats getStats(Long id) {
        return (Stats)getById(id);
    }

    public void saveStats(Stats obj) {
        save(obj);
    }

    public void removeStats(Long id) {
        deleteById(id);
    }
}
