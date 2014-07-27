/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.FailedLogin;

public interface FailedLoginDAO extends DAO {
    public List<FailedLogin> getFailedLoginList();

    public FailedLogin getFailedLogin(Long id);

    public void saveFailedLogin(FailedLogin obj);

    public void removeFailedLogin(Long id);
}
