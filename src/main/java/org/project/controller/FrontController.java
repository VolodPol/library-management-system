package org.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.factory.ActionFactory;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/front")
public class FrontController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(FrontController.class);
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
        RequestContent content = new RequestContent(req);
        ActionResult result;
        try {
            result = command.execute(content, resp);
        } catch (DaoException e) {
            log.error("Dao exception caught in controller");
            e.printStackTrace();
            return;// Here forward to error page
        }
        content.insertAttributes(req);
        String page = result.getDestinationPage();

        if (page.equals("login.jsp")) req.getSession().invalidate();

        if (result.isSendRedirect())
            resp.sendRedirect(page);
        else
            req.getRequestDispatcher(page).forward(req, resp);
    }
}
