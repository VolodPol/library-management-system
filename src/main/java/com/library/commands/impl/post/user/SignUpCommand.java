package com.library.commands.impl.post.user;

import com.library.services.UserProvider;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.entity.Role;
import com.library.exceptions.DaoException;
import com.library.services.resources.MessageName;
import com.library.utils.CaptchaVerifier;

import static com.library.services.resources.FilePath.*;
import static com.library.utils.PathProvider.getPath;

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
