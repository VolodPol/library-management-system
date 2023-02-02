package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.PublisherDao;
import org.project.entity.Publisher;

public class CreatePublisherCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String publisherName = content.getParameter("publication");

        PublisherDao publisherDao = new PublisherDao();
        if (publisherDao.isPresent(publisherName)) {
            content.setRequestAttribute("publicationPresenceError", "This publisher does already exist!");
            return new CommandResult("new_publisher.jsp", false );
        }

        publisherDao.insertPublisher(
                new Publisher(
                        publisherName
                )
        );
        return new CommandResult("new_book.jsp", true);
    }
}
