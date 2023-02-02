package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Publisher;

import java.sql.Date;

public class EditBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        BookDao bookDao = new BookDao();
        String formerIsbn = content.getParameter("formerIsbn");
        Book bookToEdit = bookDao.findBook(formerIsbn);

        String isbn = content.getParameter("isbn");
        if (isNotEmpty(isbn) && (formerIsbn.equals(isbn) || !bookDao.isIsbnPresent(isbn))) {// Метод bookDao, що перевіряє чи існує isbn
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

    private boolean isNotEmpty(String object) {
        return object != null && !object.isEmpty();
    }

    private void setPublisher (String name, Book book) {
        PublisherDao publisherDao = new PublisherDao();
        if (!publisherDao.isPresent(name)) {   //якщо паблішера не існує
            publisherDao.insertPublisher(new Publisher(name)); //створюємо в бд нового
            Publisher publisher = publisherDao.findPublisher(name);    //видобуваємо його
            book.setPublisher(publisher); //присвоюємо книзі цього паблішера
        }
    }
}
