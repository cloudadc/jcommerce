package com.jcommerce.core.service;

import java.util.List;

public interface IWebManager {
	
    public boolean addToCart(Long goodsId, long num, List spec, Long sessionId, Long userId, Long parentGoodsId);
    
    
}
