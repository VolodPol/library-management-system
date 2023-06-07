package com.library.commands.impl.get;

import com.library.entity.dto.CheckoutDTO;
import com.library.entity.Checkout;
import com.library.exceptions.DaoException;
import com.library.utils.PathProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.CheckoutDao;
import com.library.services.Mapper;

import static com.library.services.resources.FilePath.*;

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
