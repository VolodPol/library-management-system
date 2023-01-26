package org.project.commands;


import org.project.commands.get.ShowBooksCommand;
import org.project.commands.get.ShowMyBooksCommand;
import org.project.commands.get.ShowOrdersCommand;
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
    CONFIRM {
        {
            this.command = new ConfirmOrderCommand();
        }
    },
    MY_BOOKS {
        {
            this.command = new ShowMyBooksCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
