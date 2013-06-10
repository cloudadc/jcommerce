/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.UserRankDAO;
import com.jcommerce.core.model.UserRank;

public class UserRankDAOImpl extends DAOImpl implements UserRankDAO {
    public UserRankDAOImpl() {
        modelClass = UserRank.class;
    }

    public List<UserRank> getUserRankList() {
        return getList();
    }

    public UserRank getUserRank(String id) {
        return (UserRank)getById(id);
    }

    public void saveUserRank(UserRank obj) {
        save(obj);
    }

    public void removeUserRank(String id) {
        deleteById(id);
    }
}
