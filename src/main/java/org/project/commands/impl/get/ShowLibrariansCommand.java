package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.entity.dto.LibrarianDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;
import org.project.services.pagination.Paginator;
import org.project.services.pagination.impl.UserPaginator;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

import java.util.List;

public class ShowLibrariansCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        content.setRequestAttribute("usersRole", Role.LIBRARIAN.getRoleValue());

        Paginator<User> paginator = new UserPaginator();
        List<User> librarians = paginator.provideData(content);
        List<LibrarianDTO> librarianDTOS = Mapper.usersToLibrariansDTO(librarians);

        content.setRequestAttribute("librariansList", librarianDTOS);
        content.setRequestAttribute("currentPage", paginator.getCurrentPage());
        content.setRequestAttribute("numOfPages", paginator.getNumberOfPages());

        return new ActionResult(getPath(LIBRARIANS), false);
    }
}
