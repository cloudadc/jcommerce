/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.SnatchLog;

public interface SnatchLogDAO extends DAO {
    public List<SnatchLog> getSnatchLogList();

    public SnatchLog getSnatchLog(Long id);

    public void saveSnatchLog(SnatchLog obj);

    public void removeSnatchLog(Long id);
}
