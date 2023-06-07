package com.library.commands;


import com.library.exceptions.DaoException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Bare interface for command to implement
 */
public interface ActionCommand {
    ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException;
}
