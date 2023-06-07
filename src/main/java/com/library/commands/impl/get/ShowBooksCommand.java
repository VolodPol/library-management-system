package com.library.commands.impl.get;

import com.library.entity.dto.BookDTO;
import com.library.entity.Book;
import com.library.exceptions.DaoException;
import com.library.utils.PathProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.services.Mapper;
import com.library.services.pagination.impl.BookPaginator;
import com.library.services.pagination.Paginator;

import static com.library.services.resources.FilePath.*;

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
