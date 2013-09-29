package com.jcommerce.core.service.impl;

import java.util.List;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.service.Manager;

public abstract class ManagerImpl implements Manager {
 
	public abstract DAO getDAO();

	public List getList(String hsql) {
		return getDAO().getList(hsql);
	}

}
