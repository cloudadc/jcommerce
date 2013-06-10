/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.SnatchLog;
import com.jcommerce.core.service.Criteria;
public interface SnatchLogManager extends Manager {
    public SnatchLog initialize(SnatchLog obj);

    public List<SnatchLog> getSnatchLogList(int firstRow, int maxRow);

    public int getSnatchLogCount(Criteria criteria);

    public List<SnatchLog> getSnatchLogList(Criteria criteria);

    public List<SnatchLog> getSnatchLogList(int firstRow, int maxRow, Criteria criteria);

    public List<SnatchLog> getSnatchLogList();

    public SnatchLog getSnatchLog(String id);

    public void saveSnatchLog(SnatchLog obj);

    public void removeSnatchLog(String id);
}
