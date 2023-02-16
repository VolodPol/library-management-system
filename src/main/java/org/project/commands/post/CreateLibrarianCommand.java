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

public class CreateLibrarianCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        boolean action = UserProvider.createUser(content, Role.LIBRARIAN);
        if (action) {
            return new CommandResult("front?command=show_librarians", true);
        } else {
            content.setRequestAttribute("errorMessage", "Username is already in use!");
            return new CommandResult(getPath(NEW_LIBRARIAN), false);
        }
    }
}
