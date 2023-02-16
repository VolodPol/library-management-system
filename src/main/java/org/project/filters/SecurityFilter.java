package org.project.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.project.entity.Role;

import java.io.IOException;

import static org.project.utils.UtilProvider.isAccessDenied;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private Role userRole;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userRole = Role.USER;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
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
}
