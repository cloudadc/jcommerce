package com.jcommerce.core.service.impl;

import java.util.List;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.service.Manager;

public class ManagerImpl implements Manager {
	
	private DAO dao;
    
    protected void setDao(DAO dao) {
        this.dao = dao;
    }

	public List getList(String hsql) {
		return dao.getList(hsql);
	}

}
