package com.kris.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sun.istack.internal.logging.Logger;

@WebListener
public class HibernateContextListener implements ServletContextListener{
	
	private final Logger logger = Logger.getLogger(HibernateContextListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		sce.getServletContext().setAttribute("SessionFactory", sf);
		logger.info("SessionFactory is set to context");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		SessionFactory sf = (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
		if(sf != null &&  !sf.isClosed())
		{
			sf.close();
			logger.info("SessionFactory is closed");
		}
		
	}
	

}
