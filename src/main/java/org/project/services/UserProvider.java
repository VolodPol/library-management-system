package org.project.services;

import org.project.commands.SessionRequestContent;
import org.project.dao.UserDaoImpl;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.entity.dto.LibrarianDTO;

import java.util.List;

public class UserProvider {
    public static boolean createUser(SessionRequestContent content, Role role) {
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
        UserDaoImpl userCreator = new UserDaoImpl();
        if (role.equals(Role.USER)) {
            userCreator.insertUser(currentUser);
        } else if (role.equals(Role.LIBRARIAN)) {
            userCreator.insertLibrarian(currentUser);
        }
        return true;
    }

    public static List<LibrarianDTO> filterLibrarians(List<User> users) {
        return users.stream()
                .filter(user -> user.getRole().equals(Role.LIBRARIAN))
                .map(Mapper::userToLibrarianDTO)
                .toList();
    }
}