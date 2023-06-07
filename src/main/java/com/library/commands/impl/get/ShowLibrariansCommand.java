package com.library.commands.impl.get;

import com.library.entity.dto.LibrarianDTO;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import com.library.utils.PathProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.services.Mapper;
import com.library.services.pagination.Paginator;
import com.library.services.pagination.impl.UserPaginator;

import static com.library.services.resources.FilePath.*;

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

        return new ActionResult(PathProvider.getPath(LIBRARIANS), false);
    }
}
