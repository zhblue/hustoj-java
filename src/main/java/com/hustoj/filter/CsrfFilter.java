package com.hustoj.filter;

import com.hustoj.db.DB;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CsrfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Only validate POST requests
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                String sessionToken = (String) session.getAttribute(DB.SESSION_CSRF_TOKEN);
                String paramToken = req.getParameter("_token");
                if (sessionToken != null && paramToken != null && !sessionToken.equals(paramToken)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token mismatch");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
