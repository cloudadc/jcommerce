/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import com.jcommerce.core.dao.VirtualCardDAO;
import com.jcommerce.core.model.VirtualCard;

public class VirtualCardDAOImpl extends DAOImpl implements VirtualCardDAO {
    public VirtualCardDAOImpl() {
        modelClass = VirtualCard.class;
    }

    public List<VirtualCard> getVirtualCardList() {
        return getList();
    }

    public VirtualCard getVirtualCard(String id) {
        return (VirtualCard)getById(id);
    }

    public void saveVirtualCard(VirtualCard obj) {
        save(obj);
    }

    public void removeVirtualCard(String id) {
        deleteById(id);
    }
}
