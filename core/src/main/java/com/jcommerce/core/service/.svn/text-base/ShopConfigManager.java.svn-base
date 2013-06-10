/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;
import java.util.SortedMap;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.impl.ShopConfigMeta;
import com.jcommerce.core.wrapper.ShopConfigWrapper;

public interface ShopConfigManager extends Manager {
    public ShopConfig initialize(ShopConfig obj);

    public List<ShopConfig> getShopConfigList(int firstRow, int maxRow);

    public int getShopConfigCount(Criteria criteria);

    public List<ShopConfig> getShopConfigList(Criteria criteria);

    public List<ShopConfig> getShopConfigList(int firstRow, int maxRow, Criteria criteria);

    public List<ShopConfig> getShopConfigList();

    public ShopConfig getShopConfig(String id);

    public void saveShopConfig(ShopConfig obj);

    public void removeShopConfig(String id);
    
    public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap(String locale);
    public Boolean saveShopConfig(List<ShopConfig> tos);
    public ShopConfigWrapper getCachedShopConfig(String locale);
    
}
