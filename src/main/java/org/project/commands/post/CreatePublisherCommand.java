package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.PublisherDao;
import org.project.entity.Publisher;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

public class CreatePublisherCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String publisherName = content.getParameter("publication");

        PublisherDao publisherDao = new PublisherDao();
        if (publisherDao.isPresent(publisherName)) {
            content.setRequestAttribute("error", MessageName.PUBLISHER_EXISTS);
            return new CommandResult("new_publisher.jsp", false );
        }

        publisherDao.insertPublisher(new Publisher(publisherName));
        return new CommandResult(getPath(NEW_BOOK), true);
    }
}
