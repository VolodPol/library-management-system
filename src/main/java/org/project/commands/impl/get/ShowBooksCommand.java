package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.impl.Book;
import org.project.entity.dto.BookDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;
import org.project.services.pagination.impl.BookPaginator;
import org.project.services.pagination.Paginator;
import org.project.utils.PathProvider;

import static org.project.services.resources.FilePath.*;

import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        Paginator<Book> paginator = new BookPaginator();
        List<Book> books = paginator.provideData(content);
        List<BookDTO> bookDTOs = Mapper.booksToDTO(books);

        content.setRequestAttribute("bookList", bookDTOs);
        content.setRequestAttribute("currentPage", paginator.getCurrentPage());
        content.setRequestAttribute("numOfPages", paginator.getNumberOfPages());
        content.setRequestAttribute("recordsPerPage", paginator.getRecordsPerPage());

        return new ActionResult(PathProvider.getPath(MAIN), false);
    }
}
