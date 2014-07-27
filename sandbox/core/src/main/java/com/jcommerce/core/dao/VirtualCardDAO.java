/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao;

import java.util.List;

import com.jcommerce.core.model.VirtualCard;

public interface VirtualCardDAO extends DAO {
    public List<VirtualCard> getVirtualCardList();

    public VirtualCard getVirtualCard(Long id);

    public void saveVirtualCard(VirtualCard obj);

    public void removeVirtualCard(Long id);
}
