package com.jcommerce.core.service;

import com.jcommerce.core.model.KylinSoong;


public interface KylinSoongManager extends Manager {

	public void saveKylinSoong(KylinSoong obj);

    public void removeKylinSoong(String id);
}
