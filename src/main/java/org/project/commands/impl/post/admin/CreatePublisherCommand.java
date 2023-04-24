package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.PublisherDao;
import org.project.entity.impl.Publisher;
import org.project.exceptions.DaoException;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.impl.PublisherValidator;
import org.project.services.validation.dataset.impl.PublisherDataSet;
import org.project.utils.PathProvider;

import static org.project.services.resources.FilePath.*;

public class CreatePublisherCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        PublisherDataSet dataSet = DataSetProvider.getPublisherDataSet(content);

        PublisherDao publisherDao = new PublisherDao();
        Validator validator = new PublisherValidator(dataSet, publisherDao);
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
        }

        publisherDao.insert(new Publisher(dataSet.getPublisherName()));
        return new ActionResult(PathProvider.getPath(NEW_BOOK), true);
    }
}
