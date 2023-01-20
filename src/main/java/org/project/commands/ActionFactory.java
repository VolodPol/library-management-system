package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand (HttpServletRequest request) {
        ActionCommand currentCommand = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            currentCommand = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action + "errors/wrongAction.jsp");
        }
        return currentCommand;
    }
}
