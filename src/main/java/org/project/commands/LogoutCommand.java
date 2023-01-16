package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = "login.jsp";
        HttpSession session = request.getSession();
        session.removeAttribute("role");
        session.removeAttribute("name");
        session.removeAttribute("subscription");

        session.invalidate();
        return page;
    }
}
