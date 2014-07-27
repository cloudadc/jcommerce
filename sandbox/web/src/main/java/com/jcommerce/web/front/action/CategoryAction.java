package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Order;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.GoodsListWrapper;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibMain;

public class CategoryAction extends BaseAction {
	
	public void debug(String s) {
		System.out.println(" in [CategoryAction]: "+s );
	}
	@Override
	protected String getSelfURL() {
		return URLConstants.ACTION_CATEGORY;
	}
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");
			HttpServletRequest request = getRequest();
			
			includeCart();
			includeCategoryTree(request);

			includeFilterAttr();
			includePriceGrade();
			includeHistory(request);
			
			//必须在includeRecommendBest之前，否则includeRecommendBest无法获取信息
			includeGoodsList();
			
			// TODO maybe this only include goods belong to the category?
			includeRecommendBest(request);

			request.setAttribute("showMarketprice", getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_SHOW_MARKETPRICE));
//			String categoryId = request.getParameter("id");
//			debug("in [execute]: categoryId=" + categoryId);
//
//			IDefaultManager manager = getDefaultManager();
//			Category category = (Category) manager.get(ModelNames.CATEGORY, categoryId);
//			CategoryWrapper cw = new CategoryWrapper(category);
//			request.setAttribute("category", cw);

			return SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void includeGoodsList() {
		HttpServletRequest request = getRequest();
        String _id = request.getParameter("id");
        if(StringUtils.isEmpty(_id)) {
        	_id = request.getParameter("category");
        }
        if(StringUtils.isEmpty(_id)) {
        	// sth wrong, maybe goto home
        	throw new RuntimeException("cat id is null");
        }
        
        String catId = _id;
        debug("in [includeGoodsList]: catId="+catId);

		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		
		String sSize = (String)getCachedShopConfig().get("pageSize");
	
		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
		
		String brandId = (String)request.getParameter("brand");
		if(brandId == null) {
			brandId = "";
		}
		
		String sort = (String)request.getParameter("sort");  //'goods_id', 'shop_price', 'last_update'
		String order = (String)request.getParameter("order"); // ASC DESC
		String display = (String)request.getParameter("display");
		if(display == null){
			display = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE);
		}
		if(sort == null){
			sort = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD);
		}
		if(order == null){
			order = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE);
		}

		Category cat = getCatInfo(catId);
		if(cat==null) {
			throw new RuntimeException("cannot find category");
		}
		catId = cat.getId();
		
		CategoryWrapper cw = new CategoryWrapper(cat);
		String brandName = "";
		if(StringUtils.isEmpty(brandId)) {
			// TODO brandName 
		}

		if(cat.getGrade()==0 && cat.getParent()!=null) {
			cw.put(ICategory.GRADE, getParentGrade(catId));
		}
		
		if(((Number)cw.get(ICategory.GRADE)).intValue() > 1) {
			// TODO pricegrade
		}
		
        List<String> children = LibCommon.catList(catId, -1, false, SpringUtil.getCategoryManager());

		int priceMin = 0;
		int priceMax = 0;
		request.setAttribute("categories", LibGoods.getCategoriesTree(catId));
        request.setAttribute("category", catId);
		request.setAttribute("brandId", brandId);
		request.setAttribute("priceMin", priceMin);
		request.setAttribute("priceMax", priceMax);
		request.setAttribute("filterAttr", "");
		
		int count = getCategoryGoodsCount(children, brandId, priceMin, priceMax);
//		int maxPage = count>0? (count/size)+1 : 1;
//		if(page > maxPage) {
//			page = maxPage;
//		}
//		
		Criteria criteria = getGoodsCriteria(children, brandId, priceMin, priceMax, sort, order);
        GoodsListWrapper goodsListWrapper = new GoodsListWrapper(criteria);
		request.setAttribute("goodsList", goodsListWrapper);
		
		Pager pager = LibMain.assignPager("category", catId, count, size, sort, order, page,
				"", brandId, 0, 0, display, "", "", null, getRequest(), getCachedShopConfig());
		
		goodsListWrapper.setPager(pager);
		
		LibMain.assignUrHere(request, catId, "");
	}
	
	private Category getCatInfo(String catId) {
		return SpringUtil.getCategoryManager().getCategory(catId);
	}
		
	private int getParentGrade(String catId) {
		Map<String, String> parentArr = new HashMap<String, String>();
		Map<String, Integer> gradeArr = new HashMap<String, Integer>();
		List<Category> allCats = SpringUtil.getCategoryManager().getCategoryList();
		for(Category cat: allCats) {
			parentArr.put(cat.getId(), cat.getParent() == null ? null : cat.getParent().getId());
			gradeArr.put(cat.getId(), cat.getGrade());
		}
		
		String cid = catId;
		while(parentArr.get(cid)!=null && gradeArr.get(cid)==0) {
			cid = parentArr.get(cid);
		}
		
		return gradeArr.get(cid);
		
	}
	
	private int getCategoryGoodsCount(List<String> children, String brandId, int priceMin, int priceMax) {
		// TODO getCategoryGoodsCount
        List<Goods> goods = new ArrayList<Goods>();
        
        Criteria criteria = new Criteria();
        
        Condition cond = new Condition();
        cond.setField(IGoods.MAINCATEGORY);
        cond.setOperator(Condition.EQUALS);
        criteria.addCondition(cond);
        
        if(!brandId.equals("")){
        	Condition cond2 = new Condition();
        	cond2.setField(IGoods.BRAND);
        	cond2.setOperator(Condition.EQUALS);
        	cond2.setValue(brandId);
        	criteria.addCondition(cond2);
        }
        
        if(priceMin > 0){
        	Condition cond3 = new Condition();
        	cond3.setField(IGoods.SHOPPRICE);
        	cond3.setOperator(Condition.GREATERTHAN);
        	cond3.setValue(priceMin+"");
        	criteria.addCondition(cond3);
        }
        
        if(priceMax > 0){
        	Condition cond4 = new Condition();
        	cond4.setField(IGoods.SHOPPRICE);
        	cond4.setOperator(Condition.LESSTHAN);
        	cond4.setValue(priceMax+"");
        	criteria.addCondition(cond4);
        }     
        
        for (String cid : children) {
        	cond.setValue(cid);  
        	goods.addAll(SpringUtil.getGoodsManager().getGoodsList(criteria));
		}
        
        LibCommon.removeDuplicateWithOrder(goods);
		
		return goods.size();
	}
	
	private Criteria getGoodsCriteria(List<String> children, String brandId, int priceMin, int priceMax,
			String sort, String order) {
		
		// TODO categoryGetGoods
//        int firstRow = (page-1)*size;
//        int maxRow = firstRow + size;
        List<Goods> goods = new ArrayList<Goods>();
        
        Criteria criteria = new Criteria();
        
        Condition cond = new Condition();
//        cond.setField(IGoods.MAINCATEGORY);
//        cond.setOperator(Condition.EQUALS);
//        criteria.addCondition(cond);

        if(!brandId.equals("")){
        	Condition cond2 = new Condition();
        	cond2.setField(IGoods.BRAND);
        	cond2.setOperator(Condition.EQUALS);
        	cond2.setValue(brandId);
        	criteria.addCondition(cond2);
        }
        
        if(priceMin > 0){
        	Condition cond3 = new Condition();
        	cond3.setField(IGoods.SHOPPRICE);
        	cond3.setOperator(Condition.GREATERTHAN);
        	cond3.setValue(priceMin+"");
        	criteria.addCondition(cond3);
        }
        
        if(priceMax > 0){
        	Condition cond4 = new Condition();
        	cond4.setField(IGoods.SHOPPRICE);
        	cond4.setOperator(Condition.LESSTHAN);
        	cond4.setValue(priceMax+"");
        	criteria.addCondition(cond4);
        }  

        Order order1 = new Order();
        order1.setField(IGoods.ID);
        if(order.equals("ASC")){
        	order1.setAscend(Order.ASCEND);
        }
        else{
        	order1.setAscend(Order.DESCEND);
        }
        //criteria.addOrder(order1);
                
        return criteria;
//        for (String cid : children) {
//        	cond.setValue(cid);
//        	goods.addAll(SpringUtil.getGoodsManager().getGoodsList(criteria));
//		}
//        
//        LibCommon.removeDuplicateWithOrder(goods);
//        if(goods.size()<maxRow){
//        	maxRow = goods.size();
//        }
//		return goods.subList(firstRow, maxRow);
//		
	}
//	@Override
//	public void includeRecommendBest(HttpServletRequest request) {
//    	setCatRecSign(request); 	
//        request.setAttribute("bestGoods", getBestSoldGoods(getCatInfo((String)request.getAttribute("category")).getId(),request));
//    }
//	
//	private List getBestSoldGoods(String catId ,HttpServletRequest request) {
//		List<GoodsWrapper> goodsList = (List<GoodsWrapper>)request.getAttribute("goodsList");
//		List<GoodsWrapper> list = new ArrayList<GoodsWrapper>();
//		for (GoodsWrapper goodsWrapper : goodsList) {
//			if((Boolean)goodsWrapper.get("isBest")){
//				list.add(goodsWrapper);
//			}
//		}
//		if(list.size()>0){
//			return list;
//		}
//		else{
//			return null;
//		}
//		         
//    }
//	
}
