package org.project.commands;


import jakarta.servlet.http.HttpServletResponse;

public class EmptyCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) {
        return new CommandResult("login.jsp");
    }
}
