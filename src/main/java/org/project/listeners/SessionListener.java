package org.project.listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SessionListener implements HttpSessionListener {
    private final Logger log = LoggerFactory.getLogger(SessionListener.class);
    private String id;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        id = session.getId();
        log.info(String.format("New session with id : %s created ", id));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info(String.format("Session with id : %s destroyed ", id));
    }
}
