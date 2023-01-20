package org.project.commands.post;


import org.project.commands.ActionCommand;
import org.project.commands.SessionRequestContent;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent content) {
        String page = "login.jsp";

        content.removeSessionAttribute("role");
        content.removeSessionAttribute("name");
        content.removeSessionAttribute("subscription");

//        session.invalidate();

        return page;
    }
}
