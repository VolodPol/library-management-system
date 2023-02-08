package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDao;
import org.project.entity.dto.UserDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

//profile
public class UserProfileCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        String login = (String) content.getSessionAttribute("login");
        UserDao userDao = new UserDao();
        UserDTO currentUser = Mapper.userToUserDTO(userDao.findUser(login));

        content.setRequestAttribute("user", currentUser);
        return new CommandResult("profile.jsp", false);
    }
}
