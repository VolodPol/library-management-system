package org.project.commands;


public class EmptyCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        return new CommandResult("login.jsp");
    }
}
