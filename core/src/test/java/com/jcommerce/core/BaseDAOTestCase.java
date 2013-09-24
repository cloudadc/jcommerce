package com.jcommerce.core;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Ignore
public class BaseDAOTestCase extends TestCase {
    protected static Log log = LogFactory.getLog(BaseDAOTestCase.class);
    protected ApplicationContext ctx = null;

    public BaseDAOTestCase() {
        String[] paths = {"/META-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
}
