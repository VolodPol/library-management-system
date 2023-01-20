package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent content) {
        return "login.jsp";
    }
}
