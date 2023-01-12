package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = "index.jsp";
        request.getSession().invalidate();
        return page;
    }
}
