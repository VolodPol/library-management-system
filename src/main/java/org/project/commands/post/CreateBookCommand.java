package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Publisher;
import org.project.exceptions.DaoException;

import java.sql.Date;

public class CreateBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        String title = content.getParameter("title");
        String author = content.getParameter("author");
        String isbn = content.getParameter("isbn");
        int copies = Integer.parseInt(content.getParameter("copies_number"));
        Date dateOfPublication = Date.valueOf(content.getParameter("date"));

        String publisherName = content.getParameter("publisher");
        PublisherDao dao = new PublisherDao();
        if (!dao.isPresent(publisherName)) {
            content.setRequestAttribute("publicationError", "No such publisher");
            return new CommandResult("new_book.jsp", false);
        }
        Publisher currentPublisher = dao.findPublisher(publisherName);
        Book currentBook = new Book(
                title,
                author,
                isbn,
                copies,
                dateOfPublication,
                currentPublisher
        );
        BookDao bookCreator = new BookDao();
        bookCreator.insertBook(currentBook);

        return new CommandResult("front?command=books", true);
    }
}
