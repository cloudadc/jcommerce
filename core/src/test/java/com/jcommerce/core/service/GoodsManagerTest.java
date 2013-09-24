/**
 * Author: Bob Chen
 */

package com.jcommerce.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;

import com.jcommerce.core.BaseDAOTestCase;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.BrandManager;
import com.jcommerce.core.service.CategoryManager;
import com.jcommerce.core.service.GoodsManager;

@Ignore
public class GoodsManagerTest extends BaseDAOTestCase {
    private GoodsManager goodsManager = null;
    private BrandManager brandManager = null;
    private CategoryManager categoryManager = null;

    public void testSave() throws Exception {
        System.out.println("---testSave");
        Brand b1 = new Brand();
        b1.setName("NOKIA");
        b1.setDescription("NOKIA Mobile");

        brandManager.saveBrand(b1);
//
//        Brand p2 = new Brand();
//        p2.setName("LENOVA");
//        p2.setDescription("LENOVA Computer");
//
//        s.save(p2);
//
        Category c1 = new Category();
        c1.setName("手机");
        
        Category c2 = new Category();
        c2.setName("国外品牌");
        Category c3 = new Category();
        c3.setName("国内品牌");
        c2.setParent(c1);
        c3.setParent(c1);

        categoryManager.saveCategory(c1);
        categoryManager.saveCategory(c2);
        categoryManager.saveCategory(c3);
//        
        Goods n72 = new Goods();
        n72.setBrand(b1);
        Set set = new TreeSet();
        set.add(c2);
        n72.setCategories(set);
        n72.setName("N72");
        
        goodsManager.saveGoods(n72);
    }

    public void _testUpdate() throws Exception {
        List gs = goodsManager.getGoodsList();
        for (Iterator it = gs.iterator(); it.hasNext();) {
            Goods g = (Goods) it.next();
            System.out.println(g.getId()+" "+g.getName());
            Set set = new TreeSet();
            Category c2 = categoryManager.getCategory("3");
            set.add(c2);
            g.setCategories(set);
            
            Brand b = g.getBrand();
            if (b != null) {
                b = brandManager.initialize(b);
                b.setName("new_"+b.getName());
                g.setBrand(b);
            }
            
            goodsManager.saveGoods(g);
        }        
    }
    
    public void _testList() throws Exception {
        System.out.println("---testList");
        List gs = goodsManager.getGoodsList();
        for (Iterator it = gs.iterator(); it.hasNext();) {
            Goods g = (Goods) it.next();
            System.out.println(g.getId()+" "+g.getName());
            Set cs = g.getCategories();
            for (Iterator cit = cs.iterator(); cit.hasNext();) {
                Category c = (Category) cit.next();
                if (c != null) {
                    c = categoryManager.initialize(c);
                    System.out.println(" " + c.getId()+" "+c.getName());
                }
            }
            Brand b = g.getBrand();
            if (b != null) {
                System.out.println("b:"+b.getClass().getName());
                b = brandManager.initialize(b);
                System.out.println(" " + b.getId()+" "+b.getName());
            }
        }
    }
    
    public void testQuery() throws Exception {
        System.out.println("---testQuery");

        List<Condition> conditions = new ArrayList<Condition>();
        String v = "国外品牌";
        conditions.add(new Condition("name", Condition.EQUALS, v));
        Criteria criteria = new Criteria();
        criteria.setConditions(conditions);
        List gs = categoryManager.getCategoryList(criteria);
        for (Iterator it = gs.iterator(); it.hasNext();) {
            Category c = (Category) it.next();
            System.out.println(" " + c.getId()+" "+c.getName());
        }
    }
    
    protected void setUp() throws Exception { 
        log = LogFactory.getLog(GoodsManagerTest.class);
        goodsManager = (GoodsManager ) ctx.getBean("GoodsManager");
        brandManager = (BrandManager ) ctx.getBean("BrandManager");
        categoryManager = (CategoryManager ) ctx.getBean("CategoryManager");
    }

    protected void tearDown() throws Exception {
        goodsManager = null;
        brandManager = null;
        categoryManager = null; 
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(GoodsManagerTest.class);
    }
}
