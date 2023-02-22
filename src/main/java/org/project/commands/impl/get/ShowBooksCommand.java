package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;
import org.project.utils.PathProvider;

import static org.project.services.resources.FilePath.*;

import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    private Sorting sorting = Sorting.ASC;
    private OrderType type = OrderType.DEFAULT;

    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int page = 1;
        final int recsPerPage = 5;
        // extract parameters
        String pageParameter = content.getParameter("page");
        if (pageParameter != null) page = Integer.parseInt(pageParameter);

        String orderByParam = content.getParameter("orderBy");
        if (orderByParam != null) type = OrderType.valueOf(orderByParam.toUpperCase());

        String typeParam = content.getParameter("orderType");
//        System.out.println(pageParameter);
//        System.out.println(orderByParam);
//        System.out.println(typeParam);
        if (typeParam != null) sorting = Sorting.valueOf(typeParam.toUpperCase());

        //get list of books by calling dao method with offset, total, limit and mapping

        BookDao dao = new BookDao();
        List<Book> books = dao.findAll((page - 1) * recsPerPage, recsPerPage, sorting, type);
        int numOfRecs = dao.getNumOfRecs();
        int numOfPages = (int) Math.ceil((double) numOfRecs / recsPerPage);
        List<BookDTO> bookDTOs = Mapper.booksToDTO(books);

//        Pagination paginator = new Pagination(pageParameter, orderByParam, typeParam);
//        List<BookDTO> bookDTOs = Mapper.booksToDTO(paginator.provideData());

        //setting attributes
        content.setRequestAttribute("bookList", bookDTOs);
        content.setRequestAttribute("currentPage", page);
        content.setRequestAttribute("numOfPages", numOfPages);

        return new ActionResult(PathProvider.getPath(MAIN), false);
    }
}
