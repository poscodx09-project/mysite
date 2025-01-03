package mysite.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)  {
    	ServletContext sc = sce.getServletContext();
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	
       	System.out.println("Application[MySite02] starts..." + contextConfigLocation);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	/* nothing */
    }
}
