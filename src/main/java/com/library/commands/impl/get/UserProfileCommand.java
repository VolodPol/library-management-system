package com.library.commands.impl.get;

import com.library.entity.dto.UserDTO;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import com.library.utils.PathProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.services.Mapper;

import static com.library.services.resources.FilePath.*;

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
