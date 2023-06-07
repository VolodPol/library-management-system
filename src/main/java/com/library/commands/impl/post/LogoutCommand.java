package com.library.commands.impl.post;


import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;

import static com.library.services.resources.FilePath.*;
import static com.library.utils.PathProvider.getPath;

public class LogoutCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) {
        String page = getPath(LOGIN);

        content.removeSessionAttribute("role");
        content.removeSessionAttribute("name");
        content.removeSessionAttribute("login");
        content.removeSessionAttribute("subscription");

        return new ActionResult(page, true);
    }
}
