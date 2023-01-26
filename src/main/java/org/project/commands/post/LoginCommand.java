package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDaoImpl;
import org.project.entity.User;
import org.project.services.LogInChecker;
import org.project.services.PasswordEncryptor;

public class LoginCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String page;

        String login = content.getParameter("login");
        String password = content.getParameter("password");

        if (LogInChecker.doesMatch(login, PasswordEncryptor.encrypt(password))) {
            User user = new UserDaoImpl().findUser(login);
            String userRole = user.getRole().getRoleValue();

            content.setSessionAttribute("role", userRole);
            content.setSessionAttribute("name", user.getFirstName() + " " + user.getSurname());
            content.setSessionAttribute("login", user.getLogin());
            content.setSessionAttribute("subscription", user.getSubscription().getValue());

            page = "index.jsp";
        } else {
            page = "login.jsp";
        }
        return new CommandResult(page, true);
    }
}