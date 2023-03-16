package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.User;
import org.project.entity.dto.UserDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import org.project.utils.PathProvider;

public class UserProfileCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String login = (String) content.getSessionAttribute("login");
        UserDao userDao = new UserDao();
        UserDTO currentUser = Mapper.userToUserDTO(userDao.findByLogin(login).orElse(new User()));

        content.setRequestAttribute("user", currentUser);
        return new ActionResult(PathProvider.getPath(PROFILE), false);
    }
}
