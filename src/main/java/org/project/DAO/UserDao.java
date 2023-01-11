package org.project.DAO;

import org.project.entity.User;
import java.util.List;

public interface UserDao {
    List<User> getAll();
    User findUser(String login);
    boolean updateUser(int id);
    boolean insertUser(User user);
    boolean deleteUser(int id);
}
