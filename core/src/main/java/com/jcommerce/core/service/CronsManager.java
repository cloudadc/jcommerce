/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Crons;
import com.jcommerce.core.service.Criteria;
public interface CronsManager extends Manager {
    public Crons initialize(Crons obj);

    public List<Crons> getCronsList(int firstRow, int maxRow);

    public int getCronsCount(Criteria criteria);

    public List<Crons> getCronsList(Criteria criteria);

    public List<Crons> getCronsList(int firstRow, int maxRow, Criteria criteria);

    public List<Crons> getCronsList();

    public Crons getCrons(String id);

    public void saveCrons(Crons obj);

    public void removeCrons(String id);
}
