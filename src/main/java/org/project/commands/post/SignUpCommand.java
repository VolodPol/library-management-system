package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.entity.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

public class SignUpCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        boolean action = UserProvider.createUser(content, Role.USER);
        if (action) {
            return new CommandResult(getPath(LOGIN), true);
        } else {
            return new CommandResult(getPath(REGISTER), false);
        }
    }
}
