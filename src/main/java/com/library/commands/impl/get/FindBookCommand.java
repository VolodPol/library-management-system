package com.library.commands.impl.get;

import com.library.dao.BookDao;
import com.library.entity.dto.BookDTO;
import com.library.entity.Book;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.exceptions.DaoException;
import com.library.services.Mapper;
import com.library.services.resources.MessageName;
import com.library.utils.PathProvider;

import static com.library.services.resources.FilePath.*;

import java.util.List;

public class FindBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String criteria = content.getParameter("filter");
        String textInput = content.getParameter("text-input");

        BookDao finder = new BookDao();
        List<Book> books = finder.searchFor(criteria, textInput);
        if (books.isEmpty())
            content.setRequestAttribute("errorMessage", MessageName.NOT_FOUND);
        else {
            List<BookDTO> dtoList = Mapper.booksToDTO(books);
            content.setRequestAttribute("bookList", dtoList);
        }
        content.setRequestAttribute("recordsPerPage", 5);
        return new ActionResult(PathProvider.getPath(MAIN), false);
    }
}
