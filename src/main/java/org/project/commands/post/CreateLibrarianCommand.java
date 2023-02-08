package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.entity.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;

public class CreateLibrarianCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        boolean action = UserProvider.createUser(content, Role.LIBRARIAN);
        if (action) {
            return new CommandResult("front?command=show_librarians", true);
        } else {
            content.setRequestAttribute("errorMessage", "Username is already in use!");
            return new CommandResult("new_librarian.jsp", false);
        }
    }
}
