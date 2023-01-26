package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        return new CommandResult("login.jsp");
    }
}
