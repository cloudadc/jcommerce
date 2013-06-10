package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.GoodsGalleryWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.LibGoods.GoodsPropertiesResult;

public class GoodsAction extends BaseAction {
	
	private static final Logger log = Logger.getLogger(GoodsAction.class.getName());
	
	public static final String KEY_GOODS_ID = "goodsId";
	public static final String KEY_PROMOTE_END_TIME = "promoteEndTime";
	public static final String KEY_NOW_TIME = "nowTime";
	private InputStream jsonRes;
	
	public void debug(String s) {
		log.info(" in [GoodsAction]: "+s );
	}
	
	@Override
	public String onExecute() throws Exception {

        
        HttpServletRequest request = getRequest();        

        
        includeCart();
        includeCategoryTree(request);
        
        includeGoodsRelated(request);
        includeGoodsFittings(request);
        includeGoodsArticle(request);
        includeGoodsAttrLinked(request);
        includeHistory(request);
        includeGoodsGallery(request);
        includeGoodsTags(request);
        includeBoughtGoods(request);
        includeComments(request);
        // goods logic
        
        String goodsId = request.getParameter("id");
        debug("in [execute]: goodsId="+goodsId);
        
        String act = request.getParameter("act");
        
	    Goods goods = SpringUtil.getGoodsManager().getGoods(goodsId);
	        // make it always be pkId instead of LongId string
	    goodsId = goods.getId();
	        
	        
	    GoodsWrapper gw = new GoodsWrapper(goods);
	        
	    request.setAttribute("goods", gw);
	        
	    String brandId = goods.getBrand().getId();
	    Brand brand = SpringUtil.getBrandManager().getBrand(brandId);
	    gw.put(GoodsWrapper.GOODS_BRAND, brand.getName());
	    request.setAttribute(GoodsWrapper.GOODS_BRAND, brand.getName());
	    
	    //获得用户评价等级
	    String id = gw.getGoods().getId();
    	Condition condition = new Condition(IComment.IDVALUE,Condition.EQUALS,id);
        Criteria criteria = new Criteria();
        criteria.addCondition(condition);
        List<Comment> comments = SpringUtil.getCommentManager().getCommentList(criteria);
        Long sum = 0L;
        int number = comments.size();
        for(Iterator iterator = comments.iterator();iterator.hasNext();) {
        	Comment comment = (Comment) iterator.next();
        	int rank = comment.getCommentRank();
        	sum += rank;
        }
        String rank = "0";
        if(number > 0)
        	rank = ((int)Math.ceil((double)sum / number)) + "";  
        gw.put("commentRank", rank);
	    
	    
	    request.setAttribute("rankPrices", new HashMap());
	        
	    /*修改,获得商品相册*/
	    Condition galleryCondition = new Condition("goods",Condition.EQUALS,goodsId);
	    criteria.removeAll();
	    criteria.addCondition(galleryCondition);
	    List<Gallery> goodsGallery = SpringUtil.getGalleryManager().getGalleryList(criteria);
	    List<GoodsGalleryWrapper> goodsGalleryWrapper = WrapperUtil.wrap(goodsGallery, GoodsGalleryWrapper.class);
	    request.setAttribute("pictures", goodsGalleryWrapper);
	    /*修改完*/
	    
	    
	    LibMain.assignUrHere(getRequest(), goods.getMainCategory() == null ? null : goods.getMainCategory().getId(), goods.getName());
	    
	    
	    GoodsPropertiesResult result = LibGoods.getGoodsProperties(goodsId); // 获得商品的规格和属性
	    request.setAttribute("properties", result.getPro());
	    request.setAttribute("specification", result.getSpe());
	    request.setAttribute("spec", result.getSpe());
	    request.setAttribute("key", 0);
	    includeComment(IComment.TYPE_GOODS, goodsId);
	       
	    request.setAttribute(KEY_GOODS_ID, gw.getId());
	    request.setAttribute(KEY_PROMOTE_END_TIME, goods.getPromoteEnd() == null ? 0 : goods.getPromoteEnd().getTime() / 1000);
	    request.setAttribute(KEY_NOW_TIME, new Date().getTime());
	        
	    // TODO 
	    Map<String, String> affiliate = new HashMap<String, String>();
	    affiliate.put("on", "yes");
	    request.setAttribute("affiliate", affiliate);
	      
	    request.setAttribute("pointsName", getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_INTEGRAL_NAME));
	    
	    /*修改,修改商品点击次数*/
	    if(act == null) {
		    goods.setClickCount(goods.getClickCount() + 1);
		    SpringUtil.getGoodsManager().saveGoods(goods);
	    }
	    /*修改完*/
	        
	    /*修改，改变属性、数量时重新计算商品价格*/
	    else if(act.equals("price")) {
	    	return getTotalPrice(request, goods);
	    }
	    /*修改完*/
	    
	    /* 记录浏览历史 */
	    Map<String,GoodsWrapper> viewHistory = (HashMap<String,GoodsWrapper>)getSession().getAttribute("viewHistory");
	    if( viewHistory == null ){
	    	viewHistory = new HashMap<String,GoodsWrapper>();
	    }
	    
	    if(viewHistory.size() < 5 ){
	    	viewHistory.put(gw.getPkId(),gw); 
		    getSession().setAttribute("viewHistory" , viewHistory);
	    }
	    else{
	    	if(viewHistory.put(gw.getPkId(),gw) == null){
	    		viewHistory.remove(viewHistory.keySet().toArray()[0]);
	    	}
	    }
	    request.setAttribute("rand", "");

        return SUCCESS;
        

	}
	
	//获得商品总价格
	public String getTotalPrice(HttpServletRequest request, Goods goods ) {
		
		String attr = request.getParameter("attr");
    	int number = Integer.parseInt(request.getParameter("number"));
    	
    	//判断是否促销
		Long promoteEndTime = goods.getPromoteEnd().getTime();
		Long promoteStartTime = goods.getPromoteStart().getTime();
		Long nowTime = new Date().getTime();
    	double shopPrice = 0;
		if(nowTime > promoteEndTime || nowTime < promoteStartTime) {
			shopPrice = goods.getShopPrice() * number;
		}
		else {
			shopPrice = goods.getPromotePrice() * number;
		}		
    	
    	double attrPrice = 0;
    	if(!attr.equals("")) {
    		String[] ids = attr.split(",");
    		for(int i = 0;i < ids.length;i++) {
	    		GoodsAttribute goodsAttr = SpringUtil.getGoodsAttributeManager().getGoodsAttribute(ids[i]);
			    attrPrice += goodsAttr.getPrice() == null ? 0 : Double.parseDouble(goodsAttr.getPrice());
    		}
    	}
	    double total = shopPrice + attrPrice;
	    debug("total: "+total);
	   
	    JSONObject res = new JSONObject();
	    try {
			res.put("qty", number);
			res.put("result", total);
			res.put("err_msg", "");
	
			String out = res.toString();
			debug("in [caculate]: out="+out);
		
			setJsonRes(new ByteArrayInputStream(out.getBytes(IWebConstants.ENC)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "price";
	}
	
	public void includeGoodsRelated(HttpServletRequest request) {
	}
	public void includeGoodsFittings(HttpServletRequest request) {
	}
	public void includeGoodsArticle(HttpServletRequest request) {
	}
	public void includeGoodsAttrLinked(HttpServletRequest request) {
		request.setAttribute("attributeLinked", new String[0]);
	}

	public void includeGoodsGallery(HttpServletRequest request) {
	}
	public void includeGoodsTags(HttpServletRequest request) {
		Lang lang = getLangMap(request);
		lang.put("goodsTag", "标签");
		
		request.setAttribute("tags", new String[0]);
	}
	public void includeBoughtGoods(HttpServletRequest request) {
	}

	public void setJsonRes(InputStream jsonRes) {
		this.jsonRes = jsonRes;
	}

	public InputStream getJsonRes() {
		return jsonRes;
	}



}
