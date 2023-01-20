package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;

public interface ActionCommand {
    String execute(SessionRequestContent content);
}
