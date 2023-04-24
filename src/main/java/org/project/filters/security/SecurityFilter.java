package org.project.filters.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.project.entity.impl.Role;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * WebFilter class to separate user access rights
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private Role userRole;
    @Override
    public void init(FilterConfig filterConfig) {
        userRole = Role.USER;
    }

    /**
     * The method determines whether a user's visit to some page is authorized by its role. By the help of
     * isAccessDenied method.
     * @param servletRequest ServletRequest object
     * @param servletResponse ServletResponse object
     * @param filterChain FilterChain instance
     * @throws IOException may occur by forwarding the request or doFilter method on filter chain
     * @throws ServletException may occur by forwarding the request or doFilter method on filter chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String role = (String) request.getSession().getAttribute("role");
        if (role != null)
            userRole = Role.valueOf(role.toUpperCase());

        String action = request.getParameter("command");
        String path = action;
        if (action == null)
            path = request.getServletPath();

        if ((userRole.equals(Role.UNREGISTERED) && isAccessDenied(path, PageNavigation.UNREGISTERED)
                || userRole.equals(Role.USER) && isAccessDenied(path, PageNavigation.USER)
                || userRole.equals(Role.LIBRARIAN) && isAccessDenied(path, PageNavigation.LIBRARIAN)
                || userRole.equals(Role.ADMIN) && isAccessDenied(path, PageNavigation.ADMIN))) {
            request.setAttribute("errorMessage", "Your account hasn't enough rights to access that resource!");
            request.getRequestDispatcher("login.jsp").forward(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * The method checks if the array of resources corresponding to the role contains the provided path. If the path
     * is not authorized, then the method returns true, otherwise false.
     * @param path the provided resource path
     * @param role the role of the user
     * @return boolean to display the result
     */
    public boolean isAccessDenied(String path, PageNavigation role) {
        boolean result = false;
        Stream<String> routes = Arrays.stream(role.getRoutes()).
                filter(elem -> elem.equals(path));
        if (routes.findAny().isPresent())
            result = true;
        return !result;
    }
}
