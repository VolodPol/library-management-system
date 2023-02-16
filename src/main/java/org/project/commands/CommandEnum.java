package org.project.commands;


import org.project.commands.get.*;
import org.project.commands.post.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SIGN_UP {
        {
            this.command = new SignUpCommand();
        }
    },
    BOOKS {
        {
            this.command = new ShowBooksCommand();
        }
    },
    ORDER {
        {
            this.command = new OrderBookCommand();
        }
    },
    SHOW_ORDERS {
        {
            this.command = new ShowOrdersCommand();
        }
    },
    FIND_BOOK {
        {
            this.command = new FindBookCommand();
        }
    },
    CONFIRM {
        {
            this.command = new ConfirmOrderCommand();
        }
    },
    MY_BOOKS {
        {
            this.command = new ShowMyBooksCommand();
        }
    },
    RETURN {
        {
            this.command = new ReturnBookCommand();
        }
    },
    CREATE_BOOK {
        {
            this.command = new CreateBookCommand();
        }
    },
    DELETE_BOOK {
        {
            this.command = new DeleteBookCommand();
        }
    },
    CREATE_PUBLISHER {
        {
            this.command = new CreatePublisherCommand();
        }
    },
    EDIT_BOOK {
        {
            this.command = new EditBookCommand();
        }
    },
    CREATE_LIBRARIAN {
        {
            this.command = new CreateLibrarianCommand();
        }
    },
    SHOW_LIBRARIANS {
        {
            this.command = new ShowLibrariansCommand();
        }
    },
    DELETE_LIBRARIAN {
        {
            this.command = new DeleteLibrarianCommand();
        }
    },
    SHOW_USERS {
        {
            this.command = new ShowUsersCommand();
        }
    },
    BLOCK_USER {
        {
            this.command = new BlockUserCommand();
        }
    },
    PROFILE {
        {
            this.command = new UserProfileCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
