package com.jcommerce.web.util;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_TITLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.ArticleCategory;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.CollectGoods;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.ArticleManager;
import com.jcommerce.core.service.CollectGoodsManager;
import com.jcommerce.core.service.CommentManager;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.service.UserManager;
import com.jcommerce.core.util.SpringUtil;
import com.jcommerce.core.wrapper.ShopConfigWrapper;
import com.jcommerce.gwt.client.model.ICollectGood;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CollectGoodsWrapper;
import com.jcommerce.web.to.CommentWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.Message;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibMain {
	
	
	/**
	 * 获得指定分类的所有上级分类
	 *
	 * @access  public
	 * @param   integer $cat    分类编号
	 * @return  array
	 */
	public static String[][] getParentCats(String catId ) {
		if(StringUtils.isEmpty(catId)) {
			return new String[0][];
		}
		
		List<String[]> cats = new ArrayList<String[]>();
		
		List<Category> arr = SpringUtil.getCategoryManager().getCategoryList();
		
		int index = 0;
		String currentCatId = catId;
		while(true) {
			for(Category row: arr) {
				if(currentCatId.equals(row.getId())) {
					currentCatId = row.getParent() == null ? null : row.getParent().getId();
					
					String[] cat = new String[]{row.getId(), row.getName()};
					cats.add(cat);
					
					index++;
					break;
				}
			}
			
			if(index==0 || StringUtils.isEmpty(currentCatId)) {
				break;
			}
		}
		
		
		return cats.toArray(new String[0][]);
	}
	/**
	 * 获得指定分类的所有上级分类
	 *
	 * @access  public
	 * @param   integer $cat    分类编号
	 * @return  array
	 */
	public static String[][] getArticleParentCats(String catId ) {
		if(StringUtils.isEmpty(catId)) {
			return new String[0][];
		}
		
		List<String[]> cats = new ArrayList<String[]>();
		
		List<ArticleCategory> arr = SpringUtil.getArticleCategoryManager().getArticleCategoryList();
		
		int index = 0;
		String currentCatId = catId;
		while(true) {
			for(ArticleCategory row: arr) {
				if(currentCatId.equals(row.getId())) {
					currentCatId = row.getParent() == null ? null : row.getParent().getId();
					
					String[] cat = new String[]{row.getId(), row.getName()};
					cats.add(cat);
					
					index++;
					break;
				}
			}
			
			if(index==0 || StringUtils.isEmpty(currentCatId)) {
				break;
			}
		}
		
		
		return cats.toArray(new String[0][]);
	}
	
	
	
	/**
	 * 取得当前位置和页面标题
	 *
	 * @access  public
	 * @param   integer     $cat    分类编号（只有商品及分类、文章及分类用到）
	 * @param   string      $str    商品名、文章标题或其他附加的内容（无链接）
	 * @return  array
	 */
	public static void assignUrHere(HttpServletRequest request, String cat, String str) {
		// ur_here.ftl
		
		/* 取得文件名 */
		// /web/front/goods.action?act=price&id=126438149&attr=&number=1&1259214737720720
		String fileName = WebUtils.getActionName(request);
		
		/* 初始化“页面标题”和“当前位置” */
	    String pageTitle = SpringUtil.getShopConfigManager().getCachedShopConfig((String)request.getSession().getAttribute(IWebConstants.KEY_LOCALE)).getString(CFG_KEY_SHOP_TITLE) + "- Powered by GCShop";
	    String urHere = "<a href=\""+request.getContextPath()+"/web/front/home.action\">"+Lang.getInstance().getString("home") + "</a>";	    
	    
	    /* 根据文件名分别处理中间的部分 */
	    if(!"home".equals(fileName)) {
	    	/* 处理有分类的 */
	    	if("category".equals(fileName) || "goods".equals(fileName) || "articleCat".equals(fileName)
	    			|| "article".equals(fileName) || "brand".equals(fileName)) {
	    		String key = "";
	    		String type = "";
	    		String[][] catArr = null;
	            /* 商品分类或商品 */
	            if ("category".equals(fileName) || "goods".equals(fileName) || "brand".equals(fileName))
	            {	    		
	            	if(cat!=null) {
	            		catArr = getParentCats(cat);
	            		key = "cid";
	            		type = "category";
	            	}else {
	            		catArr = new String[0][];
	            	}
	            }
	            /* 文章分类或文章*/
	            if ("articleCat".equals(fileName) || "article".equals(fileName))
	            {	    		
	            	if( cat != null ) {
	            		catArr = getArticleParentCats(cat);
	            		key = "acid";
	            		type = "article_cat";
	            	}else {
	            		catArr = new String[0][];
	            	}
	            }
	            
	            /* 循环分类 */
	            if(catArr!=null) {
	            	
	            	ArrayUtils.reverse(catArr);
	            	
	            	for(String[] val : catArr) {
	            		pageTitle = WebUtils.encodeHtml(val[1]) + "_" + pageTitle;
	            		Map<String, Object> args = new HashMap<String, Object>();
	            		args.put(key, val[0]);
	            		urHere = urHere + " <code>&gt;</code> <a href=\"" + 
	            				LibCommon.buildUri(type, args, val[1])+ "\">" + WebUtils.encodeHtml(val[1]) + "</a>";
	            		
	            	}

	            	
	            }
	            
	    	}
	    }
        /* 处理最后一部分 */
        if (!StringUtils.isEmpty(str)) {
            pageTitle  = str + "_" + pageTitle;
            urHere    += " <code>&gt;</code> " + str;
        }

        /* 返回值 */
        request.setAttribute("pageTitle", pageTitle);		
        request.setAttribute("urHere", urHere);
	}

	
	
	/**
	 * 查询评论内容
	 *
	 * @access  public
	 * @params  integer     $id
	 * @params  integer     $type
	 * @params  integer     $page
	 * @return  array
	 */
	public static Map<String, Object> assignComment(String id, int type, int page, CommentManager manager, ShopConfigWrapper scw) {
		/* 取得评论列表 */
		Criteria c = new Criteria();
		c.addCondition(new Condition(IComment.ID, Condition.EQUALS, id));
		c.addCondition(new Condition(IComment.COMMENTTYPE, Condition.EQUALS, ""+type));
		c.addCondition(new Condition(IComment.STATUS, Condition.EQUALS, ""+IComment.STATUS_ACTIVE));
		
		int size = scw.getInt(IShopConfigMeta.CFG_KEY_COMMENTS_NUMBER);
		if(size<0) {
			size = 5;
		}
		
		Map<String, CommentWrapper> arr = new HashMap<String, CommentWrapper>();
		Map<String,Comment> pcMap = new HashMap<String,Comment>();
		List<Comment> comments = manager.getCommentList((page-1)*size, size, c);
		
		for(Comment comment: comments) {
			String cid = comment.getId();
			
			if( comment.getParent() != null ){
				pcMap.put(comment.getParent().getId(),comment);
				continue;
			}
			
			CommentWrapper commentWrapper = arr.get(cid);
			if(commentWrapper == null) {
				commentWrapper = new CommentWrapper(comment);
				arr.put(cid, commentWrapper);
			}
			commentWrapper.put("id", cid);
			commentWrapper.put("email", comment.getEmail());
			commentWrapper.put("username", comment.getUserName());
			commentWrapper.put("content", StringUtils.replaceAll(
							StringUtils.replaceAll(comment.getContent(), "\r\n", "<br />")
							, "\n", "<br />"));
			commentWrapper.put("rank", comment.getCommentRank());
			String addTime = LibTime.localDate(
					scw.getString(IShopConfigMeta.CFG_KEY_TIME_FORMAT), 
					comment.getAddTime().getTime());
			commentWrapper.put("addTime", addTime);
			commentWrapper.put("reContent", null);
			
		}
		
		/* 取得已有回复的评论 */
		for(String pid : pcMap.keySet() ){
			CommentWrapper cw = arr.get(pid);
			Comment pcw = pcMap.get(pid);
			if( cw != null ){
				cw.put("reContent", pcw.getContent());
				cw.put("reEmail", pcw.getEmail());
				cw.put("reAddTime", pcw.getAddTime());
				cw.put("reUsername", pcw.getUserName());
			}
		}
		
		int count = arr.size();
		
		int pageCount = (int) (count > 0 ? (Math.ceil((double)count / size)) : 1);
		
		
		
		Pager pager = new Pager();
		pager.setCurrentPage(page);
        pager.setRecordPerPage(size);
        pager.setRecordCount(count);
        pager.setPageCount(pageCount);
		pager.setPageFirst("javascript:gotoPage(1,'"+id+"',"+type+")");
		pager.setPagePrev(page > 1 ? "javascript:gotoPage(" +(page-1)+ ",'"+id+"',"+type+")" : "javascript:;");
		pager.setPageNext(page < pageCount ? "javascript:gotoPage("+(page + 1)+",'"+id+"',"+type+")" : "javascript:;");
		pager.setPageLast(page < pageCount ? "javascript:gotoPage("+(pageCount)+",'"+id+"',"+type+")" : "javascript:;");
		
		Map<String, Object> cmt = new HashMap<String, Object>();
		cmt.put("comments",arr.values());
		cmt.put("pager", pager);
		
		return cmt;
		
	}
	
	/**
	 * 获得收藏列表
	 */
	public static Map<String, Object> assignCollectionList(String userId, int page, CollectGoodsManager manager, GoodsManager goodsManager, ShopConfigWrapper scw) {
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(ICollectGood.USER_ID, Condition.EQUALS, userId));
		
		int count = manager.getCollectGoodsCount(criteria);
		int size = scw.getInt(IShopConfigMeta.CFG_KEY_COLLECTION_NUMBER);
		if(size<0) {
			size = 5;
		}
		
		List<CollectGoods> collectGoods = manager.getCollectGoodsList((page-1)*size, size, criteria);
		
	    List<CollectGoodsWrapper> goodsList = new ArrayList<CollectGoodsWrapper>();
		for(Iterator iterator = collectGoods.iterator();iterator.hasNext();) {
			CollectGoods collectGood = (CollectGoods) iterator.next();
			CollectGoodsWrapper wrapper = new CollectGoodsWrapper(collectGood);
			
			String goodsId = wrapper.getCollectGoods().getGoods().getId();
			Goods goods = (Goods)goodsManager.getGoods(goodsId);
			wrapper.setGoods(goods) ;
			
			goodsList.add(wrapper);
		}
		
		Map<String, Object> cmt = new HashMap<String, Object>();
		Map<String, Object> search = new HashMap<String, Object>();
        search.put("act", "collection_list");
		Pager pager = getPager("", search, count, page, size, scw);
		cmt.put("goodsList",goodsList);
		cmt.put("pager", pager);
		return cmt;
	}
	
	/**
	 * 获得评论列表
	 */
	public static Map<String, Object> assignCommentList(String userId, int page, CommentManager manager, GoodsManager goodsManager, ArticleManager articleManager, ShopConfigWrapper scw) {
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IComment.USER,Condition.EQUALS,userId));
		
		int count = manager.getCommentCount(criteria);
		int size = scw.getInt(IShopConfigMeta.CFG_KEY_COMMENTS_NUMBER);
		if(size<0) {
			size = 5;
		}
		
		List<Comment> commentList = manager.getCommentList((page-1)*size, size, criteria);
		List<CommentWrapper> commentListWrapper = WrapperUtil.wrap(commentList, CommentWrapper.class);
		for(Iterator iterator = commentListWrapper.iterator(); iterator.hasNext();) {
			CommentWrapper commentWrapper = (CommentWrapper) iterator.next();
			if(commentWrapper.getComment().getCommentType() == 0){
				String goodsId = ""+commentWrapper.getComment().getIdValue();
				Goods goods = goodsManager.getGoods(goodsId);
				commentWrapper.put("cmtName", goods.getName());
			}else{
				String articleId = ""+commentWrapper.getComment().getIdValue();
				Article article = articleManager.getArticle(articleId);
				commentWrapper.put("cmtName", article.getTitle());
			}
			
			String id = commentWrapper.getComment().getId();
			criteria.removeAll();
			criteria.addCondition(new Condition(IComment.PARENT, Condition.EQUALS, id));
			List reply = manager.getCommentList(criteria);
			if(reply.size() == 0) {
				commentWrapper.put("replyContent", null) ;
			}
			else {
				Comment comment = (Comment) reply.get(0);
				commentWrapper.put("replyContent", comment.getContent());
			}
		}
		
		Map<String, Object> cmt = new HashMap<String, Object>();
		Map<String, Object> search = new HashMap<String, Object>();
        search.put("act", "comment_list");
		Pager pager = getPager("", search, count, page, size, scw);
		cmt.put("commentList",commentListWrapper);
		cmt.put("pager", pager);
		return cmt;
	}
	
	
	/**
	 * 创建分页信息
	 *
	 * @access  public
	 * @param   string  $app            程序名称，如category
	 * @param   string  $cat            分类ID
	 * @param   string  $record_count   记录总数
	 * @param   string  $size           每页记录数
	 * @param   string  $sort           排序类型
	 * @param   string  $order          排序顺序
	 * @param   string  $page           当前页
	 * @param   string  $keywords       查询关键字
	 * @param   string  $brand          品牌
	 * @param   string  $price_min      最小价格
	 * @param   string  $price_max      最高价格
	 * @return  void
	 */
	
	// TODO assignPager -- refactor & support of sort/order
    public static Pager assignPager(
    		String app, String catLongId, int recordCount, int size, String sort, String order, int page, 
    		String keyword, String brandId, double priceMin, double priceMax,
    		String displayType, String filterAttr, String urlFormat, Map<String, Object> schArray, 
    		HttpServletRequest request, ShopConfigWrapper scw) {
    	
    	int pageCount = recordCount > 0 ? (recordCount / size)+1 : 1;
    	
        Pager pager = new Pager();
        pager.getSearch().put("category", catLongId);
        pager.getSearch().put("keywords", keyword);
        pager.getSearch().put("sort", sort);
        pager.getSearch().put("order", order);
        pager.getSearch().put("cat", catLongId);
        pager.getSearch().put("brand", brandId);
        pager.getSearch().put("price_min", 0);
        pager.getSearch().put("price_max", 0);
        pager.getSearch().put("filter_attr", "");
        //pager.getSearch().put("display", displayType);
        pager.setCurrentPage(page);
        pager.setRecordPerPage(size);
        pager.setSort(sort);
        pager.setOrder(order);
        pager.setRecordCount(recordCount);
        pager.setPageCount(pageCount);
        pager.setDisplay(displayType);
        request.setAttribute("pager", pager);
        
        Map<String, Object> uriArgs = new HashMap<String, Object>();
        if(IWebConstants.APP_BRAND.equals(app)) {
        	uriArgs.put("cid", catLongId);
        	uriArgs.put("bid", brandId);
        	uriArgs.put("sort", sort);
        	uriArgs.put("order", order);
        	uriArgs.put("display", displayType);
        }        
        else if(IWebConstants.APP_CATEGORY.equals(app)) {
        	uriArgs.put("cid", catLongId);
        	uriArgs.put("bid", brandId);
        	uriArgs.put("sort", sort);
        	uriArgs.put("order", order);
        	uriArgs.put("display", displayType);
        	        	
        }
        else if(IWebConstants.APP_ARTICLE_CAT.equals(app)) {
        	uriArgs.put("acid", catLongId);
        	        	
        }
        
        pager.setStyleid(scw.getInt(IShopConfigMeta.CFG_KEY_PAGE_STYLE));
        
        int pagePrev  = (page > 1) ? page - 1 : 1;
        int pageNext  = (page < pageCount) ? page + 1 : pageCount;
        
        if (pager.getStyleid() == 0) {
			pager.setPageFirst(LibCommon.buildUri(app, uriArgs, "", 1, 0));
			pager.setPagePrev(LibCommon.buildUri(app, uriArgs, "", pagePrev,0));
			pager.setPageNext(LibCommon.buildUri(app, uriArgs, "", pageNext,0));
			pager.setPageLast(LibCommon.buildUri(app, uriArgs, "", pageCount, 0));

//			Map<Integer, Integer> array = pager.getArray();
//			for (int i = 1; i <= pageCount; i++) {
//				array.put(i, i);
//			}
		}
        else {
    		// TODO pager.getStyleid()!=0
    	}
        //${smarty.server.PHP_SELF}
        
        return pager;
    }
    
    /**
     *  生成给pager.lbi赋值的数组
     *
     * @access  public
     * @param   string      $url        分页的链接地址(必须是带有参数的地址，若不是可以伪造一个无用参数)
     * @param   array       $param      链接参数 key为参数名，value为参数值
     * @param   int         $record     记录总数量
     * @param   int         $page       当前页数
     * @param   int         $size       每页大小
     *
     * @return  array       $pager
     */
    
    public static Pager getPager(String url, Map<String, Object> param, int recordCount, int page, int size, ShopConfigWrapper scw) {
    	if(size<1) {
    		size = 5;
    	}
    	if(page<1) {
    		page = 1;
    	}
    	int pageCount = (int) (recordCount > 0 ? (Math.ceil((double)recordCount / size)) : 1);
    	
    	if(page > pageCount) {
    		page = pageCount;
    	}
    	Pager pager = new Pager();
    	pager.setStyleid(scw.getInt(IShopConfigMeta.CFG_KEY_PAGE_STYLE));
        int pagePrev  = (page > 1) ? page - 1 : 1;
        int pageNext  = (page < pageCount) ? page + 1 : pageCount;
        
    	StringBuffer paramUrl = new StringBuffer("?");
    	for(String key : param.keySet()) {
    		String value = param.get(key).toString();
    		paramUrl.append(key).append("=").append(value).append("&");
    	}
    	pager.setUrl(url);
//    	pager.setStart((page-1)*size);
    	pager.setCurrentPage(page);
    	pager.setRecordPerPage(size);
    	pager.setRecordCount(recordCount);
    	pager.setPageCount(pageCount);
    	
    	if(pager.getStyleid()==0) {
    		pager.setPageFirst(url+paramUrl.toString()+"page=1");
    		pager.setPagePrev(url+paramUrl.toString()+"page="+pagePrev);
    		pager.setPageNext(url+paramUrl.toString()+"page="+pageNext);
    		pager.setPageLast(url+paramUrl.toString()+"page="+pageCount);
    		
//			Map<Integer, Integer> array = pager.getArray();
//			for (int i = 1; i <= pageCount; i++) {
//				array.put(i, i);
//			}
    	}
    	else {
    		// TODO pager.getStyleid()!=0
    	}
    	
    	pager.setSearch(param);
    	
    	return pager;
    }
    
    public static String showMessage(String content, String link, String href, String type, Boolean autoRedirect, HttpServletRequest request) {
    	if(type == null) {
    		type = "info";
    	}
    	if(autoRedirect==null) {
    		autoRedirect = false;
    	}
    	Message msg = new Message();
    	msg.setContent(content);
    	msg.setLink(StringUtils.isEmpty(link)? (String)Lang.getInstance().get("backUpPage"):link);
    	msg.setHref(StringUtils.isEmpty(href)?"javascript:history.back()":href);
    	msg.setType(type);
    	
    	request.setAttribute("message", msg);
    	request.setAttribute("autoRedirect", autoRedirect);
    	
    	return "message";
    	
    }
    
    public static UserWrapper getUserInfo(String userId, UserManager manager) {
    	User user = manager.getUser(userId);
    	
    	return new UserWrapper(user);
    }
}
