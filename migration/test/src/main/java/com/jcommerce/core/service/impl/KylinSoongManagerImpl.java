package com.jcommerce.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.dao.KylinSoongDAO;
import com.jcommerce.core.model.KylinSoong;
import com.jcommerce.core.service.KylinSoongManager;

@Service("kylinSoongService")
public class KylinSoongManagerImpl extends ManagerImpl implements KylinSoongManager {
	
	@Autowired
	private KylinSoongDAO kylinSoongDAO;

	@Transactional
	public void saveKylinSoong(KylinSoong obj) {
		kylinSoongDAO.saveKylinSoong(obj);
	}

	@Transactional
	public void removeKylinSoong(String id) {
		kylinSoongDAO.removeKylinSoong(id);
	}

	public DAO getDAO() {
		return kylinSoongDAO;
	}

}
