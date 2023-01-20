package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.SessionRequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.dto.CheckoutDTO;
import org.project.entity.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ShowOrdersCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent content) {
        List<CheckoutDTO> checkoutDTOs = new ArrayList<>();
        CheckoutDao dao = new CheckoutDao();

        List<Checkout> checkouts = dao.getAllCheckouts();
        for (Checkout element : checkouts) {
            checkoutDTOs.add(Mapper.checkoutToDTO(element));
        }

        content.setRequestAttribute("orderList", checkoutDTOs);
        return "order_list.jsp";
    }
}
