package com.jcommerce.core.service.impl;

import com.jcommerce.core.dao.KylinSoongDAO;
import com.jcommerce.core.model.KylinSoong;
import com.jcommerce.core.service.KylinSoongManager;

public class KylinSoongManagerImpl extends ManagerImpl implements KylinSoongManager {
	
	private KylinSoongDAO kylinSoongDAO;

	public void setKylinSoongDAO(KylinSoongDAO dao) {
		this.kylinSoongDAO = dao;
		super.setDao(dao);
	}

	public void saveKylinSoong(KylinSoong obj) {
		kylinSoongDAO.saveKylinSoong(obj);
	}

	public void removeKylinSoong(String id) {
		kylinSoongDAO.removeKylinSoong(id);
	}

}
