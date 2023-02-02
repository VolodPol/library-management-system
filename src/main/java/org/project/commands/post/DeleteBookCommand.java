package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;

public class DeleteBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String isbn = content.getParameter("isbn");
        BookDao dao = new BookDao();
        dao.deleteBook(isbn);
        return new CommandResult("front?command=books", true);
    }
}
