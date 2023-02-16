package org.project.commands.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.entity.dto.OrderedBookDTO;
import org.project.exceptions.DaoException;
import org.project.services.UserBooksProvider;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

import java.util.List;

public class ShowMyBooksCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String userSub = (String) content.getSessionAttribute("role");
        String login = (String) content.getSessionAttribute("login");

        List<OrderedBookDTO> myOrderList = UserBooksProvider.getUserBooks(userSub, login);

        content.setRequestAttribute("userBookList", myOrderList);
        return new CommandResult(getPath(USER_BOOKS), false);
    }
}
