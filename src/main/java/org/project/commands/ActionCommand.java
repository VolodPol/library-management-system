package org.project.commands;


import org.project.exceptions.DaoException;

public interface ActionCommand {
    CommandResult execute(SessionRequestContent content) throws DaoException;
}
