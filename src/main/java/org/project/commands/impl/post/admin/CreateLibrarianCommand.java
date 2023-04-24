package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.impl.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;
import org.project.services.resources.CommandPath;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

public class CreateLibrarianCommand implements ActionCommand {
    private final UserProvider provider = new UserProvider();
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        boolean action = provider.createUser(content, Role.LIBRARIAN);
        if (action) {
            return new ActionResult(CommandPath.SHOW_LIBRARIANS, true);
        } else {
            return new ActionResult(getPath(NEW_LIBRARIAN), false);
        }
    }
}
