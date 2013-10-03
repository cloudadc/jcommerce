/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.Wholesale;

public interface WholesaleDAO extends DAO {
    public List<Wholesale> getWholesaleList();

    public Wholesale getWholesale(Long id);

    public void saveWholesale(Wholesale obj);

    public void removeWholesale(Long id);
}
