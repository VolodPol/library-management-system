package org.project.commands.factory;

import jakarta.servlet.http.HttpServletRequest;
import org.project.commands.ActionCommand;
import org.project.commands.CommandEnum;
import org.project.commands.impl.get.EmptyCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory that defines specific implementation of ActionCommand by the request's parameter
 */
public class ActionFactory {
    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);
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
            log.warn(String.format("wrong action in the %s class", this.getClass()), e);
        }
        return currentCommand;
    }
}