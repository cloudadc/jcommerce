package com.jcommerce.core.dao;

import com.jcommerce.core.model.KylinSoong;


public interface KylinSoongDAO extends DAO{
	
	public void saveKylinSoong(KylinSoong obj);

    public void removeKylinSoong(String id);

}
