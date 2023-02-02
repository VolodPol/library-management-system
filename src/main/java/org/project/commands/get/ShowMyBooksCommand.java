package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.entity.dto.OrderedBookDTO;
import org.project.services.FineChecker;
import org.project.services.UserBooksProvider;

import java.util.List;

public class ShowMyBooksCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String userSub = (String) content.getSessionAttribute("role");
        String login = (String) content.getSessionAttribute("login");

        List<OrderedBookDTO> myOrderList = UserBooksProvider.getUserBooks(userSub, login);
        List<String> messages = myOrderList.stream()
                .map(entry -> FineChecker.compareTime(entry.getReturnDate()))
                .toList();

        content.setRequestAttribute("userBookList", myOrderList);
        content.setRequestAttribute("messageList", messages);
        return new CommandResult("user_books.jsp", false);
    }
}
