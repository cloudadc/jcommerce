/**
 * Author: Bob Chen
 */

package com.jcommerce.core.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.jcommerce.core.BaseDAOTestCase;
import com.jcommerce.core.dao.ArticleDAO;
import com.jcommerce.core.model.Article;

public class GoodsDAOTest extends BaseDAOTestCase {
    private Article goods = null;
    private ArticleDAO dao = null;

    public void testSaveGoods() throws Exception {
        System.out.println("---testSaveGoods");
        goods = new Article();
        goods.setId("1");        
        goods.setAuthor("king");
        goods.setContent("I LOVE YOU");
        goods.setTitle("love");
        dao.saveArticle(goods);
        assertTrue(goods.getAuthor() != null);
    }

    /*public void _testAddAndRemoveGoods() throws Exception {
        System.out.println("---testAddAndRemoveGoods");
        goods = new Goods();
        goods.setName("K7350");
        dao.saveGoods(goods);
        assertTrue(goods.getName().equals("K7350"));
        if (log.isDebugEnabled()) {
            log.debug("removing goods...");
        }
        dao.removeGoods(goods.getId());
        assertNull(dao.getGoods(goods.getId()));
    }
        
    public void _testListGoods() throws Exception {
        System.out.println("---testListGoods");
        List list = dao.getGoodsList();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Goods g = (Goods) it.next();
            System.out.println("g:"+g.getName());
        }
    }
    */
    protected void setUp() throws Exception {
        log = LogFactory.getLog(GoodsDAOTest.class);
        dao = (ArticleDAO) ctx.getBean("ArticleDAO");
    }

    protected void tearDown() throws Exception {
        dao = null;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(GoodsDAOTest.class);
    }
}
