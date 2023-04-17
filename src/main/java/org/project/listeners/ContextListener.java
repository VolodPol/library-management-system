package org.project.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.project.connection.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("The application has been launched with " + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DataSource.getConnection().close();
            DataSource.getDataSource().close();
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            AbandonedConnectionCleanupThread.checkedShutdown();

        } catch (SQLException e) {
            log.error("Couldn't close the datasource properly");
        }
    }
}
