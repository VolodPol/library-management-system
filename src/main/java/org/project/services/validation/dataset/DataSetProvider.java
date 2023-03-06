package org.project.services.validation.dataset;

import org.project.commands.RequestContent;
import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.services.validation.dataset.impl.PublisherDataSet;
import org.project.services.validation.dataset.impl.UserDataSet;

public class DataSetProvider {
    public static BookDataSet getBookDataSet(RequestContent content) {
        String title = content.getParameter("title");
        String author = content.getParameter("author");
        String isbn = content.getParameter("isbn");
        String copies = content.getParameter("copies_number");
        String dateOfPublication = content.getParameter("date");
        String publisherName = content.getParameter("publisher");

        return new BookDataSet(title, author, isbn, copies, dateOfPublication, publisherName);
    }

    public static UserDataSet getUserDataSet(RequestContent content) {
        String username = content.getParameter("login");
        String email = content.getParameter("email");
        String password = content.getParameter("password");
        String firstName = content.getParameter("firstname");
        String surname = content.getParameter("surname");
        String phone = content.getParameter("phone");
        String age = content.getParameter("age");

        System.out.println(username);
        System.out.println(email);
        System.out.println(password);
        System.out.println(firstName);
        System.out.println(surname);
        System.out.println(phone);
        System.out.println(age);
        System.out.println(" inside getUserDataSet method ");
        return new UserDataSet(username, email, password, firstName, surname, phone, age);
    }

    public static PublisherDataSet getPublisherDataSet(RequestContent content) {
        return new PublisherDataSet(content.getParameter("publication"));
    }
}
