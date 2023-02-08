package org.project.services;

import org.project.commands.SessionRequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.entity.dto.IUserDTO;
import org.project.exceptions.DaoException;

import java.util.List;
import java.util.stream.Stream;

public class UserProvider {
    public static boolean createUser(SessionRequestContent content, Role role) throws DaoException {
        String username = content.getParameter("login");
        String email = content.getParameter("email");
        String password = content.getParameter("password");
        String firstName = content.getParameter("firstname");
        String surname = content.getParameter("surname");
        String phone = content.getParameter("phone");
        int age = Integer.parseInt(content.getParameter("age"));

        if (LogInChecker.doesUserExist(username, email)) {
            return false;
        }
        User currentUser = new User(
                username,
                PasswordEncryptor.encrypt(password),
                email,
                firstName,
                surname,
                phone,
                age,
                (byte) 0,
                (byte) 0,
                role,
                Subscription.BASIC
        );
        UserDao userCreator = new UserDao();
        if (role.equals(Role.USER)) {
            userCreator.insertUser(currentUser);
        } else if (role.equals(Role.LIBRARIAN)) {
            userCreator.insertLibrarian(currentUser);
        }
        return true;
    }
    public static List<IUserDTO> filterUsers(List<User> users, Role role) {
        List<IUserDTO> resultList;
        Stream<User> userStream = users
                .stream()
                .filter(user -> user.getRole().equals(role));

        if (role.equals(Role.USER)) {
            resultList = userStream
                    .map(user -> (IUserDTO) Mapper.userToUserDTO(user))
                    .toList();
        } else {
            resultList = userStream
                    .map(user -> (IUserDTO) Mapper.userToLibrarianDTO(user))
                    .toList();
        }
        return resultList;
    }

}
