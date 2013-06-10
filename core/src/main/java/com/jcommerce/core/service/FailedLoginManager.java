/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.FailedLogin;
import com.jcommerce.core.service.Criteria;
public interface FailedLoginManager extends Manager {
    public FailedLogin initialize(FailedLogin obj);

    public List<FailedLogin> getFailedLoginList(int firstRow, int maxRow);

    public int getFailedLoginCount(Criteria criteria);

    public List<FailedLogin> getFailedLoginList(Criteria criteria);

    public List<FailedLogin> getFailedLoginList(int firstRow, int maxRow, Criteria criteria);

    public List<FailedLogin> getFailedLoginList();

    public FailedLogin getFailedLogin(String id);

    public void saveFailedLogin(FailedLogin obj);

    public void removeFailedLogin(String id);
}
