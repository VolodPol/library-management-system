package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.entity.dto.OrderedBookDTO;
import org.project.services.UserBooksProvider;

import java.util.List;

public class ShowMyBooksCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String userSub = (String) content.getSessionAttribute("role");
        String login = (String) content.getSessionAttribute("login");

        List<OrderedBookDTO> myOrderList = UserBooksProvider.getUserBooks(userSub, login);
        content.setRequestAttribute("userBookList", myOrderList);
        return new CommandResult("user_books.jsp", false);
    }
}
