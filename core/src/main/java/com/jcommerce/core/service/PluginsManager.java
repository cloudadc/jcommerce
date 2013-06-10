/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Plugins;
import com.jcommerce.core.service.Criteria;
public interface PluginsManager extends Manager {
    public Plugins initialize(Plugins obj);

    public List<Plugins> getPluginsList(int firstRow, int maxRow);

    public int getPluginsCount(Criteria criteria);

    public List<Plugins> getPluginsList(Criteria criteria);

    public List<Plugins> getPluginsList(int firstRow, int maxRow, Criteria criteria);

    public List<Plugins> getPluginsList();

    public Plugins getPlugins(String id);

    public void savePlugins(Plugins obj);

    public void removePlugins(String id);
}
