package com.library.tags;

import com.library.utils.UtilProvider;
import jakarta.servlet.jsp.tagext.TagSupport;
import com.library.services.resources.MessageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JSP custom tag for displaying greetings, support localization by means of ResourceBundle
 */
public class GreetingTag extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(GreetingTag.class);
    private String role;
    private String login;
    private String locale;

    public void setLogin(String login) {
        this.login = login;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() {
        String message;
        String lang = (UtilProvider.isEmpty(locale) || locale.equals("en_US") ? locale : "uk-UA");
        ResourceBundle bundle = ResourceBundle.getBundle("Localization", Locale.forLanguageTag(lang));
        if (role.isEmpty() || login.isEmpty())
            message = bundle.getString(MessageName.WELCOME_NO_NAME);
        else
            message = String.format(bundle.getString(MessageName.WELCOME_USER) + " %s %s!", role, login);
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
