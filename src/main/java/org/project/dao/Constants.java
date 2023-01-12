package org.project.dao;

public interface Constants {
    String GET_ALL_USERS = "SELECT * FROM user";
    String CREATE_USER = "INSERT INTO `user` (login, `password`, email, first_name, surname, phonenumber, age, fined_status, `status`, role, subscription) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0, 'user', 'basic')";
    String DELETE_USER = "DELETE FROM `user` WHERE id = ?";
    String FIND_USER_BY_LOGIN = "SELECT * FROM `user` WHERE login = ?";

    //Book DAO
    String GET_ALL_BOOKS = "SELECT b.id, title, author, isbn, copies_number, date_of_publication, p.id, name FROM book b JOIN publisher p ON b.publisher_id = p.id";
}
