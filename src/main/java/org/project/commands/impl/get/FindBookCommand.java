package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;
import org.project.utils.PathProvider;

import static org.project.services.resources.FilePath.*;

import java.util.List;

public class FindBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String criteria = content.getParameter("filter");
        String textInput = content.getParameter("text-input");

        BookDao finder = new BookDao();
        List<Book> books = finder.searchFor(criteria, textInput);
        List<BookDTO> dtoList = Mapper.booksToDTO(books);

        content.setRequestAttribute("bookList", dtoList);
        content.setRequestAttribute("recordsPerPage", 5);
        return new ActionResult(PathProvider.getPath(MAIN), false);
    }
}
