package Controller;

import DataAccess.DataAccess;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Utils.context = servletContextEvent.getServletContext();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SessionTask(), 0, TimeUnit.MINUTES.toMillis(10));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
