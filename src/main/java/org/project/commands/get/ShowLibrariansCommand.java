package org.project.commands.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.entity.dto.LibrarianDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

import java.util.List;

public class ShowLibrariansCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int page = 1;
        final int recsPerPage = 5;

        String pageParameter = content.getParameter("page");
        if (pageParameter != null)
            page = Integer.parseInt(pageParameter);

        List<LibrarianDTO> librarians;
        UserDao userDao = new UserDao();

        List<User> users;
        users = userDao.getAll((page - 1) * recsPerPage, recsPerPage, Role.LIBRARIAN);

        int numOfRecs = userDao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        librarians = users.stream().map(Mapper::userToLibrarianDTO).toList();

        content.setRequestAttribute("librariansList", librarians);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new CommandResult(getPath(LIBRARIANS), false);
    }
}
