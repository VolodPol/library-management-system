package org.project.commands.post;


import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

public class LogoutCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) {
        String page = getPath(LOGIN);

        content.removeSessionAttribute("role");
        content.removeSessionAttribute("name");
        content.removeSessionAttribute("subscription");

        return new CommandResult(page, true);
    }
}
