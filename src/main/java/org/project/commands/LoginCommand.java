package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.project.DAO.UserDaoImpl;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.services.LogInChecker;

public class LoginCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (LogInChecker.doesMatch(login, password)) {
            User user = new UserDaoImpl().findUser(login);
            String userRole = user.getRole().getRoleValue();

            HttpSession session = request.getSession();
            session.setAttribute("role", userRole);
            page = "index.jsp";
        } else {
            page = "login.jsp";
        }
        return page;
    }
}