package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Snatch;

public interface SnatchDAO extends DAO {
	public List<Snatch> getSnatchList();

	public Snatch getSnatch(Long id);

	public void saveSnatch(Snatch obj);

	public void removeSnatch(Long id);
}
