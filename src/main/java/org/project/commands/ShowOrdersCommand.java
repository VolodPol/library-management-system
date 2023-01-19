package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.dto.CheckoutDTO;
import org.project.entity.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ShowOrdersCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        List<CheckoutDTO> checkoutDTOs = new ArrayList<>();
        CheckoutDao dao = new CheckoutDao();

        List<Checkout> checkouts = dao.getAllCheckouts();
        for (Checkout element : checkouts) {
            checkoutDTOs.add(Mapper.checkoutToDTO(element));
        }

        request.setAttribute("orderList", checkoutDTOs);
        return "order_list.jsp";
    }
}
