package org.project.dao;

public interface Constants {
    String GET_ALL_USERS = "SELECT * FROM user";
    String CREATE_USER = "INSERT INTO `user` (login, `password`, email, first_name, surname, phonenumber, age, fined_status, `status`, role, subscription) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0, 'user', 'basic')";
    String DELETE_USER = "DELETE FROM `user` WHERE id = ?";
    String FIND_USER_BY_LOGIN = "SELECT * FROM `user` WHERE login = ?";

    //Book DAO
    String GET_ALL_BOOKS = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id";
    String FIND_BOOK_BY_ISBN = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id WHERE b.isbn = ?";
    String FIND_BOOK_BY_ID = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id WHERE b.id = ?";
    String DECREASE_BY_ID = "UPDATE book SET copies_number = copies_number - 1 WHERE id = ? and copies_number > 0";


    //Order DAO
    String GET_ALL_CHECKOUTS = "SELECT c.id, start_time, end_time, is_returned, order_status, u.id, u.login, b.id, b.isbn FROM checkout c JOIN user u JOIN book b ON c.user_id = u.id AND c.book_id = b.id";
    String GET_CHECKOUTS_BY_LOGIN = "SELECT c.id, start_time, end_time, is_returned, order_status, u.id, u.login, b.id, b.isbn FROM checkout c JOIN user u JOIN book b ON c.user_id = u.id AND c.book_id = b.id WHERE u.login = ?";
    String CREATE_CHECKOUT = "INSERT INTO checkout (start_time, end_time, is_returned, order_status, user_id, book_id) VALUES (?, ?, 1, 0, ?, ?)";
    String UPDATE_CHECKOUT_BY_ID = "UPDATE checkout SET is_returned = 0, order_status = 1 WHERE id = ?";
}
