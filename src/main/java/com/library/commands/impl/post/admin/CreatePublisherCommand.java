package com.library.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.PublisherDao;
import com.library.entity.Publisher;
import com.library.exceptions.DaoException;
import com.library.services.validation.Validator;
import com.library.services.validation.dataset.DataSetProvider;
import com.library.services.validation.impl.PublisherStrategy;
import com.library.services.validation.dataset.impl.PublisherDataSet;
import com.library.utils.PathProvider;

import static com.library.services.resources.FilePath.*;

public class CreatePublisherCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        PublisherDataSet dataSet = DataSetProvider.getPublisherDataSet(content);

        PublisherDao publisherDao = new PublisherDao();
        Validator validator = new Validator(new PublisherStrategy(dataSet, publisherDao));
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
        }

        publisherDao.insert(new Publisher(dataSet.getPublisherName()));
        return new ActionResult(PathProvider.getPath(NEW_BOOK), true);
    }
}
