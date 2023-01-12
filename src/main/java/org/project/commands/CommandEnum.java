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
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
