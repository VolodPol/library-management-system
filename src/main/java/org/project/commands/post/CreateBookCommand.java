package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Publisher;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;

import static org.project.services.validation.BookValidator.*;

import java.sql.Date;

public class CreateBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String title = content.getParameter("title");
        String author = content.getParameter("author");
        String isbn = content.getParameter("isbn");
        String copies = content.getParameter("copies_number");
        String dateOfPublication = content.getParameter("date");

        boolean validResult = true;
        validResult = validateTitle(title);
        validResult = validateAuthor(author);
        validResult = validateIsbn(isbn);
        validResult = validateCopiesNum(copies);
        validResult = validateDate(dateOfPublication);

        if (!validResult) {
            content.setRequestAttribute("error", MessageName.INCORRECT_FORM);
            System.out.println("not valid");
            return new CommandResult("new_book.jsp", false);
        }

        String publisherName = content.getParameter("publisher");
        PublisherDao dao = new PublisherDao();
        if (!dao.isPresent(publisherName)) {
            content.setRequestAttribute("error", MessageName.NO_PUBLISHER);
            return new CommandResult("new_book.jsp", false);
        }
        Publisher currentPublisher = dao.findPublisher(publisherName);
        assert copies != null;
        assert dateOfPublication != null;
        Book currentBook = new Book(
                title,
                author,
                isbn,
                Integer.parseInt(copies),//was asserted ...!= null
                Date.valueOf(dateOfPublication),//same
                currentPublisher
        );
        BookDao bookCreator = new BookDao();
        bookCreator.insertBook(currentBook);

        return new CommandResult("front?command=books", true);
    }
}
