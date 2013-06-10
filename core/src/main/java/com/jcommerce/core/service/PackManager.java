/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Pack;
import com.jcommerce.core.service.Criteria;
public interface PackManager extends Manager {
    public Pack initialize(Pack obj);

    public List<Pack> getPackList(int firstRow, int maxRow);

    public int getPackCount(Criteria criteria);

    public List<Pack> getPackList(Criteria criteria);

    public List<Pack> getPackList(int firstRow, int maxRow, Criteria criteria);

    public List<Pack> getPackList();

    public Pack getPack(String id);

    public void savePack(Pack obj);

    public void removePack(String id);
}
