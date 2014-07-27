package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.BrandWrapper;
import com.jcommerce.web.to.GoodsListWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibMain;

public class BrandAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [BrandAction]: "+s );
	}
	
	@Override
	protected String getSelfURL() {
		return URLConstants.ACTION_BRAND;
	}
	
	public void includeGoodsList() {
		HttpServletRequest request = getRequest();
        String brandId = request.getParameter("id");
        if(StringUtils.isEmpty(brandId)) {
        	brandId = request.getParameter("brand");
        }
        if(StringUtils.isEmpty(brandId)) {
        	// sth wrong, maybe goto home
        	throw new RuntimeException("brand id is null");
        }
        request.setAttribute("brandId", brandId);
        
		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		
		String sSize = (String)getCachedShopConfig().get("pageSize");
		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
		
		String cat = (String)request.getParameter("cat");
		long cate = (cat!=null && Integer.valueOf(cat)>0) ? Integer.valueOf(cat) : 0;
		request.setAttribute("category", cate);
		
		String sort = (String)request.getParameter("sort");  //'goods_id', 'shop_price', 'last_update'
		String order = (String)request.getParameter("order"); // ASC DESC
		String display = (String)request.getParameter("display");
		if(display == null){
			display = getCachedShopConfig().getString(CFG_KEY_SHOW_ORDER_TYPE);
		}
		if(sort == null){
			sort = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_METHOD);
		}
		if(order == null){
			order = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_TYPE);
		}

		// TODO 分类品牌
//		int recordCount = goodsCountByBrand(brandId, cate);
		
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.BRAND);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue(brandId);
        c1.addCondition(cond1);

        GoodsListWrapper goodsList = new GoodsListWrapper(c1); 
//		List<Goods> goods = brandGetGoods(brandId, cate, size, page, sort, order);
//		List<GoodsWrapper> gws = WrapperUtil.wrap(goods, GoodsWrapper.class);
		request.setAttribute("goodsList", goodsList);
		
		List<Category> relatedCate = brandRelatedCat();
		request.setAttribute("brandCatList", relatedCate); // 相关分类

		Pager pager = LibMain.assignPager(APP_BRAND, ""+cate, -1, size, sort, order, page, 
				"", brandId, 0, 0, display, "", "", null, getRequest(), getCachedShopConfig());
		
		goodsList.setPager(pager);
		
		request.setAttribute("priceMin", 0);
		request.setAttribute("priceMax", 0);
		request.setAttribute("filterAttr", "");
	}
	
	/**
	 * 获得指定的品牌下的商品总数
	 *
	 * @access  private
	 * @param   integer     $brand_id
	 * @param   integer     $cate
	 * @return  integer
	 */
//	private int goodsCountByBrand(String brandId, long cat) {
//        Criteria c1 = new Criteria();
//        Condition cond1 = new Condition();
//        cond1.setField(IGoods.BRAND);
//        cond1.setOperator(Condition.EQUALS);
//        cond1.setValue(brandId);
//        c1.addCondition(cond1);
//        
//        int count = SpringUtil.getGoodsManager().getGoodsCount(c1);
//		return count;
//	}
//
//	private List<Goods> brandGetGoods(String brandId, long cate, int size, int page, String sort, String order) {
//        Criteria c1 = new Criteria();
//        Condition cond1 = new Condition();
//        cond1.setField(IGoods.BRAND);
//        cond1.setOperator(Condition.EQUALS);
//        cond1.setValue(brandId);
//        c1.addCondition(cond1);
//        int firstRow = (page-1)*size;
//        int maxRow = firstRow + size;
//        List<Goods> list = SpringUtil.getGoodsManager().getGoodsList(firstRow, maxRow, c1);
//        debug("brandId: "+brandId+", size of goods: "+list.size());
//        return list;
//	}
	
	private List<Category> brandRelatedCat() {
		// TODO refer to brand.php line333
		return new ArrayList<Category>();
	}
	
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");        
	        HttpServletRequest request = getRequest();
	        includeCart();
	        includeCategoryTree(request);
	        includeHistory(request);
	        
	        includeFilterAttr();
	        includePriceGrade();
	        
	        String brandId = request.getParameter("id");
	        if(StringUtils.isEmpty(brandId)) {
	        	brandId = request.getParameter("brand");
	        }
	        if(StringUtils.isEmpty(brandId)) {
	        	List<Brand> brands = SpringUtil.getBrandManager().getBrandList();
	        	Criteria criteria = new Criteria();
	            Condition cond = new Condition();
	            cond.setField(IGoods.BRAND);
	            cond.setOperator(Condition.EQUALS);
	            List<BrandWrapper> brandInfoList = new ArrayList<BrandWrapper>();
	            for (Brand brand:brands) {
	                cond.setValue(brand.getId());
	                criteria.addCondition(cond);
	                Integer goodsNum = SpringUtil.getGoodsManager().getGoodsCount(criteria);
	                BrandWrapper bw = new BrandWrapper(brand);
	                bw.put("goodsNum", goodsNum);
	                bw.put("brandLogo", URLConstants.SERVLET_IMAGE+brand.getLogo());
	                brandInfoList.add(bw);
	                criteria.removeAll();
	            } 
	        	
	        	LibMain.assignUrHere(request, "" , getLangMap(request).getString("allBrand"));
	        	
	        	request.setAttribute("pageTitle", getLangMap(request).getString("allBrand"));
	        	request.setAttribute("brandList", brandInfoList);

	        	return "brandList";
	        }
	     // TODO maybe this only include goods belong to the brand?
	        includeRecommendBest(request);
	        
	        includeGoodsList();
	        debug("in [execute]: brandId="+brandId);
	        
	        Brand brand = SpringUtil.getBrandManager().getBrand(brandId);
	        BrandWrapper bw = new BrandWrapper(brand);
	        
	        bw.put("brandLogo", URLConstants.SERVLET_IMAGE+brand.getLogo());

	        request.setAttribute("brand", bw);    
	       
	        LibMain.assignUrHere(request, "" , (String)bw.get("brandName"));
			return SUCCESS;
        
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}




}
