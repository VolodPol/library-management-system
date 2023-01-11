package org.project.DAO;

public interface Constants {
    String GET_ALL_USERS = "SELECT * FROM user";
    String CREATE_USER = "INSERT INTO `user` (login, `password`, email, first_name, surname, phonenumber, age, fined_status, `status`, role_id, subscrtiption_id) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0, 2, 1);";
    String DELETE_USER = "DELETE FROM `user` WHERE id = ?";
    String FIND_USER_BY_LOGIN = "SELECT * FROM `user` WHERE login = ?";
}
