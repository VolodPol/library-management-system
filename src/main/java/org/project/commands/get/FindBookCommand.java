package org.project.commands.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

import java.util.List;

public class FindBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String criteria = content.getParameter("filter");
        String textInput = content.getParameter("text-input");
        BookDao finder = new BookDao();
        List<Book> books;
        books = finder.searchForBook(criteria, textInput);

        List<BookDTO> dtoList = books.stream()
                .map(Mapper::bookToDTO)
                .toList();


        content.setRequestAttribute("bookList", dtoList);
        return new CommandResult(getPath(MAIN), false);
    }
}
