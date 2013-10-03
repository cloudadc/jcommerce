/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.UserRankDAO;
import com.jcommerce.core.model.UserRank;

@Repository
@SuppressWarnings("unchecked")
public class UserRankDAOImpl extends DAOImpl implements UserRankDAO {
    public UserRankDAOImpl() {
        modelClass = UserRank.class;
    }

    public List<UserRank> getUserRankList() {
        return getList();
    }

    public UserRank getUserRank(Long id) {
        return (UserRank)getById(id);
    }

    public void saveUserRank(UserRank obj) {
        save(obj);
    }

    public void removeUserRank(Long id) {
        deleteById(id);
    }
}
