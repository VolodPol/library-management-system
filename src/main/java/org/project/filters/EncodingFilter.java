package org.project.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

/**
 * WebFilter Filter's descendant for assigning the encoding of the page
 */
@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name="encoding", value = "UTF-8"))
public class EncodingFilter implements Filter {
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        filterChain.doFilter(request, response);
    }
}
