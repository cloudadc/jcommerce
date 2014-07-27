package com.jcommerce.web.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.BrandManager;
import com.jcommerce.core.service.CategoryManager;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.RegionManager;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.WrapperUtil;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class LibCommon {

	
	public static String buildUri(String app, Map<String, Object> uriArgs, String append) {
		return buildUri(app, uriArgs, append, 0, 0);
	}
	/**
	 * 重写 URL 地址
	 *
	 * @access  public
	 * @param   string  $app    执行程序
	 * @param   array   $params 参数数组
	 * @param   string  $append 附加字串
	 * @param   integer $page   页数
	 * @return  void
	 */
    public static String buildUri(String app, Map<String, Object> uriArgs, String append, int page, int size){
    	StringBuffer buf = new StringBuffer();
    	if(IWebConstants.APP_BRAND.equals(app)) {
    		buf.append("brand.action?id=").append(uriArgs.get("bid"));
    		if(uriArgs.get("cid")!=null) {
    			buf.append("&amp;cat=").append(uriArgs.get("cid"));
    		}
   			buf.append("&amp;page=").append(page);

   			if(uriArgs.get("sort")!=null) {
    			buf.append("&amp;sort=").append(uriArgs.get("sort"));
    		}
    		if(uriArgs.get("order")!=null) {
    			buf.append("&amp;order=").append(uriArgs.get("order"));
    		}    		
    	}
    	else if(IWebConstants.APP_CATEGORY.equals(app)) {
    		buf.append("category.action?id=").append(uriArgs.get("cid"));
    		if(uriArgs.get("bid")!=null) {
    			buf.append("&amp;brand=").append(uriArgs.get("bid"));
    		}
   			buf.append("&amp;page=").append(page);

   			if(uriArgs.get("sort")!=null) {
    			buf.append("&amp;sort=").append(uriArgs.get("sort"));
    		}
    		if(uriArgs.get("order")!=null) {
    			buf.append("&amp;order=").append(uriArgs.get("order"));
    		}    		
    		
    	}
    	else if(IWebConstants.APP_GOODS.equals(app)) {
    		buf.append("goods.action?id=").append(uriArgs.get("gid"));
    	}
    	else if(IWebConstants.APP_ARTICLE.equals(app)) {
    		buf.append("article.action?id=").append(uriArgs.get("aid"));
    	}
    	else if(IWebConstants.APP_ARTICLE_CAT.equals(app)) {
    		buf.append("articleCat.action?id=").append(uriArgs.get("acid"));
    	}
    	return buf.toString();
    }
    
    /**
     * 取得品牌列表
     * @return array 品牌列表 id => name
     */
    public static Map<String,String> getBrandList( BrandManager manager){
    	List<Brand> brandList = manager.getBrandList();
    	Map<String,String> brandMap = new HashMap<String,String>();
    	for (Brand brand : brandList) {
			brandMap.put(brand.getId(), brand.getName());
		}
    	return brandMap;
    }
	/**
	 * 获得指定分类下的子分类的数组
	 * 
	 * @access public
	 * @param int $cat_id 分类的ID
	 * @param int $selected 当前选中分类的ID
	 * @param boolean $re_type 返回的类型: 值为真时返回下拉列表,否则返回数组
	 * @param int $level 限定返回的级数。为0时返回所有级数
	 * @param int $is_show_all 如果为true显示所有分类，如果为false隐藏不可见分类。
	 * @return mix
	 */
	public static List<String> catList(String catId, int level,
			boolean isShowAll, CategoryManager manager) {

		List<String> res = new ArrayList<String>();

		List<Category> allCats = (List<Category>) manager.getCategoryList();
		if (catId == null) {
			for (Category cat : allCats) {
				res.add(cat.getId());
			}
			return res;
		}

		// child->parent
		Map<String, String> cpMap = new HashMap<String, String>();
		// parent->children
		Map<String, List<CategoryWrapper>> pcMap = new HashMap<String, List<CategoryWrapper>>();
		// 1st round loop
		for (Category cat : allCats) {
		    String pid = cat.getParent() != null ? cat.getParent().getId() : null;
	        cpMap.put(cat.getId(), pid);

	        List<CategoryWrapper> children = pcMap.get(pid);
			if (children == null) {
				children = new ArrayList<CategoryWrapper>();
				pcMap.put(pid, children);
			}
			children.add(new CategoryWrapper(cat));
		}

		// 2st round loop
		for (String cId : cpMap.keySet()) {
			int foundLevel = findParent(-1, cId, catId, cpMap);
			if (foundLevel < 0) {
				// didn't find catId as parent

				// } else if(foundLevel > level){
				// level deeper than required

			} else {
				res.add(cId);
			}
		}

		return res;
	}
	
	public static String selectCatList(String catId, String selected , int level,
			boolean isShowAll, CategoryManager manager) {

		StringBuffer res = new StringBuffer();

		List<Category> allCats = (List<Category>) manager.getCategoryList();
		if(allCats.size() == 0 ){
			return res.toString();
		}
//		Map<String,String> idNameMap = new HashMap<String,String>();
		
		// parent->children
		Map<String, List<CategoryWrapper>> pcMap = new HashMap<String, List<CategoryWrapper>>();
		// 1st round loop
		for (Category cat : allCats) {
//			cpMap.put(cat.getPkId(), cat.getParentId());
		    String pid = cat.getParent() != null ? cat.getParent().getId() : null;
			List<CategoryWrapper> children = pcMap.get(pid);
			if (children == null) {
				children = new ArrayList<CategoryWrapper>();
				pcMap.put(pid, children);
			}
			children.add(new CategoryWrapper(cat));
		}
		
		List<CategoryWrapper> level1 = pcMap.get(null);
		if(level1==null) {
			// to overcome NPE in case there is no any category at all
			return res.toString();
		}
		for (CategoryWrapper cw : level1) {
			res.append("<option value='"+cw.getPkId()+"' ");
			res.append(cw.getPkId().equals(selected) ? "selected='true'" : "");
			res.append(" >"+repeat("&nbsp;", 0) + cw.getString("catName") + "</option>");
			List<CategoryWrapper> level2 = pcMap.get(cw.getPkId());
			if (level2 != null) {
				for (CategoryWrapper categoryWrapper : level2) {
					res.append("<option value='"+categoryWrapper.getPkId()+"' ");
					res.append(categoryWrapper.getPkId().equals(selected) ? "selected='true'" : "");
					res.append(" >"+repeat("&nbsp;", 4) + categoryWrapper.getString("catName") + "</option>");
				}
			}
		}
		
		
		
		
		
//		for (Category cat : allCats) {
//			idNameMap.put(cat.getPkId(), cat.getCatName());
//		}		

//		// child->parent
//		Map<String, String> cpMap = new HashMap<String, String>();
//		// parent->children
//		Map<String, List<CategoryWrapper>> pcMap = new HashMap<String, List<CategoryWrapper>>();
//		// 1st round loop
//		for (Category cat : allCats) {
//			cpMap.put(cat.getPkId(), cat.getParentId());
//
//			List<CategoryWrapper> children = pcMap.get(cat.getParentId());
//			if (children == null) {
//				children = new ArrayList<CategoryWrapper>();
//				pcMap.put(cat.getParentId(), children);
//			}
//			children.add(new CategoryWrapper(cat));
//		}
		
//		if(catId != null ){
//			// 2st round loop
//			for (String cId : cpMap.keySet()) {
//				int foundLevel = findParent(-1, cId, catId, cpMap);
//				if (foundLevel < 0) {
//					// didn't find catId as parent
//	
//					// } else if(foundLevel > level){
//					// level deeper than required
//	
//				} else {
//					res.append("<option value='"+cId+"'>");
//					res.append(StringUtil.repeat("&nbsp", foundLevel*4) + idNameMap.get(cId) + "</option>");
//				}
//			}
//		}
//		else{
//			// 2st round loop
//			for (CategoryWrapper cw : pcMap.get(null)) {
//				for (String cId : cpMap.keySet()) {
//					int foundLevel = findParent(-1, cId, cw.getPkId(), cpMap);
//					if (foundLevel < 0) {
//						// didn't find catId as parent
//		
//						// } else if(foundLevel > level){
//						// level deeper than required
//		
//					} else {
//						res.append("<option value='"+cId+"'>");
//						res.append(StringUtil.repeat("&nbsp", foundLevel*4) + idNameMap.get(cId) + "</option>");
//					}
//				}
//
//			}
//		}

		return res.toString();
	}

	public static int findParent(int searchLevel, String cId, String catId,
			Map<String, String> cpMap) {
		int foundLevel = -1;
		if (cId.equals(catId)) {
			// found
			foundLevel = searchLevel + 1;
			return foundLevel;
		}
		String pid = cpMap.get(cId);
		if (pid == null) {
			// didn't find
			return foundLevel;
		} else {
			return findParent(searchLevel + 1, pid, catId, cpMap);
		}

	}
	
	public static List<RegionWrapper> getRegion(int regionType, String parentId, RegionManager manager) {
		Criteria c = new Criteria();
		c.addCondition(new Condition(IRegion.TYPE, Condition.EQUALS, ""+regionType));
		if(parentId != null){
			c.addCondition(new Condition(IRegion.PARENT, Condition.EQUALS, parentId));
		}
		List<Region> list = (List<Region>)manager.getRegionList(c);
		return WrapperUtil.wrap(list, RegionWrapper.class);
		
		// TODO region list
//		if(regionType == IRegion.TYPE_COUNTRY) {
//			return getCountryList();
//		}
//		else if(regionType == IRegion.TYPE_PROVINCE) {
//			return getProvinceList();
//		}
//		else if(regionType == IRegion.TYPE_CITY) {
//			return getCityList();
//		}
//		else {
//			return getCountryList();
//		}
		
	}
	
//    private static List<RegionWrapper> getCountryList() {
//    	Region r = new Region();
//    	r.setId("1");
//    	r.setName("中国");
//    	r.setType(new Integer(Region.TYPE_COUNTRY));
//    	RegionWrapper rw = new RegionWrapper(r);
//    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
//    	res.add(rw);
//    	return res;
//    }
//    private static List<RegionWrapper> getProvinceList() {
//    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
//    	Region r = new Region();
//    	r.setParentId("1");
//    	r.setId("2");
//    	r.setName("北京");
//    	r.setType(new Integer(IRegion.TYPE_PROVINCE));
//    	RegionWrapper rw = new RegionWrapper(r);
//    	res.add(rw);
//    	
//    	r = new Region();
//    	r.setParentId("1");
//    	r.setId("3");
//    	r.setName("广东");
//    	r.setType(new Integer(IRegion.TYPE_PROVINCE));
//    	rw = new RegionWrapper(r);
//    	res.add(rw);
//    	return res;
//    }
//
//    private static List<RegionWrapper> getCityList() {
//    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
//    	Region r = new Region();
//    	r.setParentId("3");
//    	r.setId("4");
//    	r.setName("广州");
//    	r.setType(new Integer(IRegion.TYPE_CITY));
//    	RegionWrapper rw = new RegionWrapper(r);
//    	res.add(rw);
//    	
//    	r = new Region();
//    	r.setParentId("3");
//    	r.setId("5");
//    	r.setName("深圳");
//    	r.setType(new Integer(IRegion.TYPE_CITY));
//    	rw = new RegionWrapper(r);
//    	res.add(rw);
//    	
//
//    	return res;
//    }

	/**
	 * arraylist去重
	 * @param arlList
	 */
	public static void removeDuplicateWithOrder(List arlList) 
	{
	  Set set = new HashSet();
	  List newList = new ArrayList();
	  for (Iterator iter = arlList.iterator(); iter.hasNext(); )
	  {
	      Object element = iter.next();
	      if (set.add(element)){
	    	  newList.add(element);
	      }
	  }
	  arlList.clear();
	  arlList.addAll(newList);
	}
	
	/**
	 * 格式化重量：小于1千克用克表示，否则用千克表示
	 * @param   float   $weight     重量
	 * @return  string  格式化后的重量
	 */
	public static String formatedWeight(double weight){
		Long newWeight = Math.round(weight);
		Lang lang = Lang.getInstance();
		if( newWeight > 0 ){
			if( newWeight < 1 ){
				return newWeight.intValue() * 1000 + lang.getString("gram");
			}
			else{
				return newWeight.intValue() + lang.getString("kilogram");
			}
		}
		else{
			return "0";
		}
	}
	
	public static String getTempleteContent( Map map , String templateName) throws IOException, TemplateException {
		//根据数据将ftl转化为html，并以String类型返回
        Configuration cfg = new Configuration();

        // TODO: 
        // as shown in TestFreeMarker, it's not perfect that the ?keys will return method names also, need refer to how spring freemarker plugin do the render
        // NOTE: however in runtime it works ok !!
        
		// refer to JAVADOC: Configuration.isClassicCompatible()
		cfg.setClassicCompatible(true);
		// refer to Freemarker manuual chapter: Bean wrapper
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		
		//TODO
    	cfg.setDirectoryForTemplateLoading(new File("D:/Jcommerce/JCommerceGae/war/web/front/library"));  
		Template t = cfg.getTemplate(templateName,"UTF-8");
		StringWriter stringWriter = new StringWriter();
     	t.process(map, stringWriter);
     	return stringWriter.toString();
		
	}
//	/**   
//     * 全角转半角   
//     * @param QJstr   
//     * @return   
//     */   
//    public static final String QBchange(String QJstr) {    
//        String outStr = "";    
//        String Tstr = "";    
//        byte[] b = null;    
//   
//        for (int i = 0; i < QJstr.length(); i++) {    
//            try {    
//                Tstr = QJstr.substring(i, i + 1);    
//                b = Tstr.getBytes("unicode");    
//            } catch (java.io.UnsupportedEncodingException e) {    
//                e.printStackTrace();    
//            }    
//   
//            if (b[3] == -1) {    
//                b[2] = (byte) (b[2] + 32);    
//                b[3] = 0;    
//                try {    
//                    outStr = outStr + new String(b, "unicode");    
//                } catch (java.io.UnsupportedEncodingException e) {    
//                    e.printStackTrace();    
//                }    
//            } else   
//                outStr = outStr + Tstr;    
//        }    
//   
//        return outStr;    
//    }    

	private static String repeat(String s, int n) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0 ; i < n ; i++) {
	        sb.append(s);
	    }
	    return sb.toString();
	}
}
