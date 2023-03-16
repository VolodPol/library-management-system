package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.dto.OrderedBookDTO;
import org.project.exceptions.DaoException;
import org.project.services.BookService;

import static org.project.services.resources.FilePath.*;
import org.project.utils.PathProvider;

import java.util.List;

public class ShowMyBooksCommand implements ActionCommand {
    private final BookService bookService;
    {
        bookService = new BookService(new CheckoutDao());
    }
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String userSub = (String) content.getSessionAttribute("role");
        String login = (String) content.getSessionAttribute("login");

        List<OrderedBookDTO> myOrderList = bookService.getUserBooks(userSub, login);

        content.setRequestAttribute("userBookList", myOrderList);
        return new ActionResult(PathProvider.getPath(USER_BOOKS), false);
    }
}
