/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.AffiliateLog;

public interface AffiliateLogDAO extends DAO {
    public List<AffiliateLog> getAffiliateLogList();

    public AffiliateLog getAffiliateLog(Long id);

    public void saveAffiliateLog(AffiliateLog obj);

    public void removeAffiliateLog(Long id);
}
