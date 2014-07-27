package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.SnatchDAO;
import com.jcommerce.core.model.Snatch;

@Repository
@SuppressWarnings("unchecked")
public class SnatchDAOImpl extends DAOImpl implements SnatchDAO {

	public SnatchDAOImpl() {
        modelClass = Snatch.class;
    }

    public List<Snatch> getSnatchList() {
        return getList();
    }

    public Snatch getSnatch(Long id) {
        return (Snatch)getById(id);
    }

    public void saveSnatch(Snatch obj) {
        save(obj);
    }

    public void removeSnatch(Long id) {
        deleteById(id);
    }

}
