package org.project.commands;


import jakarta.servlet.http.HttpServletResponse;
import org.project.exceptions.DaoException;

public interface ActionCommand {
    CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException;
}
