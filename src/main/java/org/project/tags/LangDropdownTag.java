package org.project.tags;

import jakarta.servlet.jsp.tagext.TagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LangDropdownTag extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(LangDropdownTag.class);
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() {
        StringBuilder html =
                new StringBuilder("""
                <li style="float:right">
                    <form name="localForm" method="post">
                        <label for="localization"></label>
                        <select name="locale" id="localization" onclick="this.form.submit()">""");
        if (locale == null || locale.equals("en_US")) {
            html.append("""
                    <option value="en_US" selected>En</option>
                    <option value="uk_UA">Ua</option>""");
        } else {
            html.append("""
                        <option value="en_US">En</option>
                        <option value="uk_UA" selected>Ua</option>""");
        }
        html.append("""
            </select>
        </form>
    </li>""");
        try {
            pageContext.getOut().write(html.toString());
        } catch (IOException e) {
            log.debug("Error in LangDropdownTag");
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
