package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.SessionRequestContent;
import org.project.dao.CheckoutDao;

public class ConfirmOrderCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent content) {
        int id = (Integer) content.getSessionAttribute("orderId");

        CheckoutDao dao = new CheckoutDao();
        dao.confirmCheckout(id);
        content.removeSessionAttribute("orderId");


        return "front?command=show_orders";
    }
}
