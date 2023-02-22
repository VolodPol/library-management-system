package org.project.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

public class SignUpCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        boolean action = UserProvider.createUser(content, Role.USER);
        if (action) {
            return new ActionResult(getPath(LOGIN), true);
        } else {
            return new ActionResult(getPath(REGISTER), false);
        }
    }
}
