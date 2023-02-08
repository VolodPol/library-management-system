package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    private Sorting sorting = Sorting.ASC;
    private OrderType type = OrderType.DEFAULT;

    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        int page = 1;
        final int recsPerPage = 5;

        String pageParameter = content.getParameter("page");
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
        }

        String orderByParam = content.getParameter("orderBy");
        if (orderByParam != null) {
            type = OrderType.valueOf(orderByParam.toUpperCase());
        }

        String typeParam = content.getParameter("orderType");
        if (typeParam != null) {
            sorting = Sorting.valueOf(typeParam.toUpperCase());
        }

        List<BookDTO> bookDTOs;
        BookDao dao = new BookDao();
        List<Book> books = dao.getAll((page - 1) * recsPerPage, recsPerPage, sorting, type);


        int numOfRecs = dao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        bookDTOs = books.stream().map(Mapper::bookToDTO).toList();

        content.setRequestAttribute("bookList", bookDTOs);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new CommandResult("main.jsp", false);
    }
}
