package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.impl.Role;
import org.project.entity.impl.User;
import org.project.entity.dto.UserDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;

import org.project.services.pagination.Paginator;
import org.project.services.pagination.impl.UserPaginator;
import org.project.utils.PathProvider;

import java.util.List;

public class ShowUsersCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        content.setRequestAttribute("usersRole", Role.USER.getRoleValue());

        Paginator<User> paginator = new UserPaginator();
        List<User> users = paginator.provideData(content);
        List<UserDTO> userDTOS = Mapper.usersToUsersDTO(users);

        content.setRequestAttribute("usersList", userDTOS);
        content.setRequestAttribute("currentPage", paginator.getCurrentPage());
        content.setRequestAttribute("numOfPages", paginator.getNumberOfPages());

        return new ActionResult(PathProvider.getPath(USERS), false);
    }
}
