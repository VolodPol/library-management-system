package org.project.commands;


import org.project.commands.impl.get.*;
import org.project.commands.impl.get.ShowLibrariansCommand;
import org.project.commands.impl.get.ShowOrdersCommand;
import org.project.commands.impl.get.ShowMyBooksCommand;
import org.project.commands.impl.post.*;
import org.project.commands.impl.post.admin.*;
import org.project.commands.impl.post.librarian.ConfirmOrderCommand;
import org.project.commands.impl.post.user.OrderBookCommand;
import org.project.commands.impl.post.user.ReturnBookCommand;
import org.project.commands.impl.post.user.SignUpCommand;

public enum CommandEnum {
    LOGIN (new LoginCommand()),
    LOGOUT (new LogoutCommand()),
    REGISTER (new SignUpCommand()),
    BOOKS (new ShowBooksCommand()),
    ORDER (new OrderBookCommand()),
    SHOW_ORDERS (new ShowOrdersCommand()),
    FIND_BOOK (new FindBookCommand()),
    CONFIRM (new ConfirmOrderCommand()),
    MY_BOOKS (new ShowMyBooksCommand()),
    RETURN (new ReturnBookCommand()),
    CREATE_BOOK (new CreateBookCommand()),
    DELETE_BOOK (new DeleteBookCommand()),
    CREATE_PUBLISHER (new CreatePublisherCommand()),
    EDIT_BOOK (new EditBookCommand()),
    CREATE_LIBRARIAN (new CreateLibrarianCommand()),
    SHOW_LIBRARIANS (new ShowLibrariansCommand()),
    DELETE_LIBRARIAN (new DeleteLibrarianCommand()),
    SHOW_USERS (new ShowUsersCommand()),
    BLOCK_USER (new BlockUserCommand()),
    PROFILE (new UserProfileCommand());

    private final ActionCommand command;
    CommandEnum(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
