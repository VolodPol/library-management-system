package org.project.dao;

public interface Constants {
    String GET_ALL_USERS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM user WHERE role = ? LIMIT ?, ?";
    String CREATE_USER = "INSERT INTO `user` (login, `password`, email, first_name, surname, phonenumber, age, fine_amount, `status`, role, subscription) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0, ?, 'basic')";
    String DELETE_USER = "DELETE FROM `user` WHERE id = ?";
    String FIND_USER_BY_LOGIN = "SELECT * FROM `user` WHERE login = ?";
    String BLOCK_USER = "UPDATE user SET status = ? WHERE id = ?";
    String FINE_AMOUNT = "UPDATE user SET fine_amount = ? WHERE id = ?";


    //Book DAO
    String GET_ALL_BOOKS = "SELECT SQL_CALC_FOUND_ROWS b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id";
    String SEARCH_FOR_BOOK_BY_AUTHOR = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id WHERE author LIKE ?";
    String SEARCH_FOR_BOOK_BY_TITLE = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id WHERE title LIKE ?";
    String FIND_BOOK_BY_ISBN = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id WHERE b.isbn = ?";
    String CREATE_BOOK = "INSERT INTO book (title, author, isbn, copies_number, date_of_publication, publisher_id) VALUES (?, ?, ?, ?, ?, ?)";
    String DELETE_BOOK = "DELETE FROM book WHERE isbn = ?";
    String UPDATE_BOOK = "UPDATE book SET title = ?, author = ?, isbn = ?, copies_number = ?, date_of_publication = ?, publisher_id = ? WHERE isbn = ?";
    String ISBN_PRESENT = "SELECT * FROM book WHERE isbn = ?";


    //Order DAO
    String GET_ALL_CHECKOUTS = "SELECT c.id, start_time, end_time, is_returned, order_status, c.type, fined_status, u.id, u.login, b.id, b.isbn FROM checkout c JOIN user u JOIN book b ON c.user_id = u.id AND c.book_id = b.id";
    String GET_CHECKOUTS_BY_LOGIN = "SELECT c.id, start_time, end_time, is_returned, order_status, c.type, fined_status, u.id, u.login, b.id, b.isbn FROM checkout c JOIN user u JOIN book b ON c.user_id = u.id AND c.book_id = b.id WHERE order_status = 1 AND u.login = ?";
    String CREATE_CHECKOUT = "INSERT INTO checkout (start_time, end_time, is_returned, order_status, `type`, fined_status, user_id, book_id) VALUES (?, ?, 1, 0, ?, ?, ?, ?)";
    String UPDATE_CHECKOUT_BY_ID = "UPDATE checkout SET is_returned = 0, order_status = 1 WHERE id = ?";
    String DELETE_CHECKOUT = "DELETE FROM checkout WHERE book_id = ? AND start_time = ? AND end_time = ?";
    String CHECK_FINE_BY_ID = "UPDATE checkout SET fined_status = ? WHERE id = ?";
}
