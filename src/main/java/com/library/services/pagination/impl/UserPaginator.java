package com.library.services.pagination.impl;

import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import com.library.services.pagination.DataForPagination;
import com.library.services.pagination.Paginator;

import java.util.List;

/**
 * Class descendant of Paginator class with the generic of User class
 */
public class UserPaginator extends Paginator<User> {
    private final UserDao userDao;

    public UserPaginator() {
        this.userDao = new UserDao();
    }
    public UserPaginator(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Static successor of DataForPagination class to store pagination data
     */
    private static class ParamData extends DataForPagination {
        /**
         * User's role
         */
        private final Role role;

        /**
         * Constructor for initialization of role field
         * @param page current page
         * @param role user's role
         */
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
        /**
         * Records per page by default
         * @return int number of records
         */
        private int getRecsPerPage(){
            return 5;
        }
    }

    /**
     * Implementation of provideData() method {@link Paginator#provideData(RequestContent)}
     * which build paramData from RequestContent, assign pagination fields and extracts records
     * from DB.
     * @param content wrapper of HttpRequest's and HttpSession's content
     * @return list of User entities
     * @throws DaoException which may occur in dao
     */
    @Override
    public List<User> provideData(RequestContent content) throws DaoException {
        List<User> users;

        ParamData data = new ParamData(
                content.getParameter("page"),
                (String) content.getRequestAttribute("usersRole")
        );
        super.recordsPerPage = data.getRecsPerPage();
        users = userDao.findAll(
                (data.getPage() - 1) * data.getRecsPerPage(), data.getRecsPerPage(), data.getRole()
        );
        super.numberOrRecords = userDao.getNumOfRecs();
        super.numberOfPages = calcNumOfPages();
        super.currentPage = data.getPage();

        content.removeRequestAttribute("usersRole");
        return users;
    }
}
