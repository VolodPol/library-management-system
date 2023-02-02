package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.services.Mapper;

import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        int page = 1;
        final int recsPerPage = 5;

        String pageParameter = content.getParameter("page");
        if (pageParameter != null)
            page = Integer.parseInt(pageParameter);

        List<BookDTO> bookDTOs;
        BookDao dao = new BookDao();

        List<Book> books = dao.getAllBooks((page - 1) * recsPerPage, recsPerPage);
        int numOfRecs = dao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        bookDTOs = books.stream().map(Mapper::bookToDTO).toList();

        content.setRequestAttribute("bookList", bookDTOs);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new CommandResult("main.jsp", false);
    }
}