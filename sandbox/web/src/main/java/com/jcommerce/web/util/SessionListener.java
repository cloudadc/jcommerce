package com.jcommerce.web.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.jcommerce.core.model.Session;
import com.jcommerce.core.util.SpringUtil;

public class SessionListener implements HttpSessionListener {
	
	private int maxLifeTime  = 1800; // SESSION 过期时间

	private void init(){
//		getDefaultManager().txdelete(ModelNames.SESSION, null);
	}
	
	public void sessionCreated(HttpSessionEvent se) {
		
		Session session = new Session();
		session.setId(se.getSession().getId());
		session.setExpiry(new Timestamp(new Date().getTime()));
		SpringUtil.getSessionManager().saveSession(session);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
	    if (SpringUtil.getSessionManager() != null) {
	        SpringUtil.getSessionManager().removeSession(se.getSession().getId());
	    }
	}

}
