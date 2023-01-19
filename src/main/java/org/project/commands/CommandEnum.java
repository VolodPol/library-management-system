package org.project.commands;


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
