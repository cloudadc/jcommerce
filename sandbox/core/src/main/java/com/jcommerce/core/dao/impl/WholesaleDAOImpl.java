/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.WholesaleDAO;
import com.jcommerce.core.model.Wholesale;

@Repository
@SuppressWarnings("unchecked")
public class WholesaleDAOImpl extends DAOImpl implements WholesaleDAO {
    public WholesaleDAOImpl() {
        modelClass = Wholesale.class;
    }

    public List<Wholesale> getWholesaleList() {
        return getList();
    }

    public Wholesale getWholesale(Long id) {
        return (Wholesale)getById(id);
    }

    public void saveWholesale(Wholesale obj) {
        save(obj);
    }

    public void removeWholesale(Long id) {
        deleteById(id);
    }
}
