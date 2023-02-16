package org.project.commands.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.dto.CheckoutDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

import java.util.ArrayList;
import java.util.List;

public class ShowOrdersCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        List<CheckoutDTO> checkoutDTOs = new ArrayList<>();
        CheckoutDao dao = new CheckoutDao();

        List<Checkout> checkouts = dao.getAllCheckouts();

        for (Checkout element : checkouts) {
            checkoutDTOs.add(Mapper.checkoutToDTO(element));
        }

        content.setRequestAttribute("orderList", checkoutDTOs);
        return new CommandResult(getPath(ORDER_LIST), false);
    }
}
