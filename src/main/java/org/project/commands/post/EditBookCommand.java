package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.exceptions.DaoException;

import java.sql.Date;

import static org.project.services.UserBooksProvider.setPublisher;
import static org.project.utils.UtilProvider.isNotEmpty;

public class EditBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        BookDao bookDao = new BookDao();
        String formerIsbn = content.getParameter("formerIsbn");
        Book bookToEdit = bookDao.findBook(formerIsbn);


        String isbn = content.getParameter("isbn");
        boolean isPresent = bookDao.isIsbnPresent(isbn);
        if (isNotEmpty(isbn) && (formerIsbn.equals(isbn) || !isPresent)) {// Метод bookDao, що перевіряє чи існує isbn
            bookToEdit.setIsbn(isbn);
        }
        String title = content.getParameter("title");
        if (isNotEmpty(title)) {
            bookToEdit.setTitle(title);
        }
        String author = content.getParameter("author");
        if (isNotEmpty(title)) {
            bookToEdit.setAuthor(author);
        }
        String copiesParameter = content.getParameter("copies_number");
        if (isNotEmpty(copiesParameter)) {
            bookToEdit.setCopiesNumber(Integer.parseInt(copiesParameter));
        }
        String dateParameter = content.getParameter("date");
        if (isNotEmpty(dateParameter)) {
            bookToEdit.setDateOfPublication(Date.valueOf(dateParameter));
        }
        String publisherName = content.getParameter("publisher");   //отримуємо параметр ім'я
        if (isNotEmpty(publisherName)) {
            setPublisher(publisherName, bookToEdit);
        }
        bookDao.updateBook(bookToEdit, formerIsbn);
        return new CommandResult("front?command=books", true);
    }
}
