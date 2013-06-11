/**
 * Author: Bob Chen
 */

package com.jcommerce.core.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.BonusType;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.model.GoodsType;

public class TestBrand {
    private Configuration getConfiguration() {
        Configuration conf = new Configuration();
        
        Properties props = new Properties();
        InputStream is = this.getClass().getResourceAsStream("/hibernate.properties");
        try {
            props.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return conf;
    }
    
    private void initTables() throws Exception {
        Configuration conf = getConfiguration();
        
//        conf.addClass(Brand.class);
        conf.addClass(Category.class);
//        conf.addClass(BonusType.class);
//        conf.addClass(Goods.class);
//        conf.addClass(Attribute.class);
//        conf.addClass(GoodsType.class);
//        conf.addClass(Gallery.class);

        SchemaExport dbExport = new SchemaExport(conf);
        dbExport.create(true, true);
    }
    
    private void insertData() throws Exception {
        Configuration conf = getConfiguration();
//        conf.addClass(Tour.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session s = sessionFactory.openSession();

        Transaction t = s.beginTransaction();
        
//        Tour tour = new Tour();
//        tour.setName("青岛-崂山-太清宫双卧4日");
//        tour.setDescription("春季出游，比旺季便宜20%以上，更有免费赠送、+1元换购、半价折扣、立减等优惠，出行就趁现在。");
//        tour.setPrice(1300);
//        tour.setConnector("张玉");
//        tour.setAgenda("");
//        tour.setBookNote("进店参考：绝不强迫消费<br>1、参观丽江土特产店—“迈多藻中造”配送中心（参观时15分钟）<br>2、七彩云南茶艺（参观时间120分钟）；<br>3、游览赛博兴或者佳盟鲜花市场（游览时间45分钟左右）");
//        tour.setCharacteristic("<font color=red>参观青岛历史见证-栈桥，世界建筑博物馆-八大关风景区</font><br>★游海上名山-崂山，倾听石老人的故事；<br>★赠送：游船海上观光；<br>★若住宿升级到按照三星级标准建造的酒店双人标准间，仅需加30元/人。如果升级为一动一卧加70元/人");
//        tour.setConnectPhone("13683341213");
//        tour.setForPeople("青年, 老年, 情侣");
//        tour.setForReasons("春, 夏, 秋, 冬");
//        tour.setNumber(50);
//        tour.setPeriod(10);
//        tour.setFromLocation("北京市");
//        tour.setToLocation("青岛市");
//        tour.setReturnTraffic("火车");
//        tour.setStartTraffic("火车");
//        tour.setDestinationTraffic("空调巴士");
//        tour.setType("自由人线路");
//        tour.setFeeInclude("自理项目：自理景点门票价格请以景区挂牌价格为准<br>1.崇圣寺三塔电瓶车<br>2.云杉坪、蓝月谷电瓶车<br>3.森林公园电瓶车<br>4.野象谷电瓶车及野象谷索道");
//        tour.setFeeExclude("可选择项目价格：以下项目客人均可自愿参加，无强制<br>昆明：1.世博园120元（含门票、车费）<br>2.民族村90元（含门票、车费）<br>3：吉鑫宴舞（宴会+歌舞表演）238元/位、268元/位<br>4：云南印象（大型原生态歌舞）甲票220元、乙票280元、贵宾票380元）<br>大理：蝴蝶");
//
//        s.save(tour);
        
        t.commit();

        s.close();
    }     
    
    private void listData() throws Exception {
        Configuration conf = getConfiguration();
        conf.addClass(Brand.class);
        conf.addClass(Category.class);
        conf.addClass(BonusType.class);
        conf.addClass(Goods.class);
        conf.addClass(Attribute.class);
        conf.addClass(GoodsType.class);
        conf.addClass(Gallery.class);

        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session s = sessionFactory.openSession();

//        Query q = s.createQuery("from Brand as p where p.id<10");
//        List persons = q.list();
//        for (Iterator it = persons.iterator(); it.hasNext();) {
//            Brand p = (Brand) it.next();
//            System.out.println(p.getId()+" "+p.getName()+" "+p.getDescription());             
//        }
//        
        Query q = s.createQuery("from Category as p");
        List cs = q.list();
        for (Iterator it = cs.iterator(); it.hasNext();) {
            Category c = (Category) it.next();
            System.out.println(c.getId()+" "+c.getName());
            c = c.getParent();
            if (c != null) {
                System.out.println(" " + c.getId()+" "+c.getName());
            }
        }

        q = s.createQuery("from Goods as p"); 
        cs = q.list();
        for (Iterator it = cs.iterator(); it.hasNext();) {
            Goods g = (Goods) it.next();
            System.out.println(g.getId()+" "+g.getName());
//            Category c = g.getCategory();
//            if (c != null) {
//                System.out.println(" " + c.getId()+" "+c.getName());
//            }
            Brand b = g.getBrand();
            if (b != null) {
                System.out.println(" " + b.getId()+" "+b.getName());
            }
        }
        
        s.close();
    }     
    
    private void updateData() throws Exception {
        Configuration conf = getConfiguration();
        conf.addClass(Brand.class);
        conf.addClass(Category.class);
        conf.addClass(BonusType.class);
        conf.addClass(Goods.class);
        conf.addClass(Attribute.class);
        conf.addClass(GoodsType.class);
        conf.addClass(Gallery.class);

        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session s = sessionFactory.openSession();

        Transaction t = s.beginTransaction();
        Query q = s.createQuery("from Person as p where p.id=1");
        Brand p = (Brand) q.list().get(0);
        System.out.println(p.getName());
        p.setName(p.getName()+"c");
        t.commit(); 
        
        s.close();
    }     
    
    private void deleteData() throws Exception {
        Configuration conf = getConfiguration();
        conf.addClass(Brand.class);
        conf.addClass(Category.class);
        conf.addClass(BonusType.class);
        conf.addClass(Goods.class);
        conf.addClass(Attribute.class);
        conf.addClass(GoodsType.class);
        conf.addClass(Gallery.class);

        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session s = sessionFactory.openSession();

        Transaction t = s.beginTransaction();
        Query q = s.createQuery("from Brand as p where p.id=2");
        Brand p = (Brand) q.list().get(0);
        s.delete(p);
        t.commit(); 
        
        s.close();
    }     
    
    private void queryData() throws Exception {
        Configuration conf = getConfiguration();
        conf.addClass(Brand.class);
        conf.addClass(Category.class);
        conf.addClass(BonusType.class);
        conf.addClass(Goods.class);
        conf.addClass(GoodsAttribute.class);
        conf.addClass(Attribute.class);
        conf.addClass(GoodsType.class);
        conf.addClass(Gallery.class);

        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session s = sessionFactory.openSession();

        Transaction t = s.beginTransaction();
        Query q = s.createQuery("from Goods where brand = 1 AND 3 member of categories AND bestSold = true");
        q.list();
        
        t.commit(); 
                
        s.close();
    }
    
    public static void main(String[] args) throws Exception {
//        new TestBrand().initTables();
        new TestBrand().insertData();
//        new TestBrand().deleteData();
//        new TestPersonModel().updateData();
//        new TestBrand().queryData(); 
    }
}