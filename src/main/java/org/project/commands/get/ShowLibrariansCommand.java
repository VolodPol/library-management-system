package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.entity.dto.IUserDTO;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;

import java.util.List;

public class ShowLibrariansCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        int page = 1;
        final int recsPerPage = 5;

        String pageParameter = content.getParameter("page");
        if (pageParameter != null)
            page = Integer.parseInt(pageParameter);

        List<IUserDTO> librarians;
        UserDao userDao = new UserDao();

        List<User> users;
        users = userDao.getAll((page - 1) * recsPerPage, recsPerPage);

        int numOfRecs = userDao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        librarians = UserProvider.filterUsers(users, Role.LIBRARIAN);

        content.setRequestAttribute("librariansList", librarians);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new CommandResult("librarians.jsp", false);
    }
}
