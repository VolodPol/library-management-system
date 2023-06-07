package com.library.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.dao.PublisherDao;
import com.library.entity.Book;
import com.library.entity.Publisher;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.services.resources.MessageName;
import com.library.services.validation.Validator;
import com.library.services.validation.dataset.DataSetProvider;
import com.library.services.validation.impl.CreateBookValidator;
import com.library.services.validation.dataset.impl.BookDataSet;
import com.library.utils.PathProvider;

import static com.library.services.resources.FilePath.*;

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
        Publisher currentPublisher = dao.findByName(data.getPublisher()).orElse(new Publisher());//
        Book currentBook = new Book(
                data.getTitle(),
                data.getAuthor(),
                data.getIsbn(),
                Integer.parseInt(data.getCopies()),
                Date.valueOf(data.getDateOfPublication()),
                currentPublisher
        );
        BookDao bookCreator = new BookDao();
        bookCreator.insert(currentBook);

        return new ActionResult(CommandPath.BOOKS, true);
    }
}
