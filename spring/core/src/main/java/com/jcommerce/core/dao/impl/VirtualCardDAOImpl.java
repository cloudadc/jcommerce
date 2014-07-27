/**
* @Author: KingZhao
*/

package com.jcommerce.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcommerce.core.dao.VirtualCardDAO;
import com.jcommerce.core.model.VirtualCard;

@Repository
@SuppressWarnings("unchecked")
public class VirtualCardDAOImpl extends DAOImpl implements VirtualCardDAO {
    public VirtualCardDAOImpl() {
        modelClass = VirtualCard.class;
    }

    public List<VirtualCard> getVirtualCardList() {
        return getList();
    }

    public VirtualCard getVirtualCard(Long id) {
        return (VirtualCard)getById(id);
    }

    public void saveVirtualCard(VirtualCard obj) {
        save(obj);
    }

    public void removeVirtualCard(Long id) {
        deleteById(id);
    }
}
