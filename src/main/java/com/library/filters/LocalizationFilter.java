package com.library.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * WebFilter for localization purposes which coordinates the locale value from the session
 */
@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name="locale", value = "en_US"))
public class LocalizationFilter implements Filter {
    private String locale;

    @Override
    public void init(FilterConfig filterConfig) {
        locale = filterConfig.getInitParameter("locale");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        locale = request.getParameter("locale");
        if (locale != null)
            request.getSession().setAttribute("locale", locale);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
