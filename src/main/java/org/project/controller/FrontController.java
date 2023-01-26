package org.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionFactory;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;

import java.io.IOException;

@WebServlet("/front")
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionFactory client = new ActionFactory();

        ActionCommand command = client.defineCommand(req);
        SessionRequestContent content = new SessionRequestContent(req);
        CommandResult result = command.execute(content);
        content.insertAttributes(req);

        String page = result.getDestinationPage();
        if (page != null) {
            if (page.equals("login.jsp")) req.getSession().invalidate();//вихід користувача з системи

            if (result.isSendRedirect()) {
                resp.sendRedirect(page);
            } else {
                req.getRequestDispatcher(page).forward(req, resp);
            }
        } else {
            page = "index.jsp";
            req.getSession().setAttribute("nullPage", "Null page message");
            resp.sendRedirect(req.getContextPath() + page);
        }
    }
}
