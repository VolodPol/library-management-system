package org.project.commands.impl.post;


import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

public class LogoutCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) {
        String page = getPath(LOGIN);

        content.removeSessionAttribute("role");
        content.removeSessionAttribute("name");
        content.removeSessionAttribute("subscription");

        return new ActionResult(page, true);
    }
}
