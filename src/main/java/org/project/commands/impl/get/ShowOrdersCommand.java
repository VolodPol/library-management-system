package org.project.commands.impl.get;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.dto.CheckoutDTO;
import org.project.exceptions.DaoException;
import org.project.services.Mapper;

import static org.project.services.resources.FilePath.*;
import org.project.utils.PathProvider;

import java.util.List;

public class ShowOrdersCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        List<CheckoutDTO> checkoutDTOs;
        CheckoutDao dao = new CheckoutDao();

        List<Checkout> checkouts = dao.findAll();
        checkoutDTOs = Mapper.checkoutsToDTO(checkouts);

        content.setRequestAttribute("orderList", checkoutDTOs);
        return new ActionResult(PathProvider.getPath(ORDER_LIST), false);
    }
}
