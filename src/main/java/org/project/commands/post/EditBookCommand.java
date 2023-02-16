package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;
import org.project.utils.UtilProvider;

import java.sql.Date;

import static org.project.services.UserBooksProvider.setPublisher;
import static org.project.services.validation.BookValidator.*;

public class EditBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        BookDao bookDao = new BookDao();
        String formerIsbn = content.getParameter("formerIsbn");
        Book bookToEdit = bookDao.findBook(formerIsbn);

        String isbn = content.getParameter("isbn");
        String title = content.getParameter("title");
        String author = content.getParameter("author");
        String copiesParameter = content.getParameter("copies_number");
        String dateParameter = content.getParameter("date");
        String publisherName = content.getParameter("publisher");   //отримуємо параметр ім'я

        boolean validResult = true;
        if (!UtilProvider.isEmpty(title)) {
            validResult = validateTitle(title);
            System.out.println(validResult);
        }
        if (!UtilProvider.isEmpty(author)) {
            validResult = validateAuthor(author);
            System.out.println(validResult);
        }
        if (!UtilProvider.isEmpty(isbn)) {
            validResult = validateIsbn(isbn);
            System.out.println(validResult);
        }
        if (!UtilProvider.isEmpty(copiesParameter)) {
            validResult = validateCopiesNum(copiesParameter);
            System.out.println(validResult);
        }
        if (!UtilProvider.isEmpty(publisherName)) {
            validResult = validatePublisher(publisherName);
            System.out.println(validResult);
        }

        if (!validResult) {
            content.setRequestAttribute("error", MessageName.INCORRECT_FORM);
            System.out.println(MessageName.INCORRECT_FORM);
            return new CommandResult("edit_book.jsp", false);
        }

        boolean isPresent = bookDao.isIsbnPresent(isbn);
        if (formerIsbn.equals(isbn) || !isPresent) {// Метод bookDao, що перевіряє чи існує isbn
            if (!UtilProvider.isEmpty(isbn))
                bookToEdit.setIsbn(isbn);
        }
        if (!UtilProvider.isEmpty(title))
            bookToEdit.setTitle(title);
        if (!UtilProvider.isEmpty(author))
            bookToEdit.setAuthor(author);
        if (!UtilProvider.isEmpty(copiesParameter))
            bookToEdit.setCopiesNumber(Integer.parseInt(copiesParameter));
        if (!UtilProvider.isEmpty(dateParameter))
            bookToEdit.setDateOfPublication(Date.valueOf(dateParameter));
        if (!UtilProvider.isEmpty(publisherName))
            setPublisher(publisherName, bookToEdit);

        bookDao.updateBook(bookToEdit, formerIsbn);
        return new CommandResult("front?command=books", true);
    }
}
