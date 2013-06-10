/**
* @Author: KingZhao
*/

package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.VirtualCard;
import com.jcommerce.core.service.Criteria;
public interface VirtualCardManager extends Manager {
    public VirtualCard initialize(VirtualCard obj);

    public List<VirtualCard> getVirtualCardList(int firstRow, int maxRow);

    public int getVirtualCardCount(Criteria criteria);

    public List<VirtualCard> getVirtualCardList(Criteria criteria);

    public List<VirtualCard> getVirtualCardList(int firstRow, int maxRow, Criteria criteria);

    public List<VirtualCard> getVirtualCardList();

    public VirtualCard getVirtualCard(String id);

    public void saveVirtualCard(VirtualCard obj);

    public void removeVirtualCard(String id);
}
