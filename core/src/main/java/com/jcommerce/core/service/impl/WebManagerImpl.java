package com.jcommerce.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.service.AttributeManager;
import com.jcommerce.core.service.CartManager;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsAttributeManager;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.core.util.SpringUtil;

@Service("webManager")
public class WebManagerImpl extends ManagerImpl implements IWebManager {
    AttributeManager attributeManager; 
    GoodsManager goodsManager; 
    CartManager cartManager; 
    GoodsAttributeManager goodsAttributeManager; 
    
	public AttributeManager getAttributeManager() {
        return attributeManager;
    }

    public void setAttributeManager(AttributeManager attributeManager) {
        this.attributeManager = attributeManager;
    }

    public GoodsManager getGoodsManager() {
        return goodsManager;
    }

    public void setGoodsManager(GoodsManager goodsManager) {
        this.goodsManager = goodsManager;
    }

    public CartManager getCartManager() {
        return cartManager;
    }

    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }

    public GoodsAttributeManager getGoodsAttributeManager() {
        return goodsAttributeManager;
    }

    public void setGoodsAttributeManager(GoodsAttributeManager goodsAttributeManager) {
        this.goodsAttributeManager = goodsAttributeManager;
    }

    public boolean addToCart(String goodsId, long num, List spec, String sessionId, String userId, String parentId) {
		
    	try {
    		boolean res = true;
    		Goods goods = goodsManager.getGoods(goodsId);
    		
//    		if(StringUtils.isNotEmpty(cartId))

    		String goodsSpecId = "";
    		for(Iterator iterator = spec.iterator();iterator.hasNext();) {
    			String id = (String) iterator.next();
    			if(!goodsSpecId.equals(""))
    				goodsSpecId += ",";
    			goodsSpecId += id;
    		}
    		
    		//查找购物车中是否已有该商品，且规格不一样，如果有，将商品数加一
    		Criteria criteria = new Criteria();
    		criteria.addCondition(new Condition(ICart.SESSION_ID,Condition.EQUALS,sessionId));
    		criteria.addCondition(new Condition(ICart.GOODS_ID,Condition.EQUALS,goods.getId()));
    		// FIXME
//    		criteria.addCondition(new Condition(ICart.GOODS_ATTR_ID,Condition.EQUALS,goodsSpecId));
    		List<Cart> carts = cartManager.getCartList(criteria); 
    		if(carts.size() > 0) {
    			Cart cart = carts.get(0);
    			cart.setGoodsNumber(cart.getGoodsNumber() + (int)num);
    			cartManager.saveCart(cart);
    		}
    		
    		//不存在该商品
    		else {
	    		Cart cart = new Cart();
	    		cart.setGoods(goods);
	    		cart.setSession(SpringUtil.getSessionManager().getSession(sessionId));
	    		// FIXME
	    		if (userId != null) {
	    		    cart.setUser(SpringUtil.getUserManager().getUser(userId));
	    		}
	    		cart.setGoodsSN(goods.getSN());
	    		cart.setType(Constants.CART_GENERAL_GOODS);
	    		cart.setGoodsNumber((int)num);
	    		cart.setGoodsName(goods.getName());
	    		cart.setGoodsThumb(goods.getThumb());
//	    		cart.setGoodsWeight(goods.getWeight());
	    		
	    		//获得商品规格及价格
	    		String goodsSpec = "";
	    		double goodsSpecPrice = 0.0;
	    		for(Iterator iterator = spec.iterator();iterator.hasNext();) {
	    			long goodsAttrId = Long.parseLong((String) iterator.next());
	    			
	    			GoodsAttribute goodsAttr = goodsAttributeManager.getGoodsAttribute(""+goodsAttrId);
	    			String attrId = goodsAttr.getId();
	    			Attribute attribute = attributeManager.getAttribute(""+attrId);
	    			
	    			String attrName = attribute.getName();
	    			String attrValue = goodsAttr.getValue();
	    			String attrPrice = goodsAttr.getPrice();
	    			
	    			if(attrPrice == null || Double.parseDouble(attrPrice) == 0) {
	    				goodsSpec += attrName + ":" + attrValue + "<br>";
	    			}
	    			else {
	    				goodsSpec += attrName + ":" + attrValue + "[" + attrPrice + "]" + "<br>";
	    			}
	    			goodsSpecPrice += Double.parseDouble(attrPrice);
	    		}
	    		double shopPrice = goods.getShopPrice();
	    		//判断是否促销
	    		Timestamp promoteEndTime = goods.getPromoteEnd();
	    		Timestamp promoteStartTime = goods.getPromoteStart();
	    		Long nowTime = new Date().getTime();
	    		if(promoteEndTime != null && promoteStartTime != null){
		    		if(nowTime < promoteEndTime.getTime() && nowTime > promoteStartTime.getTime()) {
		    			shopPrice = goods.getPromotePrice();
		    		}	
	    		}
	    		cart.setGoodsPrice(shopPrice + goodsSpecPrice);
	    		cart.setMarketPrice(goods.getMarketPrice() + goodsSpecPrice);	    		
	    		cart.setGoodsAttribute(goodsSpecId);
//	    		cart.setGoodsAttr(goodsSpec);
	    		
	    		cartManager.saveCart(cart);
    		}
    		
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

    interface ICart {
       public static final String REC_ID = "id"; 
       public static final String USER_ID = "user"; 
       public static final String SESSION_ID = "session"; 
       public static final String GOODS_ID = "goods"; 
       public static final String GOODS_SN = "goodsSN"; 
       public static final String GOODS_NAME = "goodsName"; 
       public static final String MARKET_PRICE = "marketPrice"; 
       public static final String GOODS_PRICE = "goodsPrice"; 
       public static final String GOODS_NUMBER = "goodsNumber"; 
       public static final String GOODS_ATTR = "goodsAttribute"; 
       public static final String IS_REAL = "realGoods"; 
       public static final String EXTENSION_CODE = "extensionCode"; 
       public static final String PARENT_ID = "parent"; 
       public static final String REC_TYPE = "type"; 
       public static final String IS_GIFT = "gift"; 
       public static final String CAN_HANDSEL = "handSelectable"; 
//       public static final String GOODS_ATTR_ID = "goodsAttrId"; 
     }
}
