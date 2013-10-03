/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.ShopConfigDAO;
import com.jcommerce.core.model.ShopConfig;

@Repository
@SuppressWarnings("unchecked")
public class ShopConfigDAOImpl extends DAOImpl implements ShopConfigDAO {
    public ShopConfigDAOImpl() {
        modelClass = ShopConfig.class;
    }

    public List<ShopConfig> getShopConfigList() {
        return getList();
    }

    public ShopConfig getShopConfig(Long id) {
        return (ShopConfig)getById(id);
    }

    public void saveShopConfig(ShopConfig obj) {
        save(obj);
    }

    public void removeShopConfig(Long id) {
        deleteById(id);
    }
}
