package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Publisher;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;
import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.impl.CreateBookValidator;
import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.utils.PathProvider;

import static org.project.services.resources.FilePath.*;

import java.sql.Date;

public class CreateBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        BookDataSet data = DataSetProvider.getBookDataSet(content);

        Validator validator = new CreateBookValidator(data);
        boolean validResult = validator.validate();

        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return new ActionResult(PathProvider.getPath(NEW_BOOK), false);
        }

        PublisherDao dao = new PublisherDao();
        if (!dao.isPresent(data.getPublisher())) {
            content.setRequestAttribute("error", MessageName.NO_PUBLISHER);
            return new ActionResult(PathProvider.getPath(NEW_BOOK), false);
        }
        Publisher currentPublisher = dao.findByName(data.getPublisher());
        Book currentBook = new Book(
                data.getTitle(),
                data.getAuthor(),
                data.getIsbn(),
                Integer.parseInt(data.getCopies()),//was asserted ...!= null
                Date.valueOf(data.getDateOfPublication()),//same
                currentPublisher
        );
        BookDao bookCreator = new BookDao();
        bookCreator.insert(currentBook);

        return new ActionResult(CommandPath.BOOKS, true);
    }
}
