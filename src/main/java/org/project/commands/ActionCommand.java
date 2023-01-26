package org.project.commands;


public interface ActionCommand {
    CommandResult execute(SessionRequestContent content);
}
