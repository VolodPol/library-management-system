package org.project.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * SimpleTagSupport successor class for tag file 'stock_message'. Displays the amount of copies if present, otherwise -
 * displays the message: 'out of stock'.
 */
public class StockTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
    }
}
