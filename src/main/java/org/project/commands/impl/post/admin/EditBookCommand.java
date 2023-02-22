package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.impl.EditBookValidator;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.utils.PathProvider;
import org.project.utils.UtilProvider;
import java.sql.Date;

import static org.project.services.UserBooksManager.setIsbn;
import static org.project.services.UserBooksManager.setPublisher;
import static org.project.services.resources.FilePath.EDIT_BOOK;

public class EditBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        BookDao bookDao = new BookDao();
        String formerIsbn = content.getParameter("formerIsbn");
        Book bookToEdit = bookDao.findByIsbn(formerIsbn);

        BookDataSet data = DataSetProvider.getBookDataSet(content);
        Validator validator = new EditBookValidator(data);
        boolean validResult = validator.validate();

        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return new ActionResult(PathProvider.getPath(EDIT_BOOK), false);
        }

        bookToEdit.setTitle(data.getTitle());
        bookToEdit.setAuthor(data.getAuthor());
        setIsbn(data.getIsbn(), formerIsbn, bookToEdit, bookDao);
        setPublisher(data.getPublisher(), bookToEdit);
        if (!UtilProvider.isEmpty(data.getCopies()))
            bookToEdit.setCopiesNumber(Integer.parseInt(data.getCopies()));
        if (!UtilProvider.isEmpty(data.getDateOfPublication()))
            bookToEdit.setDateOfPublication(Date.valueOf(data.getDateOfPublication()));

        bookDao.update(bookToEdit, formerIsbn);
        return new ActionResult(CommandPath.BOOKS, true);
    }
}
