package com.library.commands.impl.post.admin;

import com.library.services.UserProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.entity.Role;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;

import static com.library.services.resources.FilePath.*;
import static com.library.utils.PathProvider.getPath;

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
