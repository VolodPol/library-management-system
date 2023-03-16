package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.exceptions.DaoException;
import org.project.services.BookService;
import org.project.services.resources.CommandPath;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.impl.EditBookValidator;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.utils.PathProvider;
import org.project.utils.UtilProvider;
import java.sql.Date;

import static org.project.services.resources.FilePath.EDIT_BOOK;

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
        Validator validator = new EditBookValidator(data);
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
