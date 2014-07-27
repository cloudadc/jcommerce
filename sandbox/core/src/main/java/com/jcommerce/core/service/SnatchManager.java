package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Snatch;

public interface SnatchManager extends Manager {
	public Snatch initialize(Snatch obj);

    public List<Snatch> getSnatchList(int firstRow, int maxRow);

    public int getSnatchCount(Criteria criteria);

    public List<Snatch> getSnatchList(Criteria criteria);

    public List<Snatch> getSnatchList(int firstRow, int maxRow, Criteria criteria);

    public List<Snatch> getSnatchList();

    public Snatch getSnatch(Long id);

    public void saveSnatch(Snatch obj);

    public void removeSnatch(Long id);
}
