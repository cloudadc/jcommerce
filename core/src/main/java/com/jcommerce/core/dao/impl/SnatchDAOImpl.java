package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.SnatchDAO;
import com.jcommerce.core.model.Snatch;

public class SnatchDAOImpl extends DAOImpl implements SnatchDAO {

	public SnatchDAOImpl() {
        modelClass = Snatch.class;
    }

    public List<Snatch> getSnatchList() {
        return getList();
    }

    public Snatch getSnatch(String id) {
        return (Snatch)getById(id);
    }

    public void saveSnatch(Snatch obj) {
        save(obj);
    }

    public void removeSnatch(String id) {
        deleteById(id);
    }

}
