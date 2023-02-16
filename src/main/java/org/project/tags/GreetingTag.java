package org.project.tags;

import jakarta.servlet.jsp.tagext.TagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

public class GreetingTag extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(GreetingTag.class);
    private String role;
    private String login;

    public void setLogin(String login) {
        this.login = login;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        String message;
        if (role.isEmpty() || login.isEmpty())
            message = "Welcome to ELibrary!";
        else
            message = String.format("Welcome back, %s %s!", role, login);
        try {
            pageContext.getOut().write("<h2>" + message + "</h2>");
        } catch (IOException e) {
            log.debug("Error in GreetingTag");
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
