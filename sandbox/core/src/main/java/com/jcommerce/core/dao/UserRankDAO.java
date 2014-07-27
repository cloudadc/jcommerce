/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.UserRank;

public interface UserRankDAO extends DAO {
    public List<UserRank> getUserRankList();

    public UserRank getUserRank(Long id);

    public void saveUserRank(UserRank obj);

    public void removeUserRank(Long id);
}
