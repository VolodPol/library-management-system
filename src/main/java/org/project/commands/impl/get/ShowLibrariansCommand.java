package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.entity.dto.LibrarianDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

import java.util.List;

public class ShowLibrariansCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int page = 1;
        final int recsPerPage = 5;

        String pageParameter = content.getParameter("page");
        if (pageParameter != null) page = Integer.parseInt(pageParameter);

        List<LibrarianDTO> librarians;
        UserDao userDao = new UserDao();

        List<User> users = userDao.findAll((page - 1) * recsPerPage, recsPerPage, Role.LIBRARIAN);

        int numOfRecs = userDao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        librarians = Mapper.usersToLibrariansDTO(users);

        content.setRequestAttribute("librariansList", librarians);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new ActionResult(getPath(LIBRARIANS), false);
    }
}
