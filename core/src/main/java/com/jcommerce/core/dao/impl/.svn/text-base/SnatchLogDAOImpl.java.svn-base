/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.SnatchLogDAO;
import com.jcommerce.core.model.SnatchLog;

public class SnatchLogDAOImpl extends DAOImpl implements SnatchLogDAO {
    public SnatchLogDAOImpl() {
        modelClass = SnatchLog.class;
    }

    public List<SnatchLog> getSnatchLogList() {
        return getList();
    }

    public SnatchLog getSnatchLog(String id) {
        return (SnatchLog)getById(id);
    }

    public void saveSnatchLog(SnatchLog obj) {
        save(obj);
    }

    public void removeSnatchLog(String id) {
        deleteById(id);
    }
}
