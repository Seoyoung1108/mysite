package mysite.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

public class ContextLoadListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  {   
         ServletContext sc = sce.getServletContext();
         String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
         System.out.println("Application[MySite02] starts..."+contextConfigLocation); // spring 관련 설정 작성 파일
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         // nothing
    }
	
}
