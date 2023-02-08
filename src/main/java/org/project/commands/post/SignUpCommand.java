package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.entity.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;

public class SignUpCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        boolean action = UserProvider.createUser(content, Role.USER);
        if (action) {
            return new CommandResult("login.jsp", true);
        } else {
            return new CommandResult("register.jsp", false);
        }
    }
}
