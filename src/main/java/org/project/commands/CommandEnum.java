package org.project.commands;


import org.project.commands.get.ShowBooksCommand;
import org.project.commands.get.ShowOrdersCommand;
import org.project.commands.post.ConfirmOrderCommand;
import org.project.commands.post.LoginCommand;
import org.project.commands.post.LogoutCommand;
import org.project.commands.post.OrderBookCommand;

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
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
