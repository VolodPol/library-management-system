package com.library.commands;


import com.library.commands.impl.get.*;
import com.library.commands.impl.post.LoginCommand;
import com.library.commands.impl.post.LogoutCommand;
import com.library.commands.impl.post.admin.*;
import com.library.commands.impl.post.librarian.ConfirmOrderCommand;
import com.library.commands.impl.post.user.OrderBookCommand;
import com.library.commands.impl.post.user.ReturnBookCommand;
import com.library.commands.impl.post.user.SignUpCommand;
import com.library.commands.impl.post.user.UpgradeSubscriptionCommand;
import com.library.commands.impl.get.ShowLibrariansCommand;
import com.library.commands.impl.get.ShowOrdersCommand;
import com.library.commands.impl.get.ShowMyBooksCommand;
import lombok.Getter;

/**
 * The enumeration that contains all the Commands
 * Command pattern
 */
@Getter
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
    PROFILE (new UserProfileCommand()),
    UPGRADE (new UpgradeSubscriptionCommand());

    private final ActionCommand command;
    CommandEnum(ActionCommand command) {
        this.command = command;
    }
}