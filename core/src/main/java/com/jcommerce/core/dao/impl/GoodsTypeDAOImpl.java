/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.dao.GoodsTypeDAO;
import com.jcommerce.core.model.GoodsType;

public class GoodsTypeDAOImpl extends DAOImpl implements GoodsTypeDAO {
    public GoodsTypeDAOImpl() {
        modelClass = GoodsType.class;
    }
    public List<GoodsType> getGoodsTypeList() {
    	return getList();
    }
    
    public List<GoodsType> getGoodsTypeListWithAttrCount(int firstRow, int maxRow, String whereClause) {
//    	List<GoodsType> list = getList();
//    	System.out.println("list.size:"+list.size());
//    	for(GoodsType gt:list) {
//    		gt.getAttributes().size();
////    		for(Attribute attr:gt.getAttributes()){
////    			attr.getName();
////    		}
//    	}
//        return list;
        
    	// in order to really save no. of sql query, have to mark lazy=true in GoodsType.hbm.xml, 
    	// now still keep it false, in order to avoid error in the other GoodsTypeListPanel implementation, where lazyloading will cause session closed exception
    	
        String hql = "select goodstype, count(attrs) from GoodsType as goodstype left join goodstype.attributes as attrs "+whereClause+" group by goodstype";
        System.out.println("hql: "+hql);
    	List list = getList(hql);
//    	System.out.println("size: "+list.size());
//    	
//    	for(Object item:list){
//    		System.out.println("item.class: "+item.getClass().getName()+", item:"+item.toString());
//    		Object[] i = (Object[])item;
//    		for(Object ii:i) {
//    			System.out.println("ii.class:"+ii.getClass().getName()+", ii: "+ii.toString());
//    		} 
//    	}
    	List<GoodsType> res = new ArrayList<GoodsType>();
    	for(Object o:list) {
    		Object[] objs = (Object[])o;
    		GoodsType gt = (GoodsType)objs[0];
    		gt.setAttrCount(((Number)objs[1]).intValue());
    		res.add(gt);
    	}
    	return res;
    	
        
        
//    	try {
//    	List list = getHibernateTemplate().find("select cat_id, (select count(gt.attributes)) as mycount from GoodsType gt");
////    	List list = getHibernateTemplate().find("select type.id, type.name, (select count(1) from Attribute attr where attr.cat_id = type.id) as mycount from GoodsType type");
//    	for(int i=0;i<list.size();i++) {
//    		Object item = list.get(i);
//    		System.out.println("item: "+item);
//    		System.out.println("item class: "+item.getClass().getName());
//    	}
//    	
//    	return list;
//    	} catch (Exception ex){
//    		ex.printStackTrace();
//    		return null;
//    	}
    }
    
    public GoodsType getGoodsType(String id) {
        return (GoodsType)getById(id);
    }

    public void saveGoodsType(GoodsType obj) {
        save(obj);
    }

    public void removeGoodsType(String id) {
        deleteById(id);
    }
}
