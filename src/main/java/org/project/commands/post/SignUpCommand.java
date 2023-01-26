package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDaoImpl;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.services.LogInChecker;
import org.project.services.PasswordEncryptor;

public class SignUpCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String username = content.getParameter("login");
        String email = content.getParameter("email");
        String password = content.getParameter("password");
        String firstName = content.getParameter("firstname");
        String surname = content.getParameter("surname");
        String phone = content.getParameter("phone");
        int age = Integer.parseInt(content.getParameter("age"));

        System.out.println(age);

        if (LogInChecker.doesUserExist(username, email)) {
            System.out.println("here");
            return new CommandResult("register.jsp", true);
        }
        String encodedPassword = PasswordEncryptor.encrypt(password);
        User currentUser = new User(
                username,
                encodedPassword,
                email,
                firstName,
                surname,
                phone,
                age,
                (byte) 0,
                (byte) 0,
                Role.USER,
                Subscription.BASIC
        );
        UserDaoImpl userCreator = new UserDaoImpl();
        userCreator.insertUser(currentUser);
        System.out.println(currentUser);
        return new CommandResult("login.jsp", true);
    }
    /*
    1. Отримати параметри (credentials) з форми
    2. Перевірити чи в бд немає такого логіну
        Є -> сторінка помилки
    3. Перевірити чи в бд немає такої пошти
        Є -> сторінка помилки
    4. Створити користувача з даних і використати шифрування паролю
     */
}
