package com.library.commands.impl.post.admin;

import com.library.services.BookService;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.dao.PublisherDao;
import com.library.entity.Book;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.services.validation.dataset.DataSetProvider;
import com.library.services.validation.impl.EditBookStrategy;
import com.library.services.validation.Validator;
import com.library.services.validation.dataset.impl.BookDataSet;
import com.library.utils.PathProvider;
import com.library.utils.UtilProvider;
import java.sql.Date;

import static com.library.services.resources.FilePath.EDIT_BOOK;

public class EditBookCommand implements ActionCommand {
    private final BookDao bookDao = new BookDao();
    private final BookService bookService;

    {
        bookService = new BookService(new PublisherDao(), bookDao);
    }
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String formerIsbn = content.getParameter("formerIsbn");
        Book bookToEdit = bookDao.findByIsbn(formerIsbn).orElse(new Book());

        BookDataSet data = DataSetProvider.getBookDataSet(content);
        Validator validator = new Validator(new EditBookStrategy(data));
        boolean validResult = validator.validate();

        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return new ActionResult(PathProvider.getPath(EDIT_BOOK), false);
        }

        bookToEdit.setTitle(data.getTitle());
        bookToEdit.setAuthor(data.getAuthor());
        bookService.setIsbn(data.getIsbn(), bookToEdit);
        bookService.setPublisher(data.getPublisher(), bookToEdit);
        if (!UtilProvider.isEmpty(data.getCopies()))
            bookToEdit.setCopiesNumber(Integer.parseInt(data.getCopies()));
        if (!UtilProvider.isEmpty(data.getDateOfPublication()))
            bookToEdit.setDateOfPublication(Date.valueOf(data.getDateOfPublication()));

        bookDao.update(bookToEdit, formerIsbn);
        return new ActionResult(CommandPath.BOOKS, true);
    }
}
