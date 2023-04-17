package org.project.listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebListener class for logging the authorization process of users
 */
@WebListener
public class SignListener implements HttpSessionAttributeListener {
    private final static Logger log = LoggerFactory.getLogger(SignListener.class);
    private String login;
    private String role;
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String currentLogin = (String) session.getAttribute("login");
        String currentRole = (String) session.getAttribute("role");

        if (currentLogin != null && !currentLogin.equals(login)) {
            log.info(String.format("%s %s signed in the application", currentRole, currentLogin));
        }
        login = currentLogin;
        role = currentRole;
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals("subscription")) {
            log.info(String.format("%s %s logged out the application", role, login));
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        HttpSessionAttributeListener.super.attributeReplaced(event);
    }
}
