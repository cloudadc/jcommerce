package com.jcommerce.core.test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcommerce.core.model.ArticleCategory;
import com.jcommerce.core.service.ArticleCategoryManager;

public class ServiceImplTest {
	
	private ApplicationContext ctx ;

	@Before
	public void setup() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@After
	public void destory() {
	}
	
	@Test
	public void testSetup() {
		assertNotNull(ctx);
	}
	
	@Test
	public void testSaveArticleCategory() {
		
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName("TestArticleCategory");
		ArticleCategoryManager articleCategoryManager = (ArticleCategoryManager) ctx.getBean("ArticleCategoryManager");
		articleCategoryManager.saveArticleCategory(articleCategory);
	}
}
