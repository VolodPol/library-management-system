package com.library.commands.impl.get;

import com.library.entity.dto.OrderedBookDTO;
import com.library.exceptions.DaoException;
import com.library.services.BookService;
import com.library.utils.PathProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.CheckoutDao;

import static com.library.services.resources.FilePath.*;

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
