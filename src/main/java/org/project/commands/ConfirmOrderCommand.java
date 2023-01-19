package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.project.dao.CheckoutDao;

public class ConfirmOrderCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("orderId");

        CheckoutDao dao = new CheckoutDao();
        dao.confirmCheckout(id);
        session.removeAttribute("orderId");

        request.getAttribute("");
        request.getSession().getAttribute("");
        request.getParameter("");
        request.getParameterMap();
        request.getParameterValues("");

        return "front?command=show_orders";
    }
}
