package org.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionFactory;

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
        String page;
        ActionFactory client = new ActionFactory();


        ActionCommand command = client.defineCommand(req);
        page = command.execute(req);
        if (page != null) {
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            page = "index.jsp";
            req.getSession().setAttribute("nullPage", "Null page message");
            resp.sendRedirect(req.getContextPath() + page);
        }
//        System.out.println("here");
//        System.out.println(req.getParameter("command"));
    }
    //        SessionRequestContent content = new SessionRequestContent();
//        content.extractValues(req);

//        ActionCommand command = client.defineCommand(content);
}
