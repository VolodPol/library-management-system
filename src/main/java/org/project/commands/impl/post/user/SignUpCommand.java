package org.project.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.entity.impl.Role;
import org.project.exceptions.DaoException;
import org.project.services.UserProvider;
import org.project.services.resources.MessageName;
import org.project.utils.CaptchaVerifier;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

public class SignUpCommand implements ActionCommand {
    private final UserProvider provider = new UserProvider();
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        ActionResult registerResult = new ActionResult(getPath(REGISTER), false);

        String captchaResponse = content.getParameter("g-recaptcha-response");
        boolean valid = CaptchaVerifier.verify(captchaResponse);
        if (!valid) {
            content.setRequestAttribute("error", MessageName.CAPTCHA_ERROR);
            return registerResult;
        }

        boolean action = provider.createUser(content, Role.USER);
        if (action) {
            return new ActionResult(getPath(LOGIN), true);
        } else {
            return registerResult;
        }
    }
}
