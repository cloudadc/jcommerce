/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Stats;
import com.jcommerce.core.service.Criteria;
public interface StatsManager extends Manager {
    public Stats initialize(Stats obj);

    public List<Stats> getStatsList(int firstRow, int maxRow);

    public int getStatsCount(Criteria criteria);

    public List<Stats> getStatsList(Criteria criteria);

    public List<Stats> getStatsList(int firstRow, int maxRow, Criteria criteria);

    public List<Stats> getStatsList();

    public Stats getStats(String id);

    public void saveStats(Stats obj);

    public void removeStats(String id);
}
