package org.project.services.pagination.impl;

import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.services.pagination.DataForPagination;
import org.project.services.pagination.Paginator;

import java.util.List;

public class UserPaginator extends Paginator<User> {
    private final UserDao userDao;

    public UserPaginator() {
        this.userDao = new UserDao();
    }

    private static class ParamData extends DataForPagination {
        private final Role role;

        public ParamData(String page, String role) {
            super(page);
            this.role = Role.valueOf(role.toUpperCase());
        }
        @Override
        protected int getPage() {
            return super.getPage();
        }
        private Role getRole() {
            return role;
        }

    }
    @Override
    public List<User> provideData(RequestContent content) throws DaoException {
        List<User> users;

        ParamData data = new ParamData(
                content.getParameter("page"),
                (String) content.getRequestAttribute("usersRole")
        );
        users = userDao.findAll((data.getPage() - 1) * 5, 5, data.getRole());

        numberOrRecords = userDao.getNumOfRecs();
        numberOfPages = calcNumOfPages();
        currentPage = data.getPage();

        content.removeRequestAttribute("usersRole");
        return users;
    }
}
